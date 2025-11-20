package com.adrian.model;

import java.time.LocalDateTime;

import com.adrian.enums.TipoVehiculo;

public abstract class Vehiculo {
    private String placa;
    private String modelo;
    private LocalDateTime horaIngreso;
    private TipoVehiculo tipoVehiculo;

    public Vehiculo(String placa, String modelo, LocalDateTime horaIngreso, TipoVehiculo tipoVehiculo) {
        this.placa = placa;
        this.modelo = modelo;
        this.horaIngreso = horaIngreso;
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void registrarIngreso() {
        horaIngreso = LocalDateTime.now();
    }
    
}
