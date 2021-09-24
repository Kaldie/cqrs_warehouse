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

    @Autowired
    private transient CommandGateway commandGateway;

    private String articleId;
    private Integer volume;
    private List<String> claimedContainers;
    private List<String> loadedContainers;
    private String shipmentId;
    
    public void on(ShipmentInitialisedEvent event) {
    }

    public void on(ShipmentCreatedEvent event) {
    }

    public void on(ContainerClaimedEvent event) {
    }

    public void on(ArticleOutOfStockEvent event) {
    }

    public void on(ContainerUnclaimedEvent event) {
    }

    public void on(ContainerLoadedEvent event) {
    }

    public void on(ShipmentDeparturedEvent event) {
    }

    public void on(ShipmentCanceledEvent event) {
    }
}
