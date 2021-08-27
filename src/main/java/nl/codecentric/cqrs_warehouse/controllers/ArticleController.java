package nl.codecentric.cqrs_warehouse.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class ArticleController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping(path = "/articles/create")
    public UUID createArticle(@RequestBody CreateArticleCommand command) {
        commandGateway.sendAndWait(command);
        return command.getId();
    }

    @PostMapping(path = "/articles/unload-container")
    public UUID unloadContainer(@RequestBody UnloadContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }

    @PostMapping(path = "/articles/load-container")
    public UUID unloadContainer(@RequestBody LoadContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }

    @PostMapping(path = "/articles/move-container")
    public UUID moveContainer(@RequestBody MoveContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }

    @PostMapping(path = "/articles/claim-container")
    public UUID moveContainer(@RequestBody ClaimContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getShipmentId();
    }

    @PostMapping(path = "/articles/expire-container")
    public UUID moveContainer(@RequestBody ExpireContainerCommand command) {
        commandGateway.sendAndWait(command);
        return command.getContainerId();
    }
}
