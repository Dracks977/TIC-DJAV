package fr.dracks.bdd;

import java.util.LinkedList;
import java.util.List;

/**
 * Class gestion des User
 * 
 * @author Dracks
 * @version 1.0
 */
public class DbUser {
	
	private String FirstName;
	private String LastName;
	List<Items> Items = new LinkedList<Items>();
	
	public DbUser(String firstName, String lastName) {
		super();
		FirstName = firstName;
		LastName = lastName;
	}
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public List<Items> getItems() {
		return Items;
	}
	public void addItem(Items items) {
		Items.add(items);
	}
	public void setItems(List<Items> x) {
		Items = x;
	}

}
