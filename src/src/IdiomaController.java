package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdiomaController {
    private Map<String, List<String>> cadenas= new HashMap<>();
    private Map<String, List<String>> imagenes= new HashMap<>();
    private Map<String, List<String>> idiomasTraducidos= new HashMap<>();
    private List<String> banderas= new ArrayList<>();
    private List<String> idiomasDisponibles= new ArrayList<>();
    private String lenguajeActual;


    public IdiomaController(String rutaArchivoLenguajes) {
        cargarIdiomas(rutaArchivoLenguajes);
    }

    private void cargarIdiomas(String rutaArchivoLenguajes) {
        try (BufferedReader reader= new BufferedReader(new FileReader(rutaArchivoLenguajes))) {
            int numIdiomas= Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < numIdiomas; i++) {
                ArrayList<String> listaIdiomasaux= new ArrayList<>();
                String idioma= reader.readLine().trim();
                idiomasDisponibles.add(idioma);
                int numCadenas= Integer.parseInt(reader.readLine().trim());
                List<String> listaCadenas= new ArrayList<>();
                for (int j = 0; j < numCadenas; j++) {
                    if(j<numIdiomas) //Esto está asi por pereza de cambiar el resto de números
                    {
                        String idiomaTraducido= reader.readLine().trim();
                        listaIdiomasaux.add(idiomaTraducido);
                    }
                    else{
                    listaCadenas.add(reader.readLine().trim());
                    }
                }
                idiomasTraducidos.put(idioma, listaIdiomasaux);
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
        lenguajeActual= idioma;
        return cadenas.get(idioma);
    }

    public List<String> getImagenes(String idioma) {
        return imagenes.get(idioma);
    }

    public String getLenguajeActual() {
        return lenguajeActual;
    }

    public List<String> getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public List<String> getIdiomasTraducidos() {
        return idiomasTraducidos.get(lenguajeActual);
    }

    public List<String> getBanderas() {
        return banderas;
    }
}
