package nl.codecentric.cqrs_warehouse.domain.truck;
import java.util.List;
import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;


@Data
public class ReportTruckArrivedCommand {
    @TargetAggregateIdentifier
    private final UUID truckId;
    private final UUID shipmentId;

}
