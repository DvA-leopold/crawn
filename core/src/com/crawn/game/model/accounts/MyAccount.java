package com.crawn.game.model.accounts;

import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.content.*;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Objects;


final public class MyAccount extends Account {
    MyAccount(final String nickName, long subscribers, long rating, long money) {
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
                    System.err.println("concurrent modyfy");
                }

                for (Content content: accountContent) {
                    content.recalculateStatistic();
                }
            }
        }, 1, 1, Integer.MAX_VALUE);
    }

    public Content produceContent(String title, ContentType contentType, int quality, boolean withAdds) {
        int contentPrice = getContentPrice(contentType, quality);
        if (contentPrice > money) {
            return null;
        }

        final int produceTime = getContentProduceTime(contentType, quality);
        final Content content = createContent(title, contentType, produceTime);
        producingContent.add(content);
        Objects.requireNonNull(content).initTask(new Timer.Task() {
            @Override
            public void run() {
                accountContent.add(content);
                producingContent.remove(content);
                if (withAdds) {
                    money += accountStatistics.advertisingMoney();
                }
                notifyObservers(content);
            }
        });
        money -= contentPrice;
        notifyObservers(null);
        return content;
    }

    public HashSet<Content> getProducingContentElements() {
        return producingContent;
    }

    private int getContentPrice(ContentType contentType, int quality) {
        return 0;
    }

    private int getContentProduceTime(ContentType contentType, int quality) {
        return 15;
    }


    final private HashSet<Content> producingContent;
}
