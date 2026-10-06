package com.carritogt.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_cart")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idShoppingCart")
    private Integer idShoppingCart;

    /*
     * Relación con la tabla producto.
     * idProducto es la llave foránea en shopping_cart.
     */
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @Column(name = "codUsuario", length = 128)
    private String codUsuario;

    @Column(name = "cantidad")
    private Integer cantidad;
}