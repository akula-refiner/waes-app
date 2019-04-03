package com.waes.assignment.waesapp.repo;

import com.waes.assignment.waesapp.model.DiffEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DiffRepo extends CrudRepository<DiffEntity, Long> {
    Optional<DiffEntity> findDiffEntityByCorrelationId(String correlationId);

}
