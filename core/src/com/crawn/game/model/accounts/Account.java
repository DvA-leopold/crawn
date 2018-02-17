package com.crawn.game.model.accounts;

import com.crawn.game.model.content.*;
import com.crawn.game.utils.components.Observable;

import java.util.TreeSet;


public class Account extends Observable {
    Account(String nickName, long subscribers, long rating, long money) {
        this.accountStatistics = new StatisticsManager();
        this.accountContent = new TreeSet<>();
        this.nickName = nickName;
        this.subscribers = subscribers;
        this.rating = rating;
        this.money = money;
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

    Content createContent(String title, ContentType type, int produceTime, boolean withAdds) {
        switch (type) {
            case VIDEO:
                return new VideoContent(title, 20, produceTime, withAdds);
            case MUSIC:
                return new MusicContent(title, 20, produceTime, withAdds);
            case PHOTO:
                return new PhotoContent(title, 10, produceTime, withAdds);
        }
        return null;
    }


    private String nickName;

    long subscribers;
    long rating;
    long money;

    final TreeSet<Content> accountContent;
    final StatisticsManager accountStatistics;

}
