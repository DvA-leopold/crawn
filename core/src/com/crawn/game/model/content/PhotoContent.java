package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;

import java.util.Iterator;
import java.util.TreeSet;


final public class PhotoContent extends Content {
    public PhotoContent(final String title, int growFactor, int quality, boolean withAdds) {
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

        Iterator<Content> rProducedContent = contentElements.iterator();
        long howMuchElement = 10, money = 0;
        while (rProducedContent.hasNext() && howMuchElement > 0) {
            final Content content = rProducedContent.next();
            final int photoRating = (int) (MathUtils.random(0.0f, 1.5f) * (content.getLikes() - content.getDislikes()));
            if (photoRating < 0) {
                newDislikes -= photoRating;
                continue;
            }
            money += (content.getViews() / 1000) * 0.015;
            howMuchElement--;
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
