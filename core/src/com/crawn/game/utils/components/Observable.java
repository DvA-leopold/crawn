package com.crawn.game.utils.components;

import java.util.Vector;


public class Observable {
    public Observable() {
        observers = new Vector<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Object object) {
        for (Observer obs: observers) {
            obs.update(this, object);
        }
    }


    final private Vector<Observer> observers;
}
