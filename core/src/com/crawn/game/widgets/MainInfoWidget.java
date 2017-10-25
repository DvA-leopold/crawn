package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.utils.resource.manager.ResourceManager;
import com.crawn.game.widgets.callbacks.RedrawMainInfo;


public class MainInfoWidget extends Table implements RedrawMainInfo {
    MainInfoWidget(PlayAccount account) {
        final float avatarSize = Gdx.graphics.getHeight() * 80 / Gdx.graphics.getWidth();
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");

        left();
        setSize(Gdx.graphics.getWidth(), avatarSize);
        final Table addInfo = new Table().left();

        addInfo.setSize(Gdx.graphics.getWidth(), avatarSize);
        addInfo.add(new Label("nick name: " + account.getNickName(), skin)).left().row();
        moneyLabel = new Label("money: " + account.getMoney(), skin);
        addInfo.add(moneyLabel).left().row();
        ratingLabel = new Label("rating: " + account.getRating(), skin);
        addInfo.add(ratingLabel).left();

        add(new ImageButton(skin, "avatar")).size(avatarSize);
        add(addInfo);
        setPosition(0, Gdx.graphics.getHeight() - avatarSize);
        account.registerRedrawCallback(this);
    }

    @Override
    public void redraw(long money, long rating) {
        moneyLabel.setText("money: " + money);
        ratingLabel.setText("rating: " + rating);
    }


    final private Label moneyLabel;
    final private Label ratingLabel;
}
