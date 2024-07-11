package br.com.lucas.screenmatch.main;

import br.com.lucas.screenmatch.model.DataSeason;
import br.com.lucas.screenmatch.model.DataSeries;
import br.com.lucas.screenmatch.service.ConsumptionApi;
import br.com.lucas.screenmatch.service.ConvertsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    }
}
