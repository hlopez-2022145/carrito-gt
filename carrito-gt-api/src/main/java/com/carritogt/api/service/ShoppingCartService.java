package com.carritogt.api.service;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.domain.ShoppingCart;
import com.carritogt.api.dto.AgregarCarritoRequestDto;
import com.carritogt.api.exception.Exceptions;
import com.carritogt.api.repository.ProductoRepository;
import com.carritogt.api.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductoRepository productoRepository;

    private static final String ENTIDAD = "ShoppingCart";

    // AGREGAR PRODUCTO AL CARRITO
    public ShoppingCart saveShoppingCart(AgregarCarritoRequestDto input, String codUsuario) {

        Producto existingProducto = productoRepository.findById(input.getIdProducto())
                .orElseThrow(() -> new Exceptions(
                        "Producto no encontrado con id: " + input.getIdProducto()
                ));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setProducto(existingProducto);
        shoppingCart.setCodUsuario(codUsuario);
        shoppingCart.setCantidad(input.getCantidad());

        return shoppingCartRepository.save(shoppingCart);
    }

    // OBTENER CARRITO POR USUARIO
    public List<ShoppingCart> getShoppingCart(String codUsuario) {
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findByCodUsuario(codUsuario);

        if (shoppingCart.isEmpty()) {
            throw new Exceptions("No se encontraron productos en el carrito");
        }

        return shoppingCart;
    }

    // OBTENER PRODUCTO DEL CARRITO POR ID
    public ShoppingCart getShoppingCartId(Integer id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new Exceptions(
                        "Producto del carrito no encontrado con id: " + id
                ));
    }

    // ELIMINAR PRODUCTO DEL CARRITO
    public void deleteShoppingCart(Integer id) {
        ShoppingCart existingShoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new Exceptions(
                        "Producto del carrito no encontrado con id: " + id
                ));

        shoppingCartRepository.delete(existingShoppingCart);
    }

    // VACIAR CARRITO DEL USUARIO
    public void deleteAllShoppingCart(String codUsuario) {
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findByCodUsuario(codUsuario);

        if (shoppingCart.isEmpty()) {
            throw new Exceptions("No se encontraron productos en el carrito");
        }

        shoppingCartRepository.deleteAll(shoppingCart);
    }
}