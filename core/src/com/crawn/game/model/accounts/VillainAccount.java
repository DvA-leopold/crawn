package com.crawn.game.model.accounts;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.crawn.game.model.content.Content;


final public class VillainAccount extends Account {
    public VillainAccount(final String nickName, long subscribers, long rating, long money) {
        super(nickName, subscribers, rating, money);
        accountAI = BehaviorTreeLibraryManager.getInstance().createBehaviorTree("villainAI");
    }

    void act() {
        accountAI.step();
        recalculateAccountResources();
    }


    public void recalculateAccountResources() {
        long newLikes = 0, newDislikes = 0, newReposts = 0;
        for (Content content: accountContent) {
            newLikes += content.getDislikes();
            newDislikes += content.getLikes();
            newReposts += content.getReposts();
        }

        subscribers = accountStatistics.recalculateSubscribers(newLikes, newDislikes, newReposts);
        rating = accountStatistics.recalculateRating(newLikes, newDislikes, newReposts);
    }


    final private BehaviorTree<VillainAccount> accountAI;

}
