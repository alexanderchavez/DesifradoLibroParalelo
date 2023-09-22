/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desifradolibroparalelo;

import com.google.gson.internal.LinkedTreeMap;
import java.util.concurrent.Callable;

/**
 *
 * @author alexander chavez
 */
public class Cifrador implements Callable<String>{
    String llave;
    String texto;
    String encriptado;
    LinkedTreeMap<String,LinkedTreeMap> codificador;
    
@Override
public String call() throws Exception {
char[] texto = this.texto.toCharArray();
        try {
            String llave = this.llave.toUpperCase();
            char[] llave_chars = llave.toCharArray();
        
            int len_key = llave_chars.length;
        
            for (int i = 0; i < texto.length ; i++) {
                int key_index = i % len_key;
                char key = llave_chars[key_index];
                String skey = ""+key;
                LinkedTreeMap<String,String> diccionario = this.codificador.get(skey);
                String sc = ""+texto[i];
                texto[i] = diccionario.get(sc).charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error en cifrado");
            System.out.println(e.getMessage());
        }
        this.encriptado = String.copyValueOf(texto);
        return this.encriptado;
}

    public Cifrador(String llave, String texto, LinkedTreeMap<String, LinkedTreeMap> codificador) {
        this.llave = llave;
        this.texto = texto;
        this.codificador = codificador;
    }
    
}
