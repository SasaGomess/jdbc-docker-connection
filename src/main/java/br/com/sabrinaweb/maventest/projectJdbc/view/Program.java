package br.com.sabrinaweb.maventest.projectJdbc.view;

import br.com.sabrinaweb.maventest.projectJdbc.service.ProducerService;

import java.util.Scanner;

public class Program {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;
        do {
            producerMenu();
            op = Integer.parseInt(SCANNER.nextLine());
            ProducerService.menu(op);
        } while (op != 0);
    }

    private static void producerMenu() {
        System.out.println("Type the number of operation");
        System.out.println("1. Search for producer (findAll or findByName)");
        System.out.println("2. Delete producer");
        System.out.println("0. To Exit");
        System.out.print("RESP: ");
    }
}
