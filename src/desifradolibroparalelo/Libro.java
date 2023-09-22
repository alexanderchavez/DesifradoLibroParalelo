/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desifradolibroparalelo;

import com.google.gson.internal.LinkedTreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author federico cirett galan
 */
public class Libro {
    String titulo;
    String llave;
    String original;
    String cifrado;
    LinkedTreeMap<String,LinkedTreeMap> diccionario_cifrado;

    public Libro(String titulo) {
        this.titulo = titulo;
    }
    public void cargaLibro() {
        String archivo = this.titulo;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            String texto ="";
            while ( (linea= reader.readLine()) !=null ) {
                texto += linea;
            }
            this.original = texto;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void guardaArchivo() {
        String archivo = this.titulo+".cifrado";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(this.cifrado);
            writer.flush();
            writer.close();
            System.out.println("Escrito:"+archivo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void cifrar () {
        char[] texto = this.original.toCharArray();
        try {
            String llave = this.llave.toUpperCase();
            char[] llave_chars = llave.toCharArray();
        
            int len_key = llave_chars.length;
        
            for (int i = 0; i < texto.length ; i++) {
                int key_index = i % len_key;
                char key = llave_chars[key_index];
                String skey = ""+key;
                LinkedTreeMap<String,String> diccionario = this.diccionario_cifrado.get(skey);
                String sc = ""+texto[i];
                texto[i] = diccionario.get(sc).charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error en cifrado");
            System.out.println(e.getMessage());
        }
        this.cifrado = String.copyValueOf(texto);
    }
}