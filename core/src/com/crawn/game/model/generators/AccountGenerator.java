package com.crawn.game.model.generators;

import com.crawn.game.model.PlayAccount;

import java.util.Vector;


final public class AccountGenerator {
    private static PlayAccount generateAccount() {
        return new PlayAccount(RandomNamesGenerator.generateAccountName(), 0, 0);
    }

    public static Vector<PlayAccount> generateAccounts(int accountNumber) {
        Vector<PlayAccount> playAccountVector = new Vector<>(accountNumber);
        for (int i = 0; i < accountNumber; i++) {
            playAccountVector.add(generateAccount());
        }
        return playAccountVector;
    }
}
