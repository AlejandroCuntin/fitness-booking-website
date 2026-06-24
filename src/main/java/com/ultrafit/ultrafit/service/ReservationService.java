package com.ultrafit.ultrafit.service;

import com.ultrafit.ultrafit.model.Member;
import com.ultrafit.ultrafit.model.Reservation;
import com.ultrafit.ultrafit.model.Trainer;
import com.ultrafit.ultrafit.repository.MemberRepository;
import com.ultrafit.ultrafit.repository.ReservationRepository;
import com.ultrafit.ultrafit.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer for Reservation. Contains all business logic related to reservations.
// Used by both WebController and ReservationRestController to avoid code duplication
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              MemberRepository memberRepository,
                              TrainerRepository trainerRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
    }

    // Returns all reservations stored in the database
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Finds a reservation by its primary key. Returns null if not found
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // Creates a new reservation. Resolves the Member and Trainer entities
    // from their IDs before persisting to satisfy the JPA relationships
    public Reservation createReservation(Reservation reservation) {
        if (reservation.getMemberId() != null) {
            Member m = memberRepository.findById(reservation.getMemberId()).orElse(null);
            reservation.setMember(m);
        }
        if (reservation.getTrainerId() != null) {
            Trainer t = trainerRepository.findById(reservation.getTrainerId()).orElse(null);
            reservation.setTrainer(t);
        }
        return reservationRepository.save(reservation);
    }

    // Full update: sets the ID and resolves Member/Trainer references before saving
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        updatedReservation.setId(id);
        if (updatedReservation.getMemberId() != null) {
            Member m = memberRepository.findById(updatedReservation.getMemberId()).orElse(null);
            updatedReservation.setMember(m);
        }
        if (updatedReservation.getTrainerId() != null) {
            Trainer t = trainerRepository.findById(updatedReservation.getTrainerId()).orElse(null);
            updatedReservation.setTrainer(t);
        }
        return reservationRepository.save(updatedReservation);
    }

    // Partial update: only modifies the fields present in the updates map
    public Reservation patchReservation(Long id, Map<String, Object> updates) {
        Reservation r = reservationRepository.findById(id).orElse(null);
        if (r == null) return null;
        if (updates.containsKey("date"))  r.setDate((String) updates.get("date"));
        if (updates.containsKey("time"))  r.setTime((String) updates.get("time"));
        if (updates.containsKey("level")) r.setLevel((String) updates.get("level"));
        return reservationRepository.save(r);
    }

    // Deletes a reservation by ID
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Returns all reservations belonging to a specific logged-in user
    public List<Reservation> getReservationsByUsername(String username) {
        return reservationRepository.findByUsername(username);
    }
}