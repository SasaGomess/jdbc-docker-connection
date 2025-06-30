package br.com.sabrinaweb.maventest.projectJdbc.service;


import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import br.com.sabrinaweb.maventest.projectJdbc.repository.ProducerRepository;

import java.util.Scanner;


public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int resp){
        switch (resp){
            case 0 -> System.out.println("Exiting...");
            case 1 -> findByName();
            case 2 -> deleteById();
            case 3 -> save();
            case 4 -> update();
            default -> throw new IllegalArgumentException("Not valid option");
        }
    }
    private static void findByName(){
        System.out.println("Type a name to find the producer or empty to all");
        String name = SCANNER.nextLine();
        ProducerRepository
                .findByName(name)
                .forEach(p -> System.out.printf("ID:[%d] - %s%n", p.getId(), p.getName()));
    }
    private static void findAll(){
        String name = "";
        ProducerRepository
                .findByName(name)
                .forEach(p -> System.out.printf("ID:[%d] - %s%n", p.getId(), p.getName()));
    }
    private static void deleteById(){
        System.out.println("First choose a producer: ");
        findAll();
        System.out.println("Type the producer id you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? [Y/N]");
        String answer = SCANNER.nextLine();
        if(answer.equalsIgnoreCase("y")) ProducerRepository.delete(id);
    }
    private static void save(){
        System.out.println("Type the name to save the producer");
        String name = SCANNER.nextLine();
        Producer producer = Producer.builder().name(name).build();
        ProducerRepository.save(producer);
    }
    private static void update(){
        try {
            System.out.println("Type the id of the object you to update");
            findAll();
            Producer producerFromDb = ProducerRepository.findById(Integer.parseInt(SCANNER.nextLine())).orElseThrow(IllegalArgumentException::new);
            System.out.println("Producer found: " + producerFromDb);
            System.out.println("Type the new name or enter to keep the same");
            String name = SCANNER.nextLine();
            name = name.isEmpty() ? producerFromDb.getName() : name;

            Producer producerToUpdate = Producer
                    .builder()
                    .id(producerFromDb.getId())
                    .name(name)
                    .build();
            ProducerRepository.update(producerToUpdate);
        }catch (IllegalArgumentException e){
            System.out.println("Producer not found");
        }
    }
}
