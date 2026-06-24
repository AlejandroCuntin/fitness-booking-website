package com.ultrafit.ultrafit.service;

import com.ultrafit.ultrafit.model.Trainer;
import com.ultrafit.ultrafit.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer for Trainer. Contains all business logic related to trainers.
// Used by both WebController and TrainerRestController to avoid code duplication
@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    // Returns all trainers stored in the database
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    // Finds a trainer by their primary key. Returns null if not found
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    // Persists a new trainer in the database
    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    // Full update: sets the ID on the incoming object and saves it, replacing all fields
    public Trainer updateTrainer(Long id, Trainer updatedTrainer) {
        updatedTrainer.setId(id);
        return trainerRepository.save(updatedTrainer);
    }

    // Partial update: only modifies the fields present in the updates map
    public Trainer patchTrainer(Long id, Map<String, Object> updates) {
        Trainer trainer = trainerRepository.findById(id).orElse(null);
        if (trainer == null) return null;
        if (updates.containsKey("name"))            trainer.setName((String) updates.get("name"));
        if (updates.containsKey("specialty"))       trainer.setSpecialty((String) updates.get("specialty"));
        if (updates.containsKey("experienceYears")) trainer.setExperienceYears((int) updates.get("experienceYears"));
        return trainerRepository.save(trainer);
    }

    // Deletes a trainer by ID.
    // The reservations list is cleared first to let orphanRemoval handle
    // the cascade deletion and avoid foreign key constraint violations in H2
    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id).orElse(null);
        if (trainer == null) return;
        if (trainer.getReservations() != null) {
            trainer.getReservations().clear();
        }
        trainerRepository.deleteById(id);
    }
}