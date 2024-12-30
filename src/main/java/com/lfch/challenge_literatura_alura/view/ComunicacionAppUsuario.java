package com.lfch.challenge_literatura_alura.view;


public class ComunicacionAppUsuario implements IComunicacionAppUsuario{

    private String saludo = """
            ****************************************************************************
            
            ¡Hola, estimado usuario! Bienvenido a nuestra aplicación
            de datos de libros con actualización de numero de descargas 
            en tiempo real. Este aplicativo utiliza datos actualizados de 
            fuentes confiables a través de una API para proporcionarte 
            los datos más recientes.
            Estamos aquí para ayudarte con tus busqueda de libros y autores
            de manera rápida y eficiente.
            ¡Es un placer atender tu solicitud!
            
            *****************************************************************************
            """;
    private String menuPrincipal = """
            *****************************************************************************
            
            Indica el número de la operación que desea realizar
            1)  Buscar un libro por titulo 
            2)  Buscar en la base de datos un libro por su idioma 
            3)  Buscar libros por su Autor en la base de datos
            4)  Buscar libros por el rango de número de descargas en la base de datos
            5)  Buscar Autor por el rango de fecha de su nacimiento en la base de datos
            6)  Buscar Autor por el rango de fecha de su fallecimiento en la base de datos
            7)  Buscar Autor por su edad
            8)  Buscar Autor por su rango de edad en la base de datos        
            9)  Indicar la cantidad total de libros en la base de datos
            10) Estadisticas Autores 
            11) Estadisticas Libros    
            0) salir
            
            *****************************************************************************
            """;
    private String mensajeSalida = """
            Gracias por utilizar el aplicativo, te esperamos de nuevo 
            """;
    private String solicitarNombreLibro = """
            ********************************************************
            Por favor indique el nombre del libro que desea buscar en el sistema
            ********************************************************
            """;


    @Override
    public void saludo() {
        System.out.println(saludo);
    }

    @Override
    public void menuPrincipal() {
        System.out.println(menuPrincipal);
    }

    @Override
    public void despedida() {
        System.out.println(mensajeSalida);
    }

    @Override
    public void solicitarNombreLibro() {
        System.out.println(solicitarNombreLibro);
    }
}
