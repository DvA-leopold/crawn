package com.crawn.game.model.content;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Updatable;


public abstract class Content extends Observable {
    Content(final String title, final int growFactor, int timeToProduce) {
        super();
        this.timeToProduce = timeToProduce;
        this.title = title;
        this.growFactor = growFactor;

        this.likes = new Statistics(0);
        this.dislikes = new Statistics(0);
        this.views = new Statistics(0);
        this.reposts = new Statistics(0);
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

    public int getFinishPercent() {
        return MathUtils.round(timeFromStart / ((float) timeToProduce / 100));
    }

    public abstract void recalculateStatistic();
    public abstract String getImageStyle();

    public void initTask(Timer.Task finishTask) {
        this.finishTask = finishTask;
    }

    public long getLikes(boolean byModule) {
        if (byModule) {
            return likes.getValueByModule();
        }
        return likes.getValue();
    }

    public long getDislikes(boolean byModule) {
        if (byModule) {
            return dislikes.getValueByModule();
        }
        return dislikes.getValue();
    }

    public long getViews(boolean byModule) {
        if (byModule) {
            return views.getValueByModule();
        }
        return views.getValue();
    }

    public long getReposts(boolean byModule) {
        if (byModule) {
            return reposts.getValueByModule();
        }
        return reposts.getValue();
    }

    public void addViews() {
        views.increment(1);
    }

    public String getTitle() {
        return title;
    }


    private int timeToProduce;
    private float timeFromStart;
    protected int growFactor;

    final protected Statistics likes;
    final protected Statistics dislikes;
    final protected Statistics views;
    final protected Statistics reposts;

    final private String title;
    private Timer.Task finishTask;
    private Updatable updatableWidget;
}
