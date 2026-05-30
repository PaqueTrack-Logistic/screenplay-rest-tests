@ui @registration
Feature: Request access to PaqueTrack (web)
  In order to join the logistics operation
  a new person must be able to request access from the web,
  and the platform must leave the account pending administrative approval.

  Background:
    Given the visitor is on the PaqueTrack registration page

  Scenario: A new applicant requests access through the web form
    When a new applicant requests access through the form
    Then the platform confirms the request is pending approval
