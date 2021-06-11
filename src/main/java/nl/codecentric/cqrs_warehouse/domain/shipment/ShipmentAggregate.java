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
    private UUID articleId;
    private String state;

    @CommandHandler
    public ShipmentAggregate(CreateShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentCreatedEvent(
            command.getShipmentId(),
            command.getCustomerName(),
            command.getVolume(),
            command.getArticleId()
        ));
    }

    @EventSourcingHandler
    public void on(ShipmentCreatedEvent event) {
        this.shipmentId = event.getShipmentId();
        this.containers = new ArrayList<>();
        this.customerName = event.getCustomerName();
        this.volume = event.getVolume();
        this.articleId = event.getArticleId();
        this.state="open";
    }

    @CommandHandler
    public void handle(IntialiseShipmentCommand command)  {
        AggregateLifecycle.apply(new ShipmentInitialisedEvent(
            command.getShipmentId(),
            command.getCustomer(),
            command.getArticleId(),
            command.getVolume()
        ));
    }

    @CommandHandler
    public void handle(ResolveShipmentCommand command) {
        AggregateLifecycle.apply(new ShipmentResolvedEvent(command.getShipmentId(), command.getState()));
    }

    @EventSourcingHandler
    public void on(ShipmentResolvedEvent event) {
        this.state = event.getState();
    }
}
