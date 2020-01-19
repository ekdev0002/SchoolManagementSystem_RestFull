package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;

public interface IUGestionBulletin {
	void addViusaliserListener(ActionListener listenerForViualiserButton);

	void addGenererListener(ActionListener listenerForGenererButton);

	void addImprimerListener(ActionListener listenerForImprimerButton);

}
