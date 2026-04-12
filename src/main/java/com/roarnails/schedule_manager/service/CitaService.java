package com.roarnails.schedule_manager.service;

import com.roarnails.schedule_manager.model.Cita;
import com.roarnails.schedule_manager.model.Cliente;
import com.roarnails.schedule_manager.model.Servicio;
import com.roarnails.schedule_manager.repository.CitaRepository;
import com.roarnails.schedule_manager.repository.ClienteRepository;
import com.roarnails.schedule_manager.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Business logic layer for Cita operations.
 * Demonstrates composition - CitaService depends on CitaRepository,
 * ClientesRepository and ServicioRepository to coordinate appointment logic.
 * Demonstrates single reponsibility - all appointment rules live here,
 * not in the controller or the model.
 */
@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final ServicioRepository servicioRepository;

    public Cita crear(Cita cita) {
        Cliente cliente = clienteRepository.findById(cita.getCliente().getId())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));

        Servicio servicio = servicioRepository.findById(cita.getServicio().getId())
                        .orElseThrow(() -> new RuntimeException("Servicio no encontrado."));

        cita.setCliente(cliente);
        cita.setServicio(servicio);
        cita.setEstado(Cita.EstadoCita.PENDIENTE);

        return citaRepository.save(cita);
    }

    public List<Cita> obtenerTodas() {

        return citaRepository.findAll();
    }

    public Optional<Cita> obtenerPorId(Long id) {

        return citaRepository.findById(id);
    }

    public List<Cita> obtenerPorFecha(LocalDate fecha) {

        return citaRepository.findByFecha(fecha);
    }

    public List<Cita> obtenerPorCliente(Long clienteId) {

        return citaRepository.findByClienteId(clienteId);
    }

    public Cita cambiarEstado(Long id, Cita.EstadoCita nuevoEstado) {
        return citaRepository.findById(id).map(cita -> {
            cita.setEstado(nuevoEstado);
            return citaRepository.save(cita);
        }).orElseThrow(() -> new RuntimeException("Cita no encontrada."));
    }

    public Cita cancelar(Long id) {

        return cambiarEstado(id, Cita.EstadoCita.CANCELADA);
    }

    public Cita confirmar(Long id) {

        return cambiarEstado(id, Cita.EstadoCita.CONFIRMADA);
    }

    public Cita completar(Long id) {

        return cambiarEstado(id, Cita.EstadoCita.COMPLETADA);
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }
}
