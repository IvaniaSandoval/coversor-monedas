package com.conversor;

import com.conversor.api.ExchangeRateClient;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<String> monedasValidas = Set.of("USD", "ARS", "BRL", "COP");
        ExchangeRateClient client = new ExchangeRateClient();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una conversión:");
            System.out.println("1. USD → ARS (Peso argentino)");
            System.out.println("2. ARS → USD");
            System.out.println("3. USD → BRL (Real brasileño)");
            System.out.println("4. BRL → USD");
            System.out.println("5. USD → COP (Peso colombiano)");
            System.out.println("6. COP → USD");
            System.out.println("0. Salir");

            System.out.print("Ingrese el número de la opción deseada: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar salto de línea

            if (opcion == 0) {
                System.out.println("¡Hasta luego!");
                break;
            }

            String base = "";
            String destino = "";

            switch (opcion) {
                case 1 -> { base = "USD"; destino = "ARS"; }
                case 2 -> { base = "ARS"; destino = "USD"; }
                case 3 -> { base = "USD"; destino = "BRL"; }
                case 4 -> { base = "BRL"; destino = "USD"; }
                case 5 -> { base = "USD"; destino = "COP"; }
                case 6 -> { base = "COP"; destino = "USD"; }
                default -> {
                    System.out.println("❌ Opción inválida.");
                    continue;
                }
            }

            System.out.print("Ingrese el monto a convertir: ");
            double monto = scanner.nextDouble();

            String tasaStr = client.obtenerTasaCambio(base, destino);
            if (tasaStr == null || tasaStr.equals("Moneda no encontrada.") || tasaStr.equals("Error en la respuesta de la API.")) {
                System.out.println("❌ Error al obtener la tasa de cambio.");
                continue;
            }

            try {
                double tasa = Double.parseDouble(tasaStr);
                double convertido = monto * tasa;
                System.out.println("\n+-------------------------------+");
                System.out.printf("|  Conversión: %-5s => %-5s     |\n", base, destino);
                System.out.println("+-------------------------------+");
                System.out.printf("|  Monto:      %-15.2f |\n", monto);
                System.out.printf("|  Tasa:       %-15.4f |\n", tasa);
                System.out.printf("|  Resultado:  %-15.2f |\n", convertido);
                System.out.println("+-------------------------------+\n");

            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error al convertir la tasa: " + tasaStr);
            }
        }
    }
}



