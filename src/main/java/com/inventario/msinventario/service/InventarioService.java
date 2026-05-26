package com.inventario.msinventario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inventario.msinventario.model.CatalogoDTO;
import com.inventario.msinventario.model.Inventario;
import com.inventario.msinventario.model.StockDTO;
import com.inventario.msinventario.model.SucursalDTO;
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
            if (producto != null) {
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

    public List<StockDTO> verificarStockConCatalogo(Long idSucursal) {
        List<Inventario> inventarios = inventarioRepository.findAll();
        List<StockDTO> resultado = new ArrayList<>();

        for (Inventario inv : inventarios) {
            if (!inv.getIdSucursal().equals(idSucursal)) {
                continue;
            }
            StockDTO item = new StockDTO();
            item.setIdProducto(inv.getIdProducto());
            item.setIdSucursal(inv.getIdSucursal());
            item.setCantidad(inv.getCantidad());
            // Consulta MS Catalogo
            try {
                String urlCatalogo = "http://localhost:8082/api/v1/catalogo/" + inv.getIdProducto();
                CatalogoDTO producto = restTemplate.getForObject(urlCatalogo, CatalogoDTO.class);
                if (producto != null) {
                    item.setNombre(producto.getNombre());
                    item.setMarca(producto.getMarca());
                    item.setPrecio(producto.getPrecio());
                }
            } catch (Exception e) {
                System.out.println("MS Catalogo no disponible: " + e.getMessage());
                item.setNombre("No disponible");
            }

            // Consulta MS Sucursales
            try {
                String urlSucursal = "http://localhost:8087/api/v1/sucursales/" + inv.getIdSucursal();
                SucursalDTO sucursal = restTemplate.getForObject(urlSucursal, SucursalDTO.class);
                if (sucursal != null) {
                    item.setNombreSucursal(sucursal.getNombre());
                    item.setCiudad(sucursal.getCiudad());
                }
            } catch (Exception e) {
                System.out.println("MS Sucursales no disponible: " + e.getMessage());
                item.setNombreSucursal("No disponible");
            }

            resultado.add(item);
        }
        return resultado;
    }

}
