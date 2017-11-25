package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;


final public class PhotoContent extends Content {
    public PhotoContent(final String title, int growFactor, int timeToProduce) {
        super(title, growFactor, timeToProduce);
    }

    @Override
    public void recalculateStatistic() {
        likes.increment(MathUtils.random(0, growFactor));
        dislikes.increment(MathUtils.random(0, growFactor));
        views.increment(MathUtils.random(0, growFactor));
        reposts.increment(MathUtils.random(0, growFactor));

        notifyObservers(null);
    }

    @Override
    public String getImageStyle() {
        return "photo_content";
    }
}
