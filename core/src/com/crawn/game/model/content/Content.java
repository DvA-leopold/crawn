package com.crawn.game.model.content;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Updatable;


public abstract class Content extends Observable implements Comparable {
    Content(final String title, final int growFactor, int timeToProduce) {
        super();
        this.timeToProduce = timeToProduce;
        this.title = title;
        this.growFactor = growFactor;

        this.likes = 0;
        this.dislikes = 0;
        this.views = 0;
        this.reposts = 0;
    }

    public void addUpdatable(Updatable updatableWidget) {
        this.updatableWidget = updatableWidget;
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

    @Override
    public int compareTo(Object o) {
        return -1;
    }

    public int getFinishPercent() {
        return MathUtils.round(timeFromStart / ((float) timeToProduce / 100));
    }

    public abstract void recalculateStatistic();
    public abstract String getImageStyle();

    public void initTask(Timer.Task finishTask) {
        this.finishTask = finishTask;
    }

    public long getLikes() {
        return likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public long getViews() {
        return views;
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


    private int timeToProduce;
    private float timeFromStart;
    int growFactor;

    long likes, newLikes;
    long dislikes, newDislikes;
    long views, newViews;
    long reposts, newReposts;

    final private String title;
    private Timer.Task finishTask;
    private Updatable updatableWidget;
}
