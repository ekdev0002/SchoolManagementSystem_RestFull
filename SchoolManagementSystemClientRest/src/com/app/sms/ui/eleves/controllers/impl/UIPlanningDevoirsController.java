package com.app.sms.ui.eleves.controllers.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;
import com.app.sms.ui.eleves.controllers.IUIPlanningDevoirsController;
import com.app.sms.ui.eleves.impl.UIPlanningDevoirs;
import com.app.sms.ui.impl.MainUIApplication;

public class UIPlanningDevoirsController implements IUIPlanningDevoirsController {
	private UIPlanningDevoirs uiPlanningDevoirs;
	private Devoirs devoirs;
	/**
	 * @param uINouveauClasse
	 */
	public UIPlanningDevoirsController(UIPlanningDevoirs uiPlanningDevoirs,Devoirs cour) {
		this.uiPlanningDevoirs = uiPlanningDevoirs;
		this.devoirs=cour;
		
		try {
			List<Devoirs> devoirs = Devoirs.listForEleve(MainUIApplication.getCurrentUser().getId());
			this.uiPlanningDevoirs.loadData(devoirs);
			this.uiPlanningDevoirs.displayNotification("Done successfully !");
			
		} catch (JAXBException | IOException | WSException exception) {
			this.uiPlanningDevoirs.displayErrorMessage(exception.getMessage());
		}	
	}
	
	public void run() {
		uiPlanningDevoirs.showMe();
	}
}
