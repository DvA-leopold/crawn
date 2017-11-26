package com.crawn.game.model;

import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.content.*;
import com.crawn.game.utils.components.Observable;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.TreeSet;


final public class PlayAccount extends Observable {
    public PlayAccount(final String accountName, long money, long rating) {
        super();
        this.nickName = accountName;
        this.money = money;
        this.rating = rating;
        this.subscribers = 0;
        this.producingContent = new HashSet<>();

        this.accountContent = new TreeSet<>((content1, content2)
                -> Long.compare(content1.getViews(false), content2.getViews(false)) > 0 ? 1 : -1);

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                try {
                    // FIXME, fix concurrent modification throw
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

    public Content produceContent(final String title,
                                  final ContentTypeConverter.ContentType contentType,
                                  int quality) {
        int contentPrice = getContentPrice(contentType, quality);
        if (contentPrice > money) {
            return null;
        }

        final int produceTime = getContentProduceTime(contentType, quality);
        final Content content = createContent(title, contentType, produceTime);
        producingContent.add(content);
        assert content != null;
        content.initTask(new Timer.Task() {
            @Override
            public void run() {
                accountContent.add(content);
                producingContent.remove(content);
                notifyObservers(content);
            }
        });
        money -= contentPrice;
        notifyObservers(null);
        return content;
    }

    public void addContentElement(final Content content) {
        accountContent.add(content);
    }

    public TreeSet<Content> getContentElements() {
        return accountContent;
    }

    public HashSet<Content> getProducingContentElements() {
        return producingContent;
    }

    public String getNickName() {
        return nickName;
    }

    public long getMoney() {
        return money;
    }

    public long getRating() {
        return rating;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public void changeSubscribers(int subscribers) {
        if (this.subscribers + subscribers < 0) {
            this.subscribers = 0;
        } else {
            this.subscribers += subscribers;
        }
    }

    private int getContentPrice(ContentTypeConverter.ContentType contentType, int quality) {
        return 0;
    }

    private int getContentProduceTime(ContentTypeConverter.ContentType contentType, int quality) {
        return 15;
    }

    private Content createContent(String title, ContentTypeConverter.ContentType type, int produceTime) {
        switch (type) {
            case VIDEO:
                return new VideoContent(title, 20, produceTime);
            case MUSIC:
                return new MusicContent(title, 20, produceTime);
            case PHOTO:
                return new PhotoContent(title, 10, produceTime);
        }
        return null;
    }


    private long subscribers;
    private long rating;
    private long money;
    private String nickName;

    final private TreeSet<Content> accountContent;
    final private HashSet<Content> producingContent;
}
