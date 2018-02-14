package com.crawn.game.model.ai.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.crawn.game.model.accounts.VillainAccount;


public class Analyze extends LeafTask<VillainAccount> {

    @TaskAttribute(required = true)
    public float urgentProb = 0.8f;

    @Override
    public Status execute() {
        System.out.println("analyze");
        if (urgentProb > 1)
            return Status.SUCCEEDED;
        else
            return Status.FAILED;
    }

    @Override
    protected Task<VillainAccount> copyTo(Task<VillainAccount> task) {
        return null;
    }
}
