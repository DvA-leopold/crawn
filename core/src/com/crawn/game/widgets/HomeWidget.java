package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Observer;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static com.crawn.game.model.content.ContentTypeConverter.stringToType;
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
        produceSettingsWidow = initProduceSettingsWindow(myAccount);

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

    private Container<Window> initProduceSettingsWindow(final MyAccount myAccount) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");

        final VerticalGroup settingsGroup = new VerticalGroup().columnLeft();
        final TextField contentTitleField = new TextField("title name", skin, "content_title_field");
        settingsGroup.addActor(contentTitleField);
        final SelectBox<String> contentTypeSelectBox = initContentTypeSelectBox(skin);
        settingsGroup.addActor(contentTypeSelectBox);

        final Window produceSettingsWidow = new Window("produce settings", skin, "produce_settings_window");
        final Slider contentQuality = initContentQualitySlider(skin);
        produceSettingsWidow.setModal(true);
        produceSettingsWidow.setMovable(false);
        produceSettingsWidow.add(contentQuality);
        produceSettingsWidow.add(settingsGroup).row();
        final Table buttonGroup = new Table();
        final ImageButton approveContentButton = createApproveContentButton(skin, () -> myAccount.produceContent(
                contentTitleField.getText(),
                stringToType(contentTypeSelectBox.getSelected()),
                (int) contentQuality.getValue(),
                true));
        buttonGroup.add(approveContentButton).size(BUTTON_SIZE).right();
        buttonGroup.add(createDiscardContentButton(skin)).size(BUTTON_SIZE).right();
        produceSettingsWidow.add(buttonGroup).bottom().colspan(2);
        final Container<Window> windowContainer = new Container<>(produceSettingsWidow);
        windowContainer.size(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.5f).center().setVisible(false);
        return windowContainer;
    }

    private VerticalGroup initProducingVerticalGroup() {
        final VerticalGroup producingContent = new VerticalGroup().columnLeft().left().reverse();
        if (producingContentContainer != null) {
            for (ProducingContentElementWidget contentWidget: producingContentContainer.values()) {
                producingContent.addActor(contentWidget);
            }
        }
        producingContent.debug();
        return producingContent;
    }

    private ImageButton createDiscardContentButton(final Skin skin) {
        final ImageButton discardContentButton = new ImageButton(skin, "dont_make_content");
        discardContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(true);
                produceSettingsWidow.setVisible(false);
            }
        });

        return discardContentButton;
    }

    private ImageButton createApproveContentButton(final Skin skin, final Supplier<Content> produceContent) {
        final ImageButton makeContentButton = new ImageButton(skin, "make_content");
        makeContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(true);
                produceSettingsWidow.setVisible(false);
                try {
                    final Content content = produceContent.get();
                    if (content != null) {
                        final ProducingContentElementWidget contentToAdd = new ProducingContentElementWidget(content);
                        producingContentContainer.put(content, contentToAdd);
                        producingContentPane.addActor(contentToAdd);
                        content.addUpdatable(contentToAdd);
                    }
                } catch (final NoSuchElementException what) {
                    System.err.println(what.toString());
                }
            }
        });

        return makeContentButton;
    }

    private SelectBox<String> initContentTypeSelectBox(final Skin skin) {
        final SelectBox<String> contentTypeSelectBox = new SelectBox<>(skin, "content_type_select");
        contentTypeSelectBox.setItems("video", "photo", "music");
//        contentTypeSelectBox.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                System.out.println("item change to: " + ((SelectBox) actor).getSelected());
//            }
//        });
        return contentTypeSelectBox;
    }

    private Slider initContentQualitySlider(final Skin skin) {
        final Slider contentQuality = new Slider(0, 100, 1, true, skin);
        contentQuality.setValue(100);
//        contentQuality.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                System.out.println("slider changed to: " + ((Slider) actor).getValue());
//            }
//        });

        return contentQuality;
    }


    final private HashMap<Content, ProducingContentElementWidget> producingContentContainer;
    final private VerticalGroup producingContentPane;

    final private Container<Stack> produceStatisticsMenu;
    final private Container<Window> produceSettingsWidow;
}
