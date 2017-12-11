package view;

import controller.HuntingControl;
import model.Animal;
import model.Location;
import model.Weather;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Interface extends JPanel implements Observer {

    private HuntingControl control;

    public Interface(HuntingControl control){
        this.control = control;
        control.addObserver(this);
        setBackground(Color.lightGray);
        setVisible(true);
        setOpaque(true);
    }

    public Font standardFont(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("Courier New", Font.PLAIN, 12);
        return font;
    }

    public Font titleFont(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("Courier New", Font.PLAIN, 16);
        return font;
    }

    void multilinePrint(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void paintWeather(Graphics g){
        //current weather
        //on start up take one of the locs (random) and get the weather for that loc
        g.setFont(titleFont(g));
        String title = "Weather";
        g.drawString(title, getWidth()*3/4-title.length()/2, 50);
        Weather current = control.current;
        String text = "";
        text += "Longitude: " + current.getLongitude() + " \n";
        text += "Latitude: " + current.getLatitude() + " \n";
        text += "Wind Speed: " + current.getWindSpeed() + " \n";
        text += "Wind Direction: " + current.getWindDirection() + " \n";
        text += "Temperature: " + current.getTemperature() + " Celcius\n";
        text += "Moon Phase: " + current.getMoonPhase() + "\n";
        text += "Description: " + current.getDescription() + "\n";
        text += "Sunrise: " + current.getSunrise() + "\n";
        text += "Sunset: " + current.getSunset() + "\n";
        text += "Humidity: " + current.getHumidity() + " %\n";
        text += "Visibility: " + current.getVisibility() + " m\n";
        text += "CloudCover: " + current.getCloudCover() + " %\n";
        g.setFont(standardFont(g));
        multilinePrint(g, text, getWidth()*2/3, 60);

        String url = "http://openweathermap.org/img/w/" + current.getIcon() + ".png";
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            g.drawImage(img, getWidth()*2/3, getHeight()-100, null);
        } catch (Exception ex){
            System.out.println(ex);
        }

    }

    public void paintAnimals(Graphics g){
        g.setColor(Color.black);
        for (int i=0; i<control.objects.getAnimalsList().size(); i++){
            Animal animal = control.objects.getAnimalsList().get(i);
            String animalText = "";
            animalText = animal.getType() + " " + animal.getAvWeight() + "kg";
            g.drawString(animalText, 100, 300+(15*i));
        }
    }

    public void paintAdvice(Graphics g){
        g.setColor(Color.black);
        Weather weather = control.current;
        List<String> advice = new ArrayList<>();
        if (Float.parseFloat(weather.getWindSpeed()) > 35){
            advice.add("It is too windy to go hunting.");
        } else if (Float.parseFloat(weather.getWindSpeed()) > 20){
            advice.add("It is quite windy.");
        } else {
            advice.add("The wind is fine for hunting.");
        }
        if (Float.parseFloat(weather.getWindDirection()) > 315 && Float.parseFloat(weather.getWindDirection()) < 45){
            advice.add("Do not sit facing North.");
        } else  if (Float.parseFloat(weather.getWindDirection()) > 45 && Float.parseFloat(weather.getWindDirection()) < 135){
            advice.add("Do not sit facing East.");
        } else  if (Float.parseFloat(weather.getWindDirection()) > 135 && Float.parseFloat(weather.getWindDirection()) < 225){
            advice.add("Do not sit facing South.");
        } else{
            advice.add("Do not sit facing West");
        }
        if (Float.parseFloat(weather.getTemperature()) > 25){
            advice.add("It is too hot for the animals to go hunting.");
        } else if (Float.parseFloat(weather.getWindSpeed()) < -20){
            advice.add("It is too cold for the animals to go hunting.");
        } else {
            advice.add("The temperature is fine for hunting.");
        }
        if (weather.getDescription().contains("snow")){
            advice.add("Snow helps you to see the animals.");
        }
        if (weather.getDescription().contains("mist")){
            advice.add("Mist can hinder visibility.");
        }
        if (weather.getDescription().contains("heavy rain")){
            advice.add("Animals are not a fan of heavy rain.");
        }
        if (weather.getDescription().contains("rain")){
            advice.add("Be careful of soft ground, don't get your vehicle stuck!");
        }
        for (int i=0; i<advice.size(); i++){
            g.drawString(advice.get(i), 100, 100+(15*i));
        }

    }

    public void update(Observable obs, Object obj){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintAnimals(g);
        paintAdvice(g);
        paintWeather(g);
    }


    }