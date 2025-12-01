package com.adrian.service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.adrian.model.Vehiculo;

public class ServicioFacturacion {
    private final String PATH_FACTURAS = "facturas";

    public void facturar(Vehiculo vehiculo, String placa, double total, long horas) {
        //var numFactura = UUID.randomUUID();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        var fileName = "factura_" + placa + "_" + timestamp + ".txt";
        File dirFile = new File(PATH_FACTURAS);
        if(!dirFile.exists()) dirFile.mkdirs();

        File file = new File(PATH_FACTURAS, fileName);


        try (FileWriter fw = new FileWriter(file)) {
            PrintWriter pw = new PrintWriter(fw);
            pw.println("=== 🅿️ RECIBO DE PARQUEADERO ===");
            pw.println("Factura Nro: " + timestamp);
            pw.println("Fecha: " + LocalDateTime.now());
            pw.println("--------------------------------");
            pw.println("Vehículo: " + vehiculo.getTipoVehiculo() + " - " + vehiculo.getModelo());
            pw.println("Placa: " + vehiculo.getPlaca());
            pw.println("--------------------------------");
            pw.println("Tiempo total: " + horas + " horas");
            pw.println("TOTAL PAGADO: $" + total);
            pw.println("================================");
            pw.println("¡Gracias por su visita!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al almacenar la factura del Vehiculo con placas: "+placa);
        }
    }
}
