@ui @administration
Feature: User administration (web)
  In order to keep the operation in trusted hands
  an administrator must be able to review and approve new applicants
  from the web panel.

  Background:
    Given a new applicant has requested access from the web
    And an operator has signed in to PaqueTrack
    And the administrator opens the pending registrations

  Scenario: An administrator approves a new applicant from the web panel
    When the administrator approves that applicant
    Then the platform confirms the applicant was approved

  Scenario: An administrator rejects a new applicant from the web panel
    When the administrator rejects that applicant
    Then the platform confirms the applicant was rejected
