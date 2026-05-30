@ui @authentication
Feature: Access to the PaqueTrack control panel (web)
  In order to operate the logistics platform from the browser
  an authorized member of the operation must be able to sign in
  through the web panel and reach their workspace.

  Background:
    Given the operator is on the PaqueTrack login page

  @smoke
  Scenario: An administrator signs in through the web panel
    When the administrator signs in with valid corporate credentials
    Then the control panel shows the session for "admin@logistics.com"
