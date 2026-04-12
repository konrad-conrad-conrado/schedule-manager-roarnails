package com.roarnails.schedule_manager.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * *Represents a nail salon client.
 * Demonstrates encapsulation - all fields are private,
 * managed by Lombok´s @Data which geneartes getters and setters automatically.
 */
@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(unique = true)
    private String email;
}
