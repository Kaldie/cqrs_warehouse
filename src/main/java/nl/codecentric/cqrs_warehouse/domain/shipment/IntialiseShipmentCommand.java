package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntialiseShipmentCommand {
    @TargetAggregateIdentifier
    private UUID shipmentId;
    private String customer;
    private UUID articleId;
    private Integer volume;
}
