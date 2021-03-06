package fr.dracks.visual;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import fr.dracks.bdd.DbUser;
import fr.dracks.bdd.Items;
import fr.dracks.bdd.MongoMain;

public class JlistItem extends JFrame {
	private JList<String> userlist;
	JlistItem me = this;

	public JlistItem(List<Items> list, MongoMain t, String Fn, String Ln) {
		// create the model and add elements
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (Items i : list) {
			listModel.addElement(i.getName() + "  (" + i.getType() + " )");
			
		}

		// create the list
		userlist = new JList<>(listModel);
		userlist.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList slist = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = slist.locationToIndex(evt.getPoint());
		            new item(t, Fn, Ln, index);
		            me.dispose();
		        } 
		    }
		});
		userlist.setFont(new Font("arial", 0, 15));
		//add(userlist);
		userlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(userlist));
		this.setTitle("Item on user");
		
		this.setSize(300, 400);
		this.setMinimumSize(new Dimension(300, 400));
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
}