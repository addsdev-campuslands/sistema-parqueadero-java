package com.adrian.factory;

import java.time.LocalDateTime;

import com.adrian.enums.TipoVehiculo;
import com.adrian.model.Moto;
import com.adrian.model.Suv;
import com.adrian.model.Vehiculo;
import com.adrian.model.builder.CarroBuilder;

public class VehiculoFactory {

    public static Vehiculo crearVehiculo(int opcion, String placa, String modelo) throws Exception {
        return switch (opcion) {
            case 3 -> new Moto(placa, modelo, LocalDateTime.now());
            case 6 -> new CarroBuilder()
                    .conPlaca(placa)
                    .conModelo(modelo)
                    .conHora(LocalDateTime.now().minusDays(3))
                    .build();
            case 7 -> new Suv(placa, modelo, LocalDateTime.now());
            default -> throw new Exception("Error: Tipo de Vehiculo no soportado");
        };
    }
    
    public static Vehiculo crearVehiculo(TipoVehiculo tipo, String placa, String modelo, LocalDateTime hora) throws Exception {
        return switch (tipo) {
            case MOTO -> new Moto(placa, modelo, LocalDateTime.now());
            case SEDAN -> new CarroBuilder()
                    .conPlaca(placa)
                    .conModelo(modelo)
                    .conHora(hora)
                    .build();
            case SUV -> new Suv(placa, modelo, LocalDateTime.now());
            default -> throw new Exception("Error: Tipo de Vehiculo no soportado");
        };
    }

}
