package com.crawn.game.model.accounts;

import com.badlogic.gdx.utils.Timer;

import java.util.Vector;


final public class AccountManager {
    public AccountManager() {
        villainAccounts = new Vector<>();
        account = new MyAccount("awesome acc", 0 , 0, 0);

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                for (VillainAccount villainAccount: villainAccounts) {
                    villainAccount.act();
                }
            }
        }, 1, 5, Integer.MAX_VALUE);
    }

    public MyAccount getAccount() {
        return account;
    }

    public Vector<VillainAccount> getVillainAccounts() {
        return villainAccounts;
    }


    final private MyAccount account;
    final private Vector<VillainAccount> villainAccounts;
}
