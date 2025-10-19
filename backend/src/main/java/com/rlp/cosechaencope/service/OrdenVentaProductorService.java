package com.rlp.cosechaencope.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlp.cosechaencope.model.Articulo;
import com.rlp.cosechaencope.model.DetalleOvp;
import com.rlp.cosechaencope.model.DetallePedido;
import com.rlp.cosechaencope.model.OrdenVentaProductor;
import com.rlp.cosechaencope.model.Pedido;
import com.rlp.cosechaencope.model.Productor;
import com.rlp.cosechaencope.repository.DetalleOvpRepository;
import com.rlp.cosechaencope.repository.OrdenVentaProductorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio para gestionar órdenes de venta a proveedores.
 * 
 * <p>Maneja la creación automática de OVPs cuando se procesan pedidos,
 * así como la consulta y actualización de estados de las órdenes.</p>
 */
@Service
@Slf4j
public class OrdenVentaProductorService {

 @Autowired
    private OrdenVentaProductorRepository ordenVentaProveedorRepository;

    @Autowired
    private DetalleOvpRepository detalleOvpRepository;


    /**
     * Genera órdenes de venta a proveedores a partir de un pedido.
     * 
     * <p>Este método agrupa los detalles del pedido por proveedor y crea una
     * orden de venta para cada proveedor con los artículos correspondientes.</p>
     * 
     * @param pedido El pedido del cual se generarán las órdenes de venta.
     */
    @Transactional
    public void generarOrdenesVentaDesdePedido(Pedido pedido) {

        Map<Productor, List<DetallePedido>> detallesPorProveedor = new HashMap<>();

        for (DetallePedido detalle : pedido.getDetallePedido()) {
            Articulo articulo = detalle.getArticulo();
            Productor proveedor = articulo.getProductor();

            detallesPorProveedor
                .computeIfAbsent(proveedor, k -> new ArrayList<>())
                .add(detalle);
        }

        for (Map.Entry<Productor, List<DetallePedido>> entry : detallesPorProveedor.entrySet()) {
            Productor proveedor = entry.getKey();
            List<DetallePedido> detalles = entry.getValue();

            OrdenVentaProductor ovp = new OrdenVentaProductor();
            ovp.setProductor(proveedor);
            ovp.setFechaCreacion(Instant.now());
            ovp = ordenVentaProveedorRepository.save(ovp);

            for (DetallePedido detalle : detalles) {
                DetalleOvp detalleOvp = new DetalleOvp();
                detalleOvp.setOrdenVenta(ovp);
                detalleOvp.setArticulo(detalle.getArticulo());
                detalleOvp.setCantidad(detalle.getCantidad());
                detalleOvp.setPrecioUnitario(detalle.getArticulo().getPrecio()); // O de proveedor
                detalleOvpRepository.save(detalleOvp);
            }
        }
    }
}
