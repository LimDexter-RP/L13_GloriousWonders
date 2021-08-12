package sg.edu.rp.c346.id20039529.gloriouswonders;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Item implements Serializable {

    private int id;
    private String name;
    private String description;
    private String country;
    private int stars;

    public Item(int id, String name, String description, String country, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.country = country;
        this.stars = stars;

    }

    public int getId() {
        return id;
    }

    public Item setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Item setCountry(String country) {
        this.country = country;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Item setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        String starsString = "";
        for(int i = 0; i < stars; i++){
            starsString += "*";
        }
        return name + "\n" + description + " - " + country + "\n" + starsString;

    }
}