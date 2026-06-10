package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.models.BannerSlide;
import org.example.service.BannerSlideService;
import org.example.util.HttpUtils;
import org.example.util.JsonUtils;

import java.io.IOException;
import java.util.List;

public class BannerSlideHandler implements HttpHandler {
    private final BannerSlideService bannerSlideService;

    public BannerSlideHandler(BannerSlideService bannerSlideService) {
        this.bannerSlideService = bannerSlideService;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Configuracion del CORS de una sola línea
        HttpUtils.configurarCors(httpExchange);
        String method = httpExchange.getRequestMethod();

        // Manejo el pre-flight request (OPTIONS) vital para el Frontend
        if ("OPTIONS".equals(method)) {
            httpExchange.sendResponseHeaders(204, -1);
            httpExchange.close();
            return;
        }

        if ("GET".equals(method)) {
            try {
                List<BannerSlide> bannerSlidesList = bannerSlideService.listarBannerSlide();
                String jsonResponse = JsonUtils.convertirListaAJson(bannerSlidesList);
                HttpUtils.enviarRespuestaJson(httpExchange, 200, jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                HttpUtils.enviarError(httpExchange, 500, "{\"error\": \"Error interno del servidor\"}");
            }
            return;
        }
        HttpUtils.enviarError(httpExchange, 405, "{\"error\": \"Método no permitido\"}");
    }
}
