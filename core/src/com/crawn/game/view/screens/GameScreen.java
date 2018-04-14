package com.crawn.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.Crawn;
import com.crawn.game.model.Model;
import com.crawn.game.model.resource.manager.ResourceManager;
import com.crawn.game.view.widgets.GameWidgetGroup;


final public class GameScreen implements Screen {
    GameScreen() {
        batch = ((Crawn) Gdx.app.getApplicationListener()).getMainBatch();
        background = new Sprite((Texture) ResourceManager.instance().get("textures/bg.jpg"));
        model = new Model();
    }

    @Override
    public void show() {
        ((Crawn) Gdx.app.getApplicationListener()).getStage().addActor(new GameWidgetGroup(model));
    }

    @Override
    public void render(float delta) {
        batch.disableBlending();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        timerDelay = TimeUtils.millis();
        Timer.instance().stop();
    }

    @Override
    public void resume() {
        Timer.instance().delay(TimeUtils.timeSinceMillis(timerDelay));
        model.resume(timerDelay);
        Timer.instance().start();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        model.dispose();
    }


    private long timerDelay;

    final private Sprite background;
    final private Batch batch;

    final private Model model;
}
