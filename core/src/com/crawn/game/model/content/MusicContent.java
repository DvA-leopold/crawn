package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;


final public class MusicContent extends Content {
    public MusicContent(final String title, int growFactor) {
        super(title, growFactor);
    }

    @Override
    public void recalculateStatistic() {
        likes.increment(MathUtils.random(0, growFactor));
        dislikes.increment(MathUtils.random(0, growFactor));
        views.increment(MathUtils.random(0, growFactor));
        reposts.increment(MathUtils.random(0, growFactor));

        callback.redraw(likes.getValue(), dislikes.getValue(), views.getValue(), reposts.getValue());
        growFactor--;
    }

    @Override
    public String getImageStyle() {
        return "music_content";
    }
}
