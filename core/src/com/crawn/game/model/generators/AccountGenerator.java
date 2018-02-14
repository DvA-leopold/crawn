package com.crawn.game.model.generators;

import com.crawn.game.model.accounts.VillainAccount;

import java.util.Vector;


final public class AccountGenerator {
    private static VillainAccount generateAccount() {
        return new VillainAccount(RandomNamesGenerator.generateAccountName(), 0, 0, 0);
    }

    public static Vector<VillainAccount> generateAccounts(int accountNumber) {
        final Vector<VillainAccount> playAccountVector = new Vector<>(accountNumber);
        for (int i = 0; i < accountNumber; i++) {
            playAccountVector.add(generateAccount());
        }
        return playAccountVector;
    }
}
