package com.assignment.assignhub.repository;

import com.assignment.assignhub.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit,Long> {
    Optional<Unit> findByUnitCode(String unitCode);
    Optional<Unit> findByUnitName(String unitName);
}
