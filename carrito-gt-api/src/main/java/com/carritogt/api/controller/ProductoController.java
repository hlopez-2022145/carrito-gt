package com.carritogt.api.controller;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.dto.ApiResponse;
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

    // Servicio que contiene la logica de productos
    private final ProductoService productoService;

    // Obtiene todos los productos registrados
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getProductos(HttpServletRequest servletRequest) {
        try {
            // Obtiene la lista de productos desde el Service
            List<Producto> productos = productoService.getProductos();
            //Registra en el log que la peticion fue correcta
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

        } catch (Exception e) {
            // Registra en el log el error ocurrido
            Utilities.errorLog(
                    servletRequest,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    e
            );

            // Retorna una respuesta indicando que ocurrio un error
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

            // Registra en el log que la peticion fue correcta
            Utilities.infoLog(
                    servletRequest,
                    HttpStatus.OK,
                    "Producto obtenido correctamente"
            );

            // Retorna la respuesta con el producto encontrado
            return ResponseEntity.ok(
                    new ApiResponse(
                            "Producto obtenido correctamente",
                            HttpStatus.OK.value(),
                            servletRequest.getRequestURI(),
                            producto
                    )
            );

        } catch (Exception e) {
            // Registra en el log el error ocurrido
            Utilities.errorLog(
                    servletRequest,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    e
            );

            //Retorna la respuesta indicando que ocurrio un error
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
}