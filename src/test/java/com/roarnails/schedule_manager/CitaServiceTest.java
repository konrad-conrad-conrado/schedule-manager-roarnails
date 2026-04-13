package com.roarnails.schedule_manager;

import com.roarnails.schedule_manager.model.Cita;
import com.roarnails.schedule_manager.model.Cliente;
import com.roarnails.schedule_manager.model.Servicio;
import com.roarnails.schedule_manager.repository.CitaRepository;
import com.roarnails.schedule_manager.repository.ClienteRepository;
import com.roarnails.schedule_manager.repository.ServicioRepository;
import com.roarnails.schedule_manager.service.CitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CitaService.
 * Demonstrates testing composition — CitaService depends on three
 * repositories, all mocked to isolate business logic from persistence.
 */
@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private CitaService citaService;

    private Cliente cliente;
    private Servicio servicio;
    private Cita cita;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Conrado Limas");
        cliente.setTelefono("8112345678");

        servicio = new Servicio();
        servicio.setId(1L);
        servicio.setNombre("Manicure clásica");
        servicio.setDuracionMinutos(45);
        servicio.setPrecio(250.0);

        cita = new Cita();
        cita.setId(1L);
        cita.setCliente(cliente);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.of(2026, 4, 15));
        cita.setHora(LocalTime.of(14, 30));
        cita.setEstado(Cita.EstadoCita.PENDIENTE);
    }

    @Test
    void crearCita_debeAsignarEstadoPendiente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita resultado = citaService.crear(cita);

        assertNotNull(resultado);
        assertEquals(Cita.EstadoCita.PENDIENTE, resultado.getEstado());
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void crearCita_clienteNoExiste_debeLanzarExcepcion() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Cita citaInvalida = new Cita();
        Cliente clienteInvalido = new Cliente();
        clienteInvalido.setId(99L);
        citaInvalida.setCliente(clienteInvalido);
        citaInvalida.setServicio(servicio);

        assertThrows(RuntimeException.class, () -> {
            citaService.crear(citaInvalida);
        });
    }

    @Test
    void confirmarCita_debeActualizarEstado() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita resultado = citaService.confirmar(1L);

        assertEquals(Cita.EstadoCita.CONFIRMADA, resultado.getEstado());
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void cancelarCita_debeActualizarEstado() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita resultado = citaService.cancelar(1L);

        assertEquals(Cita.EstadoCita.CANCELADA, resultado.getEstado());
    }

    @Test
    void completarCita_debeActualizarEstado() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        Cita resultado = citaService.completar(1L);

        assertEquals(Cita.EstadoCita.COMPLETADA, resultado.getEstado());
    }

    @Test
    void obtenerPorFecha_debeRetornarCitasDelDia() {
        when(citaRepository.findByFecha(LocalDate.of(2026, 4, 15)))
                .thenReturn(Arrays.asList(cita));

        List<Cita> resultado = citaService.obtenerPorFecha(LocalDate.of(2026, 4, 15));

        assertEquals(1, resultado.size());
        assertEquals(LocalDate.of(2026, 4, 15), resultado.get(0).getFecha());
    }

    @Test
    void obtenerPorCliente_debeRetornarCitasDelCliente() {
        when(citaRepository.findByClienteId(1L)).thenReturn(Arrays.asList(cita));

        List<Cita> resultado = citaService.obtenerPorCliente(1L);

        assertEquals(1, resultado.size());
        assertEquals("Conrado Limas", resultado.get(0).getCliente().getNombre());
    }

    @Test
    void cambiarEstado_citaNoExiste_debeLanzarExcepcion() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            citaService.confirmar(99L);
        });
    }
}