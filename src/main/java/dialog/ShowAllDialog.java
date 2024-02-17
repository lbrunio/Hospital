package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import dao.MongoDB;
import dao.MongoManagement;
import frame.Menu;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowAllDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private final String DATABASE = "hospital";
	private final String COLLECTION = "patients";

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

		MongoManagement management = new MongoManagement();

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

			
			// Poner todos los patient como texto en el area text
			infoTextArea.setText(management.showAll(DATABASE, COLLECTION));

			

		}
	}
}
