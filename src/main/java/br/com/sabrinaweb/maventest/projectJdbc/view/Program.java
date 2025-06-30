package br.com.sabrinaweb.maventest.projectJdbc.view;

import br.com.sabrinaweb.maventest.projectJdbc.service.ProducerService;

import java.util.Scanner;

public class Program {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;
        do {
            producerMenu();
            op = Integer.parseInt(scanner.nextLine());
            ProducerService.buiildMenu(op);
        } while (op != 0);
        System.out.println("Exiting...");
    }

    private static void producerMenu() {
        System.out.println("Type the number of operation");
        System.out.println("1. Search for producer (findAll or findByName)");
        System.out.printf("0. To Exit");
    }
}
