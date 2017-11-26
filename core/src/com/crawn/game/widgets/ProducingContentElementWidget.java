package com.crawn.game.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.components.Updatable;
import com.crawn.game.utils.resource.manager.ResourceManager;

import static com.crawn.game.utils.StaticUtils.CONTENT_PICK_SIZE;


public class ProducingContentElementWidget extends Table implements Updatable {
    ProducingContentElementWidget(final Content content) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        contentProgressBar = new ProgressBar(0, 100, 1, false, skin);
        add(new ImageButton(skin, content.getImageStyle())).size(CONTENT_PICK_SIZE);
        final Table infoTable = new Table();
        infoTable.add(new Label(content.getTitle(), skin)).expandX().center().row();
        infoTable.add(contentProgressBar);
        add(infoTable);
    }

    @Override
    public void update(int progress) {
        if (isVisible()) {
            contentProgressBar.setValue(progress);
        }
    }


    final private ProgressBar contentProgressBar;
}