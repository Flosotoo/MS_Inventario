package com.inventario.msinventario.model;

import lombok.Data;

@Data
public class StockDTO {

    private Long id;
    private Long idProducto;
    private Long idSucursal;
    private int cantidad;

    private String nombre;
    private String marca;
    private Double precio;

    private String nombreSucursal;
    private String ciudad;

}
