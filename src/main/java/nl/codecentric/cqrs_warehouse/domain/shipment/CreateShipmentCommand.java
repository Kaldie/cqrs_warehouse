package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Value
@Builder
public class CreateShipmentCommand {
    UUID shipmentId;
    String customerName;
    Integer volume;
    UUID articleId;
}
