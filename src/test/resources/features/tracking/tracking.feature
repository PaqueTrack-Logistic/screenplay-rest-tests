@tracking
Feature: Tracking events
  In order to keep customers informed of where their parcels are
  the platform must publish which tracking events it understands
  and reject any event that is not part of that catalog.

  Background:
    Given a logistics operator is signed in to PaqueTrack

  Scenario: The platform publishes the catalog of tracking events it understands
    When the operator consults the catalog of tracking events
    Then the catalog lists the supported tracking events

  Scenario: An unsupported tracking event is refused
    Given a shipment has been registered from "Logistica Pacifico"
    When the operator reports an unsupported "TELEPORTED" tracking event
    Then the tracking event is rejected as unsupported
