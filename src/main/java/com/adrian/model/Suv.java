package com.adrian.model;

import java.time.LocalDateTime;

import com.adrian.enums.TipoVehiculo;

public class Suv extends Vehiculo {

    public Suv(String placa, String modelo, LocalDateTime horaIngreso) {
        super(placa, modelo, horaIngreso, TipoVehiculo.SUV);
    }
    
}
