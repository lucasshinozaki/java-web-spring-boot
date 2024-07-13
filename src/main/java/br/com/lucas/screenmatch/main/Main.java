package br.com.lucas.screenmatch.main;

import br.com.lucas.screenmatch.model.DataSeason;
import br.com.lucas.screenmatch.model.DataSeries;
import br.com.lucas.screenmatch.model.Series;
import br.com.lucas.screenmatch.service.ConsumptionApi;
import br.com.lucas.screenmatch.service.ConvertsData;

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

    private List<DataSeries> dataSeries = new ArrayList<>();

    public void showMenu() {
        var option = -1;
        while (option != 0 ) {

            var menu = """
                1 - Search series
                2 - Search episodes
                3 - List searched series
                0 - Exit
                """;

            System.out.println(menu);
            option = reading.nextInt();
            reading.nextLine();

            switch (option) {
                case 1:
                    searchWebSeries();
                    break;
                case 2:
                    searchEpisodesBySeries();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 0:
                    System.out.println("Leaving");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void listSearchedSeries() {
        List<Series> series = new ArrayList<>();
        series = dataSeries.stream()
                        .map(d -> new Series(d))
                                .collect(Collectors.toList());
        series.stream()
                        .sorted(Comparator.comparing(Series::getGenre))
                                .forEach(System.out::println);

    }

    private void searchWebSeries() {
        DataSeries data = getDataSerie();
        dataSeries.add(data);
        System.out.println(data);
    }

    private DataSeries getDataSerie() {
        System.out.println("Type the name of the series to search ?");
        var nameSeries = reading.nextLine();
        var json = consumptionApi.getData(ADDRESS + nameSeries.replace(" ", "+") + API_KEY);
        return convertsData.getData(json, DataSeries.class);
    }


    private void searchEpisodesBySeries(){
        DataSeries dataSeries = getDataSerie();
        List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i < dataSeries.totalSeasons(); i++) {
            var json = consumptionApi.getData(ADDRESS + dataSeries.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = convertsData.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }
        seasons.forEach(System.out::println);
    }
}
