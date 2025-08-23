package com.rlp.cosechaencope.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rlp.cosechaencope.dto.request.CategoriaRequest;
import com.rlp.cosechaencope.dto.response.CategoriaResponse;
import com.rlp.cosechaencope.model.Categoria;
import com.rlp.cosechaencope.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaRequest categoriaRequest;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        // Inicializar el DTO de petición con datos de prueba
        categoriaRequest = new CategoriaRequest();
        categoriaRequest.setIdCategoria(1L); // Simular ID para pruebas
        categoriaRequest.setNombre("Frutas");
        categoriaRequest.setDescripcion("Frutas frescas y saludables");

        categoria = new Categoria();
        categoria.setIdCategoria(1L); // Simular ID para pruebas
        categoria.setNombre("Frutas");
        categoria.setDescripcion("Frutas frescas y saludables");
    }

    @Test
    void crearCategoria_deberiaCrearCategoriaCorrectamente() {
        // Arrange: configurar el mock para que retorne una categoría guardada
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> {
            Categoria categoria = invocation.getArgument(0);
            categoria.setIdCategoria(1L); // Simular ID generado
            return categoria;
        });
        // Act: llamar al método del servicio
        CategoriaResponse response = categoriaService.crearCategoria(categoriaRequest);

        // Assert: verificar que la categoría fue creada con los datos correctos
        assertThat(response.getNombre()).isEqualTo("Frutas");
        assertThat(response.getDescripcion()).isEqualTo("Frutas frescas y saludables");

        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void obtenerCategoriaPorId_deberiaRetornarCategoriaExistente() {
        // Arrange
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        // Act
        CategoriaResponse response = categoriaService.obtenerCategoriaPorId(1L);

        // Assert
        assertThat(response.getIdCategoria()).isEqualTo(1L);
        assertThat(response.getNombre()).isEqualTo("Frutas");
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void obtenerTodasLasCategorias_deberiaRetornarListaDeCategorias() {
        // Arrange: simular una lista de categorías
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        // Act: llamar al método del servicio
        var response = categoriaService.obtenerTodasLasCategorias();

        // Assert: verificar que la lista no esté vacía y contenga la categoría esperada
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getNombre()).isEqualTo("Frutas");

        verify(categoriaRepository).findAll();
    }

    @Test
    void actualizarCategoria_deberiaActualizarCategoriaCorrectamente() {
        // Arrange: configurar el mock para que retorne una categoría existente
        categoria = new Categoria();
        categoria.setIdCategoria(1L);
        categoria.setNombre("Frutas");
        categoria.setDescripcion("Frutas frescas");

        when(categoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Act: llamar al método del servicio
        CategoriaResponse response = categoriaService.actualizarCategoria(1L, categoriaRequest);

        // Assert: verificar que la categoría fue actualizada correctamente
        assertThat(response.getNombre()).isEqualTo("Frutas");
        assertThat(response.getDescripcion()).isEqualTo("Frutas frescas y saludables");
    }

    @Test
    void eliminarCategoria_deberiaEliminarCategoriaExistente() {
        // Arrange: configurar el mock para que retorne una categoría existente
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        // Act: llamar al método del servicio
        categoriaService.eliminarCategoria(1L);

        // Assert: verificar que el repositorio fue llamado para eliminar la categoría
        verify(categoriaRepository).delete(categoria);
    }
}
