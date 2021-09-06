package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Value
public class CreateShipmentCommand {
    @TargetAggregateIdentifier
    UUID shipmentId;
    String customerName;
    Integer volume;
    UUID articleId;
    String state;
}
