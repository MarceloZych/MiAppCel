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
        String sql = "SELECT " +
                "id_id, marca, modelo,color,almacenamiento_gb," +
                "ram_gb, precio,stock,imagen_url, es_5g, activo " +
                " FROM productos WHERE activo = TRUE";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
            ) {
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id_id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("color"),
                    rs.getInt("almacenamiento_gb"),
                    rs.getInt("ram_gb"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("imagen_url"),
                    rs.getBoolean("es_5g"),
                    rs.getBoolean("activo")
                ));
                }
            }
        return productos;
    }
    public void softDelete(int id) throws SQLException {
        String sql = "UPDATE productos SET activo = FALSE WHERE id_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement pstmt = conn.prepareStatement(sql))
        // Usamos "PreparedStatement" para evitar injeccióm SQL, una de las mejores practicas
        {
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        }
   }
}
