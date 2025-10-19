package com.rlp.cosechaencope.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Detalles de una orden de venta a productor, incluyendo artículos y cantidades")
public class DetalleOvpResponse {

    private Long idDetalleOvp;
    private ArticuloResponse articulo;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

}
