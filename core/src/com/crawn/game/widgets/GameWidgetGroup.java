package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.Vector;


final public class GameWidgetGroup extends Table {
    public GameWidgetGroup(final PlayAccount account) {
        setFillParent(true);
        add(new MainInfoWidget(account)).top().left().row();
        add(initButtonsTable()).row();
        add(initContentScrollPane(account)).width(Gdx.graphics.getWidth());
    }

    private Table initButtonsTable() {
        final int buttonNumber = 3;
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        Table menuButtonsTable = new Table();
        ButtonGroup<ImageButton> group = new ButtonGroup<>();
        for (int i = 0; i < buttonNumber; ++i) {
            final ImageButton menuButton = new ImageButton(skin);
            menuButton.setChecked(true);
            menuButtonsTable.add(menuButton).size(Gdx.graphics.getWidth() / buttonNumber, Gdx.graphics.getHeight() / 10);
            final int finalI = i;
            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    menuButton.setChecked(true);
                    System.out.println("button " + finalI);
                }
            });
            group.add(menuButton);
        }

        return menuButtonsTable;
    }

    private ScrollPane initContentScrollPane(PlayAccount account) {
        final Table widgetTable = new Table().left();
        final Vector<Content> contentElements = account.getContentElements();
        for (int i = 0; i < contentElements.size(); ++i) {
            Content contentElement = contentElements.elementAt(i);
            ContentElementWidget contentWidget = new ContentElementWidget(contentElement);
            contentElement.registerRedrawCallback(contentWidget);
            widgetTable.add(contentWidget).left().row();
        }
        return new ScrollPane(widgetTable);
    }
}
