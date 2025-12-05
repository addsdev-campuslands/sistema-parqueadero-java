package com.adrian.service;

import java.util.HashSet;
import java.util.Set;

import com.adrian.repository.ParqueaderoDatos;

public class GestorIngreso implements IValidator {
    private Set<String> placasRegistradas = new HashSet<>();

    public GestorIngreso() {
        loadParqueaderoData();
    }

    private void loadParqueaderoData() {
        placasRegistradas = ParqueaderoDatos.getInstance().loadParqueaderoDataIn();
    }
    
    public boolean registrarIngreso(String placa) {
        return placasRegistradas.add(placa);      
    }

    @Override
    public boolean exitePlaca(String placa) {
        return placasRegistradas.contains(placa);
    }

    @Override
    public void realizarSalida(String placa) {
        placasRegistradas.remove(placa);
        ParqueaderoDatos.getInstance().registrarSalida(placa);
    }
    
}
