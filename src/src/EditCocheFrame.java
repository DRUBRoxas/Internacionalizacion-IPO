package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditCocheFrame extends JFrame {
    public EditCocheFrame(CocheController controller, IdiomaController idiomaController, String idiomaActual) {
        List<String> cadenas = idiomaController.getCadenas(idiomaActual);

        setTitle(cadenas.get(6)); // "Edit Car" o "Editar Coche"
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

        JButton editButton = new JButton(cadenas.get(18)); // "Update" o "Actualizar"
        editButton.addActionListener(e -> {
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            String matricula = matriculaField.getText();
            if (!marca.isEmpty() && !modelo.isEmpty() && !matricula.isEmpty()) {
                if (controller.updateCoche(matricula, marca, modelo)) {
                    controller.guardarDatos();
                    // 19: "Car updated" o "Coche actualizado" || 28: Completado
                    JOptionPane.showMessageDialog(this, cadenas.get(19), cadenas.get(28), JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    // 16: "Coche no encontrado" o "Car not found" || 17: Error
                    JOptionPane.showMessageDialog(this, cadenas.get(16), cadenas.get(17), JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // 13: "Rellene todos los campos" o "Fill all fields" || 17: Error
                JOptionPane.showMessageDialog(this, cadenas.get(13), cadenas.get(17), JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton cancelButton = new JButton(cadenas.get(14)); // "Cancel" o "Cancelar"
        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(cancelButton, gbc);
        gbc.gridx = 1;
        add(editButton, gbc);

        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}