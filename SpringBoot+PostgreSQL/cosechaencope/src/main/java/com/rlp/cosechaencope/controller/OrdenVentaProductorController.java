package com.rlp.cosechaencope.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlp.cosechaencope.model.OrdenVentaProductor;
import com.rlp.cosechaencope.repository.OrdenVentaProductorRepository;

@RestController
@RequestMapping("/cosechaencope/productores/{idProductor}/ordenes")
@CrossOrigin(origins = "*")
public class OrdenVentaProductorController {

    @Autowired
    private OrdenVentaProductorRepository ordenVentaProductorRepository;

    @GetMapping
    public List<OrdenVentaProductor> listarPorProductor(@PathVariable Long idProductor) {
        return ordenVentaProductorRepository.findByProductor_IdProductorOrderByFechaCreacionDesc(idProductor);
    }
}
