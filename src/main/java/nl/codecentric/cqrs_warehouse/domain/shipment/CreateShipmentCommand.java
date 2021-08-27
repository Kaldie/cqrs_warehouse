package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CreateShipmentCommand {
    @TargetAggregateIdentifier
    private final UUID shipmentId;
    private final String customerName;
    private final Integer volume;
    private final UUID articleId;
    private final String state;
}
