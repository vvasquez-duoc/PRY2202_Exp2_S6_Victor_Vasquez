package com.duoc.biblioteca.entidades.biblioteca;

import com.duoc.biblioteca.archivos.OperacionesConArchivos;
import com.duoc.biblioteca.entidades.libros.Libros;
import com.duoc.biblioteca.manejadores.LibroNoEncontrado;
import com.duoc.biblioteca.manejadores.LibroYaPrestado;
import com.duoc.biblioteca.utilitarios.Utilitarios;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Biblioteca {
    private ArrayList<Libros> libros;

    public Biblioteca(){
        libros = new ArrayList<>();
    }
    
    public void ingresarLibrosDesdeCSV(HashSet<Integer> isbnUsados, TreeSet<String> titulosLibros, TreeSet<String>autoresLibros, HashMap<String, ArrayList<Integer>> librosPorAutor, HashMap<String, ArrayList<Integer>> librosPorTitulo){
        String archivoCSV = "src/com/duoc/biblioteca/archivos/librosBiblioteca.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(archivoCSV))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split(",");

                String titulo = datos[0].toUpperCase();
                String autor = datos[1].toUpperCase();
                int isbn = Integer.valueOf(datos[2]); 

                Libros libro = new Libros(titulo, autor, isbn);
                isbnUsados.add(isbn);
                libros.add(libro);
                titulosLibros.add(titulo);
                autoresLibros.add(autor);
                
                if(librosPorAutor.containsKey(autor)){
                    ArrayList<Integer> isbnLibrosAutor = librosPorAutor.get(autor);
                    isbnLibrosAutor.add(isbn);
                    librosPorAutor.put(autor, isbnLibrosAutor);
                }else{
                    ArrayList<Integer> isbnLibrosAutor = new ArrayList<>();
                    isbnLibrosAutor.add(isbn);
                    librosPorAutor.put(autor, isbnLibrosAutor);
                }
                
                if(librosPorTitulo.containsKey(titulo)){
                    ArrayList<Integer> isbnLibrosTitulo = librosPorTitulo.get(titulo);
                    isbnLibrosTitulo.add(isbn);
                    librosPorTitulo.put(titulo, isbnLibrosTitulo);
                }else{
                    ArrayList<Integer> isbnLibrosTitulo = new ArrayList<>();
                    isbnLibrosTitulo.add(isbn);
                    librosPorTitulo.put(titulo, isbnLibrosTitulo);
                }
            }

        }catch(IOException e){
            System.out.println("ERROR CON EL ARCHIVO CSV");
        }
    }

    public void ingresarLibro(HashSet<Integer> isbnUsados, TreeSet<String> titulosLibros, TreeSet<String>autoresLibros, HashMap<String, ArrayList<Integer>> librosPorAutor, HashMap<String, ArrayList<Integer>> librosPorTitulo){
        Scanner teclado = new Scanner(System.in);
        String titulo = "";
        String autor = "";
        int isbn = 0;
        
        try{
            System.out.println("INTRODUCE EL TITULO DEL LIBRO:");
            titulo = teclado.nextLine();

            System.out.println("INTRODUCE EL AUTOR DEL LIBRO:");
            autor = teclado.nextLine();

            System.out.println("INTRODUCE EL ISBN DEL LIBRO:");
            isbn = teclado.nextInt();
            
            if(isbnUsados.contains(isbn)){
                System.out.println("EL ISBN YA ESTA USADO POR OTRO LIBRO");
            }else{
                titulo = titulo.toUpperCase();
                autor = autor.toUpperCase();

                Libros libro = new Libros(titulo, autor, isbn);
                isbnUsados.add(isbn);
                libros.add(libro);
                titulosLibros.add(titulo);
                autoresLibros.add(autor);
                
                OperacionesConArchivos.escribeEnArchivo("librosBiblioteca.csv", titulo+","+autor+","+isbn);
                
                if(librosPorAutor.containsKey(autor)){
                    ArrayList<Integer> isbnLibrosAutor = librosPorAutor.get(autor);
                    isbnLibrosAutor.add(isbn);
                    librosPorAutor.put(autor, isbnLibrosAutor);
                }else{
                    ArrayList<Integer> isbnLibrosAutor = new ArrayList<>();
                    isbnLibrosAutor.add(isbn);
                    librosPorAutor.put(autor, isbnLibrosAutor);
                }
                
                if(librosPorTitulo.containsKey(titulo)){
                    ArrayList<Integer> isbnLibrosTitulo = librosPorTitulo.get(titulo);
                    isbnLibrosTitulo.add(isbn);
                    librosPorTitulo.put(titulo, isbnLibrosTitulo);
                }else{
                    ArrayList<Integer> isbnLibrosTitulo = new ArrayList<>();
                    isbnLibrosTitulo.add(isbn);
                    librosPorTitulo.put(titulo, isbnLibrosTitulo);
                }

                System.out.println("EL LIBRO HA SIDO REGISTRADO EXITOSAMENTE.");
            }
        }catch(InputMismatchException e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: ENTRADA NO VALIDA. INTRODUZCA UN NUMERO PARA EL ISBN.");
            System.out.println("");
            ingresarLibro(isbnUsados, titulosLibros, autoresLibros, librosPorAutor, librosPorTitulo);
        }

        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine(); teclado.nextLine();
    }
    
    public void mostrarLibros(){
        Scanner teclado = new Scanner(System.in);
        if(libros.isEmpty()){
            System.out.println("NO HAY LIBROS REGISTRADOS.");
        }else{
            System.out.println("LIBROS PERTENECIENTES A BIBLIOTECA:");
            System.out.println("");
            for(Libros libro : libros){
                System.out.println("TITULO  : "+libro.getTitulo());
                System.out.println("AUTOR   : "+libro.getAutor());
                System.out.println("ISBN    : "+libro.getIsbn());
                System.out.println("PRESTADO: "+libro.estaPrestado());
                System.out.println("-------------------------------");
            }
        }
        System.out.println("");
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
    }

    public Libros buscarLibro(int isbn) throws LibroNoEncontrado{
        for(Libros libro : libros){
            if(libro.getIsbn() == isbn){
                return libro;
            }
        }
        throw new LibroNoEncontrado("EL LIBRO NO FUE ENCONTRADO");
    }
    
    public boolean removerLibro(int isbn, String autor, String titulo) throws LibroNoEncontrado, LibroYaPrestado{
        boolean encontrado = false;
        boolean eliminado = false;
        String lineaCSV = titulo+","+autor+","+isbn;
        
        Iterator<Libros> iterator = libros.iterator();
            while(iterator.hasNext()){
                Libros libro = iterator.next();
                if(libro.getIsbn() == isbn){
                    if(libro.isPrestado()){
                        throw new LibroYaPrestado("EL LIBRO ESTA PRESTADO. NO SE PUEDE ELIMINAR DE LA BIBLIOTECA");
                    }else{
                        OperacionesConArchivos.eliminaEnArchivo("librosBiblioteca.csv", lineaCSV);
                        iterator.remove();
                        encontrado = true;
                        eliminado = true;
                    }
                    break;
                }
            }
        
        if(!encontrado){
            throw new LibroNoEncontrado("EL LIBRO NO FUE ENCONTRADO");
        }
        return eliminado;
    }

    public boolean prestarLibro(int isbn) throws LibroNoEncontrado, LibroYaPrestado{
        Libros libro = buscarLibro(isbn);
        if(libro.isPrestado()){
            throw new LibroYaPrestado("EL LIBRO YA ESTA PRESTADO.");
        }
        return libro.prestar();
    }

    public boolean devolverLibro(int isbn) throws LibroNoEncontrado{
        Libros libro = buscarLibro(isbn);
        return libro.devolver();
    }
}