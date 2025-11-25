package com.adrian.view;

import java.util.Scanner;

import com.adrian.enums.TipoVehiculo;
import com.adrian.service.ParqueaderoFacade;

public class MenuConsole implements IValidarPago {
    Scanner scan;
    ParqueaderoFacade facade;

    public MenuConsole() {
        scan = new Scanner(System.in);
        facade = new ParqueaderoFacade(this);
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
        String placa = leerTexto("Ingrese la Placa: ").toUpperCase();

        if (!facade.validarIngreso(placa)) {
            System.out.println("Error: La placa " + placa + " ya esta dentro del Parqueadero.");
            return;
        }

        if (!facade.validarCliente(placa)) {
            // Nevo cliente
            var modelo = leerTexto("Ingrese el modelo del Vehiculo con placa: " + placa);
            var tipo = mostrarCategorias();
            System.out.println(facade.registrarIngreso(placa, modelo, tipo));
        } else {
            // Ya es cliente
            System.out.println(facade.registrarIngreso(placa));
        }

    }

    private int mostrarCategorias() {
        System.out.println("------ CATEGORIAS ------");
        TipoVehiculo.valueOf("MOTO"); // TipoVehiculo.MOTO
        var tipos = TipoVehiculo.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println("\t" + (i + 1) + ". " + tipos[i].name());
        }
        return leerEntero("Seleccione el tipo de Vehiculo: ");
    }

    private void opcionRegistrarSalida() {
        System.out.println("--- NUEVA SALIDA ---");
        String placa = leerTexto("Ingrese la Placa: ").toUpperCase();
        if(!facade.validarSalida(placa)) {
            System.out.println("Error: La placa " + placa + " NO esta dentro del Parqueadero.");
            return;
        }

        System.out.println(facade.procesarSalida(placa));;
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

    @Override
    public int validarPago(double total) {
        return leerEntero("El vehiculo paga: $ " + total + "\n1.\tSI\n0.\tNO");
    }

}
