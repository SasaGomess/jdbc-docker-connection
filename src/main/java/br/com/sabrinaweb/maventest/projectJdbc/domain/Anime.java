package br.com.sabrinaweb.maventest.projectJdbc.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Anime {
    Integer id;
    String name;
    Integer episodes;
    Producer producer;
}
