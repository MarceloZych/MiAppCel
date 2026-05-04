package org.example.repository;

public class ProductoRepository {
    private final String url;
    private final String user;
    private final String pass;

    public ProductoRepository(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }


}
