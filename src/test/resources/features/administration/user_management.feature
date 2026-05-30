@admin
Feature: Administration of platform access
  In order to protect the operation
  only administrators may review and approve who is allowed to work in PaqueTrack,
  while operators and unauthenticated visitors must be kept out of that function.

  Background:
    Given a member of the logistics operation is using PaqueTrack

  Scenario: An administrator reviews the applicants awaiting approval
    Given an administrator is signed in to PaqueTrack
    When the administrator reviews the registrations awaiting approval
    Then the platform returns the list of applicants awaiting approval

  Scenario: An approved applicant gains operational access
    Given an administrator is signed in to PaqueTrack
    And a new applicant has requested access to the platform
    When the administrator approves the applicant as an operator
    And the approved applicant signs in
    Then the platform grants access including the role "ROLE_OPERATOR"

  Scenario: Reviewing applicants requires an authenticated session
    When an unauthenticated visitor tries to review the registrations awaiting approval
    Then the operation is rejected because authentication is required

  Scenario: An operator is not allowed to administer applicants
    Given an administrator is signed in to PaqueTrack
    And a new applicant has requested access to the platform
    And the administrator approves the applicant as an operator
    And the approved applicant signs in
    When the operator tries to review the registrations awaiting approval
    Then the operation is forbidden for the operator role
