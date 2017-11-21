package com.crawn.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.PlayAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.model.content.ContentTypeConverter;
import com.crawn.game.utils.resource.manager.ResourceManager;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static com.crawn.game.model.content.ContentTypeConverter.stringToType;
import static com.crawn.game.utils.StaticUtils.*;


final public class HomeWidget extends Container<Stack> {
    HomeWidget(final PlayAccount playAccount) {
        setVisible(false);
        this.producingContentContainer = new HashMap<>();
        for (Content producingElement: playAccount.getProducingContentElements()) {
            producingContentContainer.put(producingElement, new ContentElementWidget(producingElement));
        }

        producingContentPane = initProducingVerticalGroup();
        producingContentPane.setDebug(true);
        produceStatisticsMenu = initProduceStatusMenu();
        produceSettingsWidow = initProduceSettingsWindow(playAccount);

        setActor(new Stack(produceStatisticsMenu, produceSettingsWidow));
    }


    public void act(long progress) {
        if (produceStatisticsMenu.isVisible()) {
            for (ContentElementWidget contentWidget: producingContentContainer.values()) {
                contentWidget.update(progress);
            }
        }
    }

    public void removeProducingContent(final Content content) {
        final ContentElementWidget contentToRemove = producingContentContainer.remove(content);
        producingContentPane.removeActor(contentToRemove);
    }

    public void insertProducingContent(final Content content) {
        final ContentElementWidget contentToAdd = new ContentElementWidget(content);
        producingContentContainer.put(content, contentToAdd);
        producingContentPane.addActor(contentToAdd);
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

    private Container<Window> initProduceSettingsWindow(final PlayAccount playAccount) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final TextField contentTitleField = new TextField("title name", skin, "content_title_field");
        final SelectBox<String> contentTypeSelectBox = initContentTypeSelectBox(skin);
        final Slider contentQuality = initContentQualitySlider(skin);

        final VerticalGroup settingsGroup = new VerticalGroup().columnLeft();
        settingsGroup.addActor(contentTitleField);
        settingsGroup.addActor(contentTypeSelectBox);

        final Window produceSettingsWidow = new Window("produce settings", skin, "produce_settings_window");
        produceSettingsWidow.setModal(true);
        produceSettingsWidow.setMovable(false);
        produceSettingsWidow.add(contentQuality);
        produceSettingsWidow.add(settingsGroup).row();
        final Table buttonGroup = new Table();
        final ImageButton approveContentButton = createApproveContentButton(skin, playAccount, contentTitleField, contentQuality, contentTypeSelectBox);
        buttonGroup.add(approveContentButton).size(BUTTON_SIZE).right();
        buttonGroup.add(createDiscardContentButton(skin)).size(BUTTON_SIZE).right();
        produceSettingsWidow.add(buttonGroup).bottom().colspan(2);
        final Container<Window> windowContainer = new Container<>(produceSettingsWidow);
        windowContainer.size(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.5f).center().setVisible(false);
        return windowContainer;
    }

    private VerticalGroup initProducingVerticalGroup() {
        final VerticalGroup producingContent = new VerticalGroup().columnLeft().left();
        if (producingContentContainer != null) {
            for (ContentElementWidget contentWidget: producingContentContainer.values()) {
                producingContent.addActor(contentWidget);
            }
        }
        return producingContent;
    }

    private ImageButton createDiscardContentButton(final Skin skin) {
        final ImageButton dontMakeContentButton = new ImageButton(skin, "dont_make_content");
        dontMakeContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(true);
                produceSettingsWidow.setVisible(false);
            }
        });

        return dontMakeContentButton;
    }

    private ImageButton createApproveContentButton(final Skin skin,
                                                   final PlayAccount playAccount,
                                                   final TextField contentTitleField,
                                                   final Slider contentQuality,
                                                   final SelectBox<String> contentTypeSelectBox) {
        final ImageButton makeContentButton = new ImageButton(skin, "make_content");
        makeContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(true);
                produceSettingsWidow.setVisible(false);
                try {
                    final ContentTypeConverter.ContentType selectedType = stringToType(contentTypeSelectBox.getSelected());
                    playAccount.produceContent(HomeWidget.this, contentTitleField.getText(), selectedType, (int) contentQuality.getValue());
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
        contentTypeSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("item change to: " + ((SelectBox) actor).getSelected());
            }
        });
        return contentTypeSelectBox;
    }

    private Slider initContentQualitySlider(final Skin skin) {
        final Slider contentQuality = new Slider(0, 100, 1, true, skin);
        contentQuality.setValue(100);
        contentQuality.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("slider changed to: " + ((Slider) actor).getValue());
            }
        });

        return contentQuality;
    }


    final private VerticalGroup producingContentPane;

    final private HashMap<Content, ContentElementWidget> producingContentContainer;

    final private Container<Stack> produceStatisticsMenu;
    final private Container<Window> produceSettingsWidow;
}
