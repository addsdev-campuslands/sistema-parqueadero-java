package com.adrian.service;

import java.util.HashSet;
import java.util.Set;

public class GestorIngreso implements IValidator {
    private Set<String> placasRegistradas = new HashSet<>();

    public GestorIngreso() {
        placasRegistradas.add("ABC123");
        placasRegistradas.add("ABC124");
        placasRegistradas.add("ABC125");
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
    }
    
}
