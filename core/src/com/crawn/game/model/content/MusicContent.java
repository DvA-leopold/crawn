package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;

import java.util.TreeSet;


final public class MusicContent extends Content {
    public MusicContent(final String title, float growFactor, int quality, boolean withAdds) {
        super(title, growFactor, quality, withAdds);
    }

    @Override
    public void recalculateContentRating(long subscribers) {
        if (growComplete) {
            growFactor = Math.max(0, growFactor - MathUtils.random(growFactor / 10, growFactor / 3));
        } else {
            growFactor += MathUtils.random(growFactor / 5, growFactor / 2);
            growComplete = growFactor > 16;
        }

        newLikes += MathUtils.random(0, Math.max(growFactor * subscribers * 0.05f, 10));
        newDislikes += MathUtils.random(0, Math.max(growFactor * subscribers * 0.01f, 10));
        views += MathUtils.random(growFactor * subscribers * 10, Math.max(growFactor * subscribers, 10) * 10);
        newReposts += MathUtils.random(0, Math.max(growFactor * subscribers * 0.015f + newLikes * 0.01f, 10));

        notifyObservers(null);
    }

    @Override
    public long monetize(final TreeSet<Content> contentElements) {
        if (!monetize)
            return 0;

        long money = 0;
        for (Content content: contentElements) {
            if (content.getType() == ContentType.MUSIC) {
                money += (content.getLikes() - content.getDislikes()) * MathUtils.random(0.5f, 3.5f);
            }
        }
        return money;
    }

    @Override
    public int getContentProduceTime(int quality) {
        return 13 * Math.max(quality, 1);
    }

    @Override
    public ContentType getType() {
        return ContentType.MUSIC;
    }

    @Override
    public String getImageStyle() {
        return "music_content";
    }
}
