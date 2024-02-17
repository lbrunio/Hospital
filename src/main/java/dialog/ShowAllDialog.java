package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.MongoManagement;
import frame.Menu;

public class ShowAllDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private final String DATABASE = "hospital";
	private final String COLLECTION = "patients";
	private MongoManagement management = new MongoManagement();
	/**
	 * Launch the application.
	 */
	public static void showWindow() {
		try {
			ShowAllDialog dialog = new ShowAllDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
			dialog.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowAllDialog() {

		setBounds(100, 100, 385, 444);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());

		JLabel showLabel = new JLabel("LIST OF PATIENTS");
		showLabel.setHorizontalAlignment(SwingConstants.CENTER);
		showLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		contentPanel.add(showLabel, BorderLayout.NORTH);

		JTextArea infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		infoTextArea.setWrapStyleWord(true);
		infoTextArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(infoTextArea);
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton exitButton = new JButton("EXIT");
			exitButton.addActionListener(new ActionListener() {
				// Salir de esta ventana
				public void actionPerformed(ActionEvent e) {
					Menu.showWindow();
					setVisible(false);
				}
			});
			exitButton.setActionCommand("Cancel");
			buttonPane.add(exitButton);

			// Poner todos los pacientes como texto en el área de texto automáticamente al
			// abrir la ventana
			infoTextArea.setText(management.showAll(DATABASE, COLLECTION));

		}

		// Menú de filtrado
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu filterMenu = new JMenu("Filter");
		menuBar.add(filterMenu);

		JMenuItem byFirstNameAscItem = new JMenuItem("By First Name Asc");
		byFirstNameAscItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterAndDisplay("first_name", 1);
			}
		});
		filterMenu.add(byFirstNameAscItem);

		JMenuItem byFirstNameDescItem = new JMenuItem("By First Name Desc");
		byFirstNameDescItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterAndDisplay("first_name", -1);
			}
		});
		filterMenu.add(byFirstNameDescItem);

		JMenuItem byLastNameAscItem = new JMenuItem("By Last Name Asc");
		byLastNameAscItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterAndDisplay("last_name", 1);
			}
		});
		filterMenu.add(byLastNameAscItem);

		JMenuItem byLastNameDescItem = new JMenuItem("By Last Name Desc");
		byLastNameDescItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterAndDisplay("last_name", -1);
			}
		});
		filterMenu.add(byLastNameDescItem);
	}

	private void filterAndDisplay(String field, int order) {
		
		JTextArea infoTextArea = (JTextArea) ((JScrollPane) contentPanel.getComponent(1)).getViewport().getView();

		// Filtrar los pacientes según el campo y orden especificados

		infoTextArea.setText(management.showAll(DATABASE, COLLECTION, field, order));

	}
}
