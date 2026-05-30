@access
Feature: Access to the PaqueTrack platform
  In order to keep shipment operations in trusted hands
  the logistics company must only let registered and approved staff in,
  and must keep everyone else out.

  Background:
    Given a member of the logistics operation is using PaqueTrack

  @smoke
  Scenario: An administrator signs in and obtains administrative privileges
    When the administrator signs in with the corporate account
    Then the platform grants access including the role "ROLE_ADMIN"

  Scenario: A new applicant requests access and is left awaiting approval
    When a new applicant requests access to the platform
    Then the request is accepted and the account remains awaiting approval

  Scenario: An applicant cannot operate until an administrator approves the account
    Given a new applicant has requested access to the platform
    When the applicant tries to sign in
    Then access is denied because the account is awaiting approval

  Scenario: Sign in is refused when the password does not match
    When a person signs in with a valid corporate email but the wrong password
    Then access is denied because the credentials are invalid

  Scenario: A signed-in user renews the session without re-entering credentials
    When the administrator signs in with the corporate account
    And the user renews the session
    Then the platform grants access including the role "ROLE_ADMIN"

  # Boundary value analysis on the registration password-length policy
  # (minimum required length is 8 characters).
  @boundary
  Scenario Outline: Registration is refused for passwords below the minimum length
    When a person requests access using a password of length <length>
    Then the registration is rejected for not meeting the minimum password length

    Examples:
      | length |
      | 1      |
      | 7      |

  @boundary
  Scenario: Registration is accepted for a password at the minimum length
    When a person requests access using a password of length 8
    Then the registration is accepted and the account is left awaiting approval
