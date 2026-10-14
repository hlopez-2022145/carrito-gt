package com.carritogt.api.service;

import com.carritogt.api.domain.Producto;
import com.carritogt.api.exception.Exceptions;
import com.carritogt.api.repository.ProductoRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    // @InjectMocks: crea la instancia de ProductoService
    @InjectMocks
    private ProductoService productoService;

    // @Mock: crea un objeto simulado(falso)
    @Mock
    private ProductoRepository productoRepository;

    //objeto falso que se utiliza como plantilla de datos
    private Producto producto;

    @BeforeEach
    void setUp() {
        //inicializa las anotaciones de Mockito
        MockitoAnnotations.openMocks(this);

        producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("Coca Cola");
        producto.setCodigo("P001");
        producto.setImagen("coca.jpg");
        producto.setPrecio(10.00);
    }

    @Test
    void getProductos_Success() {

        //simula que al llamar a findAll() el repositorio retornará una lista con nuestro producto
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        List<Producto> resultado = productoService.getProductos();

        //se valida que la respuesta cumpla con lo esperado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Coca Cola", resultado.get(0).getNombre());

        verify(productoRepository).findAll();
    }

    @Test
    void getProductos_Empty() {

        //simula que el repositorio no devuelve ningún registro
        when(productoRepository.findAll())
                .thenReturn(Collections.emptyList());

        //capturamos y verificamos que el servicio lance nuestra excepción
        Exceptions exception = assertThrows(
                Exceptions.class,
                () -> productoService.getProductos()
        );

        assertEquals(
                "No se encontraron productos",
                exception.getMessage()
        );

        verify(productoRepository).findAll();
    }

    @Test
    void getProductoId_Success() {

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.getProductoId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdProducto());
        assertEquals("Coca Cola", resultado.getNombre());

        verify(productoRepository).findById(1);
    }

    @Test
    void getProductoId_NotFound() {

        when(productoRepository.findById(1)).thenReturn(Optional.empty());

        Exceptions exception = assertThrows(
                Exceptions.class,
                () -> productoService.getProductoId(1)
        );

        assertEquals(
                "Producto no encontrado con id: 1",
                exception.getMessage()
        );

        verify(productoRepository).findById(1);
    }
}