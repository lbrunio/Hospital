package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import dao.MongoManagement;
import frame.Menu;

public class InsertDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTField;
	private JTextField lastNameTField;
	private JTextField birthTField;
	private JTextField addressField;
	private JTextField phoneField;
	private JTextField emailTextField;
	private JTextField contactNameTField;
	private JTextField emergencyAddressField;
	private JTextField contactPhoneField;

	private final String DATABASE = "hospital";
	private final String COLLECTION = "patients";

	/**
	 * Launch the application.
	 */
	public static void showDialog() {
		try {
			InsertDialog dialog = new InsertDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InsertDialog() {

		MongoManagement management = new MongoManagement();

		setBounds(100, 100, 404, 519);
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

		JLabel birthDateLabel = new JLabel("Date of birth: ");
		birthDateLabel.setBounds(63, 111, 79, 14);
		contentPanel.add(birthDateLabel);

		JLabel genderLabel = new JLabel("Gender: ");
		genderLabel.setBounds(63, 143, 79, 14);
		contentPanel.add(genderLabel);

		JLabel addressLabel = new JLabel("Address: ");
		addressLabel.setBounds(63, 175, 79, 14);
		contentPanel.add(addressLabel);

		JLabel phoneLabel = new JLabel("Phone number: ");
		phoneLabel.setBounds(63, 206, 98, 14);
		contentPanel.add(phoneLabel);

		JLabel emailLabel = new JLabel("E-mail: ");
		emailLabel.setBounds(63, 235, 79, 14);
		contentPanel.add(emailLabel);

		firstNameTField = new JTextField();
		firstNameTField.setBounds(171, 46, 120, 20);
		contentPanel.add(firstNameTField);
		firstNameTField.setColumns(10);

		lastNameTField = new JTextField();
		lastNameTField.setColumns(10);
		lastNameTField.setBounds(171, 77, 120, 20);
		contentPanel.add(lastNameTField);

		// Formatear el text field del birth date para que permita solo poner numeros
		try {
			MaskFormatter birthDateFormatter = new MaskFormatter("####-##-##");
			birthDateFormatter.setPlaceholderCharacter('_'); // Marcador para indicar el formato
			birthTField = new JFormattedTextField(birthDateFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
			birthTField = new JTextField();
		}
		birthTField.setBounds(171, 108, 120, 20);
		contentPanel.add(birthTField);
		birthTField.setColumns(10);

		JComboBox genderCBox = new JComboBox();
		genderCBox.setModel(new DefaultComboBoxModel(new String[] { "-- Select One --", "Male", "Female" }));
		genderCBox.setBounds(171, 139, 120, 22);
		contentPanel.add(genderCBox);

		addressField = new JTextField();
		addressField.setBounds(171, 172, 120, 20);
		contentPanel.add(addressField);
		addressField.setColumns(10);

		// Formatear el text field del phone number para que permita solo poner numeros
		try {
			MaskFormatter phoneFormatter = new MaskFormatter("(###) #########");
			phoneFormatter.setPlaceholderCharacter('_'); // Marcador para indicar el formato
			phoneField = new JFormattedTextField(phoneFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
			phoneField = new JTextField();
		}
		phoneField.setBounds(171, 377, 120, 20);
		contentPanel.add(phoneField);
		phoneField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setBounds(172, 232, 119, 20);
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
		emergencyContactLabel.setBounds(10, 281, 368, 27);
		contentPanel.add(emergencyContactLabel);

		JLabel contactLabel = new JLabel("Contact Name: ");
		contactLabel.setBounds(63, 319, 98, 14);
		contentPanel.add(contactLabel);

		JLabel relationLabel = new JLabel("Relation:");
		relationLabel.setBounds(63, 348, 79, 14);
		contentPanel.add(relationLabel);

		JLabel phoneContactLabel = new JLabel("Phone number:");
		phoneContactLabel.setBounds(63, 380, 79, 14);
		contentPanel.add(phoneContactLabel);

		JLabel addressContactLabel = new JLabel("Address: ");
		addressContactLabel.setBounds(63, 415, 79, 14);
		contentPanel.add(addressContactLabel);

		contactNameTField = new JTextField();
		contactNameTField.setBounds(171, 316, 120, 20);
		contentPanel.add(contactNameTField);
		contactNameTField.setColumns(10);

		JComboBox relationCBox = new JComboBox();
		relationCBox
				.setModel(new DefaultComboBoxModel(new String[] { "-- Select One --", "Family", "Friends", "Other" }));
		relationCBox.setBounds(171, 344, 120, 22);
		contentPanel.add(relationCBox);

		// Formatear el text field del phone number para que permita solo poner numeros
		try {
			MaskFormatter contactPhoneFormatter = new MaskFormatter("(###) #########");
			contactPhoneFormatter.setPlaceholderCharacter('_'); // Marcador para indicar el formato
			contactPhoneField = new JFormattedTextField(contactPhoneFormatter);
		} catch (ParseException e) {
			e.printStackTrace();
			contactPhoneField = new JTextField();
		}
		contactPhoneField.setBounds(171, 203, 120, 20);
		contentPanel.add(contactPhoneField);
		contactPhoneField.setColumns(10);

		emergencyAddressField = new JTextField();
		emergencyAddressField.setBounds(171, 412, 120, 20);
		contentPanel.add(emergencyAddressField);
		emergencyAddressField.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
			    JButton okButton = new JButton("OK");
			    okButton.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			            String firstName = firstNameTField.getText();
			            String lastName = lastNameTField.getText();
			            Date dateOfBirth = null;

			            // Formatear el text field del birth date para que el formato de la fecha sea correcta
			            try {
			                dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(birthTField.getText());
			            } catch (ParseException ex) {
			                ex.printStackTrace();
			            }

			            // Guardar todos los datos del patient puestos en el text fields
			            String gender = (String) genderCBox.getSelectedItem();
			            String address = addressField.getText();
			            String phoneNumber = contactPhoneField.getText();
			            String email = emailTextField.getText();
			            String emergencyContactName = contactNameTField.getText();
			            String relation = (String) relationCBox.getSelectedItem();
			            String emergencyPhone = phoneField.getText();		
			            String emergencyAddress = emergencyAddressField.getText();
			            
//			            changeLabelColorIfEmpty(firstNameLabel, firstNameTField);
//			            changeLabelColorIfEmpty(lastNameLabel, lastNameTField);
//			            changeLabelColorIfEmpty(addressLabel, addressField);
//			            changeLabelColorIfEmpty(phoneLabel, phoneField);
//			            changeLabelColorIfEmpty(emailLabel, emailTextField);
//			            changeLabelColorIfEmpty(contactLabel, contactNameTField);
//			            changeLabelColorIfEmpty(phoneContactLabel, contactPhoneField);
//			            changeLabelColorIfEmpty(addressContactLabel, emergencyAddressField);
//			            changeTextColorIfEmpty(genderLabel, genderCBox);
//			            changeTextColorIfEmpty(relationLabel, relationCBox);
//			            changeLabelColorIfEmpty(birthDateLabel, birthTField);

			            // Verificar si algun campo obligatorio esta vacio
			            if (firstNameTField.getText().isEmpty() || lastNameTField.getText().isEmpty()
			                    || birthTField.getText().isEmpty() || genderCBox.getSelectedItem().equals("-- Select One --")
			                    || genderCBox.getSelectedItem().toString().isEmpty() || addressField.getText().isEmpty()
			                    || phoneField.getText().isEmpty() || emailTextField.getText().isEmpty()
			                    || contactNameTField.getText().isEmpty() || relationCBox.getSelectedItem().equals("-- Select One --")
			                    || contactPhoneField.getText().isEmpty() || emergencyAddressField.getText().isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Please fill in all the required fields.");
			            } else {
			                // Crear el patient
			                Document patient = new Document().append("first_name", firstName)
			                        .append("last_name", lastName).append("date_of_birth", dateOfBirth)
			                        .append("gender", gender).append("address", address)
			                        .append("phone_number", phoneNumber).append("email_address", email)
			                        .append("medical_information", new Document()).append("emergency_contact",
			                                new Document().append("name", emergencyContactName)
			                                        .append("relation", relation).append("phone_number", emergencyPhone)
			                                        .append("address", emergencyAddress));

			                // Insertar el patient en la base de datos
			                boolean result = management.insert(DATABASE, COLLECTION, patient);

			                // Insertado correctamente
			                if (result) {
			                    // Preguntar si agregar otro patient
			                    int option = JOptionPane.showConfirmDialog(null,
			                            "Data inserted. Would you like to add another piece of information? ",
			                            "Confirmation", JOptionPane.YES_NO_OPTION);

			                    // Opcion YES
			                    if (option == JOptionPane.YES_OPTION) {
			                        // Limpiar los campos de texto
			                        firstNameTField.setText("");
			                        lastNameTField.setText("");
			                        birthTField.setText("");
			                        genderCBox.setSelectedIndex(0);
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
			                        setVisible(false); // Ocultar esta ventana (InsertDialog)
			                    }
			                } else {
			                    // Error al insertar
			                    JOptionPane.showMessageDialog(null, "Error: Not inserted.");
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

						// Confirmar la salida de la ventana
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
	

	
	private void changeLabelColorIfEmpty(JLabel label, JTextField textField) {
	    Color redColor = Color.RED;
	    if (textField.getText().isEmpty()) {
	        label.setForeground(redColor);
	    } else {
	        label.setForeground(Color.BLACK);
	    }
	}

	private void changeTextColorIfEmpty(JLabel label, JComboBox<?> comboBox) {
	    Color redColor = Color.RED;

	    if (comboBox.getSelectedItem() != null && comboBox.getSelectedItem().toString().equals("-- Select One --")) {
	        label.setForeground(redColor);
	    } else {
	        label.setForeground(Color.BLACK);
	    }
	}



}
