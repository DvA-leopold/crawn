package com.crawn.game.view.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Observer;
import com.crawn.game.model.resource.manager.ResourceManager;

import java.util.HashMap;
import static com.crawn.game.utils.StaticUtils.*;


final public class HomeWidget extends Container<Stack> implements Observer {
    HomeWidget(final MyAccount myAccount) {
        setVisible(false);
        this.producingContentContainer = new HashMap<>();
        for (Content producingElement: myAccount.getProducingContentElements()) {
            producingContentContainer.put(producingElement, new ProducingContentElementWidget(producingElement));
        }

        producingContentPane = initProducingVerticalGroup();
        produceStatisticsMenu = initProduceStatusMenu();
        produceSettingsWidow = new ProduceSettingsWindow(myAccount, this);

        setActor(new Stack(produceStatisticsMenu, produceSettingsWidow));
    }

    @Override
    public void update(Observable observable, Object finishedContent) {
        if (finishedContent instanceof Content) {
            producingContentPane.removeActor(producingContentContainer.remove(finishedContent));
        }
    }

    private Container<Stack> initProduceStatusMenu() {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final Container<Stack> produceStatisticsMenu = new Container<>();
        final ImageButton produceButton = new ImageButton(skin, "produce");
        produceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(false);
                produceSettingsWidow.setVisible(true);
            }
        });

        final Stack statusMenuStack = new Stack();
        statusMenuStack.add(new ScrollPane(producingContentPane));
        statusMenuStack.add(new Container<>(produceButton).bottom().right().pad(BUTTON_PADDING));
        produceStatisticsMenu.size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - MENU_BUTTON_HEIGHT - AVATAR_SIZE).left().top();
        produceStatisticsMenu.setActor(statusMenuStack);
        return produceStatisticsMenu;
    }

    private VerticalGroup initProducingVerticalGroup() {
        final VerticalGroup producingContent = new VerticalGroup().columnLeft().left().reverse();
        if (producingContentContainer != null) {
            for (ProducingContentElementWidget contentWidget: producingContentContainer.values()) {
                producingContent.addActor(contentWidget);
            }
        }
        return producingContent;
    }

    public void setStatMenuVisible(boolean visible) {
        produceStatisticsMenu.setVisible(visible);
    }

    public void addToProduced(Content content, ProducingContentElementWidget contentWidget) {
        producingContentContainer.put(content, contentWidget);
        producingContentPane.addActor(contentWidget);
    }


    final private HashMap<Content, ProducingContentElementWidget> producingContentContainer;
    final private VerticalGroup producingContentPane;

    final private Container<Stack> produceStatisticsMenu;
    final private Container<Window> produceSettingsWidow;
}
