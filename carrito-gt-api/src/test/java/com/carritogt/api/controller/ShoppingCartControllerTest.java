package com.carritogt.api.controller;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.domain.ShoppingCart;
import com.carritogt.api.dto.AgregarCarritoRequestDto;
import com.carritogt.api.dto.ApiResponse;
import com.carritogt.api.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShoppingCartControllerTest {

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private HttpServletRequest request;

    private Producto producto;
    private ShoppingCart shoppingCart;
    private AgregarCarritoRequestDto carritoDto;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("Coca Cola");
        producto.setPrecio(10.00);

        carritoDto = new AgregarCarritoRequestDto();
        carritoDto.setIdProducto(1);
        carritoDto.setCantidad(2);

        shoppingCart = new ShoppingCart();
        shoppingCart.setIdShoppingCart(1);
        shoppingCart.setProducto(producto);
        shoppingCart.setCodUsuario("USR001");
        shoppingCart.setCantidad(2);

        when(request.getRequestURI())
                .thenReturn("/api/shopping-cart");
    }

    @Test
    void getShoppingCart_Success() {

        when(shoppingCartService.getShoppingCart("USR001"))
                .thenReturn(Arrays.asList(shoppingCart));

        ResponseEntity<ApiResponse> responseEntity =
                shoppingCartController.getShoppingCart(
                        request,
                        "USR001"
                );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Carrito obtenido correctamente", responseEntity.getBody().getMessage());
        List<ShoppingCart> carrito = (List<ShoppingCart>) responseEntity.getBody().getData();
        assertEquals(1, carrito.size());
        verify(shoppingCartService).getShoppingCart("USR001");
    }

    @Test
    void addProduct_Success() {
        when(shoppingCartService.saveShoppingCart(any(AgregarCarritoRequestDto.class), eq("USR001"))).thenReturn(shoppingCart);

        ResponseEntity<ApiResponse> responseEntity =
                shoppingCartController.addProduct(
                        request,
                        "USR001",
                        carritoDto
                );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Producto agregado al carrito", responseEntity.getBody().getMessage());
        verify(shoppingCartService).saveShoppingCart(carritoDto, "USR001");
    }

    @Test
    void deleteProduct_Success() {
        doNothing().when(shoppingCartService).deleteShoppingCart(1);

        ResponseEntity<ApiResponse> responseEntity =
                shoppingCartController.deleteProduct(
                        request,
                        1
                );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Producto eliminado del carrito", responseEntity.getBody().getMessage());
        verify(shoppingCartService).deleteShoppingCart(1);
    }

    @Test
    void clearShoppingCart_Success() {

        doNothing().when(shoppingCartService).deleteAllShoppingCart("USR001");

        ResponseEntity<ApiResponse> responseEntity =
                shoppingCartController.clearShoppingCart(
                        request,
                        "USR001"
                );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Carrito vaciado correctamente", responseEntity.getBody().getMessage());
        verify(shoppingCartService).deleteAllShoppingCart("USR001");
    }
}