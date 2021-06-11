package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateArticleCommand {
    private UUID id;
    private String name;
}
