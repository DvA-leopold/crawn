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
        contentLabel = new Label(content.getTitle(), skin);
        contentPicture = new ImageButton(skin, content.getImageStyle());
        contentPicture.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                content.addViews();
                setViewsLabel(content.getViews(false), "");
            }
        });

        likes = new Label(null, skin);
        setLikesLabel(content.getLikes(false), "");

        dislikes = new Label(null, skin);
        setDislikeLabel(content.getDislikes(false), "");

        views = new Label(null, skin);
        setViewsLabel(content.getViews(false), "");

        reposts = new Label(null, skin);
        setRepostsLabel(content.getReposts(false), "");

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

    public void setLikesLabel(long likes, final String likesPrefix) {
        this.likes.setText("likes: " + likes + likesPrefix);
    }

    public void setDislikeLabel(long dislikes, final String dislikesPrefix) {
        this.dislikes.setText("dislikes: " + dislikes + dislikesPrefix);
    }

    public void setViewsLabel(long views, final String viewsPrefix) {
        this.views.setText("views: " + views + viewsPrefix);
    }

    public void setRepostsLabel(long reposts, final String repostsPrefix) {
        this.reposts.setText("reposts: " + reposts + repostsPrefix);
    }

    @Override
    public void redraw(long likes, long dislikes, long views, long reposts) {
        setLikesLabel(likes, "");
        setDislikeLabel(dislikes, "");
        setViewsLabel(views, "");
        setRepostsLabel(reposts, "");
    }

    final private ImageButton contentPicture;
    final private Label contentLabel;
    final private Label likes;
    final private Label dislikes;
    final private Label views;

    final private Label reposts;
}
