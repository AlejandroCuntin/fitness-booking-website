package com.ultrafit.ultrafit.controller;

import com.ultrafit.ultrafit.model.Reservation;
import com.ultrafit.ultrafit.service.ReservationService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// REST controller for the Reservation entity. Exposes CRUD + Patch endpoints under /api/reservations
@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    // Delegates all business logic and DB access to ReservationService
    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // GET /api/reservations — returns the full list of reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    // GET /api/reservations/{id} — returns a single reservation, or 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation r = reservationService.getReservationById(id);
        return (r == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }

    // POST /api/reservations — creates a new reservation and returns 201 Created
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation created = reservationService.createReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/reservations/{id} — full update, replaces all fields for the given ID
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation updated = reservationService.updateReservation(id, reservation);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/reservations/{id} — partial update, only modifies the fields sent in the request body
    @PatchMapping("/{id}")
    public ResponseEntity<Reservation> patchReservation(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Reservation patched = reservationService.patchReservation(id, updates);
        return ResponseEntity.ok(patched);
    }

    // DELETE /api/reservations/{id} — deletes the reservation and returns 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}