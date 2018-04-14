package com.crawn.game.view.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.model.resource.manager.ResourceManager;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static com.crawn.game.model.content.ContentTypeConverter.stringToType;
import static com.crawn.game.utils.StaticUtils.BUTTON_SIZE;
import static java.util.Objects.requireNonNull;


final class ProduceSettingsWindow extends Container<Window> {
    ProduceSettingsWindow(final MyAccount myAccount, final HomeWidget homeWidget) {
        super(new Window("produce settings", (Skin) ResourceManager.instance().get("game_skin/game_widget_skin.json"), "produce_settings_window"));

        final VerticalGroup settingsGroup = new VerticalGroup().columnLeft();
        final TextField contentTitleField = new TextField("title name", requireNonNull(getActor().getSkin()), "content_title_field");
        settingsGroup.addActor(contentTitleField);
        final SelectBox<String> contentTypeSelectBox = initContentTypeSelectBox();
        settingsGroup.addActor(contentTypeSelectBox);

        final Slider contentQuality = new Slider(0, 100, 1, true, requireNonNull(getActor().getSkin()));
        contentQuality.setValue(100);

        getActor().setModal(true);
        getActor().setMovable(false);
        getActor().add(contentQuality);
        getActor().add(settingsGroup).row();

        Supplier<Content> produceContentAction = () -> myAccount.produceContent(
                contentTitleField.getText(),
                stringToType(contentTypeSelectBox.getSelected()),
                (int) contentQuality.getValue(),
                true);

        final Table buttonGroup = new Table();
        final ImageButton approveContentButton = createApproveContentButton(produceContentAction, homeWidget);
        buttonGroup.add(approveContentButton).size(BUTTON_SIZE).right();
        buttonGroup.add(createDiscardContentButton(homeWidget)).size(BUTTON_SIZE).right();
        getActor().add(buttonGroup).bottom().colspan(2);
        size(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.5f).center().setVisible(false);
    }

    private ImageButton createDiscardContentButton(final HomeWidget homeWidget) {
        final ImageButton discardContentButton = new ImageButton(requireNonNull(getActor().getSkin()), "dont_make_content");
        discardContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                homeWidget.setStatMenuVisible(true);
                setVisible(false);
            }
        });

        return discardContentButton;
    }

    private ImageButton createApproveContentButton(Supplier<Content> produceContentAction, final HomeWidget homeWidget) {
        final ImageButton makeContentButton = new ImageButton(requireNonNull(getActor().getSkin()), "make_content");
        makeContentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    final Content content = produceContentAction.get();
                    if (content != null) {
                        setVisible(false);
                        homeWidget.setStatMenuVisible(true);
                        final ProducingContentElementWidget contentWidget = new ProducingContentElementWidget(content);
                        homeWidget.addToProduced(content, contentWidget);
                        content.addUpdatable(contentWidget);
                    }
                } catch (NoSuchElementException what) {
                    Gdx.app.error(getClass().getCanonicalName(), what.toString());
                }
            }
        });

        return makeContentButton;
    }

    private SelectBox<String> initContentTypeSelectBox() {
        final SelectBox<String> contentTypeSelectBox = new SelectBox<>(requireNonNull(getActor().getSkin()), "content_type_select");
        contentTypeSelectBox.setItems("video", "photo", "music");
        return contentTypeSelectBox;
    }
}
