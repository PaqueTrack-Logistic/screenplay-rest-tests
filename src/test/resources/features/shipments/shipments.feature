@shipments
Feature: Shipment registration and consultation
  In order to move parcels across the country
  the logistics operator must be able to register shipments, find them again,
  and give supervisors a consolidated view of the operation.

  Background:
    Given a logistics operator is signed in to PaqueTrack

  @smoke
  Scenario: An operator registers a new shipment
    When the operator registers a shipment from "Comercializadora Andina" in "Medellin" to "Cliente Final" in "Bogota"
    Then the shipment is registered with a unique tracking number
    And the shipment starts in state "CREATED"

  Scenario: An operator locates a shipment by its tracking number
    Given a shipment has been registered from "Distribuidora Norte"
    When the operator looks up the shipment by its tracking number
    Then the shipment is found by its tracking number

  Scenario: An operator locates a shipment by its identifier
    Given a shipment has been registered from "Distribuidora Sur"
    When the operator looks up the shipment by its identifier
    Then the shipment is found by its tracking number

  Scenario: An operator finds the shipments dispatched by a sender
    Given a shipment has been registered from "Comercializadora Andina"
    When the operator searches shipments sent by "Comercializadora Andina"
    Then at least one shipment is returned for that sender

  Scenario: An operator finds the shipments addressed to a recipient
    Given a shipment has been registered from "Importadora Caribe" to "Almacenes Union"
    When the operator searches shipments addressed to "Almacenes Union"
    Then the search returns shipments addressed to that recipient

  Scenario: A search combining sender and recipient is not allowed
    When the operator searches using both a sender "Comercializadora Andina" and a recipient "Almacenes Union"
    Then the search is rejected as an invalid query

  Scenario: A search without any criterion is not allowed
    When the operator searches without providing any criterion
    Then the search is rejected as an invalid query

  Scenario: A supervisor obtains the shipments report broken down by state
    Given a shipment has been registered from "Operador Central"
    When the supervisor requests the shipments report for the current year
    Then the report breaks down the shipments by delivery state

  Scenario: The report is refused when the date range is inverted
    When the supervisor requests the shipments report from "2026-12-31" to "2026-01-01"
    Then the report request is rejected as an invalid date range

  Scenario: The shipment status history records its progression
    Given a shipment has been registered from "Auditoria Andina"
    When the courier reports the "DISPATCHED" event for the shipment
    Then the delivery state eventually becomes "IN_TRANSIT"
    When the operator consults the shipment status history
    Then the shipment history records the change to "IN_TRANSIT"
