package com.carritogt.api.service;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.domain.ShoppingCart;
import com.carritogt.api.dto.AgregarCarritoRequestDto;
import com.carritogt.api.exception.Exceptions;
import com.carritogt.api.repository.ProductoRepository;
import com.carritogt.api.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository repository;

    @Mock
    private ProductoRepository productoRepository;

    private Producto producto;
    private ShoppingCart cart;
    private AgregarCarritoRequestDto addCart;

    @BeforeEach
    public void setUp(){
        producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("coca cola");
        producto.setPrecio(10.00);

        addCart = new AgregarCarritoRequestDto();
        addCart.setIdProducto(1);
        addCart.setCantidad(2);

        cart = new ShoppingCart();
        cart.setIdShoppingCart(1);
        cart.setProducto(producto);
        cart.setCodUsuario("USR001");
        cart.setCantidad(2);
    }

    @Test
    void setShoppingCart_Success(){
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(repository.save(any(ShoppingCart.class))).thenReturn(cart);

        ShoppingCart resultado = shoppingCartService.saveShoppingCart(addCart, "USR001");

        assertNotNull(resultado);
        assertEquals("USR001", resultado.getCodUsuario());
        assertEquals(2, resultado.getCantidad());
        verify(productoRepository.findById(1) );
        verify(repository).save(any(ShoppingCart.class));
    }

    @Test
    void saveShoppingCart_ProductoNoEncontrado() {
        when(productoRepository.findById(1)).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class, () ->
                shoppingCartService.saveShoppingCart(addCart, "USR001")
        );

        assertTrue(exception.getMessage().contains("Producto no encontrado con id: 1"));
        verify(repository, never()).save(any());
    }

    @Test
    void getShoppingCart_Exitoso() {
        when(repository.findByCodUsuario("USR001")).thenReturn(List.of(cart));

        List<ShoppingCart> resultado = shoppingCartService.getShoppingCart("USR001");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByCodUsuario("USR001");
    }

    @Test
    void getShoppingCart_Vacio() {
        when(repository.findByCodUsuario("USR001")).thenReturn(Collections.emptyList());

        Exceptions exception = assertThrows(Exceptions.class, () ->
                shoppingCartService.getShoppingCart("USR001")
        );

        assertEquals("No se encontraron productos en el carrito", exception.getMessage());
    }

    @Test
    void getShoppingCartId_Exito() {
        when(repository.findById(10)).thenReturn(Optional.of(cart));

        ShoppingCart resultado = shoppingCartService.getShoppingCartId(10);

        assertNotNull(resultado);
        assertEquals(10, resultado.getIdShoppingCart());
        verify(repository, times(1)).findById(10);
    }

    @Test
    void getShoppingCartId_NoEncontrado() {
        when(repository.findById(10)).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class, () ->
                shoppingCartService.getShoppingCartId(10)
        );

        assertTrue(exception.getMessage().contains("Producto del carrito no encontrado con id: 10"));
    }

    @Test
    void deleteShoppingCart_Exito() {
        when(repository.findById(10)).thenReturn(Optional.of(cart));

        shoppingCartService.deleteShoppingCart(10);

        verify(repository, times(1)).findById(10);
        verify(repository, times(1)).delete(cart);
    }

    @Test
    void deleteShoppingCart_NoEncontrado() {
        when(repository.findById(10)).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(Exceptions.class, () ->
                shoppingCartService.deleteShoppingCart(10)
        );

        assertTrue(exception.getMessage().contains("Producto del carrito no encontrado con id: 10"));
        verify(repository, never()).delete(any());
    }

    @Test
    void deleteAllShoppingCart_Exitoso() {
        List<ShoppingCart> lista = List.of(cart);
        when(repository.findByCodUsuario("USR001")).thenReturn(lista);

        shoppingCartService.deleteAllShoppingCart("USR001");

        verify(repository, times(1)).findByCodUsuario("USR001");
        verify(repository, times(1)).deleteAll(lista);
    }

    @Test
    void deleteAllShoppingCart_Vacio() {
        when(repository.findByCodUsuario("USR001")).thenReturn(Collections.emptyList());

        Exceptions exception = assertThrows(Exceptions.class, () ->
                shoppingCartService.deleteAllShoppingCart("USR001")
        );

        assertEquals("No se encontraron productos en el carrito", exception.getMessage());
        verify(repository, never()).deleteAll(any());
    }
}
