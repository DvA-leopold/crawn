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


final public class HomeWidget extends Table {
    HomeWidget(final PlayAccount playAccount) {
        setVisible(false);
        this.producingContentContainer = new HashMap<>();
        for (Content producingElement: playAccount.getProducingContentElements()) {
            producingContentContainer.put(producingElement, new ContentElementWidget(producingElement));
        }

        producingContentPane = initProducingVerticalGroup();
        produceStatisticsMenu = initProduceStatusMenu();
        produceSettingsWidowTable = initProduceSettingsWindow(playAccount);

        final Stack widgetStack = new Stack(produceStatisticsMenu, produceSettingsWidowTable);
        add(widgetStack).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - AVATAR_SIZE - MENU_BUTTON_HEIGHT);
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

    private Table initProduceStatusMenu() {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final Table produceStatisticsMenu = new Table();
        final ImageButton produceButton = new ImageButton(skin, "produce");
        produceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(false);
                produceSettingsWidowTable.setVisible(true);
            }
        });
        final HorizontalGroup produceButtonContainer = new HorizontalGroup();
        produceButtonContainer.addActor(produceButton);

        final Stack statusMenuStack = new Stack();
        statusMenuStack.add(producingContentPane);
        statusMenuStack.add(produceButtonContainer);

        produceStatisticsMenu.add(statusMenuStack).expand().right().bottom();
        produceStatisticsMenu.setDebug(true);
        return produceStatisticsMenu;
    }

    private Table initProduceSettingsWindow(final PlayAccount playAccount) {
        final Skin skin = (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json");
        final TextField contentTitleField = new TextField("title name", skin, "content_title_field");
        final SelectBox<String> contentTypeSelectBox = initContentTypeSelectBox(skin);
        final Slider contentQuality = initContentQualitySlider(skin);
        final ImageButton makeContentButton = createMakeContentButton(skin, playAccount, contentTitleField, contentQuality, contentTypeSelectBox);
        final ImageButton dontMakeContentButton = createDontMakeContentButton(skin);

        final VerticalGroup settingsGroup = new VerticalGroup().columnLeft();
        settingsGroup.addActor(contentTitleField);
        settingsGroup.addActor(contentTypeSelectBox);

        final Window produceSettingsWidow = new Window("produce settings", skin, "produce_settings_window");
        produceSettingsWidow.setModal(true);
        produceSettingsWidow.setMovable(false);
        produceSettingsWidow.setDebug(true);
        produceSettingsWidow.add(contentQuality);
        produceSettingsWidow.add(settingsGroup).row();
        final Table buttonGroup = new Table();
        buttonGroup.add(makeContentButton).size(BUTTON_SIZE).padLeft(BUTTON_PADDING).right();
        buttonGroup.add(dontMakeContentButton).size(BUTTON_SIZE).padLeft(BUTTON_PADDING).right();
        produceSettingsWidow.add(buttonGroup).colspan(2);
        final Table windowTable = new Table();
        windowTable.add(produceSettingsWidow).size(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.5f).center();
        windowTable.setVisible(false);
        return windowTable;
    }

    private VerticalGroup initProducingVerticalGroup() {
        final VerticalGroup producingContent = new VerticalGroup();
        if (producingContentContainer != null) {
            for (ContentElementWidget contentWidget: producingContentContainer.values()) {
                producingContent.addActor(contentWidget);
            }
        }
        return producingContent;
    }

    private ImageButton createDontMakeContentButton(final Skin skin) {
        final ImageButton dontMakeContentButton = new ImageButton(skin, "dont_make_content");
        dontMakeContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(true);
                produceSettingsWidowTable.setVisible(false);
            }
        });

        return dontMakeContentButton;
    }

    private ImageButton createMakeContentButton(final Skin skin,
                                                final PlayAccount playAccount,
                                                final TextField contentTitleField,
                                                final Slider contentQuality,
                                                final SelectBox<String> contentTypeSelectBox) {
        final ImageButton makeContentButton = new ImageButton(skin, "make_content");
        makeContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                produceStatisticsMenu.setVisible(true);
                produceSettingsWidowTable.setVisible(false);
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

    final private Table produceStatisticsMenu;
    final private Table produceSettingsWidowTable;
}
