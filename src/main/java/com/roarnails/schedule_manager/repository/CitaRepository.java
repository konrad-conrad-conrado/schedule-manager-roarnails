package com.roarnails.schedule_manager.repository;

import com.roarnails.schedule_manager.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByFecha(LocalDate fecha);
    List<Cita> findByClienteId(Long clienteId);
    List<Cita> findByEstado(Cita.EstadoCita estado);
}
