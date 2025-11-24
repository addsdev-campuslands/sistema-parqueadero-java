package com.adrian.enums;

public enum TipoVehiculo {
    BICICLETA(2500, 90000),
    MONOPATIN(500, 4000),
    MOTO(3500, 125000),
    TRICIMOTO(4500,250000),
    CUATRIMOTO(4500,250000),
    SEDAN(4500, 250000),
    SUV(4500, 250000),
    CAMION(5000, 270000),
    CAMIONETA(4500, 250000);

    private final double tarifaPorTiempo;
    private final double tarifaPorMensual;

    private TipoVehiculo(double tarifaPorTiempo, double tarifaPorMensual) {
        this.tarifaPorMensual = tarifaPorMensual;
        this.tarifaPorTiempo = tarifaPorTiempo;
    }

    public double getTarifaPorTiempo() {return tarifaPorTiempo;}
    public double getTarifaPorMensual() {return tarifaPorMensual;}
    
}
