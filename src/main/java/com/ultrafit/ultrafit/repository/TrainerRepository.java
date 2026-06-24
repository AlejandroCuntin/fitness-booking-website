package com.ultrafit.ultrafit.repository;

import com.ultrafit.ultrafit.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for Trainer entity. Inherits all standard CRUD operations from JpaRepository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {}