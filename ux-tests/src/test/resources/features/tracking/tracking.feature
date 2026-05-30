@ui @tracking
Feature: Tracking events (web)
  In order to keep customers informed of where their parcels are
  a logistics operator must be able to report tracking events
  for a shipment from the web panel.

  Background:
    Given an operator has signed in to PaqueTrack
    And the operator is in the shipments area

  Scenario: An operator reports a tracking event from the web
    Given the operator has just registered a shipment from "Transportes Andinos" to "Cliente Final"
    When the operator reports a "DISPATCHED" tracking event for that shipment
    Then the platform confirms the tracking event was registered
