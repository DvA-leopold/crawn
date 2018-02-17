package com.crawn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.crawn.game.Crawn;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.accounts.VillainAccount;
import com.crawn.game.utils.resource.manager.ResourceManager;
import com.crawn.game.widgets.GameWidgetGroup;

import java.util.Vector;


final public class GameScreen implements Screen {
    GameScreen() {
        batch = ((Crawn) Gdx.app.getApplicationListener()).getMainBatch();
        background = new Sprite((Texture) ResourceManager.instance().get("textures/bg.jpg"));
        myAccount = new MyAccount("awesome acc", 0 , 0, 100000);
        villainAccounts = new Vector<>();
    }

    @Override
    public void show() {
        ((Crawn) Gdx.app.getApplicationListener()).getStage().addActor(new GameWidgetGroup(myAccount, villainAccounts));
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
        Timer.instance().delay(TimeUtils.millis() - timerDelay);
        myAccount.resume(timerDelay);
        for (VillainAccount villainAccount: villainAccounts) {
            villainAccount.resume(timerDelay);
        }
        Timer.instance().start();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() { }


    private long timerDelay;

    final private Sprite background;
    final private Batch batch;

    final private MyAccount myAccount;
    final private Vector<VillainAccount> villainAccounts;

}
