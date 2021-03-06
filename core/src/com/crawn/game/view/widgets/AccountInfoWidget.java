package com.crawn.game.view.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.crawn.game.model.accounts.Account;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Observer;
import com.crawn.game.model.resource.manager.ResourceManager;

import static com.crawn.game.utils.StaticUtils.AVATAR_SIZE;


final class AccountInfoWidget extends Table implements Observer {
    AccountInfoWidget(final Account account) {
        left().setSize(Gdx.graphics.getWidth(), AVATAR_SIZE);
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");

        final Table addInfo = new Table().left();
        addInfo.setSize(Gdx.graphics.getWidth(), AVATAR_SIZE);
        addInfo.add(new Label("nick name: " + account.getNickName(), skin)).left().row();
        moneyLabel = new Label("money: " + account.getMoney(), skin);
        addInfo.add(moneyLabel).left().row();
        subscribersLabel = new Label("subscribers: " + account.getSubscribers(), skin);
        addInfo.add(subscribersLabel).left().row();
        ratingLabel = new Label("rating: " + account.getRating(), skin);
        addInfo.add(ratingLabel).left();

        add(new ImageButton(skin, "avatar")).size(AVATAR_SIZE);
        add(addInfo);
        setPosition(0, Gdx.graphics.getHeight() - AVATAR_SIZE);
    }

    @Override
    public void update(Observable observable, Object o) {
        moneyLabel.setText("money: " + ((MyAccount) observable).getMoney());
        subscribersLabel.setText("subscribers: " + ((MyAccount) observable).getSubscribers());
        ratingLabel.setText("rating: " + ((MyAccount) observable).getRating());
    }


    final private Label moneyLabel;
    final private Label subscribersLabel;
    final private Label ratingLabel;
}
