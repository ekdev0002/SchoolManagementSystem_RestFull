package com.app.sms.ui.enseignants;

import java.util.List;

import com.app.sms.models.Classe;
import com.app.sms.models.Cours;

public interface IUIPlanningCours {

	void updateUIForm(String[] data);

	void loadData(List<Cours> cours);

}
