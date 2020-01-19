package com.app.sms.ui.enseignants;

import java.util.List;

import com.app.sms.models.Devoirs;

public interface IUIPlanningDevoirs {
	void updateUIForm(String[] data);

	void loadData(List<Devoirs> devoirs);
}
