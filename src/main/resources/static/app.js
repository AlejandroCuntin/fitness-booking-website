document.addEventListener("DOMContentLoaded", () => { // with this we run everything after the DOM is fully loaded


// the confirmation to delete the reservation that we have created
// Shows a confirmation dialog before submitting delete forms

    // Select all delete forms for reservations
    const deleteForms = document.querySelectorAll("form[action='/reservations/delete']");
    deleteForms.forEach(form => {
        form.addEventListener("submit", (e) => {
            //this, ask the user to confirm. If they cancel, stop the form
            const ok = confirm("¿Seguro que quieres eliminar esta reserva?"); //if ok is 1 delete if 0 no delete
            if (!ok) e.preventDefault(); // cancel the submit
        });
    });



//Now we gonna create a fade animation when we scroll on the page (Intersection Observer we call it i think)
//the sections start invisible and slide up when we enter.
//with this we select all the elements on the page
    const sections = document.querySelectorAll("section");

    // we create an observer that fires when elements become visible
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            // If the section is now visible in the viewport
            if (entry.isIntersecting) { //if the element entry in the viewport of the user so
                // make it fully visible and move it to its normal position
                entry.target.style.opacity = "1";
                entry.target.style.transform = "translateY(0)";
            }
        });
    }, {
        // if the element entry with almost like 10% of visibility so it changed
        threshold: 0.1
    });

    // we set initial state for each section: invisible and shifted down 20px
    sections.forEach(section => {
        section.style.opacity = "0";// start invisible
        section.style.transform = "translateY(20px)"; // start shifted down
        section.style.transition = "opacity 0.6s ease, transform 0.6s ease"; // smooth transition
        observer.observe(section); // start watching it
    });



//now we gonna do a active nav link, with colours the nave lik that matches the current page

    // with this we get the current
    const currentPath = window.location.pathname;

    // we select all anchor tags  tags inside the nav
    const navLinks = document.querySelectorAll("nav ul li a");

    // loop through each link and check if it matches the current path
    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPath) {
            link.style.color = "#ff6600"; // orange text
            link.style.borderBottom = "2px solid #ff6600";// orange underline
            link.style.paddingBottom = "3px";// small gap under text
        }
    });

//navbar scroll effect
//we add a shawod to the header when the user scrolls down the page
    // get the header element
    const header = document.querySelector("header");

    // listen for scroll events on the window
    window.addEventListener("scroll", () => {
        // if the user has scrolled more than 50px from the top, than this appends
        if (window.scrollY > 50) {
            header.style.boxShadow = "0 2px 20px rgba(0, 0, 0, 0.5)"; // here we add shadow
            header.style.borderBottom = "1px solid #2a2a2a";           // and also we add border that we can see below the header, once we scroll more than 50px
        } else {
            header.style.boxShadow = "none";// remove shadow at top
            header.style.borderBottom = "none"; // remove border at top
        }
    });


//If we click a button we gonna create an animation
//we add a scale effect to all buttons on hover
    // we select all the buttons
    const buttons = document.querySelectorAll("button");

    buttons.forEach(button => {
        // when the mouse enters the button. slightly enlarge it
        button.addEventListener("mouseenter", () => {
            button.style.transform = "scale(1.04)";
            button.style.transition = "transform 0.15s ease";
        });

        // when the mouse leaves the button. Thats  back to normal size
        button.addEventListener("mouseleave", () => {
            button.style.transform = "scale(1)";
        });
    });

//now in the dashboard (Mi Cuenta), we gonna make the same thing that we have donne for the buttons ,but this time for the cards on hover
// we select all dashboard cards that are in dashboard.mustache
    const dashboardCards = document.querySelectorAll(".dashboard-card");

    dashboardCards.forEach(card => {
        // we lift the card up and highlight the border on hover
        card.addEventListener("mouseenter", () => {
            card.style.transform = "translateY(-4px)"; //the smotch transition that make the card go a little bit up
            card.style.transition = "transform 0.2s ease, border-color 0.2s ease";
            card.style.borderColor = "#ff6600";
        });

        // we reset the card when mouse leaves
        card.addEventListener("mouseleave", () => {
            card.style.transform = "translateY(0)";
            card.style.borderColor = "#2a2a2a";
        });
    });


//reservation row effect we create now
//Highlights reservation rows with an orange border on hover
// we select all reservation rows
    const reservationRows = document.querySelectorAll(".reservation-row");

    reservationRows.forEach(row => {
        // Orange border when hovering over a reservation row
        row.addEventListener("mouseenter", () => {
            row.style.borderColor = "#ff6600";
            row.style.transition = "border-color 0.2s ease";
        });

        // Back to dark border when mouse leaves
        row.addEventListener("mouseleave", () => {
            row.style.borderColor = "#2a2a2a";
        });
    });


//input focus
//Adds an orange border glow to inputs when focused

// we select all form fields on the page
    const formFields = document.querySelectorAll("input, select, textarea");

    formFields.forEach(field => {
        field.addEventListener("focus", () => { // Orange border when the user clicks into a field
            field.style.borderColor = "#ff6600";
            field.style.outline = "none";
            field.style.transition = "border-color 0.2s ease";
        });

        // Back to normal border when the user clicks out
        field.addEventListener("blur", () => {
            field.style.borderColor = "#444";
        });
    });


//caracter counter for the contact textarea
//Shows how many characters the user has typed in real time

// we try to find the contact textarea (only exists on index.mustache)
    const textarea = document.getElementById("mensaje");
    if (textarea) { // only run this if the textarea exists on this page
        const counter = document.createElement("p");     // we create a counter paragraph element

        // Style it: small, grey, right-aligned
        counter.style.cssText = "font-size: 12px; color: #888; text-align: right; margin-top: 4px;";

        // Set the initial text
        counter.textContent = "0 / 300 caracteres";

        // Insert it right after the textarea
        textarea.parentNode.appendChild(counter);

        // Update the counter on every keystroke
        textarea.addEventListener("input", () => {
            const length = textarea.value.length;               // current character count
            counter.textContent = `${length} / 300 caracteres`; // update display

            // Turn orange as a warning when approaching the limit
            counter.style.color = length > 270 ? "#ff6600" : "#888";
        });
    }

}); // here finally we end DOMContentLoaded because we just finished all the functions that interact with the page.


//Form validation functions
//we calle directly from onsubmit attributes in HTML templates


//here we validate reservations
//we check that memberId and trainerId are positive numbers
function validateReservationForm() {
    // Get the hidden memberId input
    const memberId = document.querySelector("input[name='memberId']");
    const trainerId = document.querySelector("select[name='trainerId']"); // Get the trainer select (now a dropdown, not a text input)

    if (!trainerId || !trainerId.value) {     // Check that a trainer has been selected (value must not be empty)
        alert("Por favor selecciona un entrenador.");
        return false; // cancel form submit
    }

    // All good — allow the form to submit
    return true;
}
//also validate contact form
// we Checks email format and minimum phone length
function validateContactForm() {
    const email = document.getElementById("email");     // we get the email input by its id
    const phone = document.getElementById("telefono");    // and also get the phone input by its id

    if (!email.value.includes("@")) {     // Check that the email contains an symbol
        alert("Por favor introduce un email válido.");
        return false; // cancel form submit
    }

    // Check that the phone has at least 9 digits
    if (phone.value.length < 9) {
        alert("El teléfono debe tener al menos 9 dígitos.");
        return false; // cancel form submit
    }
    
    return true;      // All the validations passed, we allow the form to submit
}