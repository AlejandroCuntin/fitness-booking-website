package com.ultrafit.ultrafit.service;

import com.ultrafit.ultrafit.model.Member;
import com.ultrafit.ultrafit.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer for Member. Contains all business logic related to members.
// Used by both WebController and MemberRestController to avoid code duplication
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Returns all members stored in the database
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Finds a member by their username (used for login and session checks)
    public Member getMemberByName(String name){
        return memberRepository.findByName(name);
    }

    // Finds a member by their primary key. Returns null if not found
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // Persists a new member in the database
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // Full update: sets the ID on the incoming object and saves it, replacing all fields
    public Member updateMember(Long id, Member updatedMember) {
        updatedMember.setId(id);
        return memberRepository.save(updatedMember);
    }

    // Partial update: only modifies the fields present in the updates map
    public Member patchMember(Long id, Map<String, Object> updates) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) return null;
        if (updates.containsKey("name"))    member.setName((String) updates.get("name"));
        if (updates.containsKey("surname")) member.setSurname((String) updates.get("surname"));
        if (updates.containsKey("email"))   member.setEmail((String) updates.get("email"));
        if (updates.containsKey("phone"))   member.setPhone((String) updates.get("phone"));
        if (updates.containsKey("plan"))    member.setPlan((String) updates.get("plan"));
        return memberRepository.save(member);
    }

    // Deletes a member by ID. Associated reservations are removed via cascade
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

}