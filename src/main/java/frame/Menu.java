package frame;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dialog.DeleteDialog;
import dialog.InsertDialog;
import dialog.SearchDialog;
import dialog.ShowAllDialog;
import dialog.UpdateMedicalDialog;
import dialog.UpdatePersonalDialog;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	public static void showWindow() {
		try {
			Menu frame = new Menu();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 303, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton insertButton = new JButton("INSERT");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertDialog.showDialog();
				setVisible(false);
			}
		});
		insertButton.setBounds(66, 28, 134, 23);
		contentPane.add(insertButton);

		JButton deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteDialog.showWindow();
				
			}
		});
		deleteButton.setBounds(66, 96, 134, 23);
		contentPane.add(deleteButton);

		JButton showAllButton = new JButton("SHOW ALL");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowAllDialog.showWindow();
				setVisible(false);
			}
		});
		showAllButton.setBounds(66, 130, 134, 23);
		contentPane.add(showAllButton);

		JButton updateButton = new JButton("UPDATE");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Personal", "Medical"};
				
				int option = JOptionPane.showOptionDialog(null,
						"What information would you like to update?", 
						"Update Information", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				
				if (option == JOptionPane.YES_OPTION) {
                    UpdatePersonalDialog.showWindow();
                    
                } else if (option == JOptionPane.NO_OPTION) {
                    
                    UpdateMedicalDialog.showWindow();
                }
			}
		});
		updateButton.setBounds(66, 62, 134, 23);
		contentPane.add(updateButton);

		JButton exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.showWindow();
				setVisible(false);
			}
		});
		exitButton.setBounds(83, 239, 107, 23);
		contentPane.add(exitButton);
		
		JButton searchButton = new JButton("SEARCH PATIENT");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchDialog.showWindow();
				setVisible(false);
				
			}
		});
		searchButton.setBounds(66, 164, 134, 23);
		contentPane.add(searchButton);
	}
}
