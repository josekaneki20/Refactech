package org.dgtic.maquetado.controller;

import org.dgtic.maquetado.model.FotosProducto;
import org.dgtic.maquetado.model.Producto;
import org.dgtic.maquetado.service.FotosProductoService;
import org.dgtic.maquetado.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class FotosProductoController {

    @Autowired
    private FotosProductoService fotosProductoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/{idProducto}")
    public List<FotosProducto> obtenerFotosPorProducto(@PathVariable Long idProducto){
        return  fotosProductoService.obtenerFotosPorProducto(idProducto);
    }






}
