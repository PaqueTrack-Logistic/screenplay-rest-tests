@lifecycle @e2e
Feature: Delivery lifecycle
  In order to give an accurate status to whoever is waiting for a parcel
  a shipment must advance through its delivery lifecycle
  CREATED -> IN_TRANSIT -> OUT_FOR_DELIVERY -> DELIVERED
  as the courier reports each milestone.

  Background:
    Given a logistics operator is signed in to PaqueTrack

  Scenario: A shipment advances through its full delivery lifecycle
    Given a shipment has been registered from "Exportadora del Valle"
    And the shipment starts in state "CREATED"
    When the courier reports the "DISPATCHED" event for the shipment
    Then the delivery state eventually becomes "IN_TRANSIT"
    When the courier reports the "OUT_FOR_DELIVERY" event for the shipment
    Then the delivery state eventually becomes "OUT_FOR_DELIVERY"
    When the courier reports the "DELIVERED" event for the shipment
    Then the delivery state eventually becomes "DELIVERED"
