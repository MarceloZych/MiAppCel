package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.config.DatabaseConfig;
import org.example.controller.BannerSlideHandler;
import org.example.controller.ProductosHandler;
import org.example.repository.BannerSlideRepository;
import org.example.repository.ProductoRepository;
import org.example.service.BannerSlideService;
import org.example.service.ProductoService;

import java.net.InetSocketAddress;

public class CelularServer {

    public static void main (String[] args) throws Exception {
        // --- 1. PRODUCTOS ---
        ProductoRepository productoRepo = new ProductoRepository(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        ProductoService productoService = new ProductoService(productoRepo);

        // --- 2. BANNERS (¡Nuevo!) ---
        BannerSlideRepository bannerRepo = new BannerSlideRepository(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        BannerSlideService bannerService = new BannerSlideService(bannerRepo);

        // --- 3. SERVIDOR HTTP ---
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // --- 4. ENDPOINTS ---
        server.createContext("/api/productos", new ProductosHandler(productoService));
        server.createContext("/api/banners", new BannerSlideHandler(bannerService)); // Mapeamos la nueva ruta

        server.setExecutor(null);
        System.out.println("Servidor Capa-API iniciado en http://localhost:8080");
        server.start();
    }
}
