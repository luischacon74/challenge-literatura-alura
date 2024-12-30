package com.lfch.challenge_literatura_alura.utils;


import java.util.InputMismatchException;
import java.util.Scanner;

public class IngresarDatos implements IIngresarDatos{

    Scanner teclado = new Scanner(System.in);
    @Override
    public String ingresarTexto() {
        return teclado.nextLine();
    }

    @Override
    public Integer ingresarNumeroEntero() {
        while (true) {
            try {
                return teclado.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor ingresa un número válido.");
                teclado.next();
            }
        }
    }

    @Override
    public void limpiarLinea() {
        teclado.nextLine();
    }

    @Override
    public void cerrar() {
        teclado.close();
    }
}
