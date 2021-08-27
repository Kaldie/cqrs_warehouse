package nl.codecentric.cqrs_warehouse.domain.truck;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;


@Data
public class ReportTruckArrivedCommand {
    @TargetAggregateIdentifier
    private final UUID truckId;
    private final UUID shipmentId;

}
