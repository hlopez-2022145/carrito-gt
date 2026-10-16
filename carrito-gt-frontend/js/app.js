// Este archivo conecta los botones y elementos HTML con las funciones de api.js.
import { SERVIDOR, obtenerProductos, obtenerCarrito, agregarProducto, eliminarProducto, vaciarCarrito } from "./api.js";

let carrito = []; // Copia temporal para dibujar la pantalla; los datos reales están en MySQL.
const elemento = id => document.getElementById(id);
const dinero = valor => Number(valor || 0).toFixed(2);

// Muestra mensajes sin interrumpir la navegacion
function mensaje(texto) {
    const caja = elemento("mensaje-alerta");
    caja.textContent = texto;
    caja.classList.remove("hidden");
    setTimeout(() => caja.classList.add("hidden"), 4500);
}

// Al abrir la página, pedimos el catálogo y el carrito a Spring Boot.
document.addEventListener("DOMContentLoaded", async () => {
    elemento("btn-ver-carrito").addEventListener("click", async () => {
        elemento("modal-carrito").classList.remove("hidden");
        await cargarCarrito();
    });
    elemento("btn-cerrar-modal").addEventListener("click", () => elemento("modal-carrito").classList.add("hidden"));
    elemento("btn-vaciar-carrito").addEventListener("click", async () => {
        if (!carrito.length) return;
        try {
            await vaciarCarrito();
            await cargarCarrito();
            mensaje("Carrito vaciado correctamente.");
        } catch (error) { mensaje(error.message); }
    });
    await cargarProductos();
    await cargarCarrito();
});

// Consulta el catálogo y crea una tarjeta HTML para cada producto.
async function cargarProductos() {
    const contenedor = elemento("contenedor-productos");
    try {
        const productos = await obtenerProductos();
        contenedor.innerHTML = "";
        if (!productos.length) {
            contenedor.textContent = "No hay productos disponibles.";
            return;
        }
        for (const producto of productos) {
            const tarjeta = document.createElement("div");
            tarjeta.className = "card";
            const foto = document.createElement("img");
            foto.src = new URL(producto.imagen, `${SERVIDOR}/`).href;
            foto.alt = producto.nombre;
            const titulo = document.createElement("h3");
            titulo.textContent = producto.nombre;
            const precio = document.createElement("p");
            precio.className = "precio";
            precio.textContent = `Q.${dinero(producto.precio)}`;
            const boton = document.createElement("button");
            boton.className = "btn-agregar";
            boton.textContent = "Agregar al Carrito";
            boton.addEventListener("click", async () => {
                boton.disabled = true;
                try {
                    await agregarProducto(producto.idProducto, 1);
                    await cargarCarrito();
                    mensaje("Producto agregado correctamente.");
                } catch (error) { mensaje(error.message); }
                finally { boton.disabled = false; }
            });
            tarjeta.append(foto, titulo, precio, boton);
            contenedor.appendChild(tarjeta);
        }
    } catch (error) {
        contenedor.textContent = "No se pudo cargar el catálogo.";
        mensaje(`No se pudo conectar con Spring Boot: ${error.message}`);
    }
}

// Actualiza la información del carrito desde la base de datos.
async function cargarCarrito() {
    try {
        carrito = await obtenerCarrito();
        mostrarCarrito();
    } catch (error) { mensaje(`Error consultando carrito: ${error.message}`); }
}

// Calcula la cantidad y el total, y dibuja los productos del carrito.
function mostrarCarrito() {
    const cantidad = carrito.reduce((suma, item) => suma + item.cantidad, 0);
    const total = carrito.reduce((suma, item) => suma + item.producto.precio * item.cantidad, 0);
    elemento("cart-count").textContent = cantidad;
    elemento("cart-total").textContent = dinero(total);
    elemento("modal-cart-total").textContent = dinero(total);
    const contenedor = elemento("items-carrito-modal");
    contenedor.innerHTML = "";
    if (!carrito.length) {
        contenedor.textContent = "El carrito está vacío.";
        return;
    }
    for (const item of carrito) {
        const fila = document.createElement("div");
        fila.className = "cart-item";
        const descripcion = document.createElement("span");
        descripcion.textContent = `${item.producto.nombre} — Q.${dinero(item.producto.precio)} x ${item.cantidad} = Q.${dinero(item.producto.precio * item.cantidad)}`;
        const boton = document.createElement("button");
        boton.className = "btn-danger";
        boton.textContent = "Eliminar";
        boton.addEventListener("click", async () => {
            boton.disabled = true;
            try {
                await eliminarProducto(item.idShoppingCart);
                await cargarCarrito();
            } catch (error) {
                mensaje(error.message);
                boton.disabled = false;
            }
        });
        fila.append(descripcion, boton);
        contenedor.appendChild(fila);
    }
}
