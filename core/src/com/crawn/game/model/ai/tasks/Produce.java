package com.crawn.game.model.ai.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.utils.random.ConstantIntegerDistribution;
import com.badlogic.gdx.ai.utils.random.IntegerDistribution;
import com.crawn.game.model.accounts.VillainAccount;


public class Produce extends LeafTask<VillainAccount> {

    @TaskAttribute
    public IntegerDistribution times = ConstantIntegerDistribution.ONE;


    @Override
    public Status execute() {
        final VillainAccount account = getObject();
        System.out.println("produce");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<VillainAccount> copyTo(Task<VillainAccount> task) {
        return task;
    }
}
