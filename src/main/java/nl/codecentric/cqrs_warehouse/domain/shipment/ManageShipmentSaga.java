package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleOutOfStockEvent;

@Saga
@Slf4j
public class ManageShipmentSaga {

    private String articleId;
    private Integer volume;
    private List<String> claimedContainers;
    private List<String> loadedContainers;
    private String shipmentId;

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentInitialisedEvent event) {
        this.articleId = event.getArticleId().toString();
        this.shipmentId = event.getShipmentId().toString();
        this.volume = event.getVolume();
        this.claimedContainers = new ArrayList<>();
        this.loadedContainers = new ArrayList<>();

        commandGateway.send(new CreateShipmentCommand(event.getShipmentId(), event.getCustomerName(), event.getVolume(), event.getArticleId(), event.getState()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentCreatedEvent event) {
        commandGateway.send(new ClaimContainerCommand(UUID.fromString(articleId), event.getShipmentId()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ContainerClaimedEvent event) {
        this.claimedContainers.add(event.getContainerId().toString());

        if (this.claimedContainers.size() < volume) {
            commandGateway.send(new ClaimContainerCommand(UUID.fromString(articleId), event.getShipmentId()));
        } else {
            commandGateway.send(new ClaimShipmentCommand(event.getShipmentId(), "Containers reserved, ready to load"));
        }
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ArticleOutOfStockEvent event) {
        commandGateway.send(new UnclaimContainerCommand(UUID.fromString(articleId), UUID.fromString(this.claimedContainers.get(0)), UUID.fromString(shipmentId)));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ContainerUnclaimedEvent event) {
        this.claimedContainers.remove(event.getContainerId().toString());
        if (!this.claimedContainers.isEmpty()) {
            commandGateway.send(new UnclaimContainerCommand(UUID.fromString(articleId), UUID.fromString(this.claimedContainers.get(0)), event.getShipmentId()));
        } else {
            commandGateway.send(new CancelShipmentCommand(UUID.fromString(shipmentId), "Failed! We do not have enough stock"));
        }
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ContainerLoadedEvent event) {
        this.loadedContainers.add(event.getContainerId().toString());
        if (this.loadedContainers.size() == volume) {
            commandGateway.send(new ResolveShipmentCommand(UUID.fromString(shipmentId), "Containers loaded, ready to be transported"));
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentDeparturedEvent event) {
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentCanceledEvent event) {}
}
