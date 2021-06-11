package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
class ReserveShipmentCommand {
    @TargetAggregateIdentifier
    private final UUID shipmentId;
}
