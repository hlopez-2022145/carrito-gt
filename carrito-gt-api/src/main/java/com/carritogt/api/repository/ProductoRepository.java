package com.carritogt.api.repository;

import com.carritogt.api.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para realizar operaciones en la tabla de productos
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // JpaRepository ya incluye metodos :
    // findAll()    obtener todos los productos
    // findById()   buscar un producto por su ID
    // save()       guardar o actualizar un producto
    // delete()     eliminar un producto
}