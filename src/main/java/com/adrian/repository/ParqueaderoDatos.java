package com.adrian.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.adrian.model.Vehiculo;

public class ParqueaderoDatos {

    private final Set<String> placas = new HashSet<>();

    private final Map<String, Vehiculo> mapaPlacas = new HashMap<>();
    
}
