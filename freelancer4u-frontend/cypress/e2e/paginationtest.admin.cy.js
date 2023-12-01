describe("Pagination Test", () => {
    before(() => {
        cy.clearAllSessionStorage();
        cy.visit("http://localhost:8080");
        cy.get("#username").type(Cypress.env()["admin"].email);
        cy.get("#password").type(Cypress.env()["admin"].password);
        cy.contains("button", "Log in").click();
        cy.get("h1").should("contain", "Welcome");
    });

    it("should create 2 more jobs and check pagination", () => {
        // ... Gehe zur Job-Erstellungsseite ...
        cy.visit("http://localhost:8080/#/jobs");

        const additionalJobs = [
            { description: 'Job Description 4', type: 'REVIEW', earnings: '4000' },
            { description: 'Job Description 5', type: 'TEST', earnings: '5000' }
        ];

        additionalJobs.forEach(job => {
            cy.get('#description').clear().type(job.description);
            cy.get('#type').select(job.type);
            cy.get('#earnings').clear().type(job.earnings);
            cy.get('#submit').click();
            // Warte auf eine Bestätigung der Job-Erstellung
            // Ersetze dies durch einen realen Befehl, wie cy.wait(...)
        });

        // Gehe zur Job-Liste
        cy.visit("http://localhost:8080/#/jobs");

        // Überprüfe, ob die erste Seite 4 Elemente enthält
        cy.get('tbody>tr').should('have.length', 4);

        // Klicke auf den Link zur zweiten Seite
        cy.get('a[href="#/jobs?page=2"]').click();

        // Überprüfe, ob die zweite Seite nur ein Element enthält
        cy.get('tbody>tr').should('have.length', 1);

        // Überprüfe den Inhalt des Elements auf der zweiten Seite
        cy.get('tbody>tr').first().should('contain', 'Job Description 5');
    });
});
