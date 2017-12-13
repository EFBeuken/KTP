package model;

/**
 * Created by Emily on 22/11/2017.
 */
public class Person {

    private String name;
    private String gun;
    private String ammunition;
    private String energy;
    private String dog;
    private String driven;

    public Person(String name, String gun, String energy, String ammunition, String dog, String driven) {
        this.name = name;
        this.gun = gun;
        this.ammunition = ammunition;
        this.energy = energy;
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

    public String getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(String ammunition) {
        this.ammunition = ammunition;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getDog() {
        return dog;
    }

    public void setDog(String dog) {
        this.dog = dog;
    }

    public String getDriven() {
        return driven;
    }

    public void setDriven(String driven) {
        this.driven = driven;
    }
}
