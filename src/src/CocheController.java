package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CocheController {
    private Map<String, Coche> coches= new HashMap<>();
    private static final String RUTA_ARCHIVO_DATOS = "ArchivoDatos.tsv";

    public CocheController() {
        cargarDatos();
    }

    public boolean addCoche(Coche coche) {
        if(coches.containsKey(coche.getMatricula())) {
            return false;
        }
        coches.put(coche.getMatricula(), coche);
        return true;
    }

    public Coche getCoche(String matricula) {
        return coches.get(matricula);
    }

    public void deleteCoche(String matricula) {
        coches.remove(matricula);
    }

    public boolean updateCoche(String matricula, String marca, String modelo) {
        Coche coche = coches.get(matricula);
        if(coche != null) {
            coche = new Coche(marca, modelo, matricula);
            coches.put(matricula, coche);
            return true;
        }else {
            return false;
        }
    }

    public void guardarDatos() {
        try (PrintWriter writer = new PrintWriter(new File(RUTA_ARCHIVO_DATOS))) {
            for (Coche coche : coches.values()) {
                writer.println(coche.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos(){
        try (Scanner scanner = new Scanner(new File(RUTA_ARCHIVO_DATOS))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                addCoche(new Coche(data[0], data[1], data[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Collection<Coche> getAllCoches() {
        return coches.values();
    }

    public Map<String, Coche> getCoches() {
        return coches;
    }
}
