package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import dao.MongoManagement;
import frame.Menu;

public class UpdateMedicalDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField insuTField;
	private JTextField bloodTextField;
	private JTextField searchTextField;

	private final String DATABASE = "hospital";
	private final String COLLECTION = "patients";

	JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();

	/**
	 * Launch the application.
	 */
	public static void showWindow() {
		try {
			UpdateMedicalDialog dialog = new UpdateMedicalDialog();
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
	public UpdateMedicalDialog() {

		MongoManagement management = new MongoManagement();

		setBounds(100, 100, 675, 602);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel medicalLabel = new JLabel("MEDICAL INFORMATION");
		medicalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		medicalLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		medicalLabel.setBounds(10, 36, 286, 14);
		contentPanel.add(medicalLabel);

		JLabel insuranceLabel = new JLabel("Insurance number: ");
		insuranceLabel.setBounds(20, 61, 94, 14);
		contentPanel.add(insuranceLabel);

		JLabel bloodLabel = new JLabel("Blood type:");
		bloodLabel.setBounds(20, 95, 94, 14);
		contentPanel.add(bloodLabel);

		JLabel allergiesLabel = new JLabel("Allergies:");
		allergiesLabel.setBounds(20, 120, 94, 14);
		contentPanel.add(allergiesLabel);

		JLabel medicationLabel = new JLabel("Medications: ");
		medicationLabel.setBounds(20, 252, 94, 14);
		contentPanel.add(medicationLabel);

		JLabel historyLabel = new JLabel("Medical history: ");
		historyLabel.setBounds(20, 390, 94, 14);
		contentPanel.add(historyLabel);

		JLabel searchPatientLabel = new JLabel("SEARCH PATIENT");
		searchPatientLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchPatientLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		searchPatientLabel.setBounds(306, 36, 286, 14);
		contentPanel.add(searchPatientLabel);

		insuTField = new JTextField();
		insuTField.setEnabled(false);
		insuTField.setBounds(134, 58, 122, 20);
		contentPanel.add(insuTField);
		insuTField.setColumns(10);

		bloodTextField = new JTextField();
		bloodTextField.setToolTipText("Blood type 1, Blood type 2");
		bloodTextField.setEnabled(false);
		bloodTextField.setBounds(134, 89, 122, 20);
		contentPanel.add(bloodTextField);
		bloodTextField.setColumns(10);

		JTextArea allergiesArea = new JTextArea();
		allergiesArea.setToolTipText("Allergy 1\r\nAllergy 2\r\nAllergy 3");
		allergiesArea.setEnabled(false);
		allergiesArea.setBounds(134, 115, 122, 100);
		contentPanel.add(allergiesArea);

		JTextArea medicationsArea = new JTextArea();
		medicationsArea.setToolTipText("Medication 1\r\nMedication 2\r\nMedication 3");
		medicationsArea.setEnabled(false);
		medicationsArea.setBounds(134, 247, 122, 100);
		contentPanel.add(medicationsArea);

		JTextArea medicalArea = new JTextArea();
		medicalArea.setToolTipText("Medical history 1\r\nMedical history 2\r\nMedical history 3");
		medicalArea.setEnabled(false);
		medicalArea.setBounds(134, 385, 122, 100);
		contentPanel.add(medicalArea);

		searchTextField = new JTextField();
		searchTextField.setBounds(316, 58, 210, 20);
		contentPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JTextArea infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		infoTextArea.setBounds(316, 89, 319, 396);
		contentPanel.add(infoTextArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// Guardar el patient id introducido por el text field
						String searchID = searchTextField.getText();

						Document update = new Document();
						String insurance = insuTField.getText();
						String[] bloods = bloodTextField.getText().split("\\s*,\\s*");
						String[] allergies = allergiesArea.getText().split("\\r?\\n");
						String[] medications = medicationsArea.getText().split("\\r?\\n");
						String[] medicalHistory = medicalArea.getText().split("\\r?\\n");

						boolean updated = false;

						// Si insurance esta vacia, actualiza todo menos el insurance
						if (insuTField.getText().isEmpty()) {
							update.append("medical_information",
									new Document().append("blood_type", Arrays.asList(bloods))
											.append("allergies", Arrays.asList(allergies))
											.append("medications", Arrays.asList(medications))
											.append("medical_history", Arrays.asList(medicalHistory)));

							updated = management.update(DATABASE, COLLECTION, searchID, update);

							// Actualiza todo
						} else {
							update.append("medical_information",
									new Document().append("insurance", insurance)
											.append("blood_type", Arrays.asList(bloods))
											.append("allergies", Arrays.asList(allergies))
											.append("medications", Arrays.asList(medications))
											.append("medical_history", Arrays.asList(medicalHistory)));
							updated = management.update(DATABASE, COLLECTION, searchID, update);
						}

						// Updated
						if (updated) {

							// Preguntar a actualizar otro patient
							int option = JOptionPane.showConfirmDialog(null,
									"Data updated. Would you like to update another piece of information? ",
									"Confirmation", JOptionPane.YES_NO_OPTION);
							if (option == JOptionPane.YES_OPTION) {

								// Limpiar todos los text fields
								insuTField.setText("");
								bloodTextField.setText("");
								allergiesArea.setText("");
								medicationsArea.setText("");
								medicalArea.setText("");

							} else {
								// Mostrar la ventana del menu
								Menu.showWindow();
								setVisible(false); // Ocultar esta ventana(UpdateMedicalDialog)
							}
						} else {
							// Not updated
							JOptionPane.showMessageDialog(null, "Error: Not updated.");
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// Confirmar salida
						int option = JOptionPane.showConfirmDialog(null, "Exit?");

						if (option == JOptionPane.YES_OPTION) {
							Menu.showWindow();
							setVisible(false);
						}
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		JButton searchButton = new JButton("SEARCH");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchID = searchTextField.getText();

				// Buscar el patient por ID
				Document result = management.findById(DATABASE, COLLECTION, searchID);

				// Found
				if (result != null) {

					// Mostrar los datos del patient en area text
					infoTextArea.setText(result.toJson(settings));

					// Habilitar los text fields
					insuTField.setEnabled(true);
					bloodTextField.setEnabled(true);
					allergiesArea.setEnabled(true);
					medicationsArea.setEnabled(true);
					medicalArea.setEnabled(true);

					// Not found
				} else {

					// Deshabilitar los text fields
					insuTField.setEnabled(false);
					bloodTextField.setEnabled(false);
					allergiesArea.setEnabled(false);
					medicationsArea.setEnabled(false);
					medicalArea.setEnabled(false);
					JOptionPane.showMessageDialog(null, "No patient selected, please search for a patient.");
				}
			}
		});
		searchButton.setBounds(536, 57, 89, 23);
		contentPanel.add(searchButton);

	}
}
