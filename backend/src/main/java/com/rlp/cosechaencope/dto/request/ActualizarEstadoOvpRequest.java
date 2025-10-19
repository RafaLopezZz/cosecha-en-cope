package com.rlp.cosechaencope.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de request para actualizar estado de OVP.
 */
@Data
public class ActualizarEstadoOvpRequest {

    @NotNull
    private String estado;

    private String observaciones;
}
