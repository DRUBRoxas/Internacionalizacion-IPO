package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainFrame extends JFrame{
    private CocheController controller;
    private IdiomaController idiomaController;
    private String idiomaActual;
    private JMenu fileMenu;
    private JMenu languageMenu;
    private JMenu optionsMenu;
    private JMenuItem addCoche;
    private JMenuItem deleteCoche;
    private JMenuItem editCoche;
    private JMenuItem exit;
    DefaultTableModel model;
    private JLabel foto;
    JTable table;
    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

    public MainFrame(){
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = new CocheController();
        idiomaController = new IdiomaController("ArchivoIdiomas.tsv");
        idiomaActual = idiomaController.getIdiomasDisponibles().get(0);
        List<String> cadenas = idiomaController.getCadenas(idiomaActual);
        UIManager.put("OptionPane.cancelButtonText", cadenas.get(14));
        UIManager.put("OptionPane.okButtonText", cadenas.get(27));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        initMenuBar(cadenas);
        initMainTable(cadenas, controller);
        initFoto();
        setVisible(true);
    }

    private void initFoto() {
        if (foto == null) {
            foto = new JLabel();
            add(foto, BorderLayout.SOUTH);
        }
        String rutaImagen = idiomaController.getImagenes(idiomaActual).get(0);
        ImageIcon icon = new ImageIcon(rutaImagen);
        Image img = icon.getImage().getScaledInstance(400, 250, Image.SCALE_REPLICATE); // Adjust size as needed
        icon = new ImageIcon(img);
        foto.setIcon(icon);
        revalidate();
        repaint();
    }

    private void initMainTable(List<String>Cadenas, CocheController controller) {
        if (table == null) {
            table = new JTable();
            Collection<Coche> coches = controller.getAllCoches();
            String[] columnNames = {Cadenas.get(8), Cadenas.get(9), Cadenas.get(10)}; // "Brand", "Model", "License Plate" o "Marca", "Modelo", "Matrícula"
            model = new DefaultTableModel(columnNames, 0);
            for (Coche coche : coches) {
                Object[] row = {coche.getMarca(), coche.getModelo(), coche.getMatricula()};
                model.addRow(row);
            }
            table.setModel(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        } else {
            model.setColumnIdentifiers(new String[]{Cadenas.get(8), Cadenas.get(9), Cadenas.get(10)}); // "Brand", "Model", "License Plate" o "Marca", "Modelo", "Matrícula"
        }

    }

    private void initMenuBar(List<String> cadenas) {
        if (fileMenu == null) {
            setTitle(cadenas.get(22));
            fileMenu = new JMenu(cadenas.get(1)); // File o Archivo
            languageMenu = new JMenu(cadenas.get(2)); // Language o Idioma
            optionsMenu = new JMenu(cadenas.get(3)); // Options o Opciones
            addCoche = new JMenuItem(cadenas.get(4)); // Add Car o Añadir Coche
            deleteCoche = new JMenuItem(cadenas.get(5)); // Delete Car o Borrar Coche
            editCoche = new JMenuItem(cadenas.get(6)); // Edit Car o Editar Coche
            exit = new JMenuItem(cadenas.get(7)); // Exit o Salir

            for (int i = 0; i < idiomaController.getIdiomasTraducidos().size(); i++) {
                String idiomaTraducido = idiomaController.getIdiomasTraducidos().get(i);
                String idiomaDisponible = idiomaController.getIdiomasDisponibles().get(i);
                String imagenBandera = idiomaController.getBanderas().get(i);
                ImageIcon banderaIcon = new ImageIcon(imagenBandera);
                Image img = banderaIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Ajusta el tamaño según necesidad
                banderaIcon = new ImageIcon(img);
                System.out.println(imagenBandera);
                JMenuItem languajeItem = new JMenuItem(idiomaTraducido, banderaIcon);
                languajeItem.addActionListener(e -> {
                    idiomaActual = idiomaDisponible;
                    initMainTable(idiomaController.getCadenas(idiomaActual), controller);
                    UIManager.put("OptionPane.cancelButtonText", idiomaController.getCadenas(idiomaActual).get(14));
                    UIManager.put("OptionPane.okButtonText", idiomaController.getCadenas(idiomaActual).get(27));
                    initMenuBar(idiomaController.getCadenas(idiomaActual));
                    initFoto();
                });
                languageMenu.add(languajeItem);
            }

            // Action for Add (no selected item needed)
            addCoche.addActionListener(e -> openFrameWithRefresh(new AddCocheFrame(controller, idiomaController, idiomaActual)));

            // Action for Delete (passes selected item if available)
            deleteCoche.addActionListener(e -> {
                Coche selectedCoche = null;
                int posicionSeleccionada = table.getSelectedRow();
                if (posicionSeleccionada == -1) {
                    //No hace nada si no hay fila seleccionada
                }else{
                    selectedCoche = new Coche((String) table.getValueAt(posicionSeleccionada, 0), (String) table.getValueAt(posicionSeleccionada, 1), (String) table.getValueAt(posicionSeleccionada, 2));
                }
                openFrameWithRefresh(new DeleteCocheFrame(controller, idiomaController, idiomaActual, selectedCoche));
            });

            // Action for Edit (passes selected item if available)
            editCoche.addActionListener(e -> {

                Coche selectedCoche = null;
                int posicionSeleccionada = table.getSelectedRow();
                if (posicionSeleccionada == -1) {
                    //No hace nada si no hay fila seleccionada
                }else{
                    selectedCoche = new Coche((String) table.getValueAt(posicionSeleccionada, 0), (String) table.getValueAt(posicionSeleccionada, 1), (String) table.getValueAt(posicionSeleccionada, 2));
                }
                openFrameWithRefresh(new EditCocheFrame(controller, idiomaController, idiomaActual, selectedCoche));
            });


            exit.addActionListener(e -> System.exit(0));

            fileMenu.add(addCoche);
            fileMenu.add(deleteCoche);
            fileMenu.add(editCoche);
            fileMenu.add(exit);
            JMenuBar menuBar = new JMenuBar();
            menuBar.add(fileMenu);
            menuBar.add(optionsMenu);
            optionsMenu.add(languageMenu);
            setJMenuBar(menuBar);
        } else {
            languageMenu.removeAll();
            setTitle(cadenas.get(22));
            fileMenu.setText(cadenas.get(1));
            languageMenu.setText(cadenas.get(2));
            optionsMenu.setText(cadenas.get(3));
            addCoche.setText(cadenas.get(4));
            deleteCoche.setText(cadenas.get(5));
            editCoche.setText(cadenas.get(6));
            exit.setText(cadenas.get(7));
            for (int i = 0; i < idiomaController.getIdiomasTraducidos().size(); i++) {
                String idiomaTraducido = idiomaController.getIdiomasTraducidos().get(i);
                String idiomaDisponible = idiomaController.getIdiomasDisponibles().get(i);
                String imagenBandera = idiomaController.getBanderas().get(i);
                ImageIcon banderaIcon = new ImageIcon(imagenBandera);
                Image img = banderaIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Ajusta el tamaño según necesidad
                banderaIcon = new ImageIcon(img);
                System.out.println(imagenBandera);
                JMenuItem languajeItem = new JMenuItem(idiomaTraducido, banderaIcon);
                languajeItem.addActionListener(e -> {
                    idiomaActual = idiomaDisponible;
                    UIManager.put("OptionPane.cancelButtonText", idiomaController.getCadenas(idiomaActual).get(14));
                    UIManager.put("OptionPane.okButtonText", idiomaController.getCadenas(idiomaActual).get(27));
                    initMenuBar(idiomaController.getCadenas(idiomaActual));
                    initMainTable(idiomaController.getCadenas(idiomaActual), controller);
                    initFoto();
                });
                languageMenu.add(languajeItem);
            }
        }
    }

    private void refrescarTabla() {
        model.setRowCount(0);
        Collection<Coche> coches = controller.getAllCoches();
        for (Coche coche : coches) {
            Object[] row = {coche.getMarca(), coche.getModelo(), coche.getMatricula()};
            model.addRow(row);
        }
    }

    // Method to open a frame and refresh table upon close
    private void openFrameWithRefresh(JFrame frame) {
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    refrescarTabla(); // Refresh the table when the frame is closed
                }
            });
            frame.setVisible(true);
    }

    public static void main(String[] args) {
        try{
            SwingUtilities.invokeLater(MainFrame::new);
        } catch (Exception e){
            logger.severe(e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.severe(sw.toString());
        }
    }


}
