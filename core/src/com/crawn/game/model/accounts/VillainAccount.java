package com.crawn.game.model.accounts;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.model.Model;


final public class VillainAccount extends Account {
    public VillainAccount(Model model, String nickName, long subscribers, long rating, long money) {
        super(nickName, subscribers, rating, money);
        accountAI = BehaviorTreeLibraryManager.getInstance().createBehaviorTree("villainAI", model);

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                accountAI.step();
                recalculateAccountRating();
            }
        }, 1, 5, Integer.MAX_VALUE);
    }

    public void resume(long timeDelay) {

    }


    final private BehaviorTree<Model> accountAI;
}
