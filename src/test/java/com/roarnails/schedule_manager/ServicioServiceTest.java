package com.roarnails.schedule_manager;

import com.roarnails.schedule_manager.model.Servicio;
import com.roarnails.schedule_manager.repository.ServicioRepository;
import com.roarnails.schedule_manager.service.ServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ServicioService.
 * Demonstrates testing business logic in isolation
 * using Mockito to simulate repository behavior.
 */
@ExtendWith(MockitoExtension.class)
public class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    private Servicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new Servicio();
        servicio.setId(1L);
        servicio.setNombre("Manicure clásica");
        servicio.setDuracionMinutos(45);
        servicio.setPrecio(250.0);
    }

    @Test
    void crearServicio_debeRetornarServicioGuardado() {
        when(servicioRepository.save(servicio)).thenReturn(servicio);

        Servicio resultado = servicioService.crear(servicio);

        assertNotNull(resultado);
        assertEquals("Manicure clásica", resultado.getNombre());
        assertEquals(250.0, resultado.getPrecio());
        verify(servicioRepository, times(1)).save(servicio);
    }

    @Test
    void obtenerTodos_debeRetornarListaDeServicios() {
        Servicio servicio2 = new Servicio();
        servicio2.setId(2L);
        servicio2.setNombre("Pedicure");
        servicio2.setDuracionMinutos(60);
        servicio2.setPrecio(300.0);

        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio, servicio2));

        List<Servicio> resultado = servicioService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(servicioRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_servicioExiste_debeRetornarServicio() {
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        Optional<Servicio> resultado = servicioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(45, resultado.get().getDuracionMinutos());
    }

    @Test
    void actualizar_servicioExiste_debeActualizarDatos() {
        Servicio datosNuevos = new Servicio();
        datosNuevos.setNombre("Manicure gel");
        datosNuevos.setDuracionMinutos(60);
        datosNuevos.setPrecio(350.0);

        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        Servicio resultado = servicioService.actualizar(1L, datosNuevos);

        assertNotNull(resultado);
        verify(servicioRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    void actualizar_servicioNoExiste_debeLanzarExcepcion() {
        when(servicioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            servicioService.actualizar(99L, servicio);
        });
    }
}