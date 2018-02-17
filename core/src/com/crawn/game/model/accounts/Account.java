package com.crawn.game.model.accounts;

import com.badlogic.gdx.math.MathUtils;
import com.crawn.game.model.content.*;
import com.crawn.game.utils.components.Observable;

import java.util.TreeSet;


public class Account extends Observable {
    Account(String nickName, long subscribers, long rating, long money) {
        this.accountContent = new TreeSet<>();
        this.nickName = nickName;
        this.subscribers = subscribers;
        this.rating = rating;
        this.money = money;
    }

    void recalculateSubscribers(long newLikes, long newDislikes, long newReposts) {
        subscribers = (long) (MathUtils.random(0.1f, 3.3f) * (newReposts * 0.5 * (newLikes - newDislikes)));
    }

    void recalculateRating(long newLikes, long newDislikes, long newReposts) {
        rating = (long) (MathUtils.random(0.5f, 5.5f) * (newReposts * 0.5 * (newLikes - newDislikes)));
    }

    Content createContent(String title, ContentType type, int quality, boolean monetize) {
        switch (type) {
            case VIDEO:
                return new VideoContent(title, 20, quality, monetize);
            case MUSIC:
                return new MusicContent(title, 20, quality, monetize);
            case PHOTO:
                return new PhotoContent(title, 10, quality, monetize);
        }
        return null;
    }

    public String getNickName() {
        return nickName;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public long getRating() {
        return rating;
    }

    public long getMoney() {
        return money;
    }

    public TreeSet<Content> getContentElements() {
        return accountContent;
    }


    private String nickName;

    private long subscribers;
    private long rating;
    long money;

    final TreeSet<Content> accountContent;

}
