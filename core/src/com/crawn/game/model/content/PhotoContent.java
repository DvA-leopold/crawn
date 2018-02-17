package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;

import java.util.Iterator;
import java.util.TreeSet;


final public class PhotoContent extends Content {
    public PhotoContent(final String title, float growFactor, int quality, boolean withAdds) {
        super(title, growFactor, quality, withAdds);
    }

    @Override
    public void recalculateContentRating(long subscribers) {
        if (growComplete) {
            growFactor = Math.max(0, growFactor - MathUtils.random(growFactor / 10, growFactor / 3));
        } else {
            growFactor += MathUtils.random(growFactor / 5, growFactor / 2);
            growComplete = growFactor > 11;
        }

        newLikes += MathUtils.random(0, growFactor * subscribers * 0.05f);
        newDislikes += MathUtils.random(0, growFactor * subscribers * 0.01f);
        newViews += MathUtils.random(subscribers * 0.5f * growFactor, growFactor * subscribers);
        newReposts += MathUtils.random(0, growFactor * subscribers * 0.015f);

        notifyObservers(null);
    }

    @Override
    public long monetize(final TreeSet<Content> contentElements) {
        if (!monetize)
            return 0;

        Iterator<Content> rProducedContent = contentElements.iterator();
        long howMatchElement = 10, money = 0;
        while (rProducedContent.hasNext() && howMatchElement > 0) {
            final Content content = rProducedContent.next();
            final int photoRating = (int) (MathUtils.random(0.0f, 1.5f) * (content.getLikes() - content.getDislikes()));
            if (photoRating < 0) {
                newDislikes -= photoRating;
                continue;
            }
            money += (content.getViews() / 1000) * 0.015;
            howMatchElement--;
        }

        return money / Math.min(contentElements.size(), 10);
    }

    @Override
    public int getContentProduceTime(int quality) {
        return 5 * Math.max(1, quality);
    }

    @Override
    public ContentType getType() {
        return ContentType.PHOTO;
    }

    @Override
    public String getImageStyle() {
        return "photo_content";
    }
}
