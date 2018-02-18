package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.accounts.VillainAccount;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.Vector;

import static com.crawn.game.utils.StaticUtils.MENU_BUTTON_HEIGHT;


final public class GameWidgetGroup extends Table {
    public GameWidgetGroup(final MyAccount myAccount, final Vector<VillainAccount> villainAccounts) {
        setFillParent(true);
        top().left();

        accountContentPane = new ElementScrollPane(myAccount);
        villainAccountsPane = new ElementScrollPane(villainAccounts);
        accountInfoWidget = new AccountInfoWidget(myAccount);
        homeWidget = new HomeWidget(myAccount);

        myAccount.addObserver(homeWidget);
        myAccount.addObserver(accountContentPane);
        myAccount.addObserver(accountInfoWidget);

        add(accountInfoWidget).top().left().row();
        add(initButtonsTable()).row();
        final Stack paneStack = new Stack();
        paneStack.add(accountContentPane);
        paneStack.add(villainAccountsPane);
        paneStack.add(homeWidget);
        add(paneStack).width(Gdx.graphics.getWidth());
    }

    private Table initButtonsTable() {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final Table menuButtonsTable = new Table();
        final ButtonGroup<ImageButton> settingsButtonGroup = new ButtonGroup<>();
        settingsButtonGroup.setMinCheckCount(1);

        for (int i = 0; i < 3; ++i) {
            final ImageButton menuButton = new ImageButton(skin);
            // FIXME button size is proportional, image height and width wont change independently
            menuButtonsTable.add(menuButton).size(Gdx.graphics.getWidth() / 3, MENU_BUTTON_HEIGHT);
            settingsButtonGroup.add(menuButton);
            int fi = i;
            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!menuButton.isChecked()) {
                        menuButton.setChecked(true);
                        return;
                    }
                    accountContentPane.setVisible(fi==0);
                    villainAccountsPane.setVisible(fi==1);
                    homeWidget.setVisible(fi==2);
                }
            });
        }
        return menuButtonsTable;
    }


    final private ElementScrollPane accountContentPane;
    final private ScrollPane villainAccountsPane;
    final private HomeWidget homeWidget;
    final private AccountInfoWidget accountInfoWidget;
}
