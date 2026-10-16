// Aquí configuramos la dirección del servidor Spring Boot y el usuario de prueba.
export const SERVIDOR = "http://carrito-gt-api";
const API = `${SERVIDOR}/api`;
const USUARIO = "USR123";

// Esta función se encarga de todas las peticiones HTTP.
async function peticion(ruta, opciones = {}) {
    const respuesta = await fetch(`${API}${ruta}`, opciones);
    const resultado = await respuesta.json();
    if (!respuesta.ok) {
        throw new Error(resultado.message || `Error HTTP ${respuesta.status}`);
    }
    return resultado.data;
}

// GET: devuelve la lista de productos de MySQL.
export function obtenerProductos() {
    return peticion("/productos/list");
}

// GET: devuelve los productos guardados por el usuario.
export async function obtenerCarrito() {
    try {
        return await peticion(`/shopping-cart/list/${USUARIO}`);
    } catch (error) {
        // El backend usa 404 cuando el carrito está vacío.
        if (error.message === "No se encontraron productos en el carrito") return [];
        throw error;
    }
}

// POST: manda al backend el ID del producto y la cantidad.
export function agregarProducto(idProducto, cantidad = 1) {
    return peticion(`/shopping-cart/add/${USUARIO}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idProducto, cantidad })
    });
}

// DELETE: elimina una fila del carrito usando su ID.
export function eliminarProducto(idShoppingCart) {
    return peticion(`/shopping-cart/delete/${idShoppingCart}`, { method: "DELETE" });
}

// DELETE: borra todos los productos del usuario.
export function vaciarCarrito() {
    return peticion(`/shopping-cart/clear/${USUARIO}`, { method: "DELETE" });
}
