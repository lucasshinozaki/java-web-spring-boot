package br.com.lucas.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Episode {
    private Integer season;
    private String title;
    private Integer numberEpisode;
    private Double rating;
    private LocalDate dateRelease;

    public Episode(Integer numberSeason, DataEpisode dataEpisode) {
        this.season = numberSeason;
        this.title = dataEpisode.title();
        this.numberEpisode = dataEpisode.number();
        try {
            this.rating = Double.valueOf(dataEpisode.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }
        try {
            this.dateRelease = LocalDate.parse(dataEpisode.dateRelease());
        } catch (DateTimeException ex) {
            this.dateRelease = null;
        }
    }


    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(LocalDate dateRelease) {
        this.dateRelease = dateRelease;
    }

    @Override
    public String toString() {
        return
                "season=" + season +
                ", title='" + title + '\'' +
                ", numberEpisode=" + numberEpisode +
                ", rating=" + rating +
                ", dateRelease=" + dateRelease;
    }
}
