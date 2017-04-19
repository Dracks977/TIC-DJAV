package fr.dracks.bdd;

import java.util.Date;

/**
 * Class gestion des items
 * 
 * @author Dracks
 * @version 1.1
 */
public class Items {
	private Date PDate;
	private String Name;
	private String Type;
	
	public Items(Date pDate, String name, String type) {
		super();
		PDate = pDate;
		Name = name;
		Type = type;
	}
	
	
	public Date getPDate() {
		return PDate;
	}
	public void setPDate(Date pDate) {
		PDate = pDate;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}

}
