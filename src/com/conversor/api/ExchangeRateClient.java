package com.conversor.api;

       import com.conversor.model.ExchangeRateResponse;
       import com.google.gson.Gson;
       import java.io.IOException;
       import java.net.URI;
       import java.net.http.HttpClient;
       import java.net.http.HttpRequest;
       import java.net.http.HttpResponse;

    public class ExchangeRateClient {

       private static final String API_KEY = "5f2bf5a27bd752c8682776f7"; // Reemplaza con tu clave real
       private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
       private final Gson gson = new Gson();

    public String obtenerTasaCambio(String baseCurrency, String targetCurrency) {
        String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ExchangeRateResponse rateResponse = gson.fromJson(response.body(), ExchangeRateResponse.class);

            if (!"success".equalsIgnoreCase(rateResponse.getResult())) {
                return "Error en la respuesta de la API.";
            }

            Double tasa = rateResponse.getConversionRates().get(targetCurrency);

            return tasa != null ? tasa.toString() : "Moneda no encontrada.";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
