package fr.dracks.xml;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;

import fr.dracks.bdd.Items;

/**
 * Class génération des User XML
 * 
 * @author Dracks
 * @version 1.0
 */
public class User {
	
	private String _FirstName;
	private String _LastName;
	List<Items> _Items = new LinkedList<Items>();
	
	public User(String firstName, String lastName, List<fr.dracks.bdd.Items> items) {
		super();
		_FirstName = firstName;
		_LastName = lastName;
		_Items = items;
	}

	public void createXml(Element Users){
		Element User = new Element("User");
		
		Element FirstName = new Element("FirstName");
		FirstName.setText(_FirstName);
		User.addContent(FirstName);
		
		Element LastName = new Element("LastName");
		LastName.setText(_LastName);
		User.addContent(LastName);

		Element Items = new Element("Items");
		
	    if (_Items.size() != 0){
	        for(fr.dracks.bdd.Items i:_Items){
	        	Element item = new Element("Item");
	        	
	        	Element PDate = new Element("PDate");
	        	PDate.setText(i.getPDate().toString());
	        	item.addContent(PDate);
	       
	        	Element Name = new Element("Name");
	        	Name.setText(i.getName());
	        	item.addContent(Name);
	        	
	        	Element Type = new Element("Type");
	        	Type.setText(i.getType());
	        	item.addContent(Type);
	        	Items.addContent(item);
	        }
	       
	    }
	    User.addContent(Items);
	    Users.addContent(User);
	    
	}

}
