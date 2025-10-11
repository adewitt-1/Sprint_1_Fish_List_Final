package cis3334.java_sprint_1_fish_list.data.model;

/**
 * Fish Class for the Fish List app. Stores all the information about a fish.
 */
public class Fish {
    private String id;
    private String species;
    private String date;
    private String location;
    private String weather;
    private double weight;
    private double length;
    private float battleRating;

    /**
     * Default Constructor -- Empty for Firebase requirements
      */
    public Fish() {

    }

    /**
     * Constructor for the Fish class.
     * @param id Unique identifier for the fish.
     * @param species Species of the fish.
     * @param date Date of the catch.
     * @param weight Weight of the fish.
     * @param length Length of the fish.
     * @param location Location of the catch.
     * @param weather Weather during the time of catch.
     * @param battleRating How difficult the catch was.
     */
    public Fish(String id, String species, String date, double weight, double length,
                String location, String weather, float battleRating) {
        this.id = id;
        this.species = species;
        this.date = date;
        this.weight = weight;
        this.length = length;
        this.location = location;
        this.weather = weather;
        this.battleRating = battleRating;
    }

    // Setter for Firestore
    public void setId(String id) {
        this.id = id;
    }

    // Getter Methods
    public String getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public String getDate() {
        return date;
    }

    public double getWeight() {
        return weight;
    }

    public double getLength() {
        return length;
    }

    public String getLocation() {
        return location;
    }

    public String getWeather() {
        return weather;
    }

    public float getBattleRating() {
        return battleRating;
    }


}




