package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnclaimedEvent;
import nl.codecentric.cqrs_warehouse.domain.container.UnclaimContainerCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleOutOfStockEvent;
import nl.codecentric.cqrs_warehouse.domain.container.ClaimContainerCommand;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerClaimedEvent;

@Saga
public class ManageShipmentSaga {

    private UUID articleId;
    private Integer volume;
    private List<String> containerIds;
    private UUID shipmentId;

    
    @Autowired
    CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentInitialisedEvent event) {
        this.articleId = event.getArticleId();
        this.shipmentId = event.getShipmentId();
        this.volume = event.getVolume();
        this.containerIds = new ArrayList<>();

        commandGateway.send(new CreateShipmentCommand(event.getShipmentId(), event.getCustomerName(), event.getVolume(), event.getArticleId(), event.getState()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentCreatedEvent event) {
        commandGateway.send(new ClaimContainerCommand(articleId, event.getShipmentId()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ContainerClaimedEvent event) {
        this.containerIds.add(event.getContainerId().toString());

        if(this.containerIds.size() < volume){
            commandGateway.send(new ClaimContainerCommand(articleId, event.getShipmentId()));
        } else {
            commandGateway.send(new ClaimShipmentCommand(event.getShipmentId()));
        }
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ArticleOutOfStockEvent event) {
        commandGateway.send(new UnclaimContainerCommand(this.articleId, UUID.fromString(this.containerIds.get(0)), this.shipmentId));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ContainerUnclaimedEvent event) {
        this.containerIds.remove(event.getContainerId().toString());
        if(!this.containerIds.isEmpty()){
            commandGateway.send(new UnclaimContainerCommand(articleId, UUID.fromString(this.containerIds.get(0)), event.getShipmentId()));
        } else {
            commandGateway.send(new ResolveShipmentCommand(this.shipmentId, "failed due to out of stock"));
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentResolvedEvent event) {
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentClaimedEvent event) {
        commandGateway.send(new ResolveShipmentCommand(this.shipmentId, "Shipment ready"));
    }
}
