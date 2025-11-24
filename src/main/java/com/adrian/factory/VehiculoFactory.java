package com.adrian.factory;

import java.time.LocalDateTime;

import com.adrian.model.Moto;
import com.adrian.model.Suv;
import com.adrian.model.Vehiculo;
import com.adrian.model.builder.CarroBuilder;

public class VehiculoFactory {

    public static Vehiculo crearVehiculo(int opcion, String placa, String modelo) throws Exception {
        return switch (opcion) {
            case 1 -> new Moto(placa, modelo, LocalDateTime.now());
            case 3 -> new CarroBuilder()
                    .conPlaca(placa)
                    //.conModelo(modelo)
                    .conHora(LocalDateTime.now().minusDays(3))
                    .build();
            case 4 -> new Suv(placa, modelo, LocalDateTime.now());
            default -> throw new Exception("Error: Tipo de Vehiculo no soportado");
        };
    }

}
