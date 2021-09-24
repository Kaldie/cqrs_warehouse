package nl.codecentric.cqrs_warehouse.projections;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnloadedEvent;
import nl.codecentric.cqrs_warehouse.domain.shipment.*;
import nl.codecentric.cqrs_warehouse.repositories.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ProcessingGroup("ShipmentProjection")
@Slf4j
public class ShipmentProjection {

}
