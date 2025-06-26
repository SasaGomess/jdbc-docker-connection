package br.com.sabrinaweb.maventest.test;


import br.com.sabrinaweb.maventest.dominio.Producer;
import br.com.sabrinaweb.maventest.services.ProducerService;
import lombok.extern.log4j.Log4j2;


import java.util.Set;

@Log4j2
public class ConnectionFactoryTest {
    public static void main(String[] args) {
        Producer producer = Producer.builder().name("Mad House").build();
        Producer producer1 = Producer.builder().name("Studio Ghibli").build();
        Producer producer2 = Producer.builder().name("Mappa").build();
        Producer producer3 = Producer.builder().name("Studio deen").build();
        Producer producer4 = Producer.builder().name("nhk").build();
        Producer producer5 = Producer.builder().name("Studio Kishimoto").build();

        Producer producerToUpdate = Producer.builder().id(5).name("Studio Danio").build();
        Producer producerToUpdate2 = Producer.builder().id(4).name("Studio Ghibli").build();
        Producer producerToDelete = Producer.builder().id(6).build();

        ProducerService.save(producer);
        ProducerService.save(producer1);
        ProducerService.save(producer2);
        ProducerService.save(producer3);
        ProducerService.save(producer5);
        ProducerService.save(producer5);

        ProducerService.deleteBetween(7, 12);
        ProducerService.delete(producerToDelete.getId());
        ProducerService.update(producerToUpdate);
        ProducerService.update(producerToUpdate2);

        Set<Producer> producers1 = ProducerService.findAll();
        log.info("Producers found '{}'",producers1);

        Set<Producer> producers2 = ProducerService.findByName("Studio");
        log.info("Producers found '{}'",producers2);

        ProducerService.showProducerMetaData();
        ProducerService.showDriverMetaData();
        ProducerService.showTypeScrollWorking();

        Set<Producer> producers3 = ProducerService.findByNameToUpperCase("");
        log.info("Producers found '{}'",producers3);
        Set<Producer> producers4 = ProducerService.findByNameAndInsertWhenNotFound("Bones");
        log.info("Producers found '{}'", producers4);
    }
}
