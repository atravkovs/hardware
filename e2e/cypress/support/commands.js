import "@testing-library/cypress/add-commands";

Cypress.Commands.add("loginWith", ({ email, password }) =>
  cy
    .visit("/login")
    .findByLabelText("Email address")
    .type(email)
    .findByLabelText("Password")
    .type(password)
    .findByText("Sign in")
    .click()
);
