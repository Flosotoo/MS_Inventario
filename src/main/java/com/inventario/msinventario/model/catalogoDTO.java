package com.inventario.msinventario.model;

import lombok.Data;

@Data
public class catalogoDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
}
