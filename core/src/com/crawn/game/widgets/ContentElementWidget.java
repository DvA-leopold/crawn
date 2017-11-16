package com.crawn.game.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.resource.manager.ResourceManager;
import com.crawn.game.widgets.callbacks.ProgressUpdater;
import com.crawn.game.widgets.callbacks.RedrawContent;

import static com.crawn.game.utils.StaticUtils.CONTENT_PICK_SIZE;


public final class ContentElementWidget extends Table implements RedrawContent, ProgressUpdater {
    public ContentElementWidget(final Content content) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        contentLabel = new Label(content.getTitle(), skin);
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

        add(contentPicture).size(CONTENT_PICK_SIZE);
        constructMainContentInfo();
    }

    ContentElementWidget(final String contentTitle, final String imageStyle) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        contentLabel = new Label(contentTitle, skin);
        final ImageButton contentPicture = new ImageButton(skin, imageStyle);
        contentProgressBar = new ProgressBar(0, 100, 1, false, skin, "content");
        add(contentPicture).size(CONTENT_PICK_SIZE);
        final Table infoTable = new Table();
        infoTable.add(contentLabel).expandX().center().row();
        infoTable.add(contentProgressBar);
        add(infoTable);
    }

    @Override
    public void redraw(long likes, long dislikes, long views, long reposts) {
        if (isVisible()) {
            this.likes.setText("likes: " + likes);
            this.dislikes.setText("dislikes: " + dislikes);
            this.views.setText("views: " + views);
            this.reposts.setText("reposts: " + reposts);
        }
    }

    @Override
    public void update(long progress) {
        contentProgressBar.setValue(progress);

        if (progress == 100) {
            removeActor(contentProgressBar);
            constructMainContentInfo();
        }
    }

    private void constructMainContentInfo() {
        final Table verticalGroup = new Table();
//        verticalGroup.setFillParent(true);
        verticalGroup.add(contentLabel).expandX().center().row();
        verticalGroup.add(likes).left().row();
        verticalGroup.add(dislikes).left().row();
        verticalGroup.add(views).left().row();
        verticalGroup.add(reposts).left().row();
        add(verticalGroup);
    }


    private ProgressBar contentProgressBar;
    private Label contentLabel;
    private Label likes;
    private Label dislikes;
    private Label views;

    private Label reposts;
}
