package com.ultrafit.ultrafit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

// Entity representing a personal trainer available at the gym
@Entity
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;
    private int experienceYears;

    // A trainer can have many reservations (1:N relationship)
    // orphanRemoval ensures that when a reservation is removed from this list,
    // it is also deleted from the database
    // JsonIgnore prevents infinite recursion when serializing to JSON
    @JsonIgnore
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    public Trainer() {}

    public Trainer(String name, String specialty, int experienceYears) {
        this.name = name;
        this.specialty = specialty;
        this.experienceYears = experienceYears;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
}