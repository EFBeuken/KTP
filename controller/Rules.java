package controller;

import model.Animal;
import model.Person;
import model.Weather;
import view.Interface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Emily on 23/12/2017.
 */
public class Rules {

    private HuntingControl control;
    private List<String> weatherAdvice;
    private List<String> animalAdvice;

    public Rules(HuntingControl control){
        this.control = control;
        this.weatherAdvice = weatherRules();
    }

    public List<String> weatherRules(){
        Weather weather = control.current;
        List<String> advice = new ArrayList<>();
        //Wind Speed
        if (Float.parseFloat(weather.getWindSpeed()) > 35){
            advice.add("It is too windy to go hunting.");
        } else if (Float.parseFloat(weather.getWindSpeed()) > 20){
            advice.add("It is quite windy.");
        } else {
            advice.add("The wind is fine for hunting.");
        }
        //Wind Direction
        if (Float.parseFloat(weather.getWindDirection()) > 315 && Float.parseFloat(weather.getWindDirection()) < 45){
            advice.add("Do not sit facing North.");
        } else  if (Float.parseFloat(weather.getWindDirection()) > 45 && Float.parseFloat(weather.getWindDirection()) < 135){
            advice.add("Do not sit facing East.");
        } else  if (Float.parseFloat(weather.getWindDirection()) > 135 && Float.parseFloat(weather.getWindDirection()) < 225){
            advice.add("Do not sit facing South.");
        } else{
            advice.add("Do not sit facing West");
        }
        //Temperature
        if (Float.parseFloat(weather.getTemperature()) > 25){
            advice.add("It is too hot for the animals to go hunting.");
        } else if (Float.parseFloat(weather.getWindSpeed()) < -20){
            advice.add("It is too cold for the animals to go hunting.");
        } else {
            advice.add("The temperature is fine for hunting.");
        }
        //Visibility
        if (Float.parseFloat(weather.getVisibility()) < 50){
            advice.add("Visibility is very poor.");
        } else if (weather.getDescription().contains("mist")){
            advice.add("Mist can hinder visibility.");
        }
        //Rain
        if (weather.getDescription().contains("heavy rain")){
            advice.add("Animals are not a fan of heavy rain.");
        }
        if (weather.getDescription().contains("rain")){
            advice.add("Be careful of soft ground, don't get your vehicle stuck!");
        }
        //Other weather conditioned mentioned
        if (weather.getDescription().contains("snow")){
            advice.add("Snow helps you to see the animals.");
        }

        return advice;
    }

    public List<String> animalRules(int animalIndex){
        List<String> advice = new ArrayList<>();
        Weather weather = control.current;
        Person person = control.objects.getPersonsList().get(control.getSelectPerson());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMM");
        Date date = new Date();
        Date currentTime, currentDay = null, sunrise, sunset, maleSeasonStart, maleSeasonEnd, femaleSeasonStart, femaleSeasonEnd;
        Boolean canHunt, canHuntMale, canHuntFemale;
        try {
            currentTime = sdf.parse(sdf.format(date));
            currentDay = sdf2.parse(sdf2.format(date));
        } catch (ParseException ex){
            advice.add("CANNOT PARSE CURRENT TIME");
            currentTime = null;
        }
        canHunt = true;
        Animal animal = control.objects.getAnimalsList().get(animalIndex);
        advice.add(animal.getType());
        //Hunting Season
        if (currentDay!=null){
           try {
               maleSeasonStart = sdf2.parse(animal.getMaleHuntingSeasonStart());
               maleSeasonEnd = sdf2.parse(animal.getMaleHuntingSeasonEnd());
               femaleSeasonStart = sdf2.parse(animal.getFemaleHuntingSeasonStart());
               femaleSeasonEnd = sdf2.parse(animal.getFemaleHuntingSeasonEnd());
               if ((maleSeasonStart.before(maleSeasonEnd) && currentDay.after(maleSeasonStart) && currentDay.before(maleSeasonEnd)) || (maleSeasonStart.after(maleSeasonEnd) && !(currentDay.after(maleSeasonStart) && currentDay.before(maleSeasonEnd)))){
                   canHuntMale = true;
               } else {
                   canHuntMale = false;
               }
               if ((femaleSeasonStart.before(femaleSeasonEnd) && currentDay.after(femaleSeasonStart) && currentDay.before(femaleSeasonEnd)) || (femaleSeasonStart.after(femaleSeasonEnd) && !(currentDay.after(femaleSeasonStart) && currentDay.before(femaleSeasonEnd)))){
                   canHuntFemale = true;
               } else {
                   canHuntFemale = false;
               }
               if (!canHuntMale && !canHuntFemale) {
                   canHunt = false;
                   advice.add("This animal cannot be hunted.");
               } else if (!canHuntFemale){
                   advice.add("Females cannot be hunted.");
               } else if (!canHuntMale){
                   advice.add("Males cannot be hunted.");
               }
           } catch (ParseException ex){
               advice.add("CANNOT PARSE HUNTING SEASON");
           }
        }
        // Energy in the gun
        if (Integer.parseInt(animal.getEnergy()) > Integer.parseInt(person.getEnergy())){
            advice.add("The gun selected doesn't have enough energy to kill this animal.");
            canHunt = false;
        }
        if (canHunt) {
            //Time for hunting
            if (currentTime != null) {
                try {
                    sunrise = sdf.parse(weather.getSunrise());
                    sunset = sdf.parse(weather.getSunset());
                    if (animal.getBestTime().contains("night")) {
                        if (currentTime.after(sunset)) {
                            advice.add("The sun has already set, it's time to go out and hunt.");
                        } else if (sunset.getTime() - currentTime.getTime() < 3600000) {
                            advice.add("Less than an hour until sunset, time to get ready to hunt.");
                        }
                    } else if (animal.getBestTime().contains("getting")) {
                        if (currentTime.after(sunset)) {
                            advice.add("It's already dark, not a good time to hunt this animal.");
                        } else if (sunset.getTime() - currentTime.getTime() < 7200000) {
                            advice.add("Less than an two hours until sunset, time to go out and hunt.");
                        } else if (sunrise.getTime() - currentTime.getTime() < 7200000) {
                            advice.add("Less than an two hours until sunrise, time to go out and hunt.");
                        }
                    }
                } catch (ParseException ex) {
                    advice.add("CANNOT PARSE CURRENT TIME");
                }
            }
            // Food
            if (!animal.getFood().contains("null")){
                advice.add("Food to leave out:" + animal.getFood());
            }
            // Callers
            if (!animal.getCallers().contains("null")){
                advice.add("Take a caller for this animal (e.g. " + animal.getCallers() + ")");
            }
            // Environment
            if (!animal.getEnvironment().contains("everywhere")){
                advice.add("Mostly seen in: " + animal.getEnvironment());
            }
            //Driven hunt
            if (person.getDriven().contains("yes") && animal.getType().contains("boar")){
                advice.add("This animal is suitable for a driven hunt.");
            }
            if (person.getDog().contains("yes")){
                if (animal.getType().contains("boar")){
                    advice.add("Dogs are beneficial to the driven hunt.");
                } else if (Integer.parseInt(animal.getEnergy()) == 500){
                    advice.add("Dogs are beneficial to fetch smaller creatures.");
                }
            }
        }
        advice.add("");
        return advice;
    }
}


