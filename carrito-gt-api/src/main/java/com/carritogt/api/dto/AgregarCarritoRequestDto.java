package com.carritogt.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgregarCarritoRequestDto {

    @NotNull(message = "El id del producto no puede ser nulo.")
    @Min(value=1, message = "El id del producto debe ser mayor o igual a 1")
    private Integer idProducto;

    @NotNull(message = "La cantidad no puede ser nula.")
    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1.")
    private Integer cantidad;
}