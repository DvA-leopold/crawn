package com.crawn.game.model.content;


import com.crawn.game.widgets.callbacks.RedrawContent;

public abstract class Content {
    Content(final String title, final int growFactor) {
        this.title = title;
        this.growFactor = growFactor;

        this.likes = new Statistics(0);
        this.dislikes = new Statistics(0);
        this.views = new Statistics(0);
        this.reposts = new Statistics(0);
    }

    public abstract void recalculateStatistic();
    public abstract String getImageStyle();

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

    public String getlikesPrefix() {
        return likes.getPrefix();
    }

    public String getdislikePrefix() {
        return dislikes.getPrefix();
    }

    public String getViewsPrefix() {
        return views.getPrefix();
    }

    public String getRepostsPrefix() {
        return reposts.getPrefix();
    }

    public String getTitle() {
        return title;
    }

    public void registerRedrawCallback(RedrawContent redrawContent) {
        callback = redrawContent;
    }


    protected RedrawContent callback;

    protected int growFactor;

    final protected Statistics likes;
    final protected Statistics dislikes;
    final protected Statistics views;
    final protected Statistics reposts;

    final private String title;
}
