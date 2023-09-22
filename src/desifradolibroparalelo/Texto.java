/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desifradolibroparalelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 *
 * @author DELL
 */
public class Texto implements Callable<String>{
    String archivo;

    public Texto(String archivo) {
        this.archivo = archivo;
    }
   @Override
    public String call() throws Exception{
     String texto="";
     try{
         
         BufferedReader reader= new BufferedReader(new FileReader(this.archivo));
         String linea;
         
         while( (linea=reader.readLine()) != null){
           texto+=linea;
         }
     }
     catch(IOException e){
         System.out.println("Texto "+e.getMessage());
     }
     return texto;
    }
   public static void cargarArchivo(){
   
   
   }
}
