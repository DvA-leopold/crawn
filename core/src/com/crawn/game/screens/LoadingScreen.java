package com.crawn.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.crawn.game.Crawn;
import com.crawn.game.utils.MusicManager;
import com.crawn.game.utils.resource.manager.ResourceManager;


final public class LoadingScreen implements Screen {
    @Override
    public void show() {
        batch = ((Crawn) Gdx.app.getApplicationListener()).getMainBatch();
        ResourceManager.instance().loadFile("game_skin/game_widget_skin.json", false);
        ResourceManager.instance().loadSection("audio", false);
        ResourceManager.instance().loadSection("account_userdata", false);
        ResourceManager.instance().loadSection("i18n", false);
        ResourceManager.instance().loadSection("textures", false);

        progressBar = new ProgressBar(0, 100, 1, true, (Skin) ResourceManager.instance().get("preload_skin/preload_skin.json"));
        progressBar.setSize(progressBar.getWidth(), Gdx.graphics.getHeight() * 0.8f);

        final float x = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.1f;
        final float y = Gdx.graphics.getHeight() * 0.1f;
        progressBar.setPosition(x, y);
    }

    @Override
    public void render(float delta) {
        progressBar.act(delta);
        int progress = (int) (ResourceManager.instance().updateAndGetProgress() * 100);
        progressBar.setValue(progress);

        progressBar.draw(batch, 1);

        if (progress == 100) {
            MusicManager.instance().initialize();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() { }


    private ProgressBar progressBar;
    private Batch batch;
}
