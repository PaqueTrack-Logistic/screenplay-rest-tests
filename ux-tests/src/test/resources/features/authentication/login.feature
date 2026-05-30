@ui @authentication
Feature: Access to the PaqueTrack control panel (web)
  In order to operate the logistics platform from the browser
  an authorized member of the operation must be able to sign in
  through the web panel, and be kept out when credentials are wrong.

  Background:
    Given the operator is on the PaqueTrack login page

  @smoke
  Scenario: An administrator signs in through the web panel
    When the administrator signs in with valid corporate credentials
    Then the control panel shows the session for "admin@logistics.com"

  Scenario: Sign in is refused with the wrong password
    When a person signs in with an incorrect password
    Then access is refused with a message on screen

  Scenario: A signed-in user can log out of the panel
    When the administrator signs in with valid corporate credentials
    And the user logs out
    Then the platform returns to the login page
