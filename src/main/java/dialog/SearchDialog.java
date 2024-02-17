package dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

public class SearchDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField searchTextField;
    private JTextArea infoTextArea;
    private final String DATABASE = "hospital";
    private final String COLLECTION = "patients";
    private final JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();
    private final MongoManagement management = new MongoManagement();

    /**
     * Launch the application.
     */
    public static void showWindow() {
        try {
            SearchDialog dialog = new SearchDialog();
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
    public SearchDialog() {
    	
        setResizable(false);
        setBounds(100, 100, 417, 514);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel searchDialogLabel = new JLabel("SEARCH PATIENT");
        searchDialogLabel.setFont(new Font("Cambria Math", Font.BOLD, 12));
        searchDialogLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchDialogLabel.setBounds(10, 11, 381, 14);
        contentPanel.add(searchDialogLabel);

        searchTextField = new JTextField();
        searchTextField.setToolTipText("Search patient by id, first name or last name");
        searchTextField.setBounds(20, 36, 271, 20);
        contentPanel.add(searchTextField);
        searchTextField.setColumns(10);

        JButton searchButton = new JButton("SEARCH");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchPatients();
            }
        });
        searchButton.setBounds(301, 35, 89, 23);
        contentPanel.add(searchButton);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setBounds(20, 67, 371, 364);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        scrollPane.setBounds(20, 67, 371, 364);
        contentPanel.add(scrollPane);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton exitButton = new JButton("EXIT");
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Menu.showWindow();
                    setVisible(false);
                }
            });
            exitButton.setActionCommand("Cancel");
            buttonPane.add(exitButton);
        }

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu filterMenu = new JMenu("Filter");
        menuBar.add(filterMenu);

        JMenuItem byFirstNameAscItem = new JMenuItem("By First Name Asc");
        byFirstNameAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAndDisplay("first_name", 1);
            }
        });
        filterMenu.add(byFirstNameAscItem);

        JMenuItem byFirstNameDescItem = new JMenuItem("By First Name Desc");
        byFirstNameDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAndDisplay("first_name", -1);
            }
        });
        filterMenu.add(byFirstNameDescItem);

        JMenuItem byLastNameAscItem = new JMenuItem("By Last Name Asc");
        byLastNameAscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAndDisplay("last_name", 1);
            }
        });
        filterMenu.add(byLastNameAscItem);

        JMenuItem byLastNameDescItem = new JMenuItem("By Last Name Desc");
        byLastNameDescItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchAndDisplay("last_name", -1);
            }
        });
        filterMenu.add(byLastNameDescItem);
    }

    private void searchPatients() {
        String patientSearch = searchTextField.getText();

        if (patientSearch.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter patient ID, first name, or last name.");
            return;
        }

        // Buscar el paciente segun el texto ingresado (ID, nombre o apellido)
        List<Document> results = management.findPatients(DATABASE, COLLECTION, patientSearch);

        // Mostrar el resultado en el area text
        if (!results.isEmpty()) {
            StringBuilder resultText = new StringBuilder();
            for (Document doc : results) {
                resultText.append(doc.toJson(settings)).append("\n\n"); 
            }
            infoTextArea.setText(resultText.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No patient found for the given ID, first name, or last name.");
        }
    }

    private void searchAndDisplay(String field, int order) {
        String patientSearch = searchTextField.getText();

        if (patientSearch.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter patient ID, first name, or last name.");
            return;
        }

        // Buscar el paciente según el texto ingresado (ID, nombre o apellido) y el campo de ordenamiento
        List<Document> results = management.findPatients(DATABASE, COLLECTION, patientSearch, field, order);

        // Mostrar el resultado en el area de texto
        if (!results.isEmpty()) {
            StringBuilder resultText = new StringBuilder();
            for (Document doc : results) {
                resultText.append(doc.toJson(settings)).append("\n\n"); 
            }
            infoTextArea.setText(resultText.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No patient found for the given ID, first name, or last name.");
        }
    }
}
