package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Data;

@Data
public class FetchAllContainersByArticleQuery {
    final String articleId;
}
