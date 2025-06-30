package br.com.sabrinaweb.maventest.projectJdbc.service;


import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import br.com.sabrinaweb.maventest.projectJdbc.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;


public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int resp){
        switch (resp){
            case 1 -> findByName();
            case 2 -> deleteById();
            default -> throw new IllegalArgumentException("Not valid option");
        }
    }
    private static void findByName(){
        System.out.println("Type a name to find the producer or empty to all");
        String name = SCANNER.nextLine();
        List<Producer> producers = ProducerRepository.findByName(name);
        for (int i = 0; i < producers.size(); i++) {
            System.out.printf("[%d] - ID: %d %s%n",i, producers.get(i).getId(), producers.get(i).getName());
        }
    }
    private static void findAll(){
        String name = "";
        List<Producer> producers = ProducerRepository.findByName(name);
        for (int i = 0; i < producers.size(); i++) {
            System.out.printf("[%d] - ID: %d %s%n",i, producers.get(i).getId(), producers.get(i).getName());
        }
    }
    private static void deleteById(){
        System.out.println("First choose a producer: ");
        findAll();
        System.out.println("Type the producer id you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? [Y/N]");
        String answer = SCANNER.nextLine();
        if(answer.equalsIgnoreCase("Y")) ProducerRepository.delete(id);
    }
}
