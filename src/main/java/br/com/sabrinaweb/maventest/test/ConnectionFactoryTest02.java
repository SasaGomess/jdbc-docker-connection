package br.com.sabrinaweb.maventest.test;

import br.com.sabrinaweb.maventest.dominio.Producer;
import br.com.sabrinaweb.maventest.services.ProducerServiceRowSet;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class ConnectionFactoryTest02 {
    public static void main(String[] args) {
        Set<Producer> producers = ProducerServiceRowSet.findByNameJdbcRowSet("Studio");
        log.info("Producers found '{}'", producers);
    }
}
