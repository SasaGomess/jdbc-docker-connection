package br.com.sabrinaweb.maventest.projectJdbc.service;


import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import br.com.sabrinaweb.maventest.projectJdbc.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;


public class ProducerService {
    private static Scanner sc = new Scanner(System.in);

    public static void buiildMenu(int resp){
        switch (resp){
            case 1 -> findByName();
            default -> throw new IllegalArgumentException("Not valid option");
        }
    }
    private static void findByName(){
        System.out.println("Type a name to find the producer or empty to all");
        String name = sc.nextLine();
        List<Producer> producers = ProducerRepository.findByName(name);
        for (int i = 0; i < producers.size(); i++) {
            System.out.printf("[%d] - %s%n", i, producers.get(i).getName());
        }
    }
}
