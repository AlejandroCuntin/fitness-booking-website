package com.ultrafit.ultrafit.repository;

import com.ultrafit.ultrafit.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for Member entity. Provides standard CRUD operations via JpaRepository.
// findByName is a derived query method generated automatically by Spring Data JPA
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);
}