package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Observer;
import com.crawn.game.utils.resource.manager.ResourceManager;

import static com.crawn.game.utils.StaticUtils.CONTENT_PICK_SIZE;


public final class ContentElementWidget extends Table implements Observer {
    ContentElementWidget(final Content content) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final ImageButton contentPicture = new ImageButton(skin, content.getImageStyle());
//        contentPicture.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                content.addViews(1);
//                views.setText("views: " + content.getNewViews());
//            }
//        });

        likes = new Label("likes: " + content.getLikes(), skin);
        dislikes = new Label("dislikes: " + content.getDislikes(), skin);
        views = new Label("views: " + content.getViews(), skin);
        reposts = new Label("reposts: " + content.getReposts(), skin);

        add(contentPicture).size(CONTENT_PICK_SIZE);

        final Table verticalGroup = new Table();
        verticalGroup.add(new Label(content.getTitle(), skin)).expandX().center().row();
        verticalGroup.add(likes).left().row();
        verticalGroup.add(dislikes).left().row();
        verticalGroup.add(views).left().row();
        verticalGroup.add(reposts).left().row();
        add(verticalGroup).width(Gdx.graphics.getWidth() - CONTENT_PICK_SIZE);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (isVisible()) {
            this.likes.setText("likes: " + ((Content) observable).getLikes());
            this.dislikes.setText("dislikes: " + ((Content) observable).getDislikes());
            this.views.setText("views: " + ((Content) observable).getViews());
            this.reposts.setText("reposts: " + ((Content) observable).getReposts());
        }
    }


    final private Label likes;
    final private Label dislikes;
    final private Label views;
    final private Label reposts;
}
