package org.example.taskmanager.repository;

import org.example.taskmanager.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmailIgnoreCase(String email);
    List<Supplier> findByCityIgnoreCase(String city);
    boolean existsByEmailIgnoreCase(String email);
}
