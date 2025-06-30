package br.com.sabrinaweb.maventest.JdbcStudies.test;

import br.com.sabrinaweb.maventest.JdbcStudies.dominio.Producer;
import br.com.sabrinaweb.maventest.JdbcStudies.services.ProducerServiceRowSet;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConnectionFactoryTest02 {
    public static void main(String[] args) {
        Producer producer = Producer.builder().id(1).name("MADHOUSE").build();
        ProducerServiceRowSet.updateCachedRowSet(producer);
        log.info("--------------------------------------------");
//        Set<Producer> producers = ProducerServiceRowSet.findByNameJdbcRowSet("");
//        log.info("Producers found '{}'", producers);


    }
}
