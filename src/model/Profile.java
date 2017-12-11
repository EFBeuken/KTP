package model;

/**
 * Created by Emily on 22/11/2017.
 */
public class Profile {

    String name;
    String gun;
    String calibere;
    Boolean dog;
    Boolean driven;

    public Profile(String name, String gun, String calibere, Boolean dog, Boolean driven) {
        this.name = name;
        this.gun = gun;
        this.calibere = calibere;
        this.dog = dog;
        this.driven = driven;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public String getCalibere() {
        return calibere;
    }

    public void setCalibere(String calibere) {
        this.calibere = calibere;
    }

    public Boolean getDog() {
        return dog;
    }

    public void setDog(Boolean dog) {
        this.dog = dog;
    }

    public Boolean getDriven() {
        return driven;
    }

    public void setDriven(Boolean driven) {
        this.driven = driven;
    }
}
