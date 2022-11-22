import { When, Then } from "cypress-cucumber-preprocessor/steps";

When("I login", () => {
  cy.loginWith({ email: "test.user@test.com", password: "testUser123" });
});

Then("the url is {word}", (url) => {
  cy.url().should("eq", `${Cypress.config().baseUrl}${url}`);
});

Then("I'm logged in", () => {
  cy.window().its("localStorage.token").should("exist");
});
