package  model;


public class Location {

    private String name;
    private int orientation;
    private double longitude;
    private double latitude;
    private Boolean covered;
    private String environments;
    private Boolean hasFood;

    public Location(String name, int orientation, double longitude, double latitude, Boolean covered, String environments, Boolean hasFood, PawPrint[] pawPrints) {
        this.name = name;
        this.orientation = orientation;
        this.longitude = longitude;
        this.latitude = latitude;
        this.covered = covered;
        this.environments = environments;
        this.hasFood = hasFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Boolean getCovered() {
        return covered;
    }

    public void setCovered(Boolean covered) {
        this.covered = covered;
    }

    public String getEnvironments() {
        return environments;
    }

    public void setEnvironments(String environments) {
        this.environments = environments;
    }

    public Boolean getHasFood() {
        return hasFood;
    }

    public void setHasFood(Boolean hasFood) {
        this.hasFood = hasFood;
    }
}
