package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.model.generators.AccountGenerator;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.TreeSet;
import java.util.Vector;

import static com.crawn.game.utils.StaticUtils.MENU_BUTTON_HEIGHT;


public final class GameWidgetGroup extends Table {
    public GameWidgetGroup(final PlayAccount account) {
        setFillParent(true);
        top().left();

        accountsContentPane = initContentScrollPane(account);
        accountsPane = initAccountsScrollPane(AccountGenerator.generateAccounts(15));
        homeWidget = new HomeWidget(account);

        add(new AccountInfoWidget(account)).top().left().row();
        add(initButtonsTable()).row();
        final Stack paneStack = new Stack();
        paneStack.add(accountsContentPane);
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

    private ScrollPane initContentScrollPane(final PlayAccount account) {
        final VerticalGroup contentGroup = new VerticalGroup().columnLeft().left();
        final TreeSet<Content> contentElements = account.getContentElements();
        for (Content contentElement: contentElements) {
            final ContentElementWidget contentWidget = new ContentElementWidget(contentElement);
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
                accountsContentPane.setVisible(true);
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
                accountsContentPane.setVisible(false);
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
                accountsContentPane.setVisible(false);
                accountsPane.setVisible(false);
                homeWidget.setVisible(true);
            }
        });
    }


    final private ScrollPane accountsContentPane;
    final private ScrollPane accountsPane;
    final private HomeWidget homeWidget;

}
