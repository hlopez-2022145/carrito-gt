package com.carritogt.api.repository;

import com.carritogt.api.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    // Busca todos los productos que pertenecen al carrito de un usuario.
    List<ShoppingCart> findByCodUsuario(String codUsuario);

    // Elimina todos los productos que pertenecen al carrito de un usuario.
    void deleteByCodUsuario(String codUsuario);
}