package controller;

import model.Animal;
import model.Weather;

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
    private List<String> gunAdvice;

    public Rules(HuntingControl control){
        this.control = control;
        this.weatherAdvice = weatherRules();
        this.animalAdvice = animalRules();
        this.gunAdvice = gunRules();
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
        if (Float.parseFloat(weather.getVisibility()) < 400){
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

    public List<String> animalRules(){
        List<String> advice = new ArrayList<>();
        Weather weather = control.current;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        Date currentTime;
        Date sunrise;
        Date sunset;
        try {
            currentTime = sdf.parse(sdf.format(date));
        } catch (ParseException ex){
            advice.add("CANNOT PARSE CURRENT TIME");
            currentTime = null;
        }
        for (int i=0; i<control.objects.getAnimalsList().size(); i++){
            Animal animal = control.objects.getAnimalsList().get(i);
            advice.add(animal.getType());
            //Time for hunting
            if (currentTime!= null) {
                try {
                    sunrise = sdf.parse(weather.getSunrise());
                    sunset = sdf.parse(weather.getSunset());
                    if (animal.getBestTime().contains("night")){
                        if (currentTime.after(sunset)){
                            advice.add("The sun has already set, it's time to go out and hunt.");
                        } else if (sunset.getTime() - currentTime.getTime() < 3600000) {
                            advice.add("Less than an hour until sunset, time to get ready to hunt.");
                        }
                    } else if (animal.getBestTime().contains("getting")){
                        if (currentTime.after(sunset)){
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

        }
        return advice;
    }

    public List<String> gunRules(){
        List<String> advice = new ArrayList<>();
        return advice;
    }
}


