package controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.*;
import oracle.jrockit.jfr.StringConstantPool;
import sun.java2d.cmm.Profile;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;


public class HuntingControl extends Observable implements Observer {

    public Boolean homepage;
    public LoadObjects objects;
    public Weather current;

    public HuntingControl() {
        this.objects = loadObjects();
        this.current = currentWeather();
        this.homepage = true;
    }

    public LoadObjects loadObjects(){
        LoadObjects getObjects = new LoadObjects(null,null);
        getObjects.animalsList = loadAnimals();
        return getObjects;
    }

    public Weather currentWeather(){
        Weather current = new Weather(null, null, null, null, null, null, null, null, null, null, null, null, null);
        double longitude = 9.8;
        double latitude = 53.5;
        current.setLongitude(String.valueOf(longitude));
        current.setLatitude(String.valueOf(latitude));
        String locURL = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=57115fa390cea73bc8f4803c8fea348d";
        try {
            URL url = new URL(locURL);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            String[] words = line.split(",");
            List<String> stripped = new ArrayList<>();
            for (String word : words){
                word = word.replaceAll("[^a-zA-Z0-9 .,]|(?<!\\d)[.,]|[.,](?!\\d)", " ");
                word = word.trim().replaceAll("\\s{2,}", " ");
                stripped.add(word);
            }
            String desc = "";
            for (int i = 0; i < stripped.size(); i++) {
                String word = stripped.get(i);
                if (word.contains("icon")){
                    word = word.replace("icon ", "");
                    current.setIcon(word);
                }
                if (word.contains("description")) {
                    word = word.replace("description ", "");
                    desc += word + " ";
                } else if (word.contains("main temp")) {
                    word = word.replace("main temp ", "");
                    float temp = Float.parseFloat(word);
                    temp -= 273.15;
                    current.setTemperature(String.valueOf(temp));
                } else if (word.contains("wind speed")) {
                    word = word.replace("wind speed ", "");
                    current.setWindSpeed(word);
                } else if (word.contains("humidity")) {
                    word = word.replace("humidity ", "");
                    current.setHumidity(word);
                } else if (word.contains("visibility")) {
                    word = word.replace("visibility ", "");
                    current.setVisibility(word);
                } else if (word.contains("deg")) {
                    word = word.replace("deg ", "");
                    current.setWindDirection(word);
                } else if (word.contains("clouds all")) {
                    word = word.replace("clouds all ", "");
                    current.setCloudCover(word);
                } else if (word.contains("sunset")) {
                    word = word.replace("sunset ", "");
                    current.setSunset(UTCtoGMT(Long.parseLong(word)));
                } else if (word.contains("sunrise")) {
                    word = word.replace("sunrise ", "");
                    current.setSunrise(UTCtoGMT(Long.parseLong(word)));
                }
            }
            current.setDescription(desc);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return current;
    }

    public String UTCtoGMT(long unitUTC){
        Date date = new Date(unitUTC*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return sdf.format(date);
    }


    public List<Animal> loadAnimals(){
        List<Animal> getAnimals = new ArrayList<>();
        try {
            File folder = new File("C:/Users/Emily/Documents/AI - 3rd Year/1B/Knowledge Technology Practical/src/data/animal");
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String name = file.getName();
                    BufferedReader br = new BufferedReader(new FileReader("C:/Users/Emily/Documents/AI - 3rd Year/1B/Knowledge Technology Practical/src/data/animal/" + name));
                    String line = null;
                    List<String> object = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        object.add(line);
                    }
                    Animal animal = new Animal(null, null,null, null, null, null, null,null, null, null, null);
                    animal.setType(object.get(0));
                    animal.setEnergy(object.get(1));
                    animal.setAvWeight(object.get(2));
                    animal.setMaleHuntingSeasonStart(object.get(3));
                    animal.setMaleHuntingSeasonEnd(object.get(4));
                    animal.setFemaleHuntingSeasonStart(object.get(5));
                    animal.setFemaleHuntingSeasonEnd(object.get(6));
                    animal.setBestTime(object.get(7));
                    animal.setFood(object.get(8));
                    animal.setCallers(object.get(9));
                    animal.setEnvironment(object.get(10));
                    getAnimals.add(animal);
                }
            }
        } catch (Exception ex){
            System.out.print(ex);
        }
        return getAnimals;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }

    public void update(Observable obs, Object obj) {
        setChanged();
        notifyObservers();
    }

   
}
