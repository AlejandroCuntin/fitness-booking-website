package com.ultrafit.ultrafit.model;

import jakarta.persistence.*;

// Entity representing a training session booked by a member with a trainer
// Acts as the join table for the N:M relationship between Member and Trainer
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N:1 relationship with Member (many reservations -> one member)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // N:1 relationship with Trainer (many reservations -> one trainer)
    // Together with the member side, this models the N:M between Member and Trainer
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    private String date;
    private String time;
    private String level;
    private String username;

    public Reservation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    // Convenience getters used by WebController and Mustache templates
    public Long getMemberId() { return member != null ? member.getId() : null; }
    public Long getTrainerId() { return trainer != null ? trainer.getId() : null; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // Convenience setters: allow setting the foreign key by ID alone,
    // creating a shell object if the full entity has not been loaded yet
    public void setMemberId(Long memberId) {
        if (this.member == null) this.member = new Member();
        this.member.setId(memberId);
    }

    public void setTrainerId(Long trainerId) {
        if (this.trainer == null) this.trainer = new Trainer();
        this.trainer.setId(trainerId);
    }
}