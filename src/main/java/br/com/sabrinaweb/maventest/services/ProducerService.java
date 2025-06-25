package br.com.sabrinaweb.maventest.services;

import br.com.sabrinaweb.maventest.dominio.Producer;
import br.com.sabrinaweb.maventest.repository.ProducerRepository;

import java.util.Set;

public class ProducerService {
    public static void save(Producer producer){
        ProducerRepository.save(producer);
    }
    public static void deleteBetween(Integer firstId, Integer lastId){
        requireValidId(firstId);
        requireValidId(lastId);
        ProducerRepository.deleteBetween(firstId, lastId);
    }
    public static void delete(Integer id){
        requireValidId(id);
        ProducerRepository.delete(id);
    }
    public static void update(Producer producer){
        ProducerRepository.update(producer);
    }
    public Set<Producer> findAll(){

        return ProducerRepository.findAll();
    }
    public static Set<Producer> findByName(String name){
        return ProducerRepository.findByName(name);
    }
    private static void requireValidId(Integer id){
        if (id == null || id <= 0 ){
            throw new IllegalArgumentException("Error the id which has been passed is invalid. Try again!");
        }
    }
}
