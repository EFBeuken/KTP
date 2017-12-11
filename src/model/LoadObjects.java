package model;

import sun.java2d.cmm.Profile;

import java.util.List;

/**
 * Created by Emily on 29/11/2017.
 */
public class LoadObjects {

    public List<Animal> animalsList;
    public List<Profile> profileList;

    public LoadObjects(List<Animal> animalsList, List<Profile> profileList) {
        this.animalsList = animalsList;
        this.profileList = profileList;
    }


    public List<Animal> getAnimalsList() {
        return animalsList;
    }

    public void setAnimalsList(List<Animal> animalsList) {
        this.animalsList = animalsList;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }
}

