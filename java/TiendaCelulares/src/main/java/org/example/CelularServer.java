package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

public class CelularServer {
        // datos de la conexión a postgre
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda_celulares";
    private static final String USER = "postgres";
    private static final String PASSWORD = "marce";

    public static void main (String[] args) throws IOException {
        // servidor en el puerto 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // Ruta o endpint llamado /api/productos
        server.createContext("/api/productos", new ProductosHandler());

        server.setExecutor(null); // crea un ejecutor por defecto
        System.out.println("Servidor Java iniciado en http://localhost:8080");
        server.start();
    }

    static class ProductosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            // Permitir que el front (Node.js) pueda consultar este servidor (CORS)
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String response = "";
            if ("GET".equals(exchange.getRequestMethod())) {
                response = obtenerProductosDesdeDB();
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private String obtenerProductosDesdeDB (){
            StringBuilder json = new StringBuilder("[");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM productos")
            ) {
                boolean primero = true;

                while(rs.next()){
                    if (!primero){
                        json.append(",");
                    }
                    // Usamos comillas dobles explícitas para evitar errores de formato
                    json.append("{");
                    json.append("\"id\":").append(rs.getInt("id_id")).append(",");
                    json.append("\"marca\":\"").append(rs.getString("marca")).append("\",");
                    json.append("\"modelo\":\"").append(rs.getString("modelo")).append("\",");
                    json.append("\"precio\":\"").append(rs.getDouble("precio")).append("\"");
                    json.append("}");
                    primero = false;
                }
             
                json.append("]");
            } catch (SQLException e) {
                e.printStackTrace();
                return "{\"error\": \"Error de BD: " + e.getMessage() + "\"}";
            }
            return json.toString();
        }
    }
}
