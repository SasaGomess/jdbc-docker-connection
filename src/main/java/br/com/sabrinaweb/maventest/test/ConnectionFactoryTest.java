package br.com.sabrinaweb.maventest.test;

import br.com.sabrinaweb.maventest.dominio.Producer;
import br.com.sabrinaweb.maventest.repository.ConnectionFactory;
import br.com.sabrinaweb.maventest.services.ProducerService;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class ConnectionFactoryTest {
    public static void main(String[] args) {
        Producer producer = Producer.builder().name("Kyoto").build();
        Producer producerToUpdate = Producer.builder().id(5).name("Studio Danio").build();
        Producer producerToUpdate2 = Producer.builder().id(4).name("Studio Ghibli").build();
        Producer producerToDelete = Producer.builder().id(6).build();

        ProducerService.save(producer);
        ProducerService.deleteBetween(7, 9);
        ProducerService.delete(producerToDelete.getId());
        ProducerService.update(producerToUpdate);
        ProducerService.update(producerToUpdate2);

        Set<Producer> producers = ProducerService.findAll();
        log.info("Producers found '{}'",producers);

        Set<Producer> producers2 = ProducerService.findByName("Studio");
        log.info("Producers found '{}'",producers2);

        ProducerService.showProducerMetaData();
        ProducerService.showDriverMetaData();
        ProducerService.showTypeScrollWorking();

        Set<Producer> producers4 = ProducerService.findByNameToUpperCase(producerToUpdate2.getName());
        log.info("Producers found '{}'",producers4);

    }
}
