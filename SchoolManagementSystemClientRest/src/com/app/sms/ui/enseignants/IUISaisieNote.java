package com.app.sms.ui.enseignants;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;

public interface IUISaisieNote {
	void updateUIForm(String[] data);

	void loadData() throws WSException, JAXBException, IOException;
}
