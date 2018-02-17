package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;

import java.util.TreeSet;


final public class MusicContent extends Content {
    public MusicContent(final String title, int growFactor, int quality, boolean withAdds) {
        super(title, growFactor, quality, withAdds);
    }

    @Override
    public void recalculateContentRating(long subscribers) {
        newLikes += MathUtils.random(0, growFactor);
        newDislikes += MathUtils.random(0, growFactor);
        newViews += MathUtils.random(0, growFactor);
        newReposts += MathUtils.random(0, growFactor);

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
