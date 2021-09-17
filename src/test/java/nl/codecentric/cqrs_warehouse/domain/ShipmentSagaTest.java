package nl.codecentric.cqrs_warehouse.domain;

import nl.codecentric.cqrs_warehouse.domain.container.ClaimContainerCommand;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerClaimedEvent;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerLoadedEvent;
import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;


public class ShipmentSagaTest {
    private static final String SHIPMENT_ID = UUID.randomUUID().toString();
    private static final UUID ARTICLE_ID = UUID.randomUUID();
    private static final String CUSTOMER_NAME = "Jan Danoontje";
    private static final int VOLUME = 3;
    private static final String SHIPMENT_INITIALISED_STATE = "Start Customer Request";
    private static final String CLAIM_SHIPMENT_STATE = "Containers reserved, ready to load";
    private static final String RESOLVE_SHIPMENT_STATE = "Containers loaded, ready to be transported";
    public static final UUID CONTAINER_ID1 = UUID.randomUUID();
    public static final UUID CONTAINER_ID2 = UUID.randomUUID();
    public static final UUID CONTAINER_ID3 = UUID.randomUUID();

    private SagaTestFixture<ManageShipmentSaga> fixture;

    @BeforeEach
    public void setup() {
        fixture = new SagaTestFixture<>(ManageShipmentSaga.class);
    }

    @Test
    public void shipmentInitialised() {
        fixture.givenAggregate(SHIPMENT_ID).published().
                whenAggregate(SHIPMENT_ID).publishes(shipmentInitialisedEvent())
                .expectActiveSagas(1)
                .expectDispatchedCommands(CreateShipmentCommand.builder()
                        .shipmentId(UUID.fromString(SHIPMENT_ID))
                        .articleId(ARTICLE_ID)
                        .customerName(CUSTOMER_NAME)
                        .volume(VOLUME)
                        .state(SHIPMENT_INITIALISED_STATE)
                        .build());
    }

    @Test
    public void shipmentCreated() {
        fixture.givenAggregate(SHIPMENT_ID)
                .published(shipmentInitialisedEvent())
                .whenAggregate(SHIPMENT_ID)
                .publishes(shipmentCreatedEvent())
                .expectActiveSagas(1)
                .expectDispatchedCommands(ClaimContainerCommand.builder()
                        .shipmentId(UUID.fromString(SHIPMENT_ID))
                        .articleId(ARTICLE_ID)
                        .build());

    }

    @Test
    public void containerClaimed() {
        fixture.givenAggregate(SHIPMENT_ID)
                .published(shipmentInitialisedEvent(), shipmentCreatedEvent())
                .whenAggregate(SHIPMENT_ID).publishes(containerClaimedEvent(CONTAINER_ID1))
                .expectActiveSagas(1)
                .expectDispatchedCommands(ClaimContainerCommand.builder()
                        .shipmentId(UUID.fromString(SHIPMENT_ID))
                        .articleId(ARTICLE_ID)
                        .build());
    }

    @Test
    public void shipmentClaimedWhenAllContainersClaimed() {
        fixture.givenAggregate(SHIPMENT_ID)
                .published(shipmentInitialisedEvent(), shipmentCreatedEvent(), containerClaimedEvent(CONTAINER_ID1), containerClaimedEvent(CONTAINER_ID2))
                .whenAggregate(SHIPMENT_ID).publishes(containerClaimedEvent(CONTAINER_ID3))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new ClaimShipmentCommand(UUID.fromString(SHIPMENT_ID), CLAIM_SHIPMENT_STATE));
    }

    @Test
    public void containerLoaded() {
        fixture.givenAggregate(SHIPMENT_ID)
                .published(shipmentInitialisedEvent(), shipmentCreatedEvent())
                .whenAggregate(SHIPMENT_ID).publishes(containerLoadedEvent(CONTAINER_ID1))
                .expectActiveSagas(1)
                .expectNoDispatchedCommands();
    }

    @Test
    public void shipmentResolved() {
        fixture.givenAggregate(SHIPMENT_ID)
                .published(shipmentInitialisedEvent(), shipmentCreatedEvent(), containerLoadedEvent(CONTAINER_ID1), containerLoadedEvent(CONTAINER_ID2))
                .whenAggregate(SHIPMENT_ID).publishes(containerLoadedEvent(CONTAINER_ID3))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new ResolveShipmentCommand(UUID.fromString(SHIPMENT_ID), RESOLVE_SHIPMENT_STATE));
    }

    @Test
    public void shipmentDeparted() {
        fixture.givenAggregate(SHIPMENT_ID)
                .published(shipmentInitialisedEvent(), shipmentCreatedEvent())
                .whenAggregate(SHIPMENT_ID).publishes(shipmentDeparturedEvent())
                .expectActiveSagas(0);
    }

    private ShipmentInitialisedEvent shipmentInitialisedEvent() {
        return new ShipmentInitialisedEvent(UUID.fromString(SHIPMENT_ID), CUSTOMER_NAME, ARTICLE_ID, VOLUME, SHIPMENT_INITIALISED_STATE);
    }

    private ShipmentCreatedEvent shipmentCreatedEvent() {
        return new ShipmentCreatedEvent(UUID.fromString(SHIPMENT_ID), CUSTOMER_NAME, VOLUME, ARTICLE_ID, SHIPMENT_INITIALISED_STATE);
    }

    private ContainerClaimedEvent containerClaimedEvent(UUID containerId) {
        return new ContainerClaimedEvent(ARTICLE_ID, containerId, UUID.fromString(SHIPMENT_ID));
    }

    private ContainerLoadedEvent containerLoadedEvent(UUID containerId) {
        return new ContainerLoadedEvent(containerId, UUID.fromString(SHIPMENT_ID));
    }

    private ShipmentDeparturedEvent shipmentDeparturedEvent() {
        return new ShipmentDeparturedEvent(UUID.fromString(SHIPMENT_ID));
    }
}
