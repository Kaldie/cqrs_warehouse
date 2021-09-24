package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateArticleCommand {
    private UUID id;
    private String name;
}
