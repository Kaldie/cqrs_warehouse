package nl.codecentric.cqrs_warehouse.domain.truck;

import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.codecentric.cqrs_warehouse.domain.container.Container;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Aggregate
public class TruckAggregate {
    @AggregateIdentifier
    private UUID truckId;
    private UUID shipmentId;

    @CommandHandler
    public TruckAggregate(ReportTruckArrivedCommand command) {
        AggregateLifecycle.apply(new TruckArrivedEvent(command.getTruckId(), command.getShipmentId()));
    }

    @EventSourcingHandler
    private void on(TruckArrivedEvent event) {
        this.truckId = event.getTruckId();
        this.shipmentId = event.getShipmentId();
    }

    // @CommandHandler
    // private 
}
