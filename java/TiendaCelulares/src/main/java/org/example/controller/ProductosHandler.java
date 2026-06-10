package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.models.Producto;
import org.example.service.ProductoService;
import org.example.util.JsonUtils;

import java.io.IOException;
import java.util.List;

import static org.example.util.HttpUtils.*; // configurarCors(), enviarError(),

public class ProductosHandler implements HttpHandler {
        // Configuramos CORS para que Node.js pueda leer los datos
        private final ProductoService productoService;

        public ProductosHandler(ProductoService service) {
            this.productoService = service;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            configurarCors(httpExchange);

            String methodResponse = httpExchange.getRequestMethod();

            if ("OPTIONS".equals(methodResponse)) {
                httpExchange.sendResponseHeaders(204,-1);
                httpExchange.close();
                return;
            }

            // Manejamos el metodo GET
            if ("GET".equals(methodResponse)) {
                try {
                    List<Producto> listaProducto = productoService.listarTodo();
                    String response = JsonUtils.convertirListaAJson(listaProducto);
                    enviarRespuestaJson(httpExchange, 200, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    enviarError(httpExchange, 500, "{\"error\": \"Error interno del servidor\"}");
                }
                return;
            }

            if ("DELETE".equals(methodResponse)) {
                try {
                    String query = httpExchange.getRequestURI().getQuery();
                    if (query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        productoService.desactivarProducto(id);
                        httpExchange.sendResponseHeaders(204, -1);
                    } else {
                        enviarError(httpExchange, 400, "{\"error\": \"ID No Proporcionado\"}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    enviarError(httpExchange, 400, "{\"error\": \"Solicitud incorrecta\"}");
                }
                httpExchange.close();
                return;
            }
            // Sí no coincide con ningún método anterior
            enviarError(httpExchange, 405, "Método no permitido");
        }
    }

