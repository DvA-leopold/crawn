package com.crawn.game.model.accounts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.content.*;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Objects;


final public class MyAccount extends Account {
    public MyAccount(final String nickName, long subscribers, long rating, long money) {
        super(nickName, subscribers, rating, money);
        this.producingContent = new HashSet<>();

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                try {
                    // FIXME concurrent modification throw
                    for (Content content: producingContent) {
                        content.notifyObservers(content.getFinishPercent());
                        content.update();
                    }
                } catch (ConcurrentModificationException err) {
                    Gdx.app.error(getClass().getCanonicalName(), "concurrent modification exception");
                }

                for (Content content: accountContent) {
                    content.recalculateContentRating(subscribers);
                }
            }
        }, 1, 1, Integer.MAX_VALUE);

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                recalculateAccountRating();
            }
        }, 5, 5, Integer.MAX_VALUE);
    }

    public Content produceContent(String title, ContentType contentType, int quality, boolean monetize) {
        int contentPrice = Content.calculateContentPrice(contentType, quality);
        if (contentPrice > money) {
            Gdx.app.log(getClass().getCanonicalName(), "not enought money to produce, price: " + contentPrice + " u have: " + money);
            return null;
        }

        final Content content = createContent(title, contentType, quality, monetize);
        producingContent.add(content);
        Objects.requireNonNull(content).initTask(new Timer.Task() {
            @Override
            public void run() {
                accountContent.add(content);
                producingContent.remove(content);
                money += content.monetize(accountContent);
                notifyObservers(content);
            }
        });
        money -= contentPrice;
        notifyObservers(null);
        return content;
    }

    public void resume(long timeDelay) {

    }

    public HashSet<Content> getProducingContentElements() {
        return producingContent;
    }


    final private HashSet<Content> producingContent;
}
