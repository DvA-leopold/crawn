package com.crawn.game.model.ai.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.crawn.game.model.Model;


public class Analyze extends LeafTask<Model> {
    @Override
    public Status execute() {
        System.out.println("analyze");
        if (urgentProb > 1)
            return Status.SUCCEEDED;
        else
            return Status.FAILED;
    }

    @Override
    protected Task<Model> copyTo(Task<Model> task) {
        return task;
    }


    @TaskAttribute(required = true)
    public float urgentProb = 0.8f;

}
