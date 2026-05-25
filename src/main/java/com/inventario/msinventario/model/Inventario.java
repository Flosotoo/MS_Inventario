package com.inventario.msinventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private Long idSucursal;

    @Column(nullable = false)
    private int cantidad;

    public Inventario(int cantidad, Long id, Long idProducto, Long idSucursal) {
        this.cantidad = cantidad;
        this.id = id;
        this.idProducto = idProducto;
        this.idSucursal = idSucursal;
    }
}
