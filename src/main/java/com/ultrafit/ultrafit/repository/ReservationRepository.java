package com.ultrafit.ultrafit.repository;

import com.ultrafit.ultrafit.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository for Reservation entity. Provides standard CRUD operations via JpaRepository.
// findByUsername is a derived query used to load all reservations for the logged-in user
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUsername(String username);
}