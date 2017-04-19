package fr.dracks.xml;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import fr.dracks.bdd.DbUser;
import fr.dracks.bdd.Items;
import fr.dracks.bdd.MongoMain;

public class XmlImport {
	SAXBuilder sxb;
	org.jdom.Document document;
	Element racine;

	public XmlImport(String path) {
		try {
			sxb = new SAXBuilder();
			document = sxb.build(new File(path));
			racine = document.getRootElement();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean ImportUser(MongoMain con){
		Element Users = racine.getChild("Users");
		List<Element> User = Users.getChildren("User");
		int count = 0;
		
		for (Element u:User){
			Element items = u.getChild("Items");
			List<Element> item = items.getChildren();
			DbUser ut = new DbUser(u.getChildText("FirstName"), u.getChildText("LastName"));
			for (Element i:item){
				ut.addItem(new Items(new Date(), i.getChildText("Name"), i.getChildText("Type")));
			}
			con.InsertUser(ut);
			count++;
		}
		JOptionPane.showMessageDialog(null, "Import Complete (" + count + " user(s) Imported)", "Great",JOptionPane.INFORMATION_MESSAGE);;
		return true;
		
	}

}
