package com.adrian.service;

import com.adrian.factory.VehiculoFactory;
import com.adrian.model.Vehiculo;
import com.adrian.repository.ParqueaderoDatos;
import com.adrian.view.IValidarPago;

public class ParqueaderoFacade {

    private final GestorIngreso gIngreso;
    private final GestorSalida gSalida;
    private final ParqueaderoDatos db;
    private final IValidarPago onValidPayment;

    public ParqueaderoFacade(IValidarPago onValidPayment) {
        gIngreso = new GestorIngreso();
        gSalida = new GestorSalida(gIngreso);
        db = ParqueaderoDatos.getInstance();
        this.onValidPayment = onValidPayment;
    }

    public boolean validarIngreso(String placa) {
        return gIngreso.registrarIngreso(placa);
    }

    public boolean validarSalida(String placa) {
        return gSalida.validarSalida(placa);
    }

    public boolean validarCliente(String placa) {
        return db.existePlaca(placa);
    }

    public String registrarIngreso(String placa, String modelo, int tipo) {
        gIngreso.registrarIngreso(placa);

        if (db.existePlaca(placa)) {
            return "Error: El vehiculo no esta registrado en nuestro sistema";
        }

        try {
            Vehiculo vehiculo = VehiculoFactory.crearVehiculo(tipo, placa, modelo);
            db.guardar(vehiculo);

            return "Vehículo registrado exitosamente.";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String registrarIngreso(String placa) {
        if (!gIngreso.registrarIngreso(placa)) {
            return "Error: No se puede registrar el ingreso";
        }

        if (!db.existePlaca(placa)) {
            return "Error: El vehiculo no esta registrado en nuestro sistema";
        }

        // Conclusion => Si existe y No esta dentro del Parqueadero
        db.registrarIngreso(placa);
        return "Vehículo registrado exitosamente.";
    }

    public String procesarSalida(String placa) {
        try {
            var total = gSalida.calcularCosto(db.buscar(placa));
            int pago = onValidPayment.validarPago(total);
            if (pago < 1) { 
                return "Error: al procesar el pago del vehiculo con placas: " + placa;
            }

            /// PAAGOOOOOOOOO
            gSalida.procesarSalida(placa);
            return "Gracias por utilizarnos como ella uso el sistema.\nVehiculo con placas:"
                    + placa + " Saliendoooooooo.";

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
