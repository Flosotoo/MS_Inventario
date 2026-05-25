package com.inventario.msinventario.model;

import lombok.Data;

@Data
public class SucursalDTO {
    private Long idSucursal;
    private String nombre;
    private String direccion;
    private String ciudad;

}
