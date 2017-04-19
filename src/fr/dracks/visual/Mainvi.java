package fr.dracks.visual;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import fr.dracks.bdd.DbUser;
import fr.dracks.bdd.MongoMain;
import fr.dracks.xml.XmlExport;
import fr.dracks.xml.XmlImport;

public class Mainvi {
	public void aff() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		MongoMain test = new MongoMain();
		new Fenetre(test);
	}
}
