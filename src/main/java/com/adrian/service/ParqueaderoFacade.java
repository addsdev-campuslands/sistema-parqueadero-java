package com.adrian.service;

import com.adrian.factory.VehiculoFactory;
import com.adrian.model.Vehiculo;
import com.adrian.repository.ParqueaderoDatos;
import com.adrian.view.IValidarPago;
import com.adrian.view.IValidarTipo;

public class ParqueaderoFacade {

    private final GestorIngreso gIngreso;
    private final GestorSalida gSalida;
    private final ParqueaderoDatos db;
    private final IValidarPago onValidPayment;
    private final IValidarTipo onRequestType;

    public ParqueaderoFacade(IValidarPago onValidPayment, IValidarTipo onRequestType) {
        gIngreso = new GestorIngreso();
        gSalida = new GestorSalida(gIngreso);
        db = ParqueaderoDatos.getInstance();
        this.onValidPayment = onValidPayment;
        this.onRequestType = onRequestType;
    }

    private boolean validarIngreso(String placa) {
        return gIngreso.registrarIngreso(placa);
    }

    private boolean validarSalida(String placa) {
        return gSalida.validarSalida(placa);
    }

    public String registrarIngreso(String placa) {
        if (!validarIngreso(placa)) {
            return "Error: La placa " + placa + " ya esta dentro del Parqueadero.";
        }

        if (!db.existePlaca(placa)) {
            // NUEVO
            var modelo = onRequestType.seleccionarModelo(placa);
            var tipo = onRequestType.seleccionarTipoVehiculo();
            try {
                Vehiculo vehiculo = VehiculoFactory.crearVehiculo(tipo, placa, modelo);
                db.guardar(vehiculo);

                return "Vehículo registrado exitosamente.";
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        // Conclusion => Si existe y No esta dentro del Parqueadero
        db.registrarIngreso(placa);
        return "Vehículo registrado exitosamente.";
    }

    public String procesarSalida(String placa) {
        if(!validarSalida(placa)) {
            return "Error: La placa " + placa + " NO esta dentro del Parqueadero.";
        }

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
