package br.com.sabrinaweb.maventest.test;


import br.com.sabrinaweb.maventest.dominio.Producer;
import br.com.sabrinaweb.maventest.services.ProducerService;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class ConnectionFactoryTest03 {
    public static void main(String[] args) {
        Producer producer1 = Producer.builder().name("Toei Animation").build();
        Producer producer2 = Producer.builder().name("White Fox").build();
        Producer producer3 = Producer.builder().name("A-1 Pictures").build();
        Set<Producer> producers = new TreeSet<>(Comparator.comparing(Producer::getName));
        producers.add(producer1);
        producers.add(producer2);
        producers.add(producer3);
        ProducerService.saveTransaction(producers);
    }
}
