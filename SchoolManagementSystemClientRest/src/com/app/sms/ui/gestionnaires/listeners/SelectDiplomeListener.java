package com.app.sms.ui.gestionnaires.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import com.app.sms.ui.gestionnaires.impl.UINouvelleCandidature;
import com.app.sms.ui.gestionnaires.impl.UINouvelleCandidature2;

public class SelectDiplomeListener implements ActionListener {

	private int index;
	private UINouvelleCandidature uINouvelleCandidature;
	private UINouvelleCandidature2 uINouvelleCandidature2;
	/**
	 * @param index
	 */
	public SelectDiplomeListener(UINouvelleCandidature uiNouvelleCandidature, int index) {
		this.uINouvelleCandidature = uiNouvelleCandidature;
		this.index = index;
	}

	public SelectDiplomeListener(UINouvelleCandidature2 uiNouvelleCandidature2, int i) {
		// TODO Auto-generated constructor stub
		this.uINouvelleCandidature2 = uiNouvelleCandidature2;
		this.index = index;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getActionCommand().equals("+")) {
			addDiplome(uINouvelleCandidature, index);
		} else {
			delDiplome(uINouvelleCandidature, index);
		}
	}

	private void addDiplome(UINouvelleCandidature uINouvelleCandidature, int index) {
		JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(uINouvelleCandidature2);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            uINouvelleCandidature2.addDiplomesUrl(index, file.getAbsolutePath());
            uINouvelleCandidature2.setDiplomesFileAt(index, file.getName());
            uINouvelleCandidature2.setButtonAt (index, "-");
        }
	}

	private void delDiplome(UINouvelleCandidature uINouvelleCandidature2, int index) {		
        uINouvelleCandidature2.removeDiplomesUrlAt(index);
        uINouvelleCandidature2.setDiplomesFileAt(index, "");
        uINouvelleCandidature2.setButtonAt (index, "+");
	}
}