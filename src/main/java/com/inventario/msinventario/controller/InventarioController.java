package com.inventario.msinventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.msinventario.model.Inventario;
import com.inventario.msinventario.model.StockDTO;
import com.inventario.msinventario.service.InventarioService;

@RestController
@RequestMapping("api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    //Deja ver todo en el Inventario
    @GetMapping
    public ResponseEntity<List<Inventario>> getInventario() {
        List<Inventario> lista = inventarioService.readAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(lista);
    }

    //Agrega un nuevo producto al inventario
    @PostMapping
    public ResponseEntity<Inventario> postInventario(@RequestBody Inventario inventario) {
        Inventario nuevo;
        try {
            nuevo = (Inventario) inventarioService.agregarInventario(inventario);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{idProducto}/{idSucursal}")
    public ResponseEntity<Inventario> getInventario(
            @PathVariable Long idProducto,
            @PathVariable Long idSucursal) {
        Inventario buscado = inventarioService.getInventario(idProducto, idSucursal);
        if (buscado == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(buscado, HttpStatus.OK);
    }

    @GetMapping("/stock/sucursal/{idSucursal}")
    public ResponseEntity<List<StockDTO>> getStockPorSucursal(
            @PathVariable Long idSucursal) {
        List<StockDTO> resultado = inventarioService.verificarStockConCatalogo(idSucursal);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    //Actualizar cantidades de stock
    @PutMapping("/{idProducto}/{idSucursal}")
    public ResponseEntity<Inventario> putInventario(
            @PathVariable Long idProducto,
            @PathVariable Long idSucursal,
            @RequestBody Inventario inventario) {
        try {
            Inventario actualizado = inventarioService.actualizarStock(idProducto, idSucursal, inventario);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar prodcutos del inventario
    @DeleteMapping("/{idProducto}/{idSucursal}")
    public ResponseEntity<HttpStatus> deleteInventario(
            @PathVariable Long idProducto,
            @PathVariable Long idSucursal) {
        try {
            inventarioService.eliminarInventario(idProducto, idSucursal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
