package org.example.service;

import org.example.models.Producto;
import org.example.repository.ProductoRepository;

import java.util.List;

public class ProductoService {

    private final ProductoRepository repository;

    // Inyección por constructor
    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarTodo() throws Exception {
        // Aquí podrías agregar lógica: ej. filtrar productos sin stock
        return repository.findAll();
    }
}
