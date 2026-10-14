package com.carritogt.api.controller;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.dto.ApiResponse;
import com.carritogt.api.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private ProductoService productoService;

    //Simula la peticion HTTP
    @Mock
    private HttpServletRequest request;

    private Producto producto;

    // Se ejecuta antes de cada prueba
    @BeforeEach
    void setUp() {

        // Inicializa las anotaciones de Mockito
        MockitoAnnotations.openMocks(this);

        // Preparamos un objeto Producto con datos de prueba
        producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("Coca Cola");
        producto.setCodigo("P001");
        producto.setImagen("coca.jpg");
        producto.setPrecio(10.00);

        //simulamos que al consultar  la petiión HTTP, retornará la ruta "/api/productos"
        when(request.getRequestURI())
                .thenReturn("/api/productos");
    }

    @Test
    void getProductos_Success() {
        when(productoService.getProductos()).thenReturn(Arrays.asList(producto));

        ResponseEntity<ApiResponse> responseEntity = productoController.getProductos(request);

        assertEquals(
                HttpStatus.OK,
                responseEntity.getStatusCode()
        );

        assertNotNull(responseEntity.getBody());

        assertEquals(
                "Productos obtenidos correctamente",
                responseEntity.getBody().getMessage()
        );

        List<Producto> productos = (List<Producto>) responseEntity.getBody().getData();

        assertEquals(1, productos.size());
        assertEquals("Coca Cola", productos.get(0).getNombre());

        verify(productoService).getProductos();
    }

    @Test
    void getProductoId_Success() {

        when(productoService.getProductoId(1)).thenReturn(producto);

        ResponseEntity<ApiResponse> responseEntity =
                productoController.getProductoId(
                        request,
                        1
                );

        assertEquals(
                HttpStatus.OK,
                responseEntity.getStatusCode()
        );

        assertNotNull(responseEntity.getBody());

        assertEquals(
                "Producto obtenido correctamente",
                responseEntity.getBody().getMessage()
        );

        Producto resultado = (Producto) responseEntity.getBody().getData();

        assertEquals(1, resultado.getIdProducto());
        assertEquals("Coca Cola", resultado.getNombre());

        verify(productoService).getProductoId(1);
    }
}