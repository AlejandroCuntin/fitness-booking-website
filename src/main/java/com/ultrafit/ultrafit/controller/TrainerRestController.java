package com.ultrafit.ultrafit.controller;

import com.ultrafit.ultrafit.model.Trainer;
import com.ultrafit.ultrafit.service.TrainerService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// REST controller for the Trainer entity. Exposes CRUD + Patch endpoints under /api/trainers
@RestController
@RequestMapping("/api/trainers")
public class TrainerRestController {

    // Delegates all business logic and DB access to TrainerService
    private final TrainerService trainerService;

    public TrainerRestController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // GET /api/trainers — returns the full list of trainers
    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    // GET /api/trainers/{id} — returns a single trainer, or 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        Trainer trainer = trainerService.getTrainerById(id);
        return (trainer == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(trainer);
    }

    // POST /api/trainers — creates a new trainer and returns 201 Created
    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        Trainer created = trainerService.createTrainer(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/trainers/{id} — full update, replaces all fields for the given ID
    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        Trainer updated = trainerService.updateTrainer(id, trainer);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/trainers/{id} — partial update, only modifies the fields sent in the request body
    @PatchMapping("/{id}")
    public ResponseEntity<Trainer> patchTrainer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Trainer patched = trainerService.patchTrainer(id, updates);
        return ResponseEntity.ok(patched);
    }

    // DELETE /api/trainers/{id} — deletes the trainer (and cascade removes its reservations), returns 204
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }
}