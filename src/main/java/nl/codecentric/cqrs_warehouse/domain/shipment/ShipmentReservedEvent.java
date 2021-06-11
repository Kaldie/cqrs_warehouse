package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Data;

@Data
public class ShipmentReservedEvent {
    private final UUID shipmentId;
}
