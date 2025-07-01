package br.com.sabrinaweb.maventest.projectJdbc.service;


import br.com.sabrinaweb.maventest.projectJdbc.domain.Anime;
import br.com.sabrinaweb.maventest.projectJdbc.domain.Producer;
import br.com.sabrinaweb.maventest.projectJdbc.repository.AnimeRepository;

import java.util.Scanner;


public class AnimeService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void menu(int resp) {
        switch (resp) {
            case 9 -> System.out.println("Going back...");
            case 1 -> findByName();
            case 2 -> deleteById();
            case 3 -> save();
            case 4 -> update();
            default -> throw new IllegalArgumentException("Not valid option");
        }
    }

    private static void findByName() {
        System.out.println("Type a name to find the anime or empty to all");
        String name = SCANNER.nextLine();
        AnimeRepository
                .findByName(name)
                .forEach(a -> System.out.printf("ID:[%d] - %s episodes: %d id_producer: %d%n", a.getId(), a.getName(), a.getEpisodes(), a.getProducer().getId()));
    }

    private static void findAll() {
        String name = "";
        AnimeRepository
                .findByName(name)
                .forEach(a -> System.out.printf("ID:[%d] - %s episodes: %d id_producer: %d%n", a.getId(), a.getName(), a.getEpisodes(), a.getProducer().getId()));
    }

    private static void deleteById() {
        System.out.println("First choose a anime: ");
        findAll();
        System.out.println("Type the anime id you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? [Y/N]");
        String answer = SCANNER.nextLine();
        if (answer.equalsIgnoreCase("y")) AnimeRepository.delete(id);
    }

    private static void save() {
        System.out.println("Type the name to save the anime");
        String name = SCANNER.nextLine();
        System.out.println("Type the number of the episodes");
        int episodes = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Type the id of the producer");
        Integer idProducer = Integer.parseInt(SCANNER.nextLine());
        Anime anime = Anime.builder().name(name).episodes(episodes).producer(Producer.builder().id(idProducer).build()).build();
        AnimeRepository.save(anime);
    }
}
