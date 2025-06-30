package br.com.sabrinaweb.maventest.JdbcStudies.services;

import br.com.sabrinaweb.maventest.JdbcStudies.dominio.Producer;
import br.com.sabrinaweb.maventest.JdbcStudies.repository.ProducerRepositoryRowSet;


import java.util.Set;

public class ProducerServiceRowSet {

    public static Set<Producer> findByNameJdbcRowSet(String name) {
        return ProducerRepositoryRowSet.findByNameJdbcRowSet(name);
    }

    public static void updateJdbcRowSet(Producer producer) {
        ProducerRepositoryRowSet.updateJdbcRowSet(producer);
    }
    public static void updateCachedRowSet(Producer producer) {
        ProducerRepositoryRowSet.updateCachedRowSet(producer);
    }
}
