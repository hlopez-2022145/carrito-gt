package com.carritogt.api.exception;

//Excepcion personalizada paramanejar errores
public class Exceptions extends RuntimeException {

    // Constructor para enviar un mensaje y la causa original del error
    public Exceptions(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor para enviar solamente un mensaje de error
    public Exceptions(String message) {
        //llama al constructor RutimeExeption
        super(message);
    }
}