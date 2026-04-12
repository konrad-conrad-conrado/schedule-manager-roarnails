package com.roarnails.schedule_manager.service;

import com.roarnails.schedule_manager.model.Servicio;
import com.roarnails.schedule_manager.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Business logic layer for Servicio operation.
 * Demonstrates encapsulation - all persistence access goes through
 * this service, controllers never touch the repository directly.
 */
@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public Servicio crear(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> obtenerPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio actualizar(Long id, Servicio datos) {
        return servicioRepository.findById(id).map(servicio -> {
            servicio.setNombre(datos.getNombre());
            servicio.setDuracionMinutos(datos.getDuracionMinutos());
            servicio.setPrecio(datos.getPrecio());
            return servicioRepository.save(servicio);
        }).orElseThrow(() -> new RuntimeException("Servicio no encontrado."));
    }

    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
}

