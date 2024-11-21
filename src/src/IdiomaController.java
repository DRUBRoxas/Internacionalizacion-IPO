package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdiomaController {
    private final Map<String, List<String>> cadenas= new HashMap<>();
    private final Map<String, List<String>> imagenes= new HashMap<>();
    private final List<String> banderas= new ArrayList<>();
    private final List<String> idiomasDisponibles= new ArrayList<>();

    public IdiomaController(String rutaArchivoLenguajes) {
        cargarIdiomas(rutaArchivoLenguajes);
    }

    private void cargarIdiomas(String rutaArchivoLenguajes) {
        try (BufferedReader reader= new BufferedReader(new FileReader(rutaArchivoLenguajes))) {
            // Leer el número de idiomas
            int numIdiomas= Integer.parseInt(reader.readLine().trim());

            /// Este bucle recoge las cadenas de cada idioma y las mete en el HashMap
            for (int i = 0; i < numIdiomas; i++) {
                String idioma= reader.readLine().trim();
                idiomasDisponibles.add(idioma);
                int numCadenas= Integer.parseInt(reader.readLine().trim());
                List<String> listaCadenas= new ArrayList<>();
                for (int j = 0; j < numCadenas; j++) {
                    listaCadenas.add(reader.readLine().trim());
                }
                cadenas.put(idioma, listaCadenas);
                int numImagenes= Integer.parseInt(reader.readLine().trim());
                List<String> listaImagenes= new ArrayList<>();
                for (int j = 0; j < numImagenes; j++) {
                    if (j==0) {
                        banderas.add(reader.readLine().trim());
                    }else{
                        listaImagenes.add(reader.readLine().trim());
                    }
                }
                imagenes.put(idioma, listaImagenes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getCadenas(String idioma) {
        return cadenas.get(idioma);
    }

    public List<String> getImagenes(String idioma) {
        return imagenes.get(idioma);
    }

    public List<String> getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public List<String> getBanderas() {
        return banderas;
    }
}
