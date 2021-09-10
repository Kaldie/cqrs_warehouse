package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartureShipmentCommand {
    @TargetAggregateIdentifier
    private UUID shipment;
}
