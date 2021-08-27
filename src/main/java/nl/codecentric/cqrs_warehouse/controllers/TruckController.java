package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.truck.ReportTruckArrivedCommand;
import nl.codecentric.cqrs_warehouse.domain.truck.ReportTruckDepartedCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
public class TruckController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping(path = "/trucks/arrive")
    public UUID create(ReportTruckArrivedCommand command) {
        commandGateway.sendAndWait(command);
        return command.getTruckId();
    }

    @PostMapping(path = "/trucks/depart")
    public UUID create(ReportTruckDepartedCommand command) {
        commandGateway.sendAndWait(command);
        return command.getTruckId();
    }
}
