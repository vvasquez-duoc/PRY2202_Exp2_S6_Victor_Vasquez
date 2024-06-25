package com.duoc.biblioteca.app;

import static com.duoc.biblioteca.app.App.autoresLibros;
import static com.duoc.biblioteca.app.App.biblioteca;
import static com.duoc.biblioteca.app.App.isbnUsados;
import static com.duoc.biblioteca.app.App.librosPorAutor;
import static com.duoc.biblioteca.app.App.librosPorTitulo;
import static com.duoc.biblioteca.app.App.menuBiblioteca;
import static com.duoc.biblioteca.app.App.titulosLibros;
import com.duoc.biblioteca.entidades.libros.Libros;
import com.duoc.biblioteca.manejadores.LibroNoEncontrado;
import com.duoc.biblioteca.manejadores.LibroYaPrestado;
import com.duoc.biblioteca.utilitarios.Utilitarios;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppBiblioteca {
    static void llenarBiblioteca(){
        biblioteca.ingresarLibrosDesdeCSV(isbnUsados, titulosLibros, autoresLibros, librosPorAutor, librosPorTitulo);
    }
    
    static void registrarLibro(){
        biblioteca.ingresarLibro(isbnUsados, titulosLibros, autoresLibros, librosPorAutor, librosPorTitulo);
        
        Utilitarios.limpiaPantalla();
        menuBiblioteca();
    }
    
    static void listarLibro(){
        biblioteca.mostrarLibros();
        
        Utilitarios.limpiaPantalla();
        menuBiblioteca();
    }
    
    static void encontrarLibro(){
        Scanner teclado = new Scanner(System.in);
        int isbn = 0;
        
        try{
            System.out.println("INTRODUZCA EL ISBN DEL LIBRO:");
            isbn = teclado.nextInt();
            Libros auxLibro = biblioteca.buscarLibro(isbn);
            System.out.println("");
            System.out.println("-- LIBRO ENCONTRADO --");
            System.out.println("TITULO  : "+auxLibro.getTitulo());
            System.out.println("AUTOR   : "+auxLibro.getAutor());
            System.out.println("ISBN    : "+auxLibro.getIsbn());
            System.out.println("PRESTADO: "+auxLibro.estaPrestado());
        }catch(LibroNoEncontrado e){
            System.out.println("ERROR: "+e.getMessage());
        }catch(InputMismatchException e){
            System.out.println("ERROR: EL ISBN INGRESADO NO ES UN NUMERO");
        }finally{
            System.out.println("");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine(); teclado.nextLine();
            Utilitarios.limpiaPantalla();
            menuBiblioteca();
        }
    }
    
    static void eliminarLibro(){
        Scanner teclado = new Scanner(System.in);
        int isbn = 0;
        boolean auxEliminado = false;
        String auxAutor = "";
        String auxTitulo = "";
        
        try{
            System.out.println("INTRODUZCA EL ISBN DEL LIBRO:");
            isbn = teclado.nextInt();
            Libros auxLibroEliminar = biblioteca.buscarLibro(isbn);
            auxAutor = auxLibroEliminar.getAutor();
            auxTitulo = auxLibroEliminar.getTitulo();
            
            auxEliminado = biblioteca.removerLibro(isbn, auxAutor, auxTitulo);
        }catch(LibroYaPrestado e){
            System.out.println("ERROR: "+e.getMessage());
        }catch(LibroNoEncontrado e){
            System.out.println("ERROR: "+e.getMessage());
        }catch(InputMismatchException e){
            System.out.println("ERROR: EL ISBN INGRESADO NO ES UN NUMERO");
        }finally{
            if(auxEliminado){
                if(librosPorAutor.containsKey(auxAutor)){
                    ArrayList<Integer> isbnLibrosAutor = librosPorAutor.get(auxAutor);
                    if(isbnLibrosAutor.size() <= 1){
                        librosPorAutor.remove(auxAutor);
                        autoresLibros.remove(auxAutor);
                    }else{
                        isbnLibrosAutor.remove(isbnLibrosAutor.indexOf(isbn));
                        librosPorAutor.put(auxAutor, isbnLibrosAutor);
                    }
                }

                if(librosPorTitulo.containsKey(auxTitulo)){
                    ArrayList<Integer> isbnLibrosTitulo = librosPorTitulo.get(auxTitulo);
                    if(isbnLibrosTitulo.size() <= 1){
                        librosPorTitulo.remove(auxTitulo);
                        titulosLibros.remove(auxTitulo);
                    }else{
                        isbnLibrosTitulo.remove(isbnLibrosTitulo.indexOf(isbn));
                        librosPorTitulo.put(auxTitulo, isbnLibrosTitulo);
                    }
                }
                
                Integer auxIsbn = isbn;
                isbnUsados.remove(auxIsbn);
                System.out.println("");
                System.out.println("-- LIBRO ELIMINADO --");
            }
            System.out.println("");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine(); teclado.nextLine();
            Utilitarios.limpiaPantalla();
            menuBiblioteca();
        }
    }
    
    static void listarTitulos(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("LISTADO DE TITULOS EN LA BIBLIOTECA");
        for(String titulo : titulosLibros){
            System.out.println(titulo);
        }
        System.out.println("");
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        Utilitarios.limpiaPantalla();
        menuBiblioteca();
    }
    
    static void listarAutores(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("LISTADO DE AUTORES EN LA BIBLIOTECA");
        for(String autor : autoresLibros){
            System.out.println(autor);
        }
        System.out.println("");
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        Utilitarios.limpiaPantalla();
        menuBiblioteca();
    }
}
