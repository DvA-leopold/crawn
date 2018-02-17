package com.crawn.game.model.content;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Updatable;

import java.util.TreeSet;


public abstract class Content extends Observable implements Comparable {
    Content(final String title, int growFactor, int quality, boolean monetize) {
        this.title = title;
        this.growFactor = growFactor;
        this.monetize = monetize;
        this.timeToProduce = getContentProduceTime(quality);
    }

    public void update() {
        timeFromStart++;
        final int finishPercent = MathUtils.round(timeFromStart / ((float) timeToProduce / 100));
        updatableWidget.update(finishPercent);
        if (finishPercent >= 100) {
            timeToProduce = 0;
            finishTask.run();
            finishTask = null;
        }
    }

    public static int calculateContentPrice(ContentType contentType, int quality) {
        switch (contentType) {
            case PHOTO:
                return 20 * quality;
            case MUSIC:
                return 30 * quality;
            case VIDEO:
                return 40 * quality;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public int getFinishPercent() {
        return MathUtils.round(timeFromStart / ((float) timeToProduce / 100));
    }

    public abstract String getImageStyle();
    public abstract ContentType getType();
    public abstract int getContentProduceTime(int quality);
    public abstract void recalculateContentRating(long subscribers);
    public abstract long monetize(final TreeSet<Content> rProducedContent);

    public void initTask(Timer.Task finishTask) {
        this.finishTask = finishTask;
    }
    public void addUpdatable(Updatable updatableWidget) {
        this.updatableWidget = updatableWidget;
    }

    @Override
    public int compareTo(Object o) {
        return -1;
    }

    public long getNewLikes() {
        long tmpLikes = newLikes;
        likes += newLikes;
        newLikes = 0;
        return tmpLikes;
    }

    public long getLikes() {
        return likes;
    }

    public long getNewDislikes() {
        long tmpDislikes = newDislikes;
        dislikes += newDislikes;
        newDislikes = 0;
        return tmpDislikes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public long getNewViews() {
        long tmpViews = newViews;
        views += newViews;
        newViews = 0;
        return tmpViews;
    }

    public long getViews() {
        return views;
    }

    public long getNewReposts() {
        long tmpReposts = newReposts;
        reposts += newReposts;
        newReposts = 0;
        return tmpReposts;
    }

    public long getReposts() {
        return reposts;
    }

    public void addViews(int value) {
        views += value;
    }

    public String getTitle() {
        return title;
    }


    private Timer.Task finishTask;
    private Updatable updatableWidget;

    private int timeToProduce;
    private float timeFromStart;

    int growFactor;
    final boolean monetize;

    long likes, newLikes;
    long dislikes, newDislikes;
    long views, newViews;
    long reposts, newReposts;

    final private String title;

}
