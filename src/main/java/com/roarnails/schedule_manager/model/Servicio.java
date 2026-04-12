package com.roarnails.schedule_manager.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a nail salon service offered to clients.
 * Demonstrates encapsulation and composition -
 * Servicio is later composed into Cita as relantionship.
 */
@Data
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer duracionMinutos;

    @Column(nullable = false)
    private Double precio;
}
