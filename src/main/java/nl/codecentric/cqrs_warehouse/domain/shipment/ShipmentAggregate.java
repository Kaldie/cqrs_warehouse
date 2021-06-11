package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Aggregate
@NoArgsConstructor
public class ShipmentAggregate {
    @AggregateIdentifier
    private UUID shipmentId;
    private List<UUID> containers;
    private String customerName;
    private Integer volume;
    private String contentName;

    @CommandHandler
    public ShipmentAggregate(CreateShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentCreatedEvent(
            command.getShipmentId(),
            command.getCustomerName(),
            command.getVolume(),
            command.getContentName()
        ));
    }

    public void handle(ReserveShipmentCommand command) {
        if ()
    }

    @EventSourcingHandler
    public void on(ShipmentCreatedEvent event) {
        this.shipmentId = event.getShipmentId();
        this.containers = new ArrayList<>();
        this.customerName = event.getCustomerName();
        this.volume = event.getVolume();
        this.contentName = event.getContentName();
    }


    @CommandHandler
    public void handle(ReserveShipmentCommand command) {
    }
}
