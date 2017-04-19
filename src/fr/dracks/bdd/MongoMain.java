package fr.dracks.bdd;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

@SuppressWarnings("deprecation")
/**
 * Class interface mongoDb
 * 
 * @author Dracks
 * @version 1.3
 */
public class MongoMain {
	MongoClient mongo;
	DB db;
	DBCollection table;

	public MongoMain() {
		mongo = new MongoClient("localhost", 27017);
		db = mongo.getDB("Carnet");
		table = db.getCollection("Users");
	}

	/**
	 * 
	 * @return liste des utilisateurs
	 */
	public List<DbUser> GetUserAll() {

		List<DBObject> doc = table.find().toArray();
		List<DbUser> users = new LinkedList<DbUser>();
		for (DBObject x : doc) {
			DbUser user = new DbUser(x.get("FirstName").toString(), x.get("LastName").toString());
			List<Items> Items = new LinkedList<Items>();
			BasicDBList item = (BasicDBList) x.get("Items");
			List<DBObject> res = new ArrayList<DBObject>();
			for (Object el : item) {
				res.add((DBObject) el);
			}
			for (DBObject i : res) {
				Items.add(new Items(new Date(), i.get("Name").toString(), i.get("Type").toString()));
			}
			user.setItems(Items);
			users.add(user);
		}
		return users;
	}

	/**
	 * drop db
	 */
	public void Drop(){
		db.dropDatabase();
	}
	
	/**
	 *  recherche user
	 * @return liste des utilisateurs
	 */
	public List<DbUser> Search(String Fname, String Lname) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("FirstName", Pattern.compile(".*" + Fname + ".*"));
		whereQuery.put("LastName", Pattern.compile(".*" + Lname + ".*"));

		List<DBObject> doc = table.find(whereQuery).toArray();
		List<DbUser> users = new LinkedList<DbUser>();
		for (DBObject x : doc) {
			DbUser user = new DbUser(x.get("FirstName").toString(), x.get("LastName").toString());
			List<Items> Items = new LinkedList<Items>();
			BasicDBList item = (BasicDBList) x.get("Items");
			List<DBObject> res = new ArrayList<DBObject>();
			for (Object el : item) {
				res.add((DBObject) el);
			}
			for (DBObject i : res) {
				Items.add(new Items(new Date(), i.get("Name").toString(), i.get("Type").toString()));
			}
			user.setItems(Items);
			users.add(user);
		}
		return users;
	}

	/**
	 * 
	 * @param Fname
	 *            Firstname
	 * @param Lname
	 *            Lastname
	 * @return mongodb unique ID
	 */
	public String GetId(String Fname, String Lname) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("FirstName", Fname);
		whereQuery.put("LastName", Lname);
		DBCursor cursor = table.find(whereQuery);
		if (cursor.hasNext()) {
			System.err.println("find user to up");
			return cursor.next().get("_id").toString();
		}
		return null;
	}

	/**
	 * update user dans mongo
	 * 
	 * @param id
	 * @see {@link #GetId(String, String)}
	 * @param NewFName
	 *            new Firstname
	 * @param NewLastName
	 *            new Lastname
	 * @return
	 */
	public boolean UpdateUser(String id, String NewFName, String NewLastName) {
		if (!UserIsset(NewFName, NewLastName) && id != null) {
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set",
					new BasicDBObject().append("FirstName", NewFName).append("LastName", NewLastName));
			BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(id));

			WriteResult test = table.update(searchQuery, newDocument);
			test.isUpdateOfExisting();
			return test.isUpdateOfExisting();
		}
		return false;

	}

	/**
	 * 
	 * @param id
	 *            {@link #GetId(String, String)}
	 * @param Name
	 * @param Type
	 * @return
	 */
	private boolean itemisset(String id, String Name, String Type) {
		BasicDBObject q = new BasicDBObject();
		q.put("_id", new ObjectId(id));
		DBCursor cursor = table.find(q);
		if (cursor.hasNext()) {
			BasicDBList item = (BasicDBList) cursor.next().get("Items");
			List<DBObject> res = new ArrayList<DBObject>();
			for (Object el : item) {
				res.add((DBObject) el);
			}
			for (DBObject i : res) {
				if (i.get("Name").equals(Name) && i.get("Type").equals(Type))
					return true;
			}
		}
		return false;
	}

	/**
	 * ajoute un item a l'user dans la base mongo
	 * 
	 * @param id
	 *            {@link #GetId(String, String)}
	 * @param Name
	 *            Nom de l'item
	 * @param type
	 *            type de l'item
	 * @return
	 */
	public boolean addItem(String id, String Name, String type) {
		if (id != null && !itemisset(id, Name, type)) {
			BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(id));
			BasicDBObject Item = new BasicDBObject();
			Item.put("PDate", new Date());
			Item.put("Name", Name);
			Item.put("Type", type);
			DBObject listItem = new BasicDBObject("Items", Item);
			DBObject updateQuery = new BasicDBObject("$push", listItem);
			WriteResult test = table.update(searchQuery, updateQuery);
			System.err.println(test);
			return test.isUpdateOfExisting();
		}
		return false;

	}

	/**
	 * @deprecated
	 * @param id
	 *            {@link #GetId(String, String)}
	 * @param Name
	 *            old name
	 * @param type
	 *            old type
	 * @param NName
	 *            new name
	 * @param Ntype
	 *            new type
	 * @return
	 */
	public boolean updateItem(String id, String Name, String type, String NName, String Ntype) {
		/* a changer avec la vraie request */
		if (RemoveItem(id, Name, type) && addItem(id, NName, Ntype))
			return true;
		return false;

	}

	/**
	 * supprimer item d'un utilisateur
	 * 
	 * @param id
	 *            {@link #GetId(String, String)}
	 * @param Name
	 * @param type
	 * @return
	 */
	public boolean RemoveItem(String id, String Name, String type) {
		if (id != null && itemisset(id, Name, type)) {
			BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(id));
			BasicDBObject Item = new BasicDBObject();
			Item.put("Name", Name);
			Item.put("Type", type);
			DBObject listItem = new BasicDBObject("Items", Item);
			DBObject updateQuery = new BasicDBObject("$pull", listItem);
			WriteResult test = table.update(searchQuery, updateQuery);
			System.err.println(test);
			return test.isUpdateOfExisting();
		}
		return false;

	}

	/**
	 * supprime user
	 * 
	 * @param id
	 *            {@link #GetId(String, String)}
	 * @return
	 */
	public boolean RemoveUser(String id) {
		if (id != null) {
			BasicDBObject searchQuery = new BasicDBObject().append("_id", new ObjectId(id));
			WriteResult test = table.remove(searchQuery);
			System.err.println(test);
			return test.isUpdateOfExisting();
		}
		return false;

	}

	private boolean UserIsset(String FirstName, String LastName) {
		BasicDBObject q = new BasicDBObject();
		q.put("FirstName", FirstName);
		q.put("LastName", LastName);
		DBCursor cursor = table.find(q);
		if (cursor.hasNext()) {
			return true;
		}
		return false;
	}

	/**
	 * insert un user dans mongo
	 * 
	 * @see DbUser
	 * @see items
	 * @param user
	 * @return
	 */
	public boolean InsertUser(DbUser user) {
		try {
			BasicDBObject document = new BasicDBObject();
			document.put("FirstName", user.getFirstName());
			document.put("LastName", user.getLastName());
			List<BasicDBObject> Items = new ArrayList<>();
			for (Items x : user.getItems()) {
				BasicDBObject Item = new BasicDBObject();
				Item.put("PDate", x.getPDate());
				Item.put("Name", x.getName());
				Item.put("Type", x.getType());
				Items.add(Item);
			}
			if (UserIsset(user.getFirstName(), user.getLastName()))
				return false;
			document.put("Items", Items);
			table.insert(document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}