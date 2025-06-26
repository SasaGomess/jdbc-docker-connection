package br.com.sabrinaweb.maventest.services;

import br.com.sabrinaweb.maventest.dominio.Producer;
import br.com.sabrinaweb.maventest.repository.ProducerRepositoryRowSet;


import java.util.Set;

public class ProducerServiceRowSet {

    public static Set<Producer> findByNameJdbcRowSet(String name) {
        return ProducerRepositoryRowSet.findByNameJdbcRowSet(name);
    }
}
