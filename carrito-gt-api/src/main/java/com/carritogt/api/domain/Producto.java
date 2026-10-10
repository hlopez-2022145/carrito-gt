package com.carritogt.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Integer idProducto;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "codigo", nullable = false, length = 255)
    private String codigo;

    @Column(name = "imagen", columnDefinition = "TEXT")
    private String imagen;

    @Column(name = "precio")
    private Double precio;
}