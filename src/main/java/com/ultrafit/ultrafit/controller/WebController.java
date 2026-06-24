package com.ultrafit.ultrafit.controller;

import com.ultrafit.ultrafit.model.Member;
import com.ultrafit.ultrafit.model.Reservation;
import com.ultrafit.ultrafit.model.Trainer;
import com.ultrafit.ultrafit.service.MemberService;
import com.ultrafit.ultrafit.service.ReservationService;
import com.ultrafit.ultrafit.service.TrainerService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Web controller: handles all browser-facing requests and renders Mustache templates.
// Reuses the same service beans as the REST controllers (no duplicated DB logic)
@Controller
public class WebController {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private final TrainerService trainerService;

    public WebController(ReservationService reservationService,
                         MemberService memberService,
                         TrainerService trainerService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
        this.trainerService = trainerService;
    }

    // GET / — renders the home page; passes login state to the template
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            model.addAttribute("isLoggedIn", true);
        }
        return "index";
    }

    // GET /logout — invalidates the session and redirects to home
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // GET /login — renders the login form; redirects to dashboard if already logged in
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    // POST /login — validates credentials against the database; starts session on success
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {
        Member member = memberService.getMemberByName(username);
        if (member != null && password.equals(member.getPassword())) {
            session.setAttribute("user", username);
            session.setAttribute("plan", member.getPlan());
            return "redirect:/dashboard";
        }
        return "redirect:/login?error=true";
    }

    // GET /register — renders the registration form; redirects to dashboard if already logged in
    @GetMapping("/register")
    public String register(@RequestParam(required = false) String error,
                           HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        if (error != null) {
            model.addAttribute("errorExists", true);
        }
        return "register";
    }

    // POST /register — creates a new member, starts a session, and redirects to dashboard
    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session) {
        if (memberService.getMemberByName(username) != null) {
            return "redirect:/register?error=exists";
        }
        Member newMember = new Member();
        newMember.setName(username);
        newMember.setSurname("");
        newMember.setEmail("");
        newMember.setPhone("");
        newMember.setPlan("Basic Plan");
        newMember.setPassword(password);
        memberService.createMember(newMember);
        session.setAttribute("user", username);
        session.setAttribute("plan", "Basic Plan");
        return "redirect:/dashboard";
    }

    // GET /dashboard — main authenticated view; loads the member's reservations and all trainers
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username == null) return "redirect:/login";

        Member currentUser = memberService.getMemberByName(username);
        if (currentUser == null) return "redirect:/login";

        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userMember", currentUser);
        model.addAttribute("reservations", reservationService.getReservationsByUsername(username));
        return "dashboard";
    }

    // POST /reservations/create — creates a new reservation from the web form
    @PostMapping("/reservations/create")
    public String createReservation(@RequestParam Long memberId,
                                    @RequestParam Long trainerId,
                                    @RequestParam String date,
                                    @RequestParam String time,
                                    @RequestParam String level,
                                    HttpSession session) {
        Reservation r = new Reservation();
        r.setMemberId(memberId);
        r.setTrainerId(trainerId);
        r.setDate(date);
        r.setTime(time);
        r.setLevel(level);
        r.setUsername((String) session.getAttribute("user"));
        reservationService.createReservation(r);
        return "redirect:/dashboard";
    }

    // POST /reservations/delete — deletes a reservation by ID submitted from a form
    @PostMapping("/reservations/delete")
    public String deleteReservation(@RequestParam Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/dashboard";
    }

    // GET /reservations/edit — renders the edit form pre-filled with the selected reservation
    @GetMapping("/reservations/edit")
    public String editReservation(@RequestParam Long id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("reservation", reservationService.getReservationById(id));
        return "editReservation";
    }

    // POST /reservations/update — full update of a reservation from the edit form (equivalent to PUT)
    @PostMapping("/reservations/update")
    public String updateReservation(@RequestParam Long id,
                                    @RequestParam Long memberId,
                                    @RequestParam Long trainerId,
                                    @RequestParam String date,
                                    @RequestParam String time,
                                    @RequestParam String level,
                                    HttpSession session) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setMemberId(memberId);
        r.setTrainerId(trainerId);
        r.setDate(date);
        r.setTime(time);
        r.setLevel(level);
        r.setUsername((String) session.getAttribute("user"));
        reservationService.updateReservation(id, r);
        return "redirect:/dashboard";
    }

    // POST /reservations/patch — partial update of a reservation from a form (equivalent to PATCH)
    @PostMapping("/reservations/patch")
    public String patchReservationWeb(@RequestParam Long id,
                                      @RequestParam String level) {
        java.util.Map<String, Object> updates = new java.util.HashMap<>();
        updates.put("level", level);
        reservationService.patchReservation(id, updates);
        return "redirect:/dashboard";
    }

    // POST /plan/update — partial update of the logged-in member's subscription plan (PATCH)
    @PostMapping("/plan/update")
    public String updatePlan(@RequestParam String plan, HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username == null) return "redirect:/login";
        Member member = memberService.getMemberByName(username);
        if (member != null) {
            java.util.Map<String, Object> updates = new java.util.HashMap<>();
            updates.put("plan", plan);
            memberService.patchMember(member.getId(), updates);
            session.setAttribute("plan", plan);
        }
        return "redirect:/dashboard";
    }

    // POST /member/update — full update of the logged-in member's profile (equivalent to PUT)
    @PostMapping("/member/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String email,
                                @RequestParam String phone,
                                HttpSession session) {
        String currentUsername = (String) session.getAttribute("user");
        if (currentUsername == null) return "redirect:/login";
        Member member = memberService.getMemberByName(currentUsername);
        if (member != null) {
            member.setName(name);
            member.setSurname(surname);
            member.setEmail(email);
            member.setPhone(phone);
            memberService.updateMember(member.getId(), member);
            session.setAttribute("user", name);
        }
        return "redirect:/dashboard";
    }

    // POST /account/delete — deletes the logged-in member's account and invalidates the session
    @PostMapping("/account/delete")
    public String deleteAccount(HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username != null) {
            Member member = memberService.getMemberByName(username);
            if (member != null) {
                memberService.deleteMember(member.getId());
            }
            session.invalidate();
        }
        return "redirect:/";
    }

    // POST /trainers/create — creates a new trainer from a web form
    @PostMapping("/trainers/create")
    public String createTrainerWeb(@RequestParam String name,
                                   @RequestParam String specialty,
                                   @RequestParam int experienceYears) {
        trainerService.createTrainer(new Trainer(name, specialty, experienceYears));
        return "redirect:/dashboard";
    }

    // POST /trainers/delete — deletes a trainer by ID submitted from a form
    @PostMapping("/trainers/delete")
    public String deleteTrainerWeb(@RequestParam Long id) {
        trainerService.deleteTrainer(id);
        return "redirect:/dashboard";
    }

    // GET /trainers/edit — renders the edit form pre-filled with the selected trainer
    @GetMapping("/trainers/edit")
    public String editTrainerWeb(@RequestParam Long id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("trainer", trainerService.getTrainerById(id));
        return "editTrainer";
    }

    // POST /trainers/update — full update of a trainer from the edit form (equivalent to PUT)
    @PostMapping("/trainers/update")
    public String updateTrainerWeb(@RequestParam Long id,
                                   @RequestParam String name,
                                   @RequestParam String specialty,
                                   @RequestParam int experienceYears) {
        Trainer t = new Trainer(name, specialty, experienceYears);
        trainerService.updateTrainer(id, t);
        return "redirect:/dashboard";
    }

    // POST /trainers/patch — partial update of a trainer's specialty from a form (equivalent to PATCH)
    @PostMapping("/trainers/patch")
    public String patchTrainerWeb(@RequestParam Long id,
                                  @RequestParam String specialty) {
        java.util.Map<String, Object> updates = new java.util.HashMap<>();
        updates.put("specialty", specialty);
        trainerService.patchTrainer(id, updates);
        return "redirect:/dashboard";
    }

    // POST /contact — handles the contact form submission and shows a success message
    @PostMapping("/contact")
    public String contactForm(@RequestParam String nombre,
                              @RequestParam String apellido,
                              @RequestParam String email,
                              @RequestParam String telefono,
                              @RequestParam String centro,
                              @RequestParam String mensaje,
                              HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            model.addAttribute("isLoggedIn", true);
        }
        model.addAttribute("success", true);
        return "index";
    }

    // GET /faq — renders the FAQ page
    @GetMapping("/faq")
    public String faq(HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            model.addAttribute("isLoggedIn", true);
        }
        return "faq";
    }
}