package fr.dracks.xml;

import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import fr.dracks.bdd.DbUser;

/**
 * Class export en xml de la base mongoDb
 * 
 * @author Dracks
 * @version 1.1
 */
public class XmlExport {

	public void ExportXML(List<DbUser> lusers, String path){
		 Element racine = new Element("Carnet");
		 org.jdom.Document document = new Document(racine);
		 Element Users = new Element("Users");
		 int count = 0;
		 for (DbUser u:lusers){
			 new User(u.getFirstName(), u.getLastName(), u.getItems()).createXml(Users);
			 count++;
		 }
		 JOptionPane.showMessageDialog(null, "Export Complete (" + count + " user(s) exported)","Great" ,JOptionPane.INFORMATION_MESSAGE);
		 racine.addContent(Users);
		 enregistre(path, document);
	}
	
	static void affiche(Document document) {
		try {
			Format format = Format.getPrettyFormat();
			format.setEncoding("UTF-8");
			XMLOutputter sortie = new XMLOutputter(format);
			sortie.output(document, System.out);
		} catch (java.io.IOException e) {
		}
	}
	static void enregistre(String fichier, Document document) {
		try {
			Format format = Format.getPrettyFormat();
			format.setEncoding("UTF-8");
			XMLOutputter sortie = new XMLOutputter(format);
			sortie.output(document, new FileOutputStream(fichier));
		} catch (java.io.IOException e) {
		}
	}
}
