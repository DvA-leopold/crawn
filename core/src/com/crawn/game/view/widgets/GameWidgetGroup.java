package com.crawn.game.view.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.Model;
import com.crawn.game.model.resource.manager.ResourceManager;

import static com.crawn.game.utils.StaticUtils.MENU_BUTTON_HEIGHT;


final public class GameWidgetGroup extends Table {
    public GameWidgetGroup(final Model model) {
        setFillParent(true);
        top().left();

        AccountInfoWidget mainAccountInfo = new AccountInfoWidget(model.getMyAccount());
        accountContentPane = new ElementScrollPane(model.getMyAccount());
        villainAccountsPane = new ElementScrollPane(model.getVillainAccounts());
        homeWidget = new HomeWidget(model.getMyAccount());

        model.getMyAccount().addObserver(mainAccountInfo);
        model.getMyAccount().addObserver(accountContentPane);
        model.getMyAccount().addObserver(homeWidget);

        add(mainAccountInfo).top().left().row();
        add(initButtonsTable()).row();
        final Stack paneStack = new Stack();
        paneStack.add(accountContentPane);
        paneStack.add(villainAccountsPane);
        paneStack.add(homeWidget);
        add(paneStack).width(Gdx.graphics.getWidth());
    }

    private Table initButtonsTable() {
        final Table menuButtonsTable = new Table();
        final ButtonGroup<ImageButton> settingsButtonGroup = new ButtonGroup<>();
        settingsButtonGroup.setMinCheckCount(1);

        for (int i = 0; i < 3; ++i) {
            final ImageButton menuButton = new ImageButton((Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json"));
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
}
