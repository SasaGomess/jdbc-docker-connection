package br.com.sabrinaweb.maventest.JdbcStudies.services;

import br.com.sabrinaweb.maventest.JdbcStudies.dominio.Producer;
import br.com.sabrinaweb.maventest.JdbcStudies.repository.ProducerRepository;
import br.com.sabrinaweb.maventest.JdbcStudies.services.exceptions.InvalidIdException;

import java.util.Set;

public class ProducerService {
    public static void save(Producer producer){
        ProducerRepository.save(producer);
    }
    public static void saveTransaction(Set<Producer> producers) {
        ProducerRepository.saveTransaction(producers);
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
    public static Set<Producer> findAll(){

        return ProducerRepository.findAll();
    }
    public static Set<Producer> findByName(String name){
        return ProducerRepository.findByName(name);
    }

    private static void requireValidId(Integer id) {
        if (id == null || id <= 0 ){
            throw new InvalidIdException("Error the id which has been passed is invalid. Try again!");
        }
    }
    public static void showProducerMetaData(){
        ProducerRepository.showProducerMetaData();
    }
    public static void showDriverMetaData(){
        ProducerRepository.showDriverMetaData();
    }
    public static void showTypeScrollWorking(){
        ProducerRepository.showTypeScrollWorking();
    }
    public static Set<Producer> findByNameToUpperCase(String name){
        return ProducerRepository.findByNameAndUpdateToUpperCase(name);
    }
    public static Set<Producer> findByNameAndInsertWhenNotFound(String name){
        return ProducerRepository.findByNameAndInsertWhenNotFound(name);
    }
    public static void findByNameAndDelete(String name){
        ProducerRepository.findByNameAndDelete(name);
    }
    public static Set<Producer> findByNamePreparedStatement(String name) {
       return ProducerRepository.findByNamePreparedStatement(name);
    }
    public static void updatePreparedStatement(Producer producer){
        ProducerRepository.updatePreparedStatement(producer);
    }

    public static Set<Producer> findByNameCallableStatement(String name){
        return ProducerRepository.findByNameCallableStatement(name);
    }

}
