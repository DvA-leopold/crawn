package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.resource.manager.ResourceManager;
import com.crawn.game.widgets.callbacks.RedrawContent;


class ContentElementWidget extends Table implements RedrawContent {
    ContentElementWidget(final Content content) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final Label contentLabel = new Label(content.getTitle(), skin);
        final ImageButton contentPicture = new ImageButton(skin, content.getImageStyle());
        contentPicture.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                content.addViews();
                views.setText("views: " + content.getViews(false));
            }
        });

        likes = new Label("likes: " + content.getLikes(false), skin);
        dislikes = new Label("dislikes: " + content.getDislikes(false), skin);
        views = new Label("views: " + content.getViews(false), skin);
        reposts = new Label("reposts: " + content.getReposts(false), skin);

        final float pickSize = Gdx.graphics.getHeight() * 90 / Gdx.graphics.getWidth();
        add(contentPicture).size(pickSize);
        final Table verticalGroup = new Table();
        verticalGroup.setFillParent(true);
        verticalGroup.add(contentLabel).expandX().center().row();
        verticalGroup.add(likes).left().row();
        verticalGroup.add(dislikes).left().row();
        verticalGroup.add(views).left().row();
        verticalGroup.add(reposts).left().row();
        add(verticalGroup);
    }

    @Override
    public void redraw(long likes, long dislikes, long views, long reposts) {
        this.likes.setText("likes: " + likes);
        this.dislikes.setText("dislikes: " + likes);
        this.views.setText("views: " + views);
        this.reposts.setText("reposts: " + reposts);
    }

    final private Label likes;
    final private Label dislikes;
    final private Label views;

    final private Label reposts;
}
