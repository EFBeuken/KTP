package model;


import java.util.List;

/**
 * Created by Emily on 29/11/2017.
 */
public class LoadObjects {

    public List<Animal> animalsList;
    public List<Person> personsList;

    public LoadObjects(List<Animal> animalsList, List<Person> personsList) {
        this.animalsList = animalsList;
        this.personsList = personsList;
    }


    public List<Animal> getAnimalsList() {
        return animalsList;
    }

    public void setAnimalsList(List<Animal> animalsList) {
        this.animalsList = animalsList;
    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }
}

