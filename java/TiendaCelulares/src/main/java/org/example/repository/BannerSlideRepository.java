package org.example.repository;

import org.example.models.BannerSlide;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BannerSlideRepository {
    private final String url;
    private final String user;
    private final String password;

    public BannerSlideRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<BannerSlide> obtenerBannerSlideActivos () {
        List<BannerSlide> bannerSlide = new ArrayList<>();
        String query = "SELECT * FROM banner_slide ORDER BY orden ASC";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
            )
        {
            while(rs.next()){
                bannerSlide.add(new BannerSlide(
                   rs.getInt("id"), rs.getString("image_url"), rs.getString("alt_text"),
                   rs.getString("link_url"), rs.getInt("orden")
                    )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  bannerSlide;

        // MOCK DE DATOS (borrar esto cuando conectes la base de datos real)
      /*  return Arrays.asList(
                new BannerSlide(1, "/assets/img/hero-personal-1.jpg", "Mejor Red Fija", "#/fibra", 1),
                new BannerSlide(2, "/assets/img/hero-samsung.jpg", "Oferta Samsung", "#/tienda", 2),
                new BannerSlide(3, "/assets/img/hero-flow.jpg", "Mirá Flow", "#/flow", 3)
        );*/
    }
}
