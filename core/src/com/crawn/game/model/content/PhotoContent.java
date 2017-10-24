package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;

final public class PhotoContent extends Content {
    public PhotoContent(final String title, final int factor) {
        super(title, factor);
    }

    @Override
    public void recalculateStatistic() {
        likes.increment(MathUtils.random(0, growFactor));
        dislikes.increment(MathUtils.random(0, growFactor));
        views.increment(MathUtils.random(0, growFactor));
        reposts.increment(MathUtils.random(0, growFactor));

        callback.redraw(likes.getValue(), dislikes.getValue(), views.getValue(), reposts.getValue());
    }

    @Override
    public String getImageStyle() {
        return "photo_content";
    }
}
