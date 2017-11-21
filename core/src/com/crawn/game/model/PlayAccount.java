package com.crawn.game.model;

import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.content.*;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.widgets.callbacks.RedrawMainInfo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;


final public class PlayAccount extends Observable {
    public PlayAccount(final String accountName, long money, long rating) {
        super();
        this.nickName = accountName;
        this.money = money;
        this.rating = rating;

        this.accountContent = new TreeSet<>(new Comparator<Content>() {
            @Override
            public int compare(Content content1, Content content2) {
                return Long.compare(content1.getViews(false), content2.getViews(false)) > 0 ? 1 : -1;
            }
        });
        this.producingContent = new HashSet<>();

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                for (Content content : accountContent) {
                    content.recalculateStatistic();
                    callback.redraw(PlayAccount.this.money, PlayAccount.this.rating);
                }
            }
        }, 15, 15, Integer.MAX_VALUE);
    }

    public Content produceContent(final String title,
                                  final ContentTypeConverter.ContentType contentType,
                                  int quality) {
        int contentPrice = getContentPrice(contentType, quality);
        if (contentPrice > money) {
            return null;
        }

        final Content content = createContent(title, contentType);
        producingContent.add(content);
        money -= contentPrice;

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                accountContent.add(content);
                producingContent.remove(content);
                notifyObservers(content);
            }
        }, getContentProduceTime(contentType, quality), 0, 0);

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

    public void registerRedrawCallback(RedrawMainInfo callback) {
        this.callback = callback;
    }

    private int getContentPrice(ContentTypeConverter.ContentType contentType, int quality) {
        return 0;
    }

    private int getContentProduceTime(ContentTypeConverter.ContentType contentType, int quality) {
        return 15;
    }

    private Content createContent(String title, ContentTypeConverter.ContentType type) {
        switch (type) {
            case VIDEO:
                return new VideoContent(title, 20);
            case MUSIC:
                return new MusicContent(title, 20);
            case PHOTO:
                return new PhotoContent(title, 10);
        }
        return null;
    }


    private long rating;
    private long money;
    private String nickName;

    final private TreeSet<Content> accountContent;
    final private HashSet<Content> producingContent;

    private RedrawMainInfo callback;
}
