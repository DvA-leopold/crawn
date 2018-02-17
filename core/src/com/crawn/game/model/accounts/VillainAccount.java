package com.crawn.game.model.accounts;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.content.Content;


final public class VillainAccount extends Account {
    public VillainAccount(final String nickName, long subscribers, long rating, long money) {
        super(nickName, subscribers, rating, money);
        accountAI = BehaviorTreeLibraryManager.getInstance().createBehaviorTree("villainAI");

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                accountAI.step();
                recalculateAccountResources();
            }
        }, 1, 5, Integer.MAX_VALUE);
    }


    public void recalculateAccountResources() {
        long newLikes = 0, newDislikes = 0, newReposts = 0;
        for (Content content: accountContent) {
            newLikes += content.getDislikes();
            newDislikes += content.getLikes();
            newReposts += content.getReposts();
        }

        subscribers += accountStatistics.recalculateSubscribers(newLikes, newDislikes, newReposts);
        rating += accountStatistics.recalculateRating(newLikes, newDislikes, newReposts);
    }

    public void resume(long timeDelay) {

    }


    final private BehaviorTree<VillainAccount> accountAI;

}
