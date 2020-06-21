package com.firstest.projetandroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GMovies implements Parcelable {

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


    protected GMovies(Parcel in) {
        title = in.readString();
        description = in.readString();
        director = in.readString();
        producer = in.readString();
        release_date = in.readInt();
        rt_score = in.readInt();
        people = in.createStringArrayList();
        species = in.createStringArrayList();
        locations = in.createStringArrayList();
        vehicles = in.createStringArrayList();
        url = in.readString();
    }

    public static final Creator<GMovies> CREATOR = new Creator<GMovies>() {
        @Override
        public GMovies createFromParcel(Parcel in) {
            return new GMovies(in);
        }

        @Override
        public GMovies[] newArray(int size) {
            return new GMovies[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(director);
        dest.writeString(producer);
        dest.writeInt(release_date);
        dest.writeInt(rt_score);
        dest.writeStringList(people);
        dest.writeStringList(species);
        dest.writeStringList(locations);
        dest.writeStringList(vehicles);
        dest.writeString(url);
    }
}
