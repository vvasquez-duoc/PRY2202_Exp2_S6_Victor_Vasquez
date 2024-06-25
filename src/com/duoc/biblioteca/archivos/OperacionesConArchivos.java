package com.duoc.biblioteca.archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OperacionesConArchivos {
    public static void escribeEnArchivo(String archivo, String nuevaLinea){
        String rutaArchivo = "src/com/duoc/biblioteca/archivos/"+archivo;
        
        File auxArchivo = new File(rutaArchivo);
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))){
            if(auxArchivo.length() != 0) writer.newLine();
            writer.write(nuevaLinea);
        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
    }
    
    public static void escribeEnArchivo(String RUT, String[] datos){
        String rutaArchivo = "src/com/duoc/biblioteca/archivos/usuarios/"+RUT+".txt";
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))){
            for(int i=0; i<datos.length; i++){
                writer.write(datos[i]);
                if(i<datos.length - 1){
                    writer.newLine();
                }
            }
        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
    }
    
    public static void eliminaEnArchivo(String archivo, String lineaEliminada){
        ArrayList<String> auxLineas = new ArrayList<>();
        String rutaArchivo = "src/com/duoc/biblioteca/archivos/"+archivo;
        String linea;
        
        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){    
            while((linea = br.readLine()) != null){
                if(!lineaEliminada.equalsIgnoreCase(linea)){
                    auxLineas.add(linea);
                }
            }
        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))){
            for(int i=0; i<auxLineas.size(); i++){
                writer.write(auxLineas.get(i));
                if(i<auxLineas.size() - 1){
                    writer.newLine();
                }
            }
        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
    }
    
    public static void eliminaEnArchivo(String archivo, int isbn){
        ArrayList<String> auxLineas = new ArrayList<>();
        String rutaArchivo = "src/com/duoc/biblioteca/archivos/"+archivo;
        String linea;
        String isbnString = Integer.toString(isbn);
        
        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){    
            while((linea = br.readLine()) != null){
                String[] datos = linea.split(",");
                if(!datos[0].equalsIgnoreCase(isbnString)){
                    auxLineas.add(linea);
                }
            }
        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))){
            for(int i=0; i<auxLineas.size(); i++){
                writer.write(auxLineas.get(i));
                if(i<auxLineas.size() - 1){
                    writer.newLine();
                }
            }
        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
    }
    
    public static void eliminarArchivo(String RUT){
        String rutaArchivo = "src/com/duoc/biblioteca/archivos/usuarios/"+RUT+".txt";
        
        File file = new File(rutaArchivo);
        if(file.exists()){
            file.delete();
        }
    }
}
