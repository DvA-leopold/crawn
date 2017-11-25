package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;


final public class MusicContent extends Content {
    public MusicContent(final String title, int growFactor, int timeToProduce) {
        super(title, growFactor, timeToProduce);
    }

    @Override
    public void recalculateStatistic() {
        likes.increment(MathUtils.random(0, growFactor));
        dislikes.increment(MathUtils.random(0, growFactor));
        views.increment(MathUtils.random(0, growFactor));
        reposts.increment(MathUtils.random(0, growFactor));

        notifyObservers(null);
        growFactor--;
    }

    @Override
    public String getImageStyle() {
        return "music_content";
    }
}
