package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteCocheFrame extends JFrame {
    public DeleteCocheFrame(CocheController controller, IdiomaController idiomaController, String idiomaActual) {
        List<String> cadenas = idiomaController.getCadenas(idiomaActual);
        setTitle(cadenas.get(5)); // "Delete Car" o "Borrar Coche"
        setSize(350, 200);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel matriculaLabel = new JLabel(cadenas.get(10)); // "License Plate" o "Matrícula"
        JTextField matriculaField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(matriculaLabel, gbc);
        gbc.gridx = 1;
        add(matriculaField, gbc);

        JButton deleteButton = new JButton(cadenas.get(5)); // "Delete" o "Borrar"
        deleteButton.addActionListener(e -> {
            String matricula = matriculaField.getText();
            if (!matricula.isEmpty()) {
                if (controller.getCoche(matricula) == null) {
                    // 16: "Coche no encontrado" o "Car not found" || 17: Error
                    JOptionPane.showMessageDialog(this, cadenas.get(16), cadenas.get(17), JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                //Confirmación de borrado
                int confirm = JOptionPane.showConfirmDialog(this, cadenas.get(23), cadenas.get(30), JOptionPane.YES_NO_OPTION); // "¿Estás seguro?" o "Are you sure?"
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                controller.deleteCoche(matricula);
                controller.guardarDatos();
                // 21: "Car deleted" o "Coche borrado" || 28: Confirmado o Confirmed
                JOptionPane.showMessageDialog(this, cadenas.get(21), cadenas.get(28), JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                // 13: "Rellene todos los campos" o "Fill all fields" || 17: Error
                JOptionPane.showMessageDialog(this, cadenas.get(13), cadenas.get(17), JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton cancelButton = new JButton(cadenas.get(14)); // "Cancel" o "Cancelar"
        cancelButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(cancelButton, gbc);
        gbc.gridx = 1;
        add(deleteButton, gbc);

        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}