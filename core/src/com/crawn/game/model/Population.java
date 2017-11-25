package com.crawn.game.model;


final public class Population {
    static {
        totalPopulation = 7600000000L;
    }

    private Population() { }

    static public long getPeople(int getNumber) {
        if (totalPopulation - getNumber > 0) {
            totalPopulation -= getNumber;
            return getNumber;
        }

        long peopleLeft = totalPopulation;
        totalPopulation = 0;
        return peopleLeft;
    }

    static public void changePopulation(int people) {
        if (totalPopulation + people < 0) {
            return;
        }
        totalPopulation += people;
    }


    static private long totalPopulation;
}
