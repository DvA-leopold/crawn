package com.crawn.game.model;

import com.crawn.game.model.accounts.MyAccount;
import com.crawn.game.model.accounts.VillainAccount;
import com.crawn.game.model.generators.AccountGenerator;

import java.util.Vector;


final public class Model {
    public Model() {
        myAccount = new MyAccount("awesome acc", 0 , 0, 100000);
        villainAccounts = AccountGenerator.generateAccounts(this, 1);
    }

    public void resume(long timerDelay) {
        myAccount.resume(timerDelay);
        for (VillainAccount villainAccount: villainAccounts) {
            villainAccount.resume(timerDelay);
        }
    }

    public MyAccount getMyAccount() {
        return myAccount;
    }

    public Vector<VillainAccount> getVillainAccounts() {
        return villainAccounts;
    }

    public void dispose() {

    }


    final private MyAccount myAccount;
    final private Vector<VillainAccount> villainAccounts;
}
