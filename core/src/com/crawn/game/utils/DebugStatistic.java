package com.crawn.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.crawn.game.Crawn;
import com.crawn.game.model.resource.manager.ResourceManager;


final public class DebugStatistic {
    public DebugStatistic(boolean drawFpsStat, boolean drawVersionStat) {
        final Stage stage = ((Crawn) Gdx.app.getApplicationListener()).getStage();
        final Skin skin = (Skin) ResourceManager.instance().get("preload_skin/preload_skin.json");

        fpsLabel = new Label("fps:" + Gdx.graphics.getFramesPerSecond(), skin);
        fpsLabel.setVisible(drawFpsStat);
        fpsLabel.setPosition(10, 10);
        stage.addActor(fpsLabel);

        final Label versionLabel = new Label(getVersion(), skin);
        versionLabel.setVisible(drawVersionStat);
        versionLabel.setPosition(10, fpsLabel.getY() * 4);
        stage.addActor(versionLabel);
    }

    public void act() {
        fpsLabel.setText("fps:" + Gdx.graphics.getFramesPerSecond());
    }

    private String getVersion() {
        FileHandle versionFile = Gdx.files.internal("version");
        String version;
        if (versionFile.exists()) {
            int equalIndex = versionFile.readString().indexOf("\n");
            String stringWithoutInitData = versionFile.readString().substring(equalIndex);
            version = stringWithoutInitData.
                    replace("major=", "v").
                    replace("minor=", ".").
                    replace("patch=", ".").
                    replaceAll("\n", "");
        } else {
            version = "";
        }
        return version;
    }


    final private Label fpsLabel;

}
