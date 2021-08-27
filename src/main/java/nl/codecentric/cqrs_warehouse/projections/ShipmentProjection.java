package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.shipment.ShipmentCreatedEvent;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentDTO;
import nl.codecentric.cqrs_warehouse.repositories.ShipmentRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("ShipmentProjection")
public class ShipmentProjection {

    @Autowired
    ShipmentRepository shipmentRepository;

    @EventHandler
    public void on(ShipmentCreatedEvent event) {
        shipmentRepository.save(new ShipmentDTO(
                event.getShipmentId().toString(),
                event.getCustomerName(),
                event.getVolume(),
                event.getArticleId(),
                event.getState()));
    }
}
