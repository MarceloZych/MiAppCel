package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.models.Producto;
import org.example.repository.ProductoRepository;
import org.example.service.ProductoService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;

public class CelularServer {

        // datos de la conexión a postgre
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda_celulares";
    private static final String USER = "postgres";
    private static final String PASSWORD = "marce";
    public static void main (String[] args) throws Exception {
        // 1. Mantenemos las credenciales para pasarlas como configuración
        String url = "jdbc:postgresql://localhost:5432/tienda_celulares";
        String user = "postgres";
        String password = "marce"; // Tu contraseña actualizada

        // 2. INYECCIÓN DE DEPENDENCIAS MANUAL. y Creamos la capa de datos
        ProductoRepository repository = new ProductoRepository(url, user, password);

        // Creamos la capa de negocio inyectando el repositorio
        ProductoService service = new ProductoService(repository);

        // 3. CONFIGURACIÓN DEL SERVIDOR
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Inyectamos el servicio en el Handler (Controlador)
        server.createContext("/api/productos", new ProductosHandler(service));

        server.setExecutor(null);
        System.out.println("Servidor Capa-API iniciado en http://localhost:8080");
        server.start();
    }

    static class ProductosHandler implements HttpHandler {
        // Configuramos CORS para que Node.js pueda leer los datos
        private final ProductoService productoService;

        public ProductosHandler(ProductoService service) {
            this.productoService = service;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "´*");
            exchange.getResponseHeaders().add("Content Type", "application/json");

            String response = "";

            // Manejamos el metodo GET
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    //LLAMADA AL SERVICIO: Delegamos la responsabilidad
                    List<Producto> listaProducto = productoService.listarTodo();
                    response = convertirListaAJson(listaProducto);

                    byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                    System.out.println("La respuesta del responesBytes es: " + responseBytes);
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    try (OutputStream os = exchange.getResponseBody()){
                        os.write(response.getBytes());
                    }

                } catch (Exception e) {
                    // Buenas prácticas: Enviar un 500 si algo falla en el servidor
                    e.printStackTrace();
                    response = "{\"error\": \"Error interno del servidor\"}";
                    exchange.sendResponseHeaders(500, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                }

            } else {
                // Si no es GET, respondemos que el metodo no está permitido (405)
                exchange.sendResponseHeaders(405, -1);
            }
        }

        // METODO de utilidad para mantener el standar JSON que veniamos utilizando
        private String convertirListaAJson(List<Producto> productos) {
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                json.append("{")
                        .append("\"id\":").append(p.getId()).append(",")
                        .append("\"marca\":\"").append(p.getMarca()).append("\",")
                        .append("\"modelo\":\"").append(p.getModelo()).append("\",")
                        .append("\"precio\":").append(p.getPrecio())
                        .append("}");
                if (i < productos.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            return json.toString();
        }

    }
}
