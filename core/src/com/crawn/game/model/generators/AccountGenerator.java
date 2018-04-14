package com.crawn.game.model.generators;

import com.crawn.game.model.Model;
import com.crawn.game.model.accounts.VillainAccount;

import java.util.Vector;


final public class AccountGenerator {
    private static VillainAccount generateAccount(Model model) {
        return new VillainAccount(model, RandomNamesGenerator.generateAccountName(), 0, 0, 0);
    }

    public static Vector<VillainAccount> generateAccounts(Model model, int accountNumber) {
        final Vector<VillainAccount> playAccountVector = new Vector<>(accountNumber);
        for (int i = 0; i < accountNumber; i++) {
            playAccountVector.add(generateAccount(model));
        }
        return playAccountVector;
    }
}
