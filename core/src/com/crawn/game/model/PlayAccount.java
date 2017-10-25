package com.crawn.game.model;

import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.content.*;
import com.crawn.game.widgets.callbacks.RedrawMainInfo;

import java.util.Vector;


final public class PlayAccount {
    public PlayAccount(final String accountName) {
        this.nickName = accountName;
        this.money = 0;
        this.rating = 0;

        this.accountContent = new Vector<>();
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                for (Content content: accountContent) {
                    content.recalculateStatistic();
                }
            }
        }, 15, 15, Integer.MAX_VALUE);

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                callback.redraw(money, rating);
            }
        }, 10, 10, 10);
    }

    public boolean produceContent(final String title, final ContentType contentType, int quality) {
        quality = Math.min(Math.abs(quality), 100);
        int contentPrice = getContentPrice(contentType, quality);
        if (contentPrice > money) {
            return false;
        }

        money -= contentPrice;
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                switch (contentType) {
                    case VIDEO:
                        accountContent.add(new VideoContent(title, 20));
                    case MUSIC:
                        accountContent.add(new MusicContent(title, 20));
                    case PHOTO:
                        accountContent.add(new PhotoContent(title, 10));
                }
            }
        }, getContentProduceTime(contentType, quality), 0, 0);

        return true;
    }


    public Content getContentElement(int element) throws ArrayIndexOutOfBoundsException {
        return accountContent.elementAt(element);
    }

    public void addContentElement(final Content content) {
        accountContent.add(content);
    }

    public Vector<Content> getContentElements() {
        return accountContent;
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

    private int getContentPrice(ContentType contentType, int quality) {
        return 0;
    }

    private int getContentProduceTime(ContentType contentType, int quality) {
        return 0;
    }


    private RedrawMainInfo callback;
    private Vector<Content> accountContent;

    private long rating;
    private long money;
    private String nickName;
}
