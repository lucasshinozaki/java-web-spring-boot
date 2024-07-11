package br.com.lucas.screenmatch.main;

import br.com.lucas.screenmatch.model.DataEpisode;
import br.com.lucas.screenmatch.model.DataSeason;
import br.com.lucas.screenmatch.model.DataSeries;
import br.com.lucas.screenmatch.model.Episode;
import br.com.lucas.screenmatch.service.ConsumptionApi;
import br.com.lucas.screenmatch.service.ConvertsData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner reading = new Scanner(System.in);
    private ConsumptionApi consumptionApi = new ConsumptionApi();
    private ConvertsData convertsData = new ConvertsData();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4d7a8f9e";
    public void showMenu() {
        System.out.println("Enter the name of the series ?");
        var nameSeries =  reading.nextLine();
        var json = consumptionApi.getData(ADDRESS + nameSeries.replace(" ", "+") + API_KEY);
        DataSeries data = convertsData.getData(json, DataSeries.class);
        System.out.println(data);

        List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = consumptionApi.getData(ADDRESS + nameSeries.replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = convertsData.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }
        seasons.forEach(System.out::println);

        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

        List<DataEpisode> dataEpisodes = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodes ");
        dataEpisodes
                .stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisode::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.number(), d)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("What year do you want to watch the episodes from ?");
        var year = reading.nextInt();
        reading.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateSearch = LocalDate.of(year, 1, 1);
        episodes.stream()
                .filter(e -> e.getDateRelease() != null && e.getDateRelease().isAfter(dateSearch))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                "  Episode: " + e.getTitle() +
                                "  Date Release: " + e.getDateRelease().format(formatter)
                ));
    }
}
