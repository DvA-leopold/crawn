package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.crawn.game.Crawn;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.Vector;


final public class GameWidgetGroup {
    public GameWidgetGroup(final PlayAccount account) {
        final Stage stage = ((Crawn) Gdx.app.getApplicationListener()).getStage();
        final MainInfoWidget userInfo = new MainInfoWidget();
        account.registerRedrawCallback(userInfo);

        final float buttonSize = Gdx.graphics.getHeight() / 10;
        final float highGlobalPos = Gdx.graphics.getHeight() - buttonSize - userInfo.getHeight();

        stage.addActor(userInfo);
        Array<ImageButton> buttonArray = initButtons().getButtons();
        for (int i = 0; i < buttonArray.size; ++i) {
            ImageButton button = buttonArray.get(i);
//            button.getImage().setFillParent(true);
            button.setSize(Gdx.graphics.getWidth() / 3, buttonSize);
            button.setPosition(Gdx.graphics.getWidth() / 3 * i, highGlobalPos);
            stage.addActor(button);
        }

        final ScrollPane scrollPane = new ScrollPane(initContentTable(account.getContentElements()));
        scrollPane.setSize(Gdx.graphics.getWidth(), highGlobalPos);
        stage.addActor(scrollPane);
        stage.setDebugAll(false);
    }

    private ButtonGroup<ImageButton> initButtons() {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final ImageButton homeButton = new ImageButton(skin);
        homeButton.setChecked(true);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                homeButton.setChecked(true);
                System.out.println("home");
            }
        });

        final ImageButton watchButton = new ImageButton(skin);
        watchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                watchButton.setChecked(true);
                System.out.println("watch");
            }
        });

        final ImageButton settingButton = new ImageButton(skin);
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingButton.setChecked(true);
                System.out.println("settings");
            }
        });

        return new ButtonGroup<>(homeButton, watchButton, settingButton);
    }

    private Table initContentTable(final Vector<Content> contentElements) {
        final Table widgetTable = new Table().left();
        for (int i = 0; i < contentElements.size(); ++i) {
            Content contentElement = contentElements.elementAt(i);
            ContentElementWidget contentWidget = new ContentElementWidget(contentElement);
            contentElement.registerRedrawCallback(contentWidget);
            widgetTable.add(contentWidget).left().row();
        }

        return widgetTable;
    }
}
