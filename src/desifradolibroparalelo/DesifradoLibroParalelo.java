/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desifradolibroparalelo;

import com.google.gson.Gson;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import com.google.gson.internal.LinkedTreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alexander_chavez
 */
public class DesifradoLibroParalelo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //aplicar a la inversa el cifrado cesar
        //leer archivo desencoder

        //cargar lista de libros cifrados
        Instant inicio = Instant.now();
        String directorio;
        String llave;
        if (args.length > 0) {
            directorio = args[0];
            llave = "Todos esos momentos se perderan como lagrimas en la lluvia";
        } else {
            directorio = "descarga_libros";
            llave = "Todos esos momentos se perderan como lagrimas en la lluvia";
        }
        //List<Libro> lista = cifradoLibros(directorio, encoder);
        LinkedTreeMap<String, LinkedTreeMap> dencoder = cargaCipher("decode_cipher.json");
        List<File> lista_archivos = obtenListaArchivos(directorio, "cifrado");

        for (File archivo : lista_archivos) {
            System.out.println(archivo.toString());

        }
        List<Libro> lista_libros = cargarLibros(lista_archivos,250);

        for (Libro libro : lista_libros) {
            System.out.println(libro.original.substring(1763, 1800));
        }
        //descifrar
       /* desifrar(lista_libros, llave, dencoder);
        //guardar archivos descifrado

        guardarLibros(lista_libros, "descifrado");
*/
        Instant fin = Instant.now();
        long tiempoComputo = Duration.between(inicio, fin).toMillis();
        System.out.println("Tiempo de computo: " + tiempoComputo + " milisegundos");
     
    }

    public static LinkedTreeMap cargaCipher(String archivo) {
        LinkedTreeMap map = null;
        Gson gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            String texto = "";
            while ((linea = reader.readLine()) != null) {
                texto += linea;
            }
            map = gson.fromJson(texto, LinkedTreeMap.class);
        } catch (IOException e) {
            System.out.println("cargaCipher " + e.getMessage());
        }
        return map;
    }

    public static List<File> obtenListaArchivos(String ruta, String ext) {
        List<File> lista_archivos = new ArrayList<>();
        File directorio = new File(ruta);
        File[] lista = directorio.listFiles();

        for (File archivo : lista) {
            String nombre_archivo = archivo.toString();
            int indice = nombre_archivo.lastIndexOf(".");
            if (indice > 0) {
                String extencion = nombre_archivo.substring(indice + 1);
                if (extencion.equals(ext)) {
                    lista_archivos.add(archivo);
                }
            }

        }

        return lista_archivos;
    }

    public static List<Libro> cargarLibros(List<File> lista_archivos, int milisegundos) {

        List<Libro> lista_libros = new ArrayList<>();
        List<Future<String>> lista_textos = new ArrayList<>();
        //Libro libro= new Libro(archivo.toString());
        try {
            int procesadores = Runtime.getRuntime().availableProcessors();
            System.out.println("Procesadores (cargarLibro): " + procesadores);

            ExecutorService executor = Executors.newFixedThreadPool(procesadores);
            for (File archivo : lista_archivos) {
                Future<String> texto = executor.submit(new Texto(archivo.toString()));
                lista_textos.add(texto);

            }
            executor.awaitTermination(milisegundos, TimeUnit.MILLISECONDS);
            executor.shutdown();
            System.out.println("lista de textos" + lista_textos.size());
            for (int i = 0; i < lista_textos.size(); i++) {
                Libro libro = new Libro(lista_archivos.get(i).toString());
                libro.original = lista_textos.get(i).get().toString();
                lista_libros.add(libro);

            }

        } catch (Exception ex) {
            System.out.println("Cargar Libros error :" + ex.getMessage());
        }
        return lista_libros;

    }

}
