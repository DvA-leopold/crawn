package com.crawn.game.model.ai.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.crawn.game.model.accounts.VillainAccount;


public class Produce extends LeafTask<VillainAccount> {

    @Override
    public Status execute() {
        System.out.println("produce");
        return null;
    }

    @Override
    protected Task<VillainAccount> copyTo(Task<VillainAccount> task) {
        return task;
    }
}
