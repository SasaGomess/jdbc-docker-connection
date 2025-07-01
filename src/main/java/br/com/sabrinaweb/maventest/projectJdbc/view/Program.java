package br.com.sabrinaweb.maventest.projectJdbc.view;

import br.com.sabrinaweb.maventest.projectJdbc.service.AnimeService;
import br.com.sabrinaweb.maventest.projectJdbc.service.ProducerService;

import java.util.Scanner;

public class Program {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;
        do {
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            switch (op) {
                case 1 -> {
                    producerMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    ProducerService.menu(op);
                }
                case 2 -> {
                    animeMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    AnimeService.menu(op);
                }
            }

        } while (op != 0);
    }

    private static void producerMenu() {
        System.out.println("Type the number of operation");
        System.out.println("1. Search for anime (findAll or findByName)");
        System.out.println("2. Delete anime");
        System.out.println("3. Save anime");
        System.out.println("4. Update anime");
        System.out.println("9. Go Back");
        System.out.print("RESP: ");
    }

    private static void menu() {
        System.out.println("1. Producer");
        System.out.println("2. Anime");
        System.out.println("0. Exit");
    }

    private static void animeMenu() {
        System.out.println("Type the number of operation");
        System.out.println("1. Search for anime (findAll or findByName)");
        System.out.println("2. Delete anime");
        System.out.println("3. Save anime");
        System.out.println("4. Update anime");
        System.out.println("9. Go Back");
        System.out.print("RESP: ");
    }

}
