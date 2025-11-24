package com.adrian.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.adrian.model.Carro;
import com.adrian.model.Vehiculo;

public class ParqueaderoDatos {

    private static ParqueaderoDatos instancia;

    private final Set<String> placas = new HashSet<>();

    private final Map<String, Vehiculo> mapaPlacas = new HashMap<>();

    public static ParqueaderoDatos getInstance() {
        if(instancia == null) {
            instancia = new ParqueaderoDatos();
        }

        return instancia;
    }

    private ParqueaderoDatos() {
        placas.add("ABC123");
        placas.add("ABC124");
        placas.add("ABC125");
        mapaPlacas.put("ABC123", new Carro("ABC123", "2023", LocalDateTime.now().minusDays(2)));
        mapaPlacas.put("ABC124", new Carro("ABC124", "2024", LocalDateTime.now().minusDays(1)));
        mapaPlacas.put("ABC125", new Carro("ABC125", "2025", LocalDateTime.now().minusMinutes(325)));
    }

    public void guardar(Vehiculo vehiculo) {
        placas.add(vehiculo.getPlaca());
        mapaPlacas.put(vehiculo.getPlaca(), vehiculo);
    }

    public void registrarIngreso(String placa) {
        Vehiculo v = mapaPlacas.get(placa);
        v.registrarIngreso();
    }

    public boolean existePlaca(String placa) {
        return placas.contains(placa);
    }

    public Vehiculo buscar(String placa) {
        return mapaPlacas.get(placa);
    }
    
}
