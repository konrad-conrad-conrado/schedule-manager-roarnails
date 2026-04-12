package com.roarnails.schedule_manager.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an appoinment in the nail salon.
 * Demonstrates composition - Cita owns references to Cliente and Servicio.
 * Demonstrates OOP relationships - ManyToOne reflects that many appointments
 * can belong to one client, and many appointments can include one service.
 */
@Data
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estado;

    public enum EstadoCita {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA,
        COMPLETADA
    }
}
