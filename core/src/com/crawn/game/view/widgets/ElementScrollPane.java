package com.crawn.game.view.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.crawn.game.model.accounts.Account;
import com.crawn.game.model.accounts.VillainAccount;
import com.crawn.game.model.content.Content;
import com.crawn.game.utils.components.Observable;
import com.crawn.game.utils.components.Observer;

import java.util.TreeSet;
import java.util.Vector;


final public class ElementScrollPane extends ScrollPane implements Observer {
    ElementScrollPane(final Vector<VillainAccount> villainAccounts) {
        super(initAccountScrollPane(villainAccounts));
        setVisible(false);
    }

    ElementScrollPane(final Account account) {
        super(initContentScrollPane(account));
    }

    @Override
    public void update(Observable observable, Object finishedContent) {
        if (finishedContent != null) {
            final ContentElementWidget contentWidget = new ContentElementWidget((Content) finishedContent);
            ((Content) finishedContent).addObserver(contentWidget);
            ((VerticalGroup) getActor()).addActor(contentWidget);
        }
    }

    static private VerticalGroup initContentScrollPane(final Account account){
        final VerticalGroup contentGroup = new VerticalGroup().columnLeft().left().reverse();
        final TreeSet<Content> contentElements = account.getContentElements();
        for (Content contentElement: contentElements) {
            final ContentElementWidget contentWidget = new ContentElementWidget(contentElement);
            contentElement.addObserver(contentWidget);
            contentGroup.addActor(contentWidget);
        }
        return contentGroup;
    }

    static private VerticalGroup initAccountScrollPane(final Vector<VillainAccount> villainAccounts) {
        final VerticalGroup accountsGroup = new VerticalGroup().columnLeft().left();
        for (VillainAccount account: villainAccounts) {
            accountsGroup.addActor(new AccountInfoWidget(account));
        }
        return accountsGroup;
    }
}
