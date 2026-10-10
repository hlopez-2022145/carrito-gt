package com.carritogt.api.controller;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.dto.ApiResponse;
import com.carritogt.api.exception.Exceptions;
import com.carritogt.api.service.ProductoService;
import com.carritogt.api.util.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    // Servicio que contiene la lógica de negocio de productos
    private final ProductoService productoService;

    // Obtiene todos los productos registrados
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getProductos(HttpServletRequest servletRequest) {
        try {
            // Obtiene la lista de productos desde el Service
            List<Producto> productos = productoService.getProductos();

            // Registra en el log que la petición fue correcta
            Utilities.infoLog(servletRequest, HttpStatus.OK, "Productos obtenidos correctamente");

            // Retorna la respuesta con los productos encontrados
            return ResponseEntity.ok(
                    new ApiResponse(
                            "Productos obtenidos correctamente",
                            HttpStatus.OK.value(),
                            servletRequest.getRequestURI(),
                            productos
                    )
            );

        } catch (Exceptions e) {
            // Si no hay productos registrados en la BD, devuelvo 404 NOT FOUND
            Utilities.errorLog(servletRequest, HttpStatus.NOT_FOUND, e.getMessage(), e);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    e.getMessage(),
                                    HttpStatus.NOT_FOUND.value(),
                                    servletRequest.getRequestURI(),
                                    null
                            )
                    );

        } catch (Exception e) {
            // Error inesperado en el servidor (500)
            Utilities.errorLog(
                    servletRequest,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    e
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    servletRequest.getRequestURI(),
                                    null
                            )
                    );
        }
    }

    // Obtiene un producto utilizando su id
    @GetMapping("/{idProducto}")
    public ResponseEntity<ApiResponse> getProductoId(HttpServletRequest servletRequest, @PathVariable("idProducto") Integer idProducto) {
        try {
            // Busca el producto por su id desde el Service
            Producto producto = productoService.getProductoId(idProducto);

            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.OK,
                    "Producto obtenido correctamente"
            );

            return ResponseEntity.ok(
                    new ApiResponse(
                            "Producto obtenido correctamente",
                            HttpStatus.OK.value(),
                            servletRequest.getRequestURI(),
                            producto
                    )
            );

        } catch (Exceptions e) {
            Utilities.errorLog(servletRequest, HttpStatus.NOT_FOUND, e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    e.getMessage(),
                                    HttpStatus.NOT_FOUND.value(),
                                    servletRequest.getRequestURI(),
                                    null
                            )
                    );

        } catch (Exception e) {
            Utilities.errorLog(
                    servletRequest,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    e.getMessage(),
                                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    servletRequest.getRequestURI(),
                                    null
                            )
                    );
        }
    }
}