package com.crawn.game.model.ai.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.crawn.game.model.accounts.VillainAccount;


public class Strike extends LeafTask<VillainAccount> {

    @Override
    public Status execute() {
        System.out.println("strike");
        return Status.RUNNING;
    }

    @Override
    protected Task<VillainAccount> copyTo(Task<VillainAccount> task) {
        return null;
    }
}
