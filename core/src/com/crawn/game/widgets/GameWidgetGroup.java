package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.Vector;


public final class GameWidgetGroup extends Table {
    public GameWidgetGroup(final PlayAccount account) {
        setFillParent(true);
        top().left();

        myAccountInfoWidget = new AccountInfoWidget(account);
        settingsButtonTable = initButtonsTable();
        accountsContentPane = initContentScrollPane(account);

        // FIXME tests only
        Vector<PlayAccount> accounts = new Vector<>();
        for (int i = 0; i < 10; ++i) {
            accounts.add(new PlayAccount("test" + i, 1233 * i, 444 * i));
        }
        accountsPane = initAccountsScrollPane(accounts);
        // FIXME tests only

        add(myAccountInfoWidget).top().left().row();
        add(settingsButtonTable).row();
        final Stack paneStack = new Stack();
        paneStack.add(accountsContentPane);
        paneStack.add(accountsPane);
        add(paneStack).width(Gdx.graphics.getWidth());
    }

    private Table initButtonsTable() {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final Table menuButtonsTable = new Table();
        final ButtonGroup<ImageButton> settingsButtonGroup = new ButtonGroup<>();
        settingsButtonGroup.setMinCheckCount(0);

        for (int i = 0; i < 3; ++i) {
            final ImageButton menuButton = new ImageButton(skin);
            menuButtonsTable.add(menuButton).size(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 10);
            settingsButtonGroup.add(menuButton);
        }
        initButtonsListeners(settingsButtonGroup.getButtons());

        return menuButtonsTable;
    }

    private ScrollPane initContentScrollPane(PlayAccount account) {
        final VerticalGroup contentGroup = new VerticalGroup().columnLeft().left();
        final Vector<Content> contentElements = account.getContentElements();
        for (int i = 0; i < contentElements.size(); ++i) {
            Content contentElement = contentElements.elementAt(i);
            ContentElementWidget contentWidget = new ContentElementWidget(contentElement);
            contentElement.registerRedrawCallback(contentWidget);
            contentGroup.addActor(contentWidget);
        }
        return new ScrollPane(contentGroup);
    }

    private ScrollPane initAccountsScrollPane(Vector<PlayAccount> userAccounts) {
        final VerticalGroup accountsGroup = new VerticalGroup().columnLeft().left();
        for (PlayAccount account: userAccounts) {
            accountsGroup.addActor(new AccountInfoWidget(account));
        }
        ScrollPane accountsScrollPane = new ScrollPane(accountsGroup);
        accountsScrollPane.setVisible(false);
        return accountsScrollPane;
    }

    private void initButtonsListeners(final Array<ImageButton> buttons) {
        buttons.get(0).setChecked(true);
        buttons.get(0).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buttons.get(0).isChecked()) {
                    return;
                }
                buttons.get(0).setChecked(true);
                accountsPane.setVisible(false);
                accountsContentPane.setVisible(true);
            }
        });

        buttons.get(1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttons.get(1).isChecked()) {
                    return;
                }
                buttons.get(1).setChecked(true);
                accountsPane.setVisible(true);
                accountsContentPane.setVisible(false);
            }
        });
//
//
//        buttons.get(2).addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (buttons.get(2).isChecked()) {
//                    return;
//                }
//                buttons.get(2).setChecked(true);
//                myAccountInfoWidget.setVisible(true);
//                accountsPane.setVisible(false);
//            }
//        });
    }


    final private AccountInfoWidget myAccountInfoWidget;
    final private Table settingsButtonTable;

    final private ScrollPane accountsPane;
    private ScrollPane accountsContentPane;

}
