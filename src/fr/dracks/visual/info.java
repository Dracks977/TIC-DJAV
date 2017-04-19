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

public class info extends JFrame implements ActionListener {
	/**
	 * 
	 */
	MongoMain con;
	private static final long serialVersionUID = -331309237560217709L;
	private JPanel pan = new JPanel();

	JLabel Title = new JLabel("Info User");

	JButton bouton = new JButton("Edit user");
	JTextField FirstName = new JTextField("");
	JTextField LastName = new JTextField("");
	String _Fname;
	String _Lname;

	public info(MongoMain t, String Fname, String Lname) {
		con = t;
		FirstName.setText(Fname);
		LastName.setText(Lname);
		_Fname = Fname;
		_Lname = Lname;
		this.setSize(300, 400);
		this.setMinimumSize(new Dimension(300, 400));
		this.setLocationRelativeTo(null);
		GridLayout gl = new GridLayout(4, 2, 5, 4);
		gl.setHgap(5);
		gl.setVgap(5);

		this.setLayout(gl);
		/*ajouter un menue avec show item add item and edit item*/
		Title.setHorizontalAlignment(JLabel.CENTER);
		Title.setFont(new Font("arial", Font.BOLD, 15));
		FirstName.setHorizontalAlignment(JTextField.CENTER);
		LastName.setHorizontalAlignment(JTextField.CENTER);
		bouton.addActionListener(this);
		this.setJMenuBar(new MenuUnfo(t, _Fname, _Lname, this).Get());
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
			if (!FirstName.getText().equals(_Fname) || !LastName.getText().equals(_Lname) )
				con.UpdateUser(con.GetId(_Fname, _Lname), FirstName.getText(), LastName.getText());
			this.dispose();
		}
	}
}