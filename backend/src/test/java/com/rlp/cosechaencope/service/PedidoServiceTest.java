package com.rlp.cosechaencope.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rlp.cosechaencope.dto.response.PedidoResponse;
import com.rlp.cosechaencope.exception.ResourceNotFoundException;
import com.rlp.cosechaencope.model.Articulo;
import com.rlp.cosechaencope.model.Carrito;
import com.rlp.cosechaencope.model.Categoria;
import com.rlp.cosechaencope.model.Cliente;
import com.rlp.cosechaencope.model.DetalleCarrito;
import com.rlp.cosechaencope.model.Pedido;
import com.rlp.cosechaencope.model.Productor;
import com.rlp.cosechaencope.model.Usuario;
import com.rlp.cosechaencope.repository.CarritoRepository;
import com.rlp.cosechaencope.repository.ClienteRepository;
import com.rlp.cosechaencope.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private PedidoService pedidoService;

    private Cliente cliente;
    private Usuario usuario;
    private Carrito carrito;
    private Articulo articulo;
    private DetalleCarrito detalleCarrito;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        // Configurar usuario
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setEmail("cliente@test.com");
        usuario.setTipoUsuario("CLIENTE");

        // Configurar cliente
        cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setNombre("Cliente Test");
        cliente.setUsuario(usuario);

        // Configurar categoría y productor
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1L);
        categoria.setNombre("Frutas");

        Productor productor = new Productor();
        productor.setIdProductor(1L);
        productor.setNombre("Productor Test");

        // Configurar artículo
        articulo = new Articulo();
        articulo.setIdArticulo(1L);
        articulo.setNombre("Tomate");
        articulo.setDescripcion("Tomate fresco");
        articulo.setPrecio(new BigDecimal("5.50"));
        articulo.setStock(100);
        articulo.setCategoria(categoria);
        articulo.setProductor(productor);

        // Configurar detalle carrito
        detalleCarrito = new DetalleCarrito();
        detalleCarrito.setIdDetalleCarrito(1L);
        detalleCarrito.setArticulo(articulo);
        detalleCarrito.setCantidad(5);
        detalleCarrito.setPrecioUnitario(new BigDecimal("5.50"));

        // Configurar carrito
        carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setCliente(cliente);
        carrito.setFechaCreacion(Instant.now());
        carrito.setDetalleList(List.of(detalleCarrito));
        detalleCarrito.setCarrito(carrito);

        // Configurar pedido
        pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setCliente(cliente);
        pedido.setFechaPedido(Instant.now());
        pedido.setMetodoPago("TARJETA");
        pedido.setEstadoPedido("PENDIENTE");
        pedido.setSubtotal(new BigDecimal("27.50"));
        pedido.setTotal(new BigDecimal("27.50"));
    }

    @Test
    void crearPedido_deberiaCrearPedidoCuandoDatosValidos() {
        // Arrange
        when(clienteRepository.findByUsuario_IdUsuario(1L)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setIdPedido(1L); // Simular ID generado
            return p;
        });

        // Act
        PedidoResponse response = pedidoService.crearPedido(1L, "TARJETA");

        // Assert
        assertThat(response.getIdPedido()).isEqualTo(1L);
        assertThat(response.getMetodoPago()).isEqualTo("TARJETA");
        assertThat(response.getEstadoPedido()).isEqualTo("PENDIENTE");
        assertThat(response.getDetalles()).hasSize(1);
        verify(clienteRepository).findByUsuario_IdUsuario(1L);
        verify(carritoRepository).findByCliente(cliente);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(carritoService).vaciarCarrito(1L);
    }

    @Test
    void crearPedido_deberiaLanzarExcepcionCuandoClienteNoExiste() {
        // Arrange
        when(clienteRepository.findByUsuario_IdUsuario(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> pedidoService.crearPedido(1L, "TARJETA"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cliente no encontrado");

        verify(clienteRepository).findByUsuario_IdUsuario(1L);
        verifyNoMoreInteractions(carritoRepository, pedidoRepository, carritoService);
    }

    @Test
    void crearPedido_deberiaLanzarExcepcionCuandoCarritoNoExiste() {
        // Arrange
        when(clienteRepository.findByUsuario_IdUsuario(1L)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> pedidoService.crearPedido(1L, "TARJETA"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Carrito no encontrado");

        verify(clienteRepository).findByUsuario_IdUsuario(1L);
        verify(carritoRepository).findByCliente(cliente);
        verifyNoMoreInteractions(pedidoRepository, carritoService);
    }

    @Test
    void crearPedido_deberiaLanzarExcepcionCuandoCarritoEstaVacio() {
        // Arrange
        carrito.setDetalleList(List.of()); // Carrito vacío
        when(clienteRepository.findByUsuario_IdUsuario(1L)).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByCliente(cliente)).thenReturn(Optional.of(carrito));

        // Act & Assert
        assertThatThrownBy(() -> pedidoService.crearPedido(1L, "TARJETA"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No se puede crear un pedido con un carrito vacío");

        verify(clienteRepository).findByUsuario_IdUsuario(1L);
        verify(carritoRepository).findByCliente(cliente);
        verifyNoMoreInteractions(pedidoRepository, carritoService);
    }

    @Test
    void listarPorCliente_deberiaRetornarPedidosDelCliente() {
        // Arrange
        when(clienteRepository.findByUsuario_IdUsuario(1L)).thenReturn(Optional.of(cliente));
        when(pedidoRepository.findByCliente(cliente)).thenReturn(List.of(pedido));

        // Act
        List<PedidoResponse> response = pedidoService.listarPorCliente(1L);

        // Assert
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getIdPedido()).isEqualTo(1L);
        assertThat(response.get(0).getMetodoPago()).isEqualTo("TARJETA");
        verify(clienteRepository).findByUsuario_IdUsuario(1L);
        verify(pedidoRepository).findByCliente(cliente);
    }

    @Test
    void listarPorCliente_deberiaLanzarExcepcionCuandoClienteNoExiste() {
        // Arrange
        when(clienteRepository.findByUsuario_IdUsuario(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> pedidoService.listarPorCliente(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cliente no encontrado");

        verify(clienteRepository).findByUsuario_IdUsuario(1L);
        verifyNoMoreInteractions(pedidoRepository);
    }
}