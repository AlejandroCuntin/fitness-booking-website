package com.ultrafit.ultrafit.controller;

import com.ultrafit.ultrafit.model.Member;
import com.ultrafit.ultrafit.service.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// REST controller for the Member entity. Exposes CRUD + Patch endpoints under /api/members
@RestController
@RequestMapping("/api/members")
public class MemberRestController {

    // Delegates all business logic and DB access to MemberService
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    // GET /api/members — returns the full list of members  
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // GET /api/members/{id} — returns a single member, or 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return (member == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(member);
    }

    // POST /api/members — creates a new member and returns 201 Created
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member created = memberService.createMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/members/{id} — full update, replaces all fields for the given ID
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        Member updated = memberService.updateMember(id, member);
        return ResponseEntity.ok(updated);// sent the update 
    }

    // PATCH /api/members/{id} — partial update, only modifies the fields sent in the request body
    @PatchMapping("/{id}")
    public ResponseEntity<Member> patchMember(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Member patched = memberService.patchMember(id, updates);
        return ResponseEntity.ok(patched); //send the updated member back
    }

    // DELETE /api/members/{id} — deletes the member and returns 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}