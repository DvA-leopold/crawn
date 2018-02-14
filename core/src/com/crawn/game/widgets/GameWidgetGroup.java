package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.crawn.game.model.accounts.AccountManager;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.accounts.VillainAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.TreeSet;
import java.util.Vector;

import static com.crawn.game.utils.StaticUtils.MENU_BUTTON_HEIGHT;


final public class GameWidgetGroup extends Table {
    public GameWidgetGroup(final AccountManager accountManager) {
        setFillParent(true);
        top().left();

        accountContentPane = initContentScrollPane(accountManager.getAccount());
        accountsPane = initAccountsScrollPane(accountManager.getVillainAccounts());
        accountInfoWidget = new AccountInfoWidget(accountManager.getAccount());
        homeWidget = new HomeWidget(accountManager.getAccount());

        accountManager.getAccount().addObserver(homeWidget);
        accountManager.getAccount().addObserver(accountContentPane);
        accountManager.getAccount().addObserver(accountInfoWidget);

        add(accountInfoWidget).top().left().row();
        add(initButtonsTable()).row();
        final Stack paneStack = new Stack();
        paneStack.add(accountContentPane);
        paneStack.add(accountsPane);
        paneStack.add(homeWidget);
        add(paneStack).width(Gdx.graphics.getWidth());
    }

    private Table initButtonsTable() {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final Table menuButtonsTable = new Table();
        final ButtonGroup<ImageButton> settingsButtonGroup = new ButtonGroup<>();
        settingsButtonGroup.setMinCheckCount(0);

        for (int i = 0; i < 3; ++i) {
            final ImageButton menuButton = new ImageButton(skin);
            // FIXME button size is proportional, image height and width wont change independently
            menuButtonsTable.add(menuButton).size(Gdx.graphics.getWidth() / 3, MENU_BUTTON_HEIGHT);
            settingsButtonGroup.add(menuButton);
        }
        initButtonsListeners(settingsButtonGroup.getButtons());

        return menuButtonsTable;
    }

    private ContentScrollPane initContentScrollPane(final MyAccount account) {
        final VerticalGroup contentGroup = new VerticalGroup().columnLeft().left().reverse();
        final TreeSet<Content> contentElements = account.getContentElements();
        for (Content contentElement: contentElements) {
            final ContentElementWidget contentWidget = new ContentElementWidget(contentElement);
            contentElement.addObserver(contentWidget);
            contentGroup.addActor(contentWidget);
        }
        return new ContentScrollPane(contentGroup);
    }

    private ScrollPane initAccountsScrollPane(final Vector<VillainAccount> userAccounts) {
        final VerticalGroup accountsGroup = new VerticalGroup().columnLeft().left();
        for (VillainAccount account: userAccounts) {
            accountsGroup.addActor(new AccountInfoWidget(account));
        }
        final ScrollPane accountsScrollPane = new ScrollPane(accountsGroup);
        accountsScrollPane.setVisible(false);
        return accountsScrollPane;
    }

    private void initButtonsListeners(final Array<ImageButton> buttons) {
        buttons.get(0).setChecked(true);
        buttons.get(0).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttons.get(0).isChecked()) {
                    buttons.get(0).setChecked(true);
                    return;
                }
                accountContentPane.setVisible(true);
                accountsPane.setVisible(false);
                homeWidget.setVisible(false);
            }
        });

        buttons.get(1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttons.get(1).isChecked()) {
                    buttons.get(1).setChecked(true);
                    return;
                }
                accountContentPane.setVisible(false);
                accountsPane.setVisible(true);
                homeWidget.setVisible(false);
            }
        });

        buttons.get(2).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttons.get(2).isChecked()) {
                    buttons.get(2).setChecked(true);
                    return;
                }
                accountContentPane.setVisible(false);
                accountsPane.setVisible(false);
                homeWidget.setVisible(true);
            }
        });
    }


    final private ContentScrollPane accountContentPane;
    final private ScrollPane accountsPane;
    final private HomeWidget homeWidget;
    final private AccountInfoWidget accountInfoWidget;
}
