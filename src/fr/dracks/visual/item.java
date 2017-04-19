package fr.dracks.visual;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.dracks.bdd.MongoMain;

public class item extends JFrame implements ActionListener {
	/**
	 * 
	 */
	MongoMain con;
	private static final long serialVersionUID = -331309237560217709L;
	private JPanel pan = new JPanel();

	JLabel Title = new JLabel("Info item");

	JButton bouton = new JButton("Edit item");
	JTextField Name = new JTextField("");
	JTextField Type = new JTextField("");
	String _Fname;
	String _Lname;
	int _index;

	public item(MongoMain t, String Fname, String Lname, int index) {
		con = t;
		_Fname = Fname;
		_Lname = Lname;
		_index = index;
		Name.setText(t.Search(_Fname, _Lname).get(0).getItems().get(index).getName());
		Type.setText(t.Search(_Fname, _Lname).get(0).getItems().get(index).getType());
		this.setSize(300, 400);
		this.setMinimumSize(new Dimension(300, 400));
		this.setLocationRelativeTo(null);
		GridLayout gl = new GridLayout(4, 2, 5, 4);
		gl.setHgap(5);
		gl.setVgap(5);

		this.setLayout(gl);
		/* ajouter un menue avec show item add item and edit item */
		Title.setHorizontalAlignment(JLabel.CENTER);
		Title.setFont(new Font("arial", Font.BOLD, 15));
		Name.setHorizontalAlignment(JTextField.CENTER);
		Type.setHorizontalAlignment(JTextField.CENTER);
		bouton.addActionListener(this);
		this.setJMenuBar(new Menuitem(t, _Fname, _Lname, this,
				t.Search(_Fname, _Lname).get(0).getItems().get(index).getName(),
				t.Search(_Fname, _Lname).get(0).getItems().get(index).getType()).Get());
		this.getContentPane().add(Title);
		this.getContentPane().add(Name);
		this.getContentPane().add(Type);
		this.getContentPane().add(bouton);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == bouton) {
			if (!Name.getText().equals(con.Search(_Fname, _Lname).get(0).getItems().get(_index).getName())
					|| !Type.getText().equals(con.Search(_Fname, _Lname).get(0).getItems().get(_index).getType()))
				con.updateItem(con.GetId(_Fname, _Lname),
						con.Search(_Fname, _Lname).get(0).getItems().get(_index).getName(),
						con.Search(_Fname, _Lname).get(0).getItems().get(_index).getType(), Name.getText(),
						Type.getText());
			this.dispose();
		}
	}
}