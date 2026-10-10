package com.carritogt.api.controller;

import com.carritogt.api.domain.ShoppingCart;
import com.carritogt.api.dto.AgregarCarritoRequestDto;
import com.carritogt.api.dto.ApiResponse;
import com.carritogt.api.exception.Exceptions;
import com.carritogt.api.service.ShoppingCartService;
import com.carritogt.api.util.Utilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private static final String ENTIDAD = "ShoppingCart";

    // AGREGAR PRODUCTO AL CARRITO
    @PostMapping("/add/{codUsuario}")
    public ResponseEntity<ApiResponse> addProduct(
            HttpServletRequest servletRequest,
            @PathVariable("codUsuario") String codUsuario,
            @Valid @RequestBody AgregarCarritoRequestDto carritoDto) {

        try {
            ShoppingCart carrito =
                    shoppingCartService.saveShoppingCart(carritoDto, codUsuario);

            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.CREATED,
                    "Producto agregado al carrito"
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            "Producto agregado al carrito",
                            HttpStatus.CREATED.value(),
                            servletRequest.getRequestURI(),
                            carrito
                    ));

        } catch (Exceptions e) {
            Utilities.errorLog(
                    servletRequest, HttpStatus.NOT_FOUND, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            HttpStatus.NOT_FOUND.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));

        } catch (Exception e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.INTERNAL_SERVER_ERROR, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            "Error agregando producto al carrito",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));
        }
    }

    // LISTAR CARRITO POR USUARIO
    @GetMapping("/list/{codUsuario}")
    public ResponseEntity<ApiResponse> getShoppingCart(
            HttpServletRequest servletRequest,
            @PathVariable("codUsuario") String codUsuario) {

        try {
            List<ShoppingCart> productos =
                    shoppingCartService.getShoppingCart(codUsuario);

            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.OK,
                    "Carrito obtenido correctamente"
            );

            return ResponseEntity.ok(new ApiResponse(
                    "Carrito obtenido correctamente",
                    HttpStatus.OK.value(),
                    servletRequest.getRequestURI(),
                    productos
            ));

        } catch (Exceptions e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.NOT_FOUND, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            HttpStatus.NOT_FOUND.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));

        } catch (Exception e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.INTERNAL_SERVER_ERROR, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            "Error obteniendo carrito",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));
        }
    }

    // BUSCAR REGISTRO DEL CARRITO POR ID
    @GetMapping("/listId/{idShoppingCart}")
    public ResponseEntity<ApiResponse> getShoppingCartId(
            HttpServletRequest servletRequest,
            @PathVariable("idShoppingCart") Integer idShoppingCart) {

        try {
            ShoppingCart carrito =
                    shoppingCartService.getShoppingCartId(idShoppingCart);

            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.OK,
                    "Producto del carrito encontrado"
            );

            return ResponseEntity.ok(new ApiResponse(
                    "Producto del carrito obtenido correctamente",
                    HttpStatus.OK.value(),
                    servletRequest.getRequestURI(),
                    carrito
            ));

        } catch (Exceptions e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.NOT_FOUND, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            HttpStatus.NOT_FOUND.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));

        } catch (Exception e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.INTERNAL_SERVER_ERROR, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            "Error buscando producto del carrito",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));
        }
    }

    // ELIMINAR PRODUCTO DEL CARRITO
    @DeleteMapping("/delete/{idShoppingCart}")
    public ResponseEntity<ApiResponse> deleteProduct(
            HttpServletRequest servletRequest,
            @PathVariable("idShoppingCart") Integer idShoppingCart) {

        try {
            shoppingCartService.deleteShoppingCart(idShoppingCart);

            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.OK,
                    "Producto eliminado del carrito"
            );

            return ResponseEntity.ok(new ApiResponse(
                    "Producto eliminado del carrito",
                    HttpStatus.OK.value(),
                    servletRequest.getRequestURI(),
                    null
            ));

        } catch (Exceptions e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.NOT_FOUND, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            HttpStatus.NOT_FOUND.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));

        } catch (Exception e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.INTERNAL_SERVER_ERROR, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            "Error eliminando producto del carrito",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));
        }
    }

    // VACIAR CARRITO DEL USUARIO
    @DeleteMapping("/clear/{codUsuario}")
    public ResponseEntity<ApiResponse> clearShoppingCart(
            HttpServletRequest servletRequest,
            @PathVariable("codUsuario") String codUsuario) {

        try {
            shoppingCartService.deleteAllShoppingCart(codUsuario);

            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.OK,
                    "Carrito vaciado correctamente"
            );

            return ResponseEntity.ok(new ApiResponse(
                    "Carrito vaciado correctamente",
                    HttpStatus.OK.value(),
                    servletRequest.getRequestURI(),
                    null
            ));

        } catch (Exceptions e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.NOT_FOUND, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(
                            e.getMessage(),
                            HttpStatus.NOT_FOUND.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));

        } catch (Exception e) {

            Utilities.errorLog(
                    servletRequest, HttpStatus.INTERNAL_SERVER_ERROR, ENTIDAD, e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            "Error vaciando carrito",
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            servletRequest.getRequestURI(),
                            null
                    ));
        }
    }
}