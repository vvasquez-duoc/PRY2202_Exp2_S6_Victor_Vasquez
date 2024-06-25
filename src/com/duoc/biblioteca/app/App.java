package com.duoc.biblioteca.app;

import com.duoc.biblioteca.entidades.biblioteca.Biblioteca;
import com.duoc.biblioteca.entidades.usuarios.Usuarios;
import com.duoc.biblioteca.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

public class App {
    static Biblioteca biblioteca = new Biblioteca();
    
    static ArrayList<String> itemsMenuPrincipal = new ArrayList<>();
    static ArrayList<String> itemsMenuBiblioteca = new ArrayList<>();
    static ArrayList<String> itemsMenuAdminUsuario = new ArrayList<>();
    static ArrayList<String> itemsMenuUsuario = new ArrayList<>();
    
    static HashSet<Integer> isbnUsados = new HashSet<>();
    static TreeSet<String> titulosLibros = new TreeSet<>();
    static TreeSet<String> autoresLibros = new TreeSet<>();
    
    static HashMap<String, Usuarios> usuariosRegistrados = new HashMap<>();
    static HashMap<Integer, String> librosPrestados = new HashMap<>();
    static HashMap<String, ArrayList<Integer>> librosPorTitulo = new HashMap<>();
    static HashMap<String, ArrayList<Integer>> librosPorAutor = new HashMap<>();
    
    /*public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";*/
    
    public static void main(String[] args) {
        itemsMenuPrincipal.add("MENU DE OPERACIONES DE BIBLIOTECA");
        itemsMenuPrincipal.add("MENU DE OPERACIONES DE USUARIOS");
        itemsMenuPrincipal.add("LOGIN USUARIO");
        itemsMenuPrincipal.add("SALIR");
        
        itemsMenuBiblioteca.add("REGISTRAR LIBRO EN BIBLIOTECA");
        itemsMenuBiblioteca.add("MOSTRAR TODOS LIBROS EN BIBLIOTECA");
        itemsMenuBiblioteca.add("BUSCAR LIBRO EN BIBLIOTECA");
        itemsMenuBiblioteca.add("ELIMINAR LIBRO EN BIBLIOTECA");
        itemsMenuBiblioteca.add("LISTAR TODOS LOS TITULOS DE LA BIBLIOTECA");
        itemsMenuBiblioteca.add("LISTAR TODOS LOS AUTORES DE LA BIBLIOTECA");
        itemsMenuBiblioteca.add("VOLVER A MENU PRINCIPAL");
        
        itemsMenuAdminUsuario.add("REGISTRAR USUARIO");
        itemsMenuAdminUsuario.add("MOSTRAR USUARIOS REGISTRADOS");
        itemsMenuAdminUsuario.add("BUSCAR USUARIO REGISTRADO");
        itemsMenuAdminUsuario.add("ELIMINAR USUARIO REGISTRADO");
        itemsMenuAdminUsuario.add("VOLVER AL MENU PRINCIPAL");
        
        itemsMenuUsuario.add("PRESTAR LIBRO");
        itemsMenuUsuario.add("DEVOLVER LIBRO");
        itemsMenuUsuario.add("MOSTRAR LIBROS YA PRESTADOS");
        itemsMenuUsuario.add("VOLVER A MENU PRINCIPAL");
        
        Utilitarios.limpiaPantalla();
        AppBiblioteca.llenarBiblioteca();
        AppUsuarios.llenarUsuarios();
        AppLibros.llenarLibrosPrestados();
        menuPrincipal();
    }
    
    static void menuPrincipal(){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        try{
            do{
                System.out.println("BIENVENIDO A SISTEMA DE ADMINISTRACION DE BIBLIOTECA");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenuPrincipal.size(); i++){
                    System.out.println("["+(i + 1)+"] "+itemsMenuPrincipal.get(i));
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenuPrincipal.size()){
                    Utilitarios.limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenuPrincipal.size());
        }catch(InputMismatchException e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuPrincipal();
        }
        
        if(opcion == 1){
            Utilitarios.limpiaPantalla();
            menuBiblioteca();
        }
        
        if(opcion == 2){
            Utilitarios.limpiaPantalla();
            menuAdminUsuario();
        }
        
        if(opcion == 3){
            Utilitarios.limpiaPantalla();
            AppUsuarios.loginUsuario();
        }
        
        if(opcion == 4){
            Utilitarios.limpiaPantalla();
            System.out.println("GRACIAS POR USAR SISTEMA DE ADMINISTRACION DE BIBLIOTECA");
        }
    }
    
    static void menuBiblioteca(){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        try{
            do{
                System.out.println("ADMINISTRACION DE BIBLIOTECA");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenuBiblioteca.size(); i++){
                    System.out.println("["+(i + 1)+"] "+itemsMenuBiblioteca.get(i));
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenuBiblioteca.size()){
                    Utilitarios.limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenuBiblioteca.size());
        }catch(InputMismatchException e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuBiblioteca();
        }
        
        if(opcion == 1){
            Utilitarios.limpiaPantalla();
            AppBiblioteca.registrarLibro();
        }
        
        if(opcion == 2){
            Utilitarios.limpiaPantalla();
            AppBiblioteca.listarLibro();
        }
        
        if(opcion == 3){
            Utilitarios.limpiaPantalla();
            AppBiblioteca.encontrarLibro();
        }
        
        if(opcion == 4){
            Utilitarios.limpiaPantalla();
            AppBiblioteca.eliminarLibro();
        }
        
        if(opcion == 5){
            Utilitarios.limpiaPantalla();
            AppBiblioteca.listarTitulos();
        }
        
        if(opcion == 6){
            Utilitarios.limpiaPantalla();
            AppBiblioteca.listarAutores();
        }
        
        if(opcion == 7){
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }
    }
    
    static void menuAdminUsuario(){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        try{
            do{
                System.out.println("ADMINISTRACION DE USUARIOS");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenuAdminUsuario.size(); i++){
                    System.out.println("["+(i + 1)+"] "+itemsMenuAdminUsuario.get(i));
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenuAdminUsuario.size()){
                    Utilitarios.limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenuAdminUsuario.size());
        }catch(InputMismatchException e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuPrincipal();
        }
        
        if(opcion == 1){
            Utilitarios.limpiaPantalla();
            AppUsuarios.registrarUsuario(false);
        }
        
        if(opcion == 2){
            Utilitarios.limpiaPantalla();
            AppUsuarios.mostrarUsuarios();
        }
        
        if(opcion == 3){
            Utilitarios.limpiaPantalla();
            AppUsuarios.buscarUsuario();
        }
        
        if(opcion == 4){
            Utilitarios.limpiaPantalla();
            AppUsuarios.eliminarUsuario();
        }
        
        if(opcion == 5){
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }
    }
    
    static void menuUsuario(Usuarios usuario){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        
        Utilitarios.limpiaPantalla();
        System.out.println("LOGIN EXITOSO");
        System.out.println("--------------------------");
        System.out.println("RUT      : "+Utilitarios.formatoRut(usuario.getRut()));
        System.out.println("NOMBRE   : "+usuario.getNombreCompleto());
        System.out.println("DIRECCION: "+usuario.getDireccion()+". "+usuario.getComuna());
        System.out.println("--------------------------");
        
        try{
            do{
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenuUsuario.size(); i++){
                    System.out.println("["+(i + 1)+"] "+itemsMenuUsuario.get(i));
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenuUsuario.size()){
                    Utilitarios.limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenuUsuario.size());
        }catch(InputMismatchException e){
            Utilitarios.limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuUsuario(usuario);
        }
        
        if(opcion == 1){
            Utilitarios.limpiaPantalla();
            AppLibros.prestaLibro(usuario);
        }
        
        if(opcion == 2){
            Utilitarios.limpiaPantalla();
            AppLibros.devuelveLibro(usuario);
        }
        
        if(opcion == 3){
            Utilitarios.limpiaPantalla();
            AppLibros.mostrarLibrosYaPrestados(usuario);
        }
        
        if(opcion == 4){
            Utilitarios.limpiaPantalla();
            menuPrincipal();
        }
    }
}
