package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteCocheFrame extends JFrame {
    private CocheController controller;
    private IdiomaController idiomaController;
    private String idiomaActual;
    private Coche cocheSeleccionado;
    public DeleteCocheFrame(CocheController controller, IdiomaController idiomaController, String idiomaActual, Coche selectedCoche) {
        this.controller = controller;
        this.idiomaController = idiomaController;
        this.idiomaActual = idiomaActual;
        List<String> cadenas = idiomaController.getCadenas(idiomaActual);

        setTitle(cadenas.get(5)); // "Delete Car" o "Borrar Coche"
        setSize(350, 200);
        setLayout(new GridBagLayout());
        if(selectedCoche != null) {
            setCocheSeleccionado(selectedCoche);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Confirmation Label for License Plate
            JLabel matriculaLabel = new JLabel(cadenas.get(10) + ": " + cocheSeleccionado.getMatricula()); // "License Plate: XYZ1234"
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            add(matriculaLabel, gbc);

            // Confirmation Question Label
            JLabel confirmacionLabel = new JLabel(cadenas.get(23)); // "Are you sure you want to delete the car with license plate..."
            gbc.gridy = 1;
            add(confirmacionLabel, gbc);

            // Reset grid width for buttons
            gbc.gridwidth = 1;

            // "No" Button
            JButton buttonNo = new JButton(cadenas.get(25)); // "No"
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.CENTER; // Center-aligns within cell


            // "Yes" Button
            JButton buttonYes = new JButton(cadenas.get(24)); // "Yes"
            add(buttonYes, gbc);
            gbc.gridx = 1;
            add(buttonNo, gbc);

            // Action listeners for the buttons
            buttonYes.addActionListener(e -> {
                controller.deleteCoche(cocheSeleccionado.getMatricula());
                controller.guardarDatos();
                JOptionPane.showMessageDialog(this,cadenas.get(21), cadenas.get(5),JOptionPane.INFORMATION_MESSAGE); // "Car deleted"
                dispose();
            });

            buttonNo.addActionListener(e -> dispose());

        }else{
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
                if(!matricula.isEmpty()) {
                    if(controller.getCoche(matricula) == null) {
                        JOptionPane.showMessageDialog(this,cadenas.get(16), cadenas.get(5),JOptionPane.INFORMATION_MESSAGE); // "Car deleted"
                        return;
                    }
                    controller.deleteCoche(matricula);
                    controller.guardarDatos();
                    JOptionPane.showMessageDialog(this,cadenas.get(21), cadenas.get(5),JOptionPane.INFORMATION_MESSAGE); // "Car deleted"
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(this,cadenas.get(13), cadenas.get(5),JOptionPane.INFORMATION_MESSAGE); // "Car deleted"
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

    public void setCocheSeleccionado(Coche cocheSeleccionado) {
        this.cocheSeleccionado = cocheSeleccionado;
    }
}