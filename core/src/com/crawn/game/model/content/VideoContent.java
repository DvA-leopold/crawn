package com.crawn.game.model.content;

import com.badlogic.gdx.math.MathUtils;

import java.util.Iterator;
import java.util.TreeSet;


final public class VideoContent extends Content {
    public VideoContent(final String title, float growFactor, int quality, boolean withAdds) {
        super(title, growFactor, quality, withAdds);
    }

    @Override
    public void recalculateContentRating(long subscribers) {
        if (growComplete) {
            growFactor = Math.max(0, growFactor - MathUtils.random(growFactor * 0.1f, growFactor * 0.2f));
        } else {
            growFactor += MathUtils.random(growFactor / 5, growFactor / 2);
            growComplete = growFactor > 10;
        }

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
        long howMatchElement = 4, money = 0;
        while (rProducedContent.hasNext() && howMatchElement > 0) {
            Content content = rProducedContent.next();
            money += (content.getViews() / 1000) * 0.85;
            howMatchElement--;
        }

        return money / Math.min(contentElements.size(), 4);
    }

    @Override
    public int getContentProduceTime(int quality) {
        return 20 * Math.max(quality, 1);
    }

    @Override
    public ContentType getType() {
        return ContentType.VIDEO;
    }

    @Override
    public String getImageStyle() {
        return "video_content";
    }
}
