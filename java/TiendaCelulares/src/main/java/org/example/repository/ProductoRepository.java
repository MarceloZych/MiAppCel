package org.example.repository;

import com.sun.source.tree.BreakTree;
import org.example.models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {
    private final String url;
    private final String user;
    private final String pass;

    public ProductoRepository(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public List<Producto> findAll() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_id, marca, modelo, precio, FROM productos";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
            ) {
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id_id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getDouble("precio")
                ));
                }
            }
        return productos;
    }

}
