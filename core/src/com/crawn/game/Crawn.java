package com.crawn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.crawn.game.screens.LoadingScreen;
import com.crawn.game.utils.DebugStatistic;
import com.crawn.game.utils.MusicManager;
import com.crawn.game.utils.resource.manager.ResourceManager;


final public class Crawn extends Game {
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);

		ResourceManager.instance().loadFile("preload_skin/preload_skin.json", true);

		debug = new DebugStatistic(true, true);
		((Game) Gdx.app.getApplicationListener()).setScreen(new LoadingScreen());

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor( 1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.begin();
		super.render();
		batch.end();

		stage.act();
		stage.draw();

		debug.act();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		ResourceManager.instance().dispose();
		MusicManager.instance().dispose();
		batch.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		MusicManager.instance().switchSample(screen.getClass());
	}

	public Batch getMainBatch() {
		return batch;
	}

	public Stage getStage() {
		return stage;
	}


	private Stage stage;
	private SpriteBatch batch;

	private DebugStatistic debug;

}
