package com.carritogt.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class Utilities {

    //Logger para registrar informacion, advertencias y errores
    private static final Logger logger =
            LoggerFactory.getLogger(Utilities.class);

    // Constructor privado para evitar crear objetos de la clase
    private Utilities() {
    }

    // Metodo para registrar informacion de operaciones correctas
    public static void infoLog(HttpServletRequest request, HttpStatus status, String message) {
        // Obtiene la IP del usuario que realiza la peticion
        String clientIP = request.getRemoteAddr();
        // Registra la informacion en el log
        logger.info("IP: {}, Status: {}, Message: {}", clientIP, status.value(), message);
    }

    //Metodo para registrar advertencias
    public static void warnLog(HttpServletRequest request, HttpStatus status, String message) {
        String clientIP = request.getRemoteAddr();
        // Registra la advertencia en el log
        logger.warn("IP: {}, Status: {}, Message: {}", clientIP, status.value(), message);
    }

    // Metodo para registrar errores
    public static void errorLog(HttpServletRequest request, HttpStatus status, String message, Exception e) {
        String clientIP = request.getRemoteAddr();
        // Registra elerror y el mensaje de la excepcion
        logger.error("IP: {}, Status: {}, Message: {}, Exception: {}", clientIP, status.value(), message, e.getMessage());
    }
}