package com.adrian.model;

import java.time.LocalDateTime;

import com.adrian.enums.TipoVehiculo;

public class Carro extends Vehiculo {

    public Carro(String placa, String modelo, LocalDateTime horaIngreso) {
        super(placa, modelo, horaIngreso, TipoVehiculo.SEDAN);
    }

}
