package com.roarnails.schedule_manager;

import com.roarnails.schedule_manager.model.Cliente;
import com.roarnails.schedule_manager.repository.ClienteRepository;
import com.roarnails.schedule_manager.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ClienteService.
 * Demonstrates testing with mocks — the repository is simulated
 * so tests run without a real database connection.
 */
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Conrado Limas");
        cliente.setTelefono("8112345678");
        cliente.setEmail("conrado@email.com");
    }

    @Test
    void crearCliente_debeRetornarClienteGuardado() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.crear(cliente);

        assertNotNull(resultado);
        assertEquals("Conrado Limas", resultado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void obtenerPorId_clienteExiste_debeRetornarCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("8112345678", resultado.get().getTelefono());
    }

    @Test
    void obtenerPorId_clienteNoExiste_debeRetornarVacio() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void eliminarCliente_debeInvocarRepositorio() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.eliminar(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
