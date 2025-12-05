package com.adrian.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.adrian.configuration.ConnectionDB;
import com.adrian.enums.TipoVehiculo;
import com.adrian.factory.VehiculoFactory;
import com.adrian.model.Vehiculo;

public class ParqueaderoDatos {

    private static ParqueaderoDatos instancia;

    private final Set<String> placas = new HashSet<>();

    private final Map<String, Vehiculo> mapaPlacas = new HashMap<>();

    public static ParqueaderoDatos getInstance() {
        if (instancia == null) {
            instancia = new ParqueaderoDatos();
        }

        return instancia;
    }

    private ParqueaderoDatos() {
        loadParqueaderoData();
    }

    private void loadParqueaderoData() {
        String sqlzo = "SELECT placa, modelo, tipo, hora_ingreso FROM vehiculos ORDER BY hora_ingreso ASC";
        try (Connection db = ConnectionDB.connect(); PreparedStatement stmt = db.prepareStatement(sqlzo)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                var placa = result.getString("placa");
                var modelo = result.getString("modelo");
                var tipo = result.getString("tipo");
                var hora = result.getObject("hora_ingreso", LocalDateTime.class);
                var tipoVehiculo = TipoVehiculo.valueOf(tipo);
                placas.add(placa);
                mapaPlacas.put(placa, VehiculoFactory.crearVehiculo(tipoVehiculo, placa, modelo, hora));
            }
        } catch (Exception e) {
            System.out.println("Error!, esta maal");
        }
    }

    public void guardar(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos(placa, modelo, tipo, hora_ingreso) VALUES(?,?,?,?)";
        try (Connection db = ConnectionDB.connect(); PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setString(3, vehiculo.getTipoVehiculo().name());
            stmt.setObject(4, vehiculo.getHoraIngreso(), Types.TIMESTAMP);
            stmt.executeUpdate();

            // Cache??
            placas.add(vehiculo.getPlaca());
            mapaPlacas.put(vehiculo.getPlaca(), vehiculo);
        } catch (Exception e) {
            System.out.println("Error! esta maaaaal.");
        }

    }

    public void registrarIngreso(String placa) {
        Vehiculo v = mapaPlacas.get(placa);
        v.registrarIngreso();
    }

    public void registrarSalida(String placa) {
        Vehiculo v = mapaPlacas.get(placa);
        v.registrarSalida();

        String sqlzo = "UPDATE vehiculos SET hora_ingreso = NULL WHERE placa = ?";
        try (Connection db = ConnectionDB.connect(); PreparedStatement stmt = db.prepareStatement(sqlzo)) {
            stmt.setString(1, placa);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error!, esta maal");
        }
    }

     public Set<String> loadParqueaderoDataIn() {
        Set<String> placasRegistradas = new HashSet<>();
        String sqlzo = "SELECT placa FROM vehiculos WHERE hora_ingreso IS NOT NULL";
        try (Connection db = ConnectionDB.connect(); PreparedStatement stmt = db.prepareStatement(sqlzo)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                var placa = result.getString("placa");
                placasRegistradas.add(placa);
            }
        } catch (Exception e) {
            System.out.println("Error!, esta maal");
        }

        return placasRegistradas;
    }

    public boolean existePlaca(String placa) {
        return placas.contains(placa);
    }

    public Vehiculo buscar(String placa) {
        return mapaPlacas.get(placa);
    }

}
