package com.rlp.cosechaencope.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlp.cosechaencope.dto.request.ProductorRequest;
import com.rlp.cosechaencope.dto.response.MessageResponse;
import com.rlp.cosechaencope.dto.response.ProductorResponse;
import com.rlp.cosechaencope.service.ProductorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de datos de productores asociados a
 * usuarios.
 *
 * <p>
 * Proporciona endpoints para obtener, listar y actualizar la información del
 * proveedor correspondiente a un usuario determinado. Los proveedores están
 * vinculados a usuarios mediante una relación uno a uno y contienen información
 * específica del negocio como datos fiscales, información de contacto
 * comercial, etc.</p>
 *
 * <p>
 * Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir
 * peticiones desde cualquier origen.</p>
 *
 * <p>
 * Endpoints disponibles:</p>
 * <ul>
 * <li>GET /cosechaencope/usuarios/productores              → Listar todos los proveedores</li>
 * <li>GET /cosechaencope/usuarios/productores/{idUsuario}  → Obtener datos de proveedor por ID de usuario</li>
 * <li>PUT /cosechaencope/usuarios/productores/{idUsuario}  → Actualizar datos de proveedor por ID de usuario</li>
 * </ul>
 *
 * <p>
 * <strong>Nota:</strong> Todos los endpoints requieren autenticación JWT válida
 * y permisos apropiados para acceder a la información de proveedores.</p>
 *
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cosechaencope/usuarios/productores")
@Tag(name = "Productor", description = "Gestión de datos de productores asociados a usuarios")
public class ProductorController {

    
    @Autowired
    private ProductorService productorService;

    /**
     * Obtiene los datos del proveedor asociado al usuario indicado por su ID.
     *
     * <p>
     * Busca el proveedor vinculado al usuario especificado. Cada usuario de
     * tipo PRODUCTOR tiene asociado un registro de proveedor con información
     * comercial adicional como datos fiscales, información de contacto,
     * categorías de productos, etc.</p>
     *
     * @param idUsuario ID único del usuario cuyo proveedor se desea obtener
     * @return ResponseEntity con los datos del proveedor encontrado
     * @throws UsuarioNotFoundException Si el usuario no existe
     * @throws AccessDeniedException Si el usuario no tiene permisos para
     * acceder a este proveedor
     */
    @Operation(
            summary = "Obtener productor por ID de usuario",
            description = "Busca y devuelve los datos del productor asociado al usuario especificado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Productor encontrado exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Productor no encontrado para el usuario especificado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "ID de usuario inválido"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Sin permisos para acceder a este recurso"
        )
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ProductorResponse> obtenerProductorPorUsuarioId(@PathVariable Long idUsuario) {
        ProductorResponse productor = productorService.obtenerProductorPorUsuarioId(idUsuario);
        return ResponseEntity.ok(productor);
    }

    /**
     * Lista todos los productores registrados en el sistema.
     *
     * <p>
     * Devuelve una lista completa de todos los productores con su información
     * comercial básica. Este endpoint está disponible para usuarios con
     * permisos administrativos y puede ser utilizado para generar directorios
     * de productores o realizar búsquedas administrativas.</p>
     *
     * @return ResponseEntity con la lista de todos los productores
     */
    @Operation(
            summary = "Listar todos los productores",
            description = "Obtiene una lista completa de todos los productores registrados en el sistema"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Lista de productores obtenida exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ProductorResponse.class))
                )
        ),
        @ApiResponse(
                responseCode = "204",
                description = "No hay productores registrados en el sistema"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Sin permisos para acceder a este recurso"
        )
    })
    @GetMapping
    public ResponseEntity<List<ProductorResponse>> listarProductores() {
        List<ProductorResponse> productores = productorService.listarProductores();
        return ResponseEntity.ok(productores);
    }

    /**
     * Actualiza los datos del productor asociado al usuario indicado por su ID.
     *
     * <p>
     * Modifica la información comercial del productor vinculado al usuario
     * especificado. Solo se actualizan los campos proporcionados en el DTO de
     * entrada. Los campos no incluidos mantienen sus valores actuales. Esta
     * operación puede incluir actualización de datos fiscales, información de
     * contacto, categorías de productos ofrecidos, etc.</p>
     *
     * @param idUsuario ID del usuario cuyo productor se desea actualizar
     * @param proveedorRequestDTO DTO con los nuevos datos del productor a
     * actualizar
     * @return ResponseEntity con los datos actualizados del productor
     * @throws UsuarioNotFoundException Si el usuario no existe
     * @throws ValidationException Si los datos de entrada son inválidos
     * @throws AccessDeniedException Si el usuario no tiene permisos para
     * modificar este productor
     */
    @Operation(
            summary = "Actualizar datos de productor",
            description = "Modifica la información comercial del productor asociado al usuario especificado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Productor actualizado exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Productor no encontrado para el usuario especificado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Sin permisos para modificar este recurso"
        )
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<ProductorResponse> actualizarProductor(
            @PathVariable Long idUsuario,
            @RequestBody ProductorRequest productorRequest) {
        ProductorResponse productorActualizado = productorService.actualizarProductor(idUsuario, productorRequest);
        return ResponseEntity.ok(productorActualizado);
    }

}
