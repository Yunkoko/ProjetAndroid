package com.firstest.projetandroid;

import java.util.List;

public class GMovies {

    private String title;
    private String description;
    private String director;
    private String producer;
    private int release_date;
    private int rt_score;
    private List<String> people;
    private List<String> species;
    private List<String> locations;
    private List<String> vehicles;
    private String url;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public int getRelease_date() {
        return release_date;
    }

    public int getRt_score() {
        return rt_score;
    }

    public List<String> getPeople() {
        return people;
    }

    public List<String> getSpecies() {
        return species;
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public String getUrl() {
        return url;
    }
}
