package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import dao.MongoManagement;
import frame.Menu;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class UpdatePersonalDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTField;
	private JTextField lastNameTField;
	private JTextField addressField;
	private JTextField phoneField;
	private JTextField emailTextField;
	private JTextField contactNameTField;
	private JTextField emergencyAddressField;
	private JTextField contactPhoneField;
	private JTextField searchTextField;

	private JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();

	private final String DATABASE = "hospital";
	private final String COLLECTION = "patients";

	/**
	 * Launch the application.
	 */
	public static void showWindow() {
		try {
			UpdatePersonalDialog dialog = new UpdatePersonalDialog();
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
	public UpdatePersonalDialog() {

		MongoManagement management = new MongoManagement();

		setBounds(100, 100, 760, 521);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel firstNameLabel = new JLabel("First Name: ");
		firstNameLabel.setBounds(63, 49, 79, 14);
		contentPanel.add(firstNameLabel);

		JLabel lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setBounds(63, 80, 79, 14);
		contentPanel.add(lastNameLabel);

		JLabel addressLabel = new JLabel("Address: ");
		addressLabel.setBounds(63, 111, 79, 14);
		contentPanel.add(addressLabel);

		JLabel phoneLabel = new JLabel("Phone number: ");
		phoneLabel.setBounds(63, 142, 98, 14);
		contentPanel.add(phoneLabel);

		JLabel emailLabel = new JLabel("E-mail: ");
		emailLabel.setBounds(63, 173, 79, 14);
		contentPanel.add(emailLabel);

		firstNameTField = new JTextField();
		firstNameTField.setEnabled(false);
		firstNameTField.setBounds(171, 46, 120, 20);
		contentPanel.add(firstNameTField);
		firstNameTField.setColumns(10);

		lastNameTField = new JTextField();
		lastNameTField.setEnabled(false);
		lastNameTField.setColumns(10);
		lastNameTField.setBounds(171, 77, 120, 20);
		contentPanel.add(lastNameTField);

		addressField = new JTextField();
		addressField.setEnabled(false);
		addressField.setBounds(171, 108, 120, 20);
		contentPanel.add(addressField);
		addressField.setColumns(10);

		try {
			MaskFormatter phoneFormatter = new MaskFormatter("(###) #########");
			phoneFormatter.setPlaceholderCharacter('_'); // Caracter de marcador de posicion para indicar el formato
			phoneField = new JFormattedTextField(phoneFormatter);
			phoneField.setEnabled(false);
		} catch (ParseException e) {
			e.printStackTrace();
			phoneField = new JTextField();
		}
		phoneField.setBounds(171, 300, 120, 20);
		contentPanel.add(phoneField);
		phoneField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setEnabled(false);
		emailTextField.setBounds(171, 170, 119, 20);
		contentPanel.add(emailTextField);
		emailTextField.setColumns(10);

		JLabel personalInfoLabel = new JLabel("PERSONAL INFORMATION");
		personalInfoLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		personalInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		personalInfoLabel.setBounds(10, 11, 368, 27);
		contentPanel.add(personalInfoLabel);

		JLabel emergencyContactLabel = new JLabel("EMERGENCY CONTACT");
		emergencyContactLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emergencyContactLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		emergencyContactLabel.setBounds(10, 198, 368, 27);
		contentPanel.add(emergencyContactLabel);

		JLabel contactLabel = new JLabel("Contact Name: ");
		contactLabel.setBounds(63, 239, 98, 14);
		contentPanel.add(contactLabel);

		JLabel relationLabel = new JLabel("Relation:");
		relationLabel.setBounds(63, 271, 79, 14);
		contentPanel.add(relationLabel);

		JLabel phoneContactLabel = new JLabel("Phone number:");
		phoneContactLabel.setBounds(63, 303, 79, 14);
		contentPanel.add(phoneContactLabel);

		JLabel addressContactLabel = new JLabel("Address: ");
		addressContactLabel.setBounds(63, 334, 79, 14);
		contentPanel.add(addressContactLabel);

		contactNameTField = new JTextField();
		contactNameTField.setEnabled(false);
		contactNameTField.setBounds(171, 236, 120, 20);
		contentPanel.add(contactNameTField);
		contactNameTField.setColumns(10);

		JComboBox relationCBox = new JComboBox();
		relationCBox.setEnabled(false);
		relationCBox
				.setModel(new DefaultComboBoxModel(new String[] { "-- Select One --", "Family", "Friends", "Other" }));
		relationCBox.setBounds(171, 267, 120, 22);
		contentPanel.add(relationCBox);

		try {
			MaskFormatter contactPhoneFormatter = new MaskFormatter("(###) #########");
			contactPhoneFormatter.setPlaceholderCharacter('_');
			contactPhoneField = new JFormattedTextField(contactPhoneFormatter);
			contactPhoneField.setEnabled(false);
		} catch (ParseException e) {
			e.printStackTrace();
			contactPhoneField = new JTextField();
		}
		contactPhoneField.setBounds(171, 139, 120, 20);
		contentPanel.add(contactPhoneField);
		contactPhoneField.setColumns(10);

		emergencyAddressField = new JTextField();
		emergencyAddressField.setEnabled(false);
		emergencyAddressField.setBounds(171, 331, 120, 20);
		contentPanel.add(emergencyAddressField);
		emergencyAddressField.setColumns(10);

		JLabel searchLabel = new JLabel("SEARCH PATIENT");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
		searchLabel.setBounds(342, 11, 368, 27);
		contentPanel.add(searchLabel);

		searchTextField = new JTextField();
		searchTextField.setBounds(362, 46, 254, 20);
		contentPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JTextArea infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		infoTextArea.setBounds(362, 75, 348, 354);
		contentPanel.add(infoTextArea);

		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(693, 75, 17, 354);
		contentPanel.add(scrollBar);

		JButton searchButton = new JButton("SEARCH");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchID = searchTextField.getText();

				Document result = management.findById(DATABASE, COLLECTION, searchID);

				if (result != null) {
					infoTextArea.setText(result.toJson(settings));
					firstNameTField.setEnabled(true);
					lastNameTField.setEnabled(true);

					addressField.setEnabled(true);
					phoneField.setEnabled(true);
					emailTextField.setEnabled(true);
					contactNameTField.setEnabled(true);
					contactPhoneField.setEnabled(true);
					emergencyAddressField.setEnabled(true);
					relationCBox.setEnabled(true);
				} else {
					firstNameTField.setEnabled(false);
					lastNameTField.setEnabled(false);

					addressField.setEnabled(false);
					phoneField.setEnabled(false);
					emailTextField.setEnabled(false);
					contactNameTField.setEnabled(false);
					contactPhoneField.setEnabled(false);
					emergencyAddressField.setEnabled(false);
					JOptionPane.showMessageDialog(null, "No patient selected, please search for a patient.");
				}

			}
		});
		searchButton.setBounds(621, 45, 89, 23);
		contentPanel.add(searchButton);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String searchID = searchTextField.getText();

						String firstName = firstNameTField.getText();
						String lastName = lastNameTField.getText();

						String address = addressField.getText();
						String phoneNumber = phoneField.getText();
						String email = emailTextField.getText();

						String emergencyContactName = contactNameTField.getText();
						String relation = (String) relationCBox.getSelectedItem();
						String emergencyPhone = contactPhoneField.getText();
						String emergencyAddress = emergencyAddressField.getText();

						
						if (searchTextField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please enter a patient ID");
						} else {

							Document update = new Document();

							if (firstNameTField.getText().isEmpty() || lastNameTField.getText().isEmpty()
									|| addressField.getText().isEmpty() || phoneField.getText().isEmpty()
									|| emailTextField.getText().isEmpty() || contactNameTField.getText().isEmpty()
									|| relationCBox.getSelectedIndex() == -1 || contactPhoneField.getText().isEmpty()
									|| emergencyAddressField.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Please fill in all the required fields.");

							}
						
							update.append("first_name", firstName).append("last_name", lastName)
									.append("address", address).append("phone_number", phoneNumber)
									.append("email_address", email).append("emergency_contact",
											new Document().append("name", emergencyContactName)
													.append("relation", relation).append("phone_number", emergencyPhone)
													.append("address", emergencyAddress));

							boolean updated = management.update(DATABASE, COLLECTION, searchID, update);

							if (updated) {
								int option = JOptionPane.showConfirmDialog(null,
										"Data updated. Would you like to update another piece of information? ",
										"Confirmation", JOptionPane.YES_NO_OPTION);
								if (option == JOptionPane.YES_OPTION) {
									// Limpiar los text fields
									firstNameTField.setText("");
									lastNameTField.setText("");
									addressField.setText("");
									phoneField.setText("");
									emailTextField.setText("");
									contactNameTField.setText("");
									contactPhoneField.setText("");
									emergencyAddressField.setText("");
									relationCBox.setSelectedIndex(0);
								} else {
									// Mostrar la ventana del menu
									Menu.showWindow();
									setVisible(false); // Ocultar esta venta(UpdatePersonalDialog)
								}
							} else {
								// Not updated
								JOptionPane.showMessageDialog(null, "Error: Not updated.");
							}

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
	}
}