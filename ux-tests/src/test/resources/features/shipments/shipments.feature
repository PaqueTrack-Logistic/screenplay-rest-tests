@ui @shipments
Feature: Shipment registration (web)
  In order to move parcels for customers
  a logistics operator must be able to register a shipment from the web panel
  and obtain its tracking number.

  Background:
    Given an operator has signed in to PaqueTrack
    And the operator is in the shipments area

  @smoke
  Scenario: An operator registers a shipment from the web form
    When the operator registers a shipment from "Comercializadora Andina" to "Cliente Final"
    Then the shipment is registered with a tracking number
