package org.example.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpUtils {

    // Configuracion de CORS
    public static void configurarCors (HttpExchange httpExchange) {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    // Enviar respuesta JSON
    public static void enviarRespuestaJson (HttpExchange httpExchange, int statusCode, String jsonResponse) throws IOException {
        httpExchange.getResponseHeaders().add("Content-Type", "Application/json; charset=UTF-8");
        byte[] responseByte = jsonResponse.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(statusCode, responseByte.length);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseByte);
        }
    }

    // Método auxiliar para limpiar el código de errores
    public static void enviarError(HttpExchange exchange, int code, String msg) throws IOException {
        byte[] response = msg.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }
}
