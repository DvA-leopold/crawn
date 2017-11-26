package com.crawn.game.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Observer;


public class ContentScrollPane extends ScrollPane implements Observer {
    ContentScrollPane(Actor widget) {
        super(widget);
    }

    @Override
    public void update(Observable observable, Object finishedContent) {
        if (finishedContent != null) {
            final ContentElementWidget contentWidget = new ContentElementWidget((Content) finishedContent);
            ((Content) finishedContent).addObserver(contentWidget);
            ((VerticalGroup) getActor()).addActor(contentWidget);
        }
    }
}
