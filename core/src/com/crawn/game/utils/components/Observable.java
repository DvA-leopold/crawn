package com.crawn.game.utils.components;

import java.util.HashSet;

public class Observable {
    public Observable() {
        observers = new HashSet<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    protected void notifyObservers(Object object) {
        for (Observer obs: observers) {
            obs.update(null, object);
        }
    }


    final private HashSet<Observer> observers;
}
