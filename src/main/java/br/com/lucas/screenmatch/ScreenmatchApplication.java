package br.com.lucas.screenmatch;

import br.com.lucas.screenmatch.model.DataEpisode;
import br.com.lucas.screenmatch.model.DataSeason;
import br.com.lucas.screenmatch.model.DataSeries;
import br.com.lucas.screenmatch.service.ConsumptionApi;
import br.com.lucas.screenmatch.service.ConvertsData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumptionApi = new ConsumptionApi();
		var json = consumptionApi.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=4d7a8f9e");
		System.out.println(json);
		ConvertsData convertsData = new ConvertsData();
		DataSeries data = convertsData.getData(json, DataSeries.class);
		System.out.println(data);
		json = consumptionApi.getData("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=4d7a8f9e");
		DataEpisode dataEpisode = convertsData.getData(json, DataEpisode.class);
		System.out.println(dataEpisode);

		List<DataSeason> seasons = new ArrayList<>();

		for (int i = 1; i <= data.totalSeasons(); i++) {
			json = consumptionApi.getData("https://www.omdbapi.com/?t=gilmore+girls&season="+i+"&apikey=4d7a8f9e");
			DataSeason dataSeason = convertsData.getData(json, DataSeason.class);
			seasons.add(dataSeason);
		}
		seasons.forEach(System.out::println);
	}
}
