package com.app.sms.ui.gestionnaires.observer;

import com.app.sms.utils.Utilitaire;

public class Cleaner implements IObserver {
	private IObservable observable ;
	private boolean active;
	/**
	 * @param observable
	 */
	public Cleaner(IObservable observable) {
		this.observable = observable;
		active = true;
	}
	
	@Override
	public void update() {
		
		Thread t = new Thread ( new Runnable () {
			@Override
			public void run() {
				if ( active ) {
					try {Thread.sleep(Utilitaire.DEFAULT_WAITING_TIME);} catch (InterruptedException e) {}
					// Pendant le sommeil du thread le statut de active a pu changer ...
					// donc on revérifie à nouveau avant d'effacer la zone de notification ...
					if ( active ) observable.clear();
				}
			}
		});
	
		t.start();
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
}