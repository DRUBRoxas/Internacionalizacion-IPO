package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.swing.*;

import java.awt.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainFrame extends JFrame{
    private final CocheController controller;
    private final IdiomaController idiomaController;
    private String idiomaActual;
    private JMenu fileMenu;
    private JMenu languageMenu;
    private JMenu optionsMenu;
    private JMenuItem addCoche;
    private JMenuItem deleteCoche;
    private JMenuItem editCoche;
    private JMenuItem searchCoche;
    private JMenuItem exit;
    private JLabel foto;
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
        idiomaActual = idiomaController.getIdiomasDisponibles().getFirst();
        List<String> cadenas = idiomaController.getCadenas(idiomaActual);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        initMenuBar(cadenas);
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
            searchCoche = new JMenuItem(cadenas.get(29)); // Search o Buscar
            UIManager.put("OptionPane.cancelButtonText", idiomaController.getCadenas(idiomaActual).get(14)); //CANCEL o CANCELAR
            UIManager.put("OptionPane.okButtonText", idiomaController.getCadenas(idiomaActual).get(27)); //OK o VALE
            UIManager.put("OptionPane.yesButtonText", idiomaController.getCadenas(idiomaActual).get(24)); // "Sí" o "Yes"
            UIManager.put("OptionPane.noButtonText", idiomaController.getCadenas(idiomaActual).get(25));  // "No" o "No"
            for (int i = 0; i < idiomaController.getIdiomasDisponibles().size(); i++) {
                String idiomaDisponible = idiomaController.getIdiomasDisponibles().get(i);
                String imagenBandera = idiomaController.getBanderas().get(i);
                String idiomaLargo = idiomaController.getCadenas(idiomaDisponible).get(0);
                ImageIcon banderaIcon = new ImageIcon(imagenBandera);
                Image img = banderaIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Ajusta el tamaño según necesidad
                banderaIcon = new ImageIcon(img);
                JMenuItem languajeItem = new JMenuItem(idiomaLargo, banderaIcon);
                languajeItem.addActionListener(e -> {
                    idiomaActual = idiomaDisponible;
                    UIManager.put("OptionPane.cancelButtonText", idiomaController.getCadenas(idiomaActual).get(14)); //CANCEL o CANCELAR
                    UIManager.put("OptionPane.okButtonText", idiomaController.getCadenas(idiomaActual).get(27)); //OK o VALE
                    UIManager.put("OptionPane.yesButtonText", idiomaController.getCadenas(idiomaActual).get(24)); // "Sí" o "Yes"
                    UIManager.put("OptionPane.noButtonText", idiomaController.getCadenas(idiomaActual).get(25));  // "No" o "No"
                    initMenuBar(idiomaController.getCadenas(idiomaActual));
                    initFoto();
                });
                languageMenu.add(languajeItem);
            }

            // Action for Add
            addCoche.addActionListener(e ->new AddCocheFrame(controller, idiomaController, idiomaActual));

            // Action for Delete
            deleteCoche.addActionListener(e ->new DeleteCocheFrame(controller, idiomaController, idiomaActual));

            // Action for Edit
            editCoche.addActionListener(e ->new EditCocheFrame(controller, idiomaController, idiomaActual));

            // Action for Search
            searchCoche.addActionListener(e ->new SearchCocheFrame(controller, idiomaController, idiomaActual));
            // Action for Exit
            exit.addActionListener(e -> {
                List<String> auxiliar= idiomaController.getCadenas(idiomaActual);
                UIManager.put("OptionPane.cancelButtonText", idiomaController.getCadenas(idiomaActual).get(14)); //CANCEL o CANCELAR
                UIManager.put("OptionPane.okButtonText", idiomaController.getCadenas(idiomaActual).get(27)); //OK o VALE
                UIManager.put("OptionPane.yesButtonText", idiomaController.getCadenas(idiomaActual).get(24)); // "Sí" o "Yes"
                UIManager.put("OptionPane.noButtonText", idiomaController.getCadenas(idiomaActual).get(25));  // "No" o "No"
                int option = JOptionPane.showConfirmDialog(
                        this,
                        auxiliar.get(15), // Mensaje
                        auxiliar.get(7),          // Título
                        JOptionPane.YES_NO_OPTION,         // Opciones
                        JOptionPane.WARNING_MESSAGE        // Tipo de mensaje
                );

                // Verificar opción seleccionada
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cerrar aplicación
                }
            });

            fileMenu.add(addCoche);
            fileMenu.add(deleteCoche);
            fileMenu.add(editCoche);
            fileMenu.add(searchCoche);
            fileMenu.add(exit);

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(fileMenu);
            menuBar.add(optionsMenu);
            optionsMenu.add(languageMenu);
            setJMenuBar(menuBar);
        } else {
            languageMenu.removeAll();
            setTitle(cadenas.get(22)); // Practica 4 o Practice 4
            fileMenu.setText(cadenas.get(1)); // File o Archivo
            languageMenu.setText(cadenas.get(2)); // Language o Idioma
            optionsMenu.setText(cadenas.get(3)); // Options o Opciones
            addCoche.setText(cadenas.get(4)); // Add Car o Añadir Coche
            deleteCoche.setText(cadenas.get(5)); // Delete Car o Borrar Coche
            editCoche.setText(cadenas.get(6)); // Edit Car o Editar Coche
            exit.setText(cadenas.get(7)); // Exit o Salir
            searchCoche.setText(cadenas.get(29)); // Search o Buscar

            for (int i = 0; i < idiomaController.getIdiomasDisponibles().size(); i++) {
                String idiomaDisponible = idiomaController.getIdiomasDisponibles().get(i);
                String idiomaLargo = idiomaController.getCadenas(idiomaDisponible).get(0);
                String imagenBandera = idiomaController.getBanderas().get(i);
                ImageIcon banderaIcon = new ImageIcon(imagenBandera);
                Image img = banderaIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Ajusta el tamaño según necesidad
                banderaIcon = new ImageIcon(img);
                System.out.println(imagenBandera);
                JMenuItem languajeItem = new JMenuItem(idiomaLargo, banderaIcon);
                languajeItem.addActionListener(e -> {
                    idiomaActual = idiomaDisponible;
                    UIManager.put("OptionPane.cancelButtonText", idiomaController.getCadenas(idiomaActual).get(14)); //CANCEL o CANCELAR
                    UIManager.put("OptionPane.okButtonText", idiomaController.getCadenas(idiomaActual).get(27)); //OK o VALE
                    UIManager.put("OptionPane.yesButtonText", idiomaController.getCadenas(idiomaActual).get(24)); // "Sí" o "Yes"
                    UIManager.put("OptionPane.noButtonText", idiomaController.getCadenas(idiomaActual).get(25));  // "No" o "No"
                    initMenuBar(idiomaController.getCadenas(idiomaActual));
                    initFoto();
                });
                languageMenu.add(languajeItem);
            }
        }
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
