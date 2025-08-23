package com.rlp.cosechaencope.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlp.cosechaencope.model.Categoria;

/**
 * Repositorio para la entidad {@link Categoria}.
 *
 * <p>
 * Proporciona métodos para buscar categorías por nombre y descripción.
 * </p>
 *
 * @author rafalopezzz
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Busca categorías por su nombre.
     * 
     * @param nombre Nombre de la categoría a buscar.
     * @return Lista de categorías que coinciden con el nombre proporcionado.
     */
    List<Categoria> findByNombre(String nombre);

    /**
     * Busca categorías por su descripción.
     * 
     * @param descripcion Descripción de la categoría a buscar.
     * @return Lista de categorías que coinciden con la descripción proporcionada.
     */
    List<Categoria> findByDescripcionContaining(String descripcion);

}
