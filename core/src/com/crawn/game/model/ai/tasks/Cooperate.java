package com.crawn.game.model.ai.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.crawn.game.model.Model;


public class Cooperate extends LeafTask<Model> {
    @Override
    public Status execute() {
        System.out.println("cooperate");
        return null;
    }

    @Override
    protected Task<Model> copyTo(Task<Model> task) {
        return task;
    }
}
