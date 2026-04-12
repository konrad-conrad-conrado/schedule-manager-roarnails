package com.roarnails.schedule_manager.controller;

import com.roarnails.schedule_manager.model.Cita;
import com.roarnails.schedule_manager.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for Cita operations.
 * Demonstrates composition at the API level - CitaController
 * coordinates respones that involve Cliente, Servicio, and Cita together.
 */
@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    public List<Cita> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fecha/{fecha}")
    public List<Cita> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return citaService.obtenerPorFecha(fecha);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Cita> obtenerPorCliente(@PathVariable Long clienteId) {
        return citaService.obtenerPorCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<Cita> crear(@RequestBody Cita cita) {
        return ResponseEntity.ok(citaService.crear(cita));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Cita> confirmar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(citaService.confirmar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(citaService.cancelar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<Cita> completar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(citaService.completar(id));
        } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}