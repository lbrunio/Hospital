package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import dao.MongoManagement;
import frame.Menu;

public class DeleteDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField searchTextField;

	private final String DATABASE = "hospital";
	private final String COLLECTION = "patients";

	// Ajuste para que el json sea mas legible
	private JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();

	/**
	 * Launch the application.
	 */
	public static void showWindow() {
		try {
			DeleteDialog dialog = new DeleteDialog();
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
	public DeleteDialog() {

		MongoManagement management = new MongoManagement();

		setBounds(100, 100, 365, 420);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel deleteLabel = new JLabel("DELETE PATIENT");
		deleteLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		deleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deleteLabel.setBounds(10, 11, 329, 14);
		contentPanel.add(deleteLabel);

		searchTextField = new JTextField();
		searchTextField.setBounds(10, 36, 230, 20);
		contentPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JTextArea infoTextArea = new JTextArea();
		infoTextArea.setLineWrap(true);
		infoTextArea.setWrapStyleWord(true);
		infoTextArea.setEditable(false);
		infoTextArea.setBounds(1, 1, 327, 229);
		contentPanel.add(infoTextArea);

		JButton searchButton = new JButton("SEARCH");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Guardamos el id que se ha puesto en el text field
				String searchID = searchTextField.getText();

				// Si el text field de search esta vacia, muestra un message dialog
				if (searchTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please search for a patient");
				} else {

					// Buscar el patient por id
					Document result = management.findById(DATABASE, COLLECTION, searchID);

					// Si encuentra el patient, muestra los datos en el text area
					if (result != null) {
						infoTextArea.setText(result.toJson(settings));
					} else {
						JOptionPane.showMessageDialog(null, "No patient selected, please search for a patient.");
					}

				}
			}
		});
		searchButton.setBounds(250, 35, 89, 23);
		contentPanel.add(searchButton);

		JScrollPane scrollPane = new JScrollPane(infoTextArea);
		scrollPane.setBounds(10, 67, 329, 270);
		contentPanel.add(scrollPane);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton deleteButton = new JButton("DELETE");
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// Guardar el id que se ha puesto en el text field
						String searchID = searchTextField.getText();

						// Si el text field de search esta vacia, muestra un message dialog
						if (searchTextField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please search for a patient");
						} else {

							// Confirmar el borrado del patient
							int option = JOptionPane.showConfirmDialog(null,
									"Confirm to delete the patient with ID:" + searchTextField.getText(),
									"Confirmation", JOptionPane.YES_NO_OPTION);

							// Opcion YES
							if (option == JOptionPane.YES_OPTION) {

								// Borrar el patient
								boolean deleted = management.deletePatient(DATABASE, COLLECTION, searchID);

								// Verificar si se ha eliminado correctamente
								if (deleted) {

									// Preguntar para borrar otro patient
									int optionDeleted = JOptionPane.showConfirmDialog(null,
											"Patient with ID:" + searchTextField.getText()
													+ " deleted. Delete another? ",
											"Confirmation", JOptionPane.YES_NO_OPTION);

									// Opcion YES, limpiar los text fields
									if (optionDeleted == JOptionPane.YES_OPTION) {
										searchTextField.setText("");
										infoTextArea.setText("");

										// Opcion NO, mostrar la ventana del menu
									} else {
										setVisible(false);
										Menu.showWindow();
									}

									// Search text field no vacia pero ID inexistente
								} else {
									JOptionPane.showMessageDialog(null, "ERROR: Not found or not deleted");
								}

								// Opcion de no borrar el patient, limpiar el search text field y text area
							} else {
								searchTextField.setText("");
								infoTextArea.setText("");
							}

						}

					}
				});
				deleteButton.setActionCommand("DELETE");
				buttonPane.add(deleteButton);
				getRootPane().setDefaultButton(deleteButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false); // Ocultar esta ventana(DeleteDialog)
						Menu.showWindow(); // Mostrar la ventana del menu
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
