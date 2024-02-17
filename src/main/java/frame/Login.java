package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */

	public static void showWindow() {
		try {
			Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userTextField = new JTextField();
		userTextField.setBounds(219, 77, 86, 20);
		contentPane.add(userTextField);
		userTextField.setColumns(10);
		
		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(115, 80, 94, 14);
		contentPane.add(userLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(115, 118, 94, 14);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(219, 115, 86, 20);
		contentPane.add(passwordField);
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = userTextField.getText();
				char[] password = passwordField.getPassword();
                String passwordText = new String(password);
                
                if(username.equals(USERNAME) && passwordText.equals(PASSWORD)) {
                	Menu.showWindow();
                	setVisible(false);
                } else {
                	JOptionPane.showMessageDialog(null, "ERROR: Username or password incorrect");
                }
                
			}
		});
		enterButton.setBounds(83, 188, 89, 23);
		contentPane.add(enterButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setBounds(255, 188, 89, 23);
		contentPane.add(exitButton);
	}
}
