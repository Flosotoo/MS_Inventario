package com.inventario.msinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventario.msinventario.model.CatalogoDTO;
import com.inventario.msinventario.model.Inventario;
import com.inventario.msinventario.repository.InventarioRepository;


@Service
public class InventarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> readAll() {
        return inventarioRepository.findAll();
    }

    public Inventario agregarInventario(Inventario inventario) {
        try {
            String url = "http://localhost:8082/api/v1/catalogo/" + inventario.getIdProducto();
            CatalogoDTO producto = restTemplate.getForObject(url, CatalogoDTO.class);
            if(producto != null) {
                System.out.println("Producto encontrado: " + producto);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }
        return inventarioRepository.save(inventario);
    }

    public Inventario getInventario(Long idProducto, Long idSucursal) {
        return inventarioRepository.findByIdProductoAndIdSucursal(idProducto, idSucursal).orElse(null);
    }

    public Inventario actualizarStock(Long idProducto, Long idSucursal, Inventario inventario) {
        Inventario existente = inventarioRepository
                .findByIdProductoAndIdSucursal(idProducto, idSucursal)
                .orElse(null);

        if (existente != null) {
            existente.setCantidad(inventario.getCantidad());
            return inventarioRepository.save(existente);
        }
        return null;
    }

    public void eliminarInventario(Long idProducto, Long idSucursal) {
        Inventario existente = inventarioRepository
                .findByIdProductoAndIdSucursal(idProducto, idSucursal)
                .orElse(null);

        if (existente != null) {
            inventarioRepository.delete(existente);
        }

    }

}
