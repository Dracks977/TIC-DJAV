package fr.dracks.visual;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import fr.dracks.bdd.DbUser;
import fr.dracks.bdd.MongoMain;

public class Fenetre extends JFrame implements ActionListener {
	/**
	 * 
	 */
	MongoMain con;
	private static final long serialVersionUID = -331309237560217709L;
	private JPanel pan = new JPanel();
	public static JTextField FirstName = new JTextField("FirstName");
	public static JTextField LastName = new JTextField("LastName");
	JLabel Title = new JLabel("Carnet'Master");

	private JButton bouton = new JButton("Search");

	public Fenetre(MongoMain t) {
		con = t;
		this.setTitle("Carnet'Master");
		this.setSize(300, 300);
		this.setMinimumSize(new Dimension(300, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		GridLayout gl = new GridLayout(4, 2, 5, 4);
		gl.setHgap(5);
		gl.setVgap(5);

		this.setLayout(gl);

		Title.setHorizontalAlignment(JLabel.CENTER);
		Title.setFont(new Font("arial", Font.BOLD, 15));
		FirstName.setHorizontalAlignment(JTextField.CENTER);
		LastName.setHorizontalAlignment(JTextField.CENTER);
		bouton.addActionListener(this);
		this.setJMenuBar(new MenuB(con).Get());
		this.getContentPane().add(Title);
		this.getContentPane().add(FirstName);
		this.getContentPane().add(LastName);
		this.getContentPane().add(bouton);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == bouton) {
			List<DbUser> list = con.Search(FirstName.getText(), LastName.getText());
			if (list.size() == 0) {
				int option = JOptionPane.showConfirmDialog(null, "User not found, do u want to create a new ?",
						"Not found", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					JTextField FirstName = new JTextField();
					JTextField LastName = new JTextField();
					Object[] message = { "FirstName:", FirstName, "LastName:", LastName };

					int option1 = JOptionPane.showConfirmDialog(null, message, "Add User",
							JOptionPane.OK_CANCEL_OPTION);
					if (option1 == JOptionPane.OK_OPTION) {
						if (!FirstName.getText().equals("") && !LastName.getText().equals("")) {
							if (con.InsertUser(new DbUser(FirstName.getText(), LastName.getText())))
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

			} else if (list.size() == 1)
				 new info(con, list.get(0).getFirstName(), list.get(0).getLastName());
			else
				new JlistUser(list, con);
		}

	}
}