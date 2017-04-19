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

public class Menuitem {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu Items = new JMenu("Item");
	private JMenuItem item3 = new JMenuItem("Remove");

	public Menuitem(MongoMain t, String Fn, String Ln, item item, String Name, String Type) {
		
		item3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();			
				int option = jop.showConfirmDialog(null, "Realy remove item ?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							
				if(option == JOptionPane.OK_OPTION){
				  		t.RemoveItem(t.GetId(Fn, Ln), Name, Type);
				  		item.dispose();
				}
			}
		});
		
		
	
		
		Items.add(item3);
		menuBar.add(Items);
	}

	public JMenuBar Get() {
		return menuBar;
	}
}
