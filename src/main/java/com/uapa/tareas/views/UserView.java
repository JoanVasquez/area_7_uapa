package com.uapa.tareas.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.uapa.tareas.models.User;
import com.uapa.tareas.service.UserDao;
import com.uapa.tareas.utils.GenerateReport;

public class UserView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JTextArea addressField;
    private JSpinner ageSpinner;
    private JButton submitButton;
    private JButton reportButton;
    private UserDao userDao = new UserDao();

    public UserView() {
        // Apply Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Set JFrame properties
        setTitle("Formulario de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add form components
        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        JLabel descriptionLabel = new JLabel("Dirección:");
        addressField = new JTextArea(3, 20);
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        JLabel ageLabel = new JLabel("Edad:");
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(ageLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(ageSpinner, gbc);

        // Add submit button
        submitButton = new JButton("Enviar");
        submitButton.addActionListener(this::handleSubmit);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        reportButton = new JButton("Reporte");
        reportButton.addActionListener(this::handleReport);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(reportButton, gbc);

        // Add form panel to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        pack(); // Adjust size to fit components
    }

    public void handleSubmit(ActionEvent e) {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        int age = (int) ageSpinner.getValue();

        if (name.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setAge(age);

        userDao.saveEntity(user);

        JOptionPane.showMessageDialog(this, "Usuario guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleReport(ActionEvent e) {
        new GenerateReport().generateReport();
        JOptionPane.showMessageDialog(this, "Reporte generado con éxito.", "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new UserView().setVisible(true);
    }
}
