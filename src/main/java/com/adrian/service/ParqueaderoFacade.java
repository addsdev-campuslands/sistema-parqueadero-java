package com.adrian.service;

import com.adrian.factory.VehiculoFactory;
import com.adrian.model.Vehiculo;
import com.adrian.repository.ParqueaderoDatos;

public class ParqueaderoFacade {

    private final GestorIngreso gIngreso;
    private final GestorSalida gSalida;
    private final ParqueaderoDatos db;

    public ParqueaderoFacade() {
        gIngreso = new GestorIngreso();
        gSalida = new GestorSalida(gIngreso);
        db = ParqueaderoDatos.getInstance();
    }

    public boolean validarIngreso(String placa) {
        return gIngreso.registrarIngreso(placa);
    }

    public boolean validarCliente(String placa) {
        return db.existePlaca(placa);
    }

    public String registrarIngreso(String placa, String modelo, int tipo) {
        if (!gIngreso.registrarIngreso(placa)) {
            return "Error: No se puede registrar el ingreso";
        }

        if (db.existePlaca(placa)) {
            return "Error: El vehiculo no esta registrado en nuestro sistema";
        }

        try {
            Vehiculo vehiculo = VehiculoFactory.crearVehiculo(tipo, placa, modelo);
            db.guardar(vehiculo);

            return "Vehículo registrado exitosamente.";
        }catch (Exception e) {
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

    public void procesarSalida(String placa) {

    }
}
