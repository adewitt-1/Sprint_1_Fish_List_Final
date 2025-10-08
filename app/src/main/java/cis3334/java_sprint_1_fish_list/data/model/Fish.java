package cis3334.java_sprint_1_fish_list.data.model;

public class Fish {
    private String id;
    private String species;
    private String date;
    private String location;
    private String weather;
    private double weight;
    private double length;
    private float battleRating;

    // Empty Constructor is needed for Firestore:
    public Fish() {

    }

    // Primary Constructor
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




