package com.inventario.msinventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.msinventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    Optional<Inventario> findByIdProductoAndIdSucursal(Long idProducto, Long idSucursal);
}
