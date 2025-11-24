package com.adrian.view;

import java.util.Scanner;

import com.adrian.enums.TipoVehiculo;
import com.adrian.factory.VehiculoFactory;
import com.adrian.model.Carro;
import com.adrian.model.Vehiculo;
import com.adrian.repository.ParqueaderoDatos;
import com.adrian.service.GestorIngreso;
import com.adrian.service.GestorSalida;

public class MenuConsole {
    Scanner scan;
    GestorIngreso gIngreso;
    GestorSalida gSalida;
    ParqueaderoDatos pDatos;

    public MenuConsole() {
        scan = new Scanner(System.in);
        gIngreso = new GestorIngreso();
        pDatos = ParqueaderoDatos.getInstance();
        gSalida = new GestorSalida(gIngreso);
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

        if (gIngreso.registrarIngreso(placa)) {
            // Validar si existe en el sistema
            if (ParqueaderoDatos.getInstance().existePlaca(placa)) {
                pDatos.registrarIngreso(placa);
                System.out.println("Vehículo registrado exitosamente.");
            }
            // NO -> Registro
            else {
                var modelo = leerTexto("Ingrese el modelo del Vehiculo con placa: " + placa);
                var tipo = mostrarCategorias();
                try {
                    Vehiculo vehiculo = VehiculoFactory.crearVehiculo(tipo, placa, modelo);
                    if (vehiculo instanceof Carro) {
                        var carro = (Carro) vehiculo;
                        var carro2 = carro.clone();

                        System.out.println(carro.getHoraIngreso().toString());
                        System.out.println(((Carro) carro2).getHoraIngreso().toString());
                    }
                    // Carro carrito = new Carro(placa, modelo, LocalDateTime.now());
                    // pDatos == ParqueaderoDatos.getInstance()
                    ParqueaderoDatos.getInstance().guardar(vehiculo);

                    System.out.println("Vehículo registrado exitosamente.");
                } catch (CloneNotSupportedException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        } else {
            System.out.println("Error: La placa " + placa + " ya esta dentro del Parqueadero.");
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

        if (gSalida.validarSalida(placa)) {
            try {
                var total = gSalida.calcularCosto(pDatos.buscar(placa));
                int pago = leerEntero("El vehiculo o paga: $ " + total + "\n1.\tSI\n0.\tNO");
                if (pago < 1) {
                    System.out.println("Error: al procesar el pago del vehiculo con placas: " + placa);
                    return;
                }

                /// PAAGOOOOOOOOO
                gSalida.procesarSalida(placa);
                System.out.println("Gracias por utilizarnos como ella uso el sistema.\nVehiculo con placas:"
                        + placa + " Saliendoooooooo.");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: La placa " + placa + " NO esta dentro del Parqueadero.");
        }

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
