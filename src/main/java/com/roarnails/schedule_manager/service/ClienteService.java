package com.roarnails.schedule_manager.service;

import com.roarnails.schedule_manager.model.Cliente;
import com.roarnails.schedule_manager.repository.CitaRepository;
import com.roarnails.schedule_manager.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Business logic layer for Cliente operations.
 * Demonstrates single responsibility - ClienteService only handles
 * client-related operations, delegating persistence to ClienteRepository.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> obtenerPorTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono);
    }

    public Cliente actualizar(Long id, Cliente datos) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(datos.getNombre());
            cliente.setTelefono(datos.getTelefono());
            cliente.setEmail(datos.getEmail());
            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
