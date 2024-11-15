package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddCocheFrame extends JFrame {
    private CocheController controller;
    private IdiomaController idiomaController;
    private String idiomaActual;

    public AddCocheFrame(CocheController controller, IdiomaController idiomaController, String idiomaActual) {
        this.controller = controller;
        this.idiomaController = idiomaController;
        this.idiomaActual = idiomaActual;

        List<String> cadenas = idiomaController.getCadenas(idiomaActual);

        setTitle(cadenas.get(4)); // "Add Car" o "Añadir Coche"
        setSize(350, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel marcaLabel = new JLabel(cadenas.get(8)); // "Brand" o "Marca"
        JTextField marcaField = new JTextField(20);
        JLabel modeloLabel = new JLabel(cadenas.get(9)); // "Model" o "Modelo"
        JTextField modeloField = new JTextField(20);
        JLabel matriculaLabel = new JLabel(cadenas.get(10)); // "License Plate" o "Matrícula"
        JTextField matriculaField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(marcaLabel, gbc);
        gbc.gridx = 1;
        add(marcaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(modeloLabel, gbc);
        gbc.gridx = 1;
        add(modeloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(matriculaLabel, gbc);
        gbc.gridx = 1;
        add(matriculaField, gbc);

        JButton addButton = new JButton(cadenas.get(11)); // "Add" o "Añadir"
        addButton.addActionListener(e -> {
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            String matricula = matriculaField.getText();
            if(!marca.isEmpty() && !modelo.isEmpty() && !matricula.isEmpty()) {
                if(!controller.addCoche(new Coche(marca, modelo, matricula))) {
                    JOptionPane.showMessageDialog(this,cadenas.get(26), cadenas.get(4),JOptionPane.INFORMATION_MESSAGE); // "Car already exists"
                } else {
                    controller.guardarDatos();
                    JOptionPane.showMessageDialog(this,cadenas.get(16), cadenas.get(4),JOptionPane.INFORMATION_MESSAGE); // "Car added"
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this,cadenas.get(13), cadenas.get(4),JOptionPane.INFORMATION_MESSAGE); // "Car deleted"
            }
        });

        JButton cancelButton = new JButton(cadenas.get(14)); // "Cancel" o "Cancelar"
        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(cancelButton, gbc);
        gbc.gridx = 1;
        add(addButton, gbc);

        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}