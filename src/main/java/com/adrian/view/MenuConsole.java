package com.adrian.view;

import java.util.Scanner;

import com.adrian.service.GestorIngreso;

public class MenuConsole {
    Scanner scan;
    GestorIngreso gIngreso;

    public MenuConsole() {
        scan = new Scanner(System.in);
        gIngreso = new GestorIngreso();
    }

    public void iniciar() {
        int opcion = -1;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccion una opcion del Menu anterior:");
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcionMenu) {
        switch (opcionMenu) {
            case 1 -> opcionIngresarVehiculo();
            case 2 -> opcionRegistrarSalida();
            case 3 -> opcionConsultarVehiculo();
            case 0 -> System.out.println("Muchas gracias por usarnos.....");
            default -> System.out.println("Opcion no valida.");
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("--- X SISTEMA DE PARQUEADERO X ---");
        System.out.println("\t1. Ingresar Vehiculo");
        System.out.println("\t2. Registrar Salida y Pagar");
        System.out.println("\t3. Consultar Vehiculo");
        System.out.println("\t0. Salir");
        System.out.println("------------------------------------");
    }

    private void opcionConsultarVehiculo() {
        
    }

    private void opcionIngresarVehiculo() {
        System.out.println("--- NUEVO INGRESO ---");
        String placa = leerTexto("Ingrese la Placa: ");

        if(gIngreso.registrarIngreso(placa)) {
            //Validar si existe en el sistema
            // NO -> Registro
        } else {
            
        }

    }

    private void opcionRegistrarSalida() {

    }

    // Utilidades
    private int leerEntero(String msg) {
        System.out.println(msg);
        try {
            String valorIngresado = scan.nextLine();
            return Integer.parseInt(valorIngresado);
        } catch (NumberFormatException e) {
            System.out.println("Po'favo ingrese un numero valido. ");
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
    }

    private String leerTexto(String msg) {
        System.out.println(msg);
        return scan.nextLine().trim().toLowerCase();
    }

}
