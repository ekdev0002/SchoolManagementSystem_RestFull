package com.app.sms.ui.gestionnaires.observer;

import java.util.List;

public interface IObservable {
	public void attach(IObserver observer);
	public default void notifyAllObservers(List<IObserver> observers) {
		for ( IObserver observer : observers ) {
			observer.update();
		}
	}
	public default void clear () {}
}