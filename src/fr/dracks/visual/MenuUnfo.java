package fr.dracks.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.dracks.bdd.DbUser;
import fr.dracks.bdd.MongoMain;
import fr.dracks.xml.XmlExport;
import fr.dracks.xml.XmlImport;

public class MenuUnfo {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu User = new JMenu("User");
	private JMenu Items = new JMenu("Items");
	private JMenuItem item1 = new JMenuItem("Show");
	private JMenuItem item2 = new JMenuItem("Add");
	private JMenuItem item3 = new JMenuItem("Remove");

	public MenuUnfo(MongoMain t, String Fn, String Ln, info info) {

		item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (t.Search(Fn, Ln).get(0).getItems().size() == 0 )
					JOptionPane.showMessageDialog(null, "No Items", "oops",
							JOptionPane.INFORMATION_MESSAGE);
				else if (t.Search(Fn, Ln).get(0).getItems().size() == 1)
					new item(t, Fn, Ln, 0);
				else
				new JlistItem(t.Search(Fn, Ln).get(0).getItems(), t, Fn,Ln);
			}
		});
		item2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField Name = new JTextField();
				JTextField Type = new JTextField();
				Object[] message = { "Name:", Name, "Type:", Type };

				int option1 = JOptionPane.showConfirmDialog(null, message, "Add Item",
						JOptionPane.OK_CANCEL_OPTION);
				if (option1 == JOptionPane.OK_OPTION) {
					if (!Name.getText().equals("") && !Type.getText().equals("")) {
						if (t.addItem(t.GetId(Fn, Ln), Name.getText(), Type.getText()))
							JOptionPane.showMessageDialog(null, "item has been add", "Great",
									JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Wrong input", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		item3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();			
				int option = jop.showConfirmDialog(null, "Realy remove user ?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							
				if(option == JOptionPane.OK_OPTION){
				  		t.RemoveUser(t.GetId(Fn, Ln));
				  		info.dispose();
				}
			}
		});
		
		
	
		
		Items.add(item1);
		Items.add(item2);
		User.add(item3);
		menuBar.add(User);
		menuBar.add(Items);
	}

	public JMenuBar Get() {
		return menuBar;
	}
}
