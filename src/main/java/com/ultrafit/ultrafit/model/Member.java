package com.ultrafit.ultrafit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

// Entity representing a registered member of the gym
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String plan;
    private String password;

    // A member can have many reservations (1:N relationship)
    // JsonIgnore prevents infinite recursion when serializing to JSON
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Member() {}

    public Member(String name, String surname, String email, String phone, String plan) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.plan = plan;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
}