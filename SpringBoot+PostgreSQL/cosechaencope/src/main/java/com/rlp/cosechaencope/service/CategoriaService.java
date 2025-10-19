package com.rlp.cosechaencope.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlp.cosechaencope.dto.request.CategoriaRequest;
import com.rlp.cosechaencope.dto.response.CategoriaResponse;
import com.rlp.cosechaencope.exception.ResourceNotFoundException;
import com.rlp.cosechaencope.model.Categoria;
import com.rlp.cosechaencope.repository.CategoriaRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio para gestionar las operaciones relacionadas con las categorías.
 * <p>
 * Incluye operaciones de creación, obtención, listado, actualización y
 * eliminación de categorías. Utiliza {@link CategoriaRepository} como capa de
 * acceso a datos.
 * </p>
 *
 * TODO: Implementar método de control para que dos categorías no tengan el mismo nombre.
 * 
 * @author rafalopezzz
 */
@Slf4j
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriasRepository;

    /**
     * Crea una nueva categoría en el sistema.
     *
     * @param dto DTO con los datos de la categoría a crear.
     * @return DTO de respuesta con los datos de la categoría creada.
     */
    public CategoriaResponse crearCategoria(CategoriaRequest dto) {
        log.info("Creando categoría: {}", dto.getNombre());
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        Categoria guardado = categoriasRepository.save(categoria);
        log.info("Categoría creada, id: {}", guardado.getIdCategoria());
        return mapearResponseDTO(guardado);
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id ID de la categoría a buscar.
     * @return DTO de respuesta con los datos de la categoría encontrada.
     * @throws ResourceNotFoundException si no se encuentra la categoría.
     */
    public CategoriaResponse obtenerCategoriaPorId(Long id) {
        log.info("Obteniendo categoría por id: {}", id);
        Categoria categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para el id: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada");
                });
        return mapearResponseDTO(categoria);
    }

    /**
     * Obtiene todas las categorías del sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todas las categorías.
     */
    public List<CategoriaResponse> obtenerTodasLasCategorias() {
        log.info("Obteniendo todas las categorías");
        List<Categoria> categoria = categoriasRepository.findAll();
        log.debug("Cantidad de categorías encontradas: {}", categoria.size());
        return categoria.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca categorías por su nombre.
     *
     * @param nombre Nombre de la categoría a buscar.
     * @return Lista de DTOs de respuesta con los datos de las categorías
     * encontradas.
     * @throws ResourceNotFoundException si no se encuentran categorías.
     */
    public CategoriaResponse actualizarCategoria(Long id, CategoriaRequest dto) {
        log.info("Actualizando categoría id: {}", id);
        Categoria categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para actualizar, id: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada");
                });
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria actualizada = categoriasRepository.save(categoria);
        log.info("Categoría actualizada, id: {}", actualizada.getIdCategoria());
        return mapearResponseDTO(actualizada);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría a eliminar.
     * @throws ResourceNotFoundException si no se encuentra la categoría.
     */
    public void eliminarCategoria(Long id) {
        log.info("Eliminando categoría id: {}", id);
        Categoria categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para eliminar, id: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada");
                });
        categoriasRepository.delete(categoria);
        log.info("Categoría eliminada, id: {}", id);
    }

    /**
     * Convierte una entidad {@link Categoria} en un DTO de respuesta.
     *
     * @param categoria La entidad a convertir.
     * @return DTO de respuesta con los datos de la categoría.
     */
    private CategoriaResponse mapearResponseDTO(Categoria categoria) {
        CategoriaResponse dto = new CategoriaResponse();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setImagenUrl(categoria.getImagenUrl());
        return dto;
    }
}
