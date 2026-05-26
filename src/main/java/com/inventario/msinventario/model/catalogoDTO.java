package com.inventario.msinventario.model;

import lombok.Data;

@Data
public class CatalogoDTO {
    private Long idProducto;
    private String nombre;
    private String marca;
    private String descripcion;
    private double precio;
}
