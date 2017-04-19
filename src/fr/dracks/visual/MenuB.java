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

public class MenuB {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu BDD = new JMenu("BDD");
	private JMenu User = new JMenu("User");
	private JMenuItem u_item1 = new JMenuItem("Show All");
	private JMenuItem u_item2 = new JMenuItem("Add");
	private JMenuItem item1 = new JMenuItem("Import");
	private JMenuItem item2 = new JMenuItem("Export");
	private JMenuItem item3 = new JMenuItem("Drop");

	public MenuB(MongoMain t) {

		item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					XmlImport imp = new XmlImport(selectedFile.getAbsolutePath());
					imp.ImportUser(t);
				}
			}
		});

		
		item2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
				fileChooser.setFileFilter(filter);
				fileChooser.setSelectedFile(new File("export.xml"));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					XmlExport xml = new XmlExport();
					xml.ExportXML(t.GetUserAll(), selectedFile.getAbsolutePath());
				}
			}
		});
		item3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();			
				int option = jop.showConfirmDialog(null, "Realy drop db ?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							
				if(option == JOptionPane.OK_OPTION){
				  		t.Drop();
				}
			}
		});
		
		u_item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new JlistUser(t.GetUserAll(), t);
			}
		});
		
		u_item2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField FirstName = new JTextField();
				JTextField LastName = new JTextField();
				Object[] message = { "FirstName:", FirstName, "LastName:", LastName };

				int option = JOptionPane.showConfirmDialog(null, message, "Add User", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					if (!FirstName.getText().equals("") && !LastName.getText().equals("")) {
						if (t.InsertUser(new DbUser(FirstName.getText(), LastName.getText())))
							JOptionPane.showMessageDialog(null, "User has been add", "Great",
									JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "User already on db", "Error",
									JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Wrong input", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	
		
		BDD.add(item1);
		BDD.add(item2);
		BDD.addSeparator();
		BDD.add(item3);
		User.add(u_item2);
		User.add(u_item1);
		menuBar.add(BDD);
		menuBar.add(User);
	}

	public JMenuBar Get() {
		return menuBar;
	}
}
