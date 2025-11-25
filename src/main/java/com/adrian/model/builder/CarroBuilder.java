package com.adrian.model.builder;

import java.time.LocalDateTime;

import com.adrian.model.Carro;
import com.adrian.model.Vehiculo;

public class CarroBuilder implements VehiculoBuilder {
    private String placa, modelo;
    private LocalDateTime horaIngreso;

    public CarroBuilder() {
        placa = "NNN000";
        modelo = "2000";
        horaIngreso = LocalDateTime.now();
    }

    @Override
    public VehiculoBuilder conPlaca(String placa) {
        this.placa = placa;
        return this;
    }

    @Override
    public VehiculoBuilder conModelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    @Override
    public VehiculoBuilder conHora(LocalDateTime hora) {
        this.horaIngreso = hora;
        return this;
    }

    @Override
    public Vehiculo build() {
        return new Carro(placa, modelo, horaIngreso);
    }
}
