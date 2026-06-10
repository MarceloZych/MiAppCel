package org.example.models;

public class Producto {
    private int id;
    private String marca;
    private String modelo;
    private String color;
    private int almacenamientoGb;
    private int ramGb;
    private Double precio;
    private int stock;
    private String imagenUrl;
    private boolean es5g;
    private boolean activo;

    public Producto() {
    }

    public Producto(int id, String marca, String modelo, String color,
                    int almacenamientoGb, int ramGb, Double precio,
                    int stock, String imagenUrl, boolean es5g, boolean activo)
    {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.almacenamientoGb = almacenamientoGb;
        this.ramGb = ramGb;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.es5g = es5g;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAlmacenamientoGb() {
        return almacenamientoGb;
    }

    public void setAlmacenamientoGb(int almacenamientoGb) {
        this.almacenamientoGb = almacenamientoGb;
    }

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public boolean isEs5g() {
        return es5g;
    }

    public void setEs5g(boolean es5g) {
        this.es5g = es5g;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
