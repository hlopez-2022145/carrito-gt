package com.carritogt.api.service;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.exception.Exceptions;
import com.carritogt.api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    //Repositorio para consultar los productos en la base de datos
    private final ProductoRepository productoRepository;

    // Obtiene todos los productos registrados
    public List<Producto> getProductos() {
        List<Producto> productos = productoRepository.findAll();
        if (productos.isEmpty()) {
            throw new Exceptions("No se encontraron productos");
        }
        return productos;
    }

    // Busca un producto por su id
    public Producto getProductoId(Integer id) {
        // Si el producto no existe genera una excepcion
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new Exceptions(
                                "Producto no encontrado con id: " + id
                        )
                );
    }
}