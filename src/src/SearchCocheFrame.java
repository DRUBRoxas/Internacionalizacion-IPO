package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchCocheFrame extends JFrame {
    private CocheController controller;
    private IdiomaController idiomaController;
    private String idiomaActual;

    public SearchCocheFrame(CocheController controller, IdiomaController idiomaController, String idiomaActual) {
        this.controller = controller;
        this.idiomaController = idiomaController;
        this.idiomaActual = idiomaActual;

        List<String> cadenas = idiomaController.getCadenas(idiomaActual);

        setTitle(cadenas.get(29)); // "Search" o "Buscar"
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Panel superior: campo de búsqueda y botón
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel(cadenas.get(10)); // "License Plate" o "Matrícula"
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton(cadenas.get(29)); // "Search" o "Buscar"

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Tabla para mostrar resultados
        String[] columnNames = {cadenas.get(8), cadenas.get(9), cadenas.get(10)}; // "Brand", "Model", "License Plate"
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Cargar todos los coches al inicio
        cargarTodosLosCoches(tableModel, controller.getCoches());

        // Acción del botón de búsqueda
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            Map<String, Coche> coches = controller.getCoches(); // Map<String, Coche>

            if (searchTerm.isEmpty()) {
                // Si no hay término de búsqueda, mostrar todos los coches
                cargarTodosLosCoches(tableModel, coches);
            } else {
                // Filtrar coches por matrícula
                List<Coche> resultados = coches.entrySet().stream()
                        .filter(entry -> entry.getKey().contains(searchTerm))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());

                // Actualizar la tabla con los resultados
                tableModel.setRowCount(0); // Limpiar la tabla
                if (!resultados.isEmpty()) {
                    for (Coche coche : resultados) {
                        tableModel.addRow(new Object[]{coche.getMarca(), coche.getModelo(), coche.getMatricula()});
                    }
                } else {
                    // 16: "Coche no encontrado" o "Car not found" || 5: "Error" o "Error"
                    JOptionPane.showMessageDialog(this, cadenas.get(16), cadenas.get(17), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Agregar paneles a la ventana
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Configuración de la ventana
        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Método para cargar todos los coches en la tabla.
     */
    private void cargarTodosLosCoches(DefaultTableModel tableModel, Map<String, Coche> coches) {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (Coche coche : coches.values()) {
            tableModel.addRow(new Object[]{coche.getMarca(), coche.getModelo(), coche.getMatricula()});
        }
    }
}
