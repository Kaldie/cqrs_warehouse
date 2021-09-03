package nl.codecentric.cqrs_warehouse.domain.shipment;

import java.util.UUID;

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
    private Integer numberOfClaimedContainers;
    private UUID shipmentId;

    
    @Autowired
    CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentInitialisedEvent event) {
        this.articleId = event.getArticleId();
        this.shipmentId = event.getShipmentId();
        this.volume = event.getVolume();
        this.numberOfClaimedContainers = 0;

        commandGateway.send(new CreateShipmentCommand(event.getShipmentId(), event.getCustomerName(), event.getVolume(), event.getArticleId(), event.getState()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentCreatedEvent event) {
        commandGateway.send(new ClaimContainerCommand(articleId, event.getShipmentId()));
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ContainerClaimedEvent event) {
        this.numberOfClaimedContainers++;

        if(numberOfClaimedContainers < volume){
            commandGateway.send(new ClaimContainerCommand(articleId, event.getShipmentId()));
        } else {
            commandGateway.send(new ClaimShipmentCommand(event.getShipmentId()));
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ArticleOutOfStockEvent event) {
        commandGateway.send(new ResolveShipmentCommand(this.shipmentId, "failed due to out of stock"));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "shipmentId")
    public void on(ShipmentClaimedEvent event) {
        commandGateway.send(new ResolveShipmentCommand(this.shipmentId, "resolved"));
    }
}
