package view;

import controller.HuntingControl;
import model.Animal;
import model.Person;
import model.Weather;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Interface extends JPanel implements Observer, ActionListener {

    private HuntingControl control;
    JButton newLoc;
    JTextField longField;
    JTextField latField;
    JComboBox gunField;

    public Interface(HuntingControl control){
        this.control = control;
        control.addObserver(this);
        setBackground(Color.lightGray);
        setVisible(true);
        setOpaque(true);

        longField = new JTextField(control.current.getLongitude());
        latField = new JTextField(control.current.getLatitude());
        newLoc = new JButton("Change Location");

        String options = "";
        File folder = new File("./src/data/person");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                options += file.getName();
            }
        }
        String[] optionsList = options.split(".txt");

        gunField = new JComboBox(optionsList);
        gunField.setSelectedIndex(0);

        newLoc.addActionListener(this);
        gunField.addActionListener(this);

        add(gunField);
        add(longField);
        add(latField);
        add(newLoc);

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
            g.drawImage(img, getWidth()-w, 0, null);
        } catch (Exception ex){
            System.out.println(ex);
        }

    }

    public void paintAnimals(Graphics g){
        g.setColor(Color.black);
        Person selectedPerson = new Person(null, null, null, null, null, null);
        for (int i=0; i<control.objects.getPersonsList().size(); i++){
            if (gunField.getSelectedItem().equals(control.objects.getPersonsList().get(i).getName())) {
                selectedPerson = control.objects.getPersonsList().get(i);
            }
        }
        for (int i=0; i<control.objects.getAnimalsList().size(); i++){
            Animal animal = control.objects.getAnimalsList().get(i);
            g.setFont(titleFont(g));
            g.drawString(animal.getType(), 50, 50+(75*i));
            g.setFont(standardFont(g));
            String animalText = "";
            animalText += "Weight: " + animal.getAvWeight() + "kg\n";
            animalText += "Male Season: " + animal.getMaleHuntingSeasonStart() + " - " + animal.getMaleHuntingSeasonEnd() + "\n";
            animalText += "Female Season: " + animal.getFemaleHuntingSeasonStart() + " - " + animal.getFemaleHuntingSeasonEnd() + "\n";
            if (Integer.parseInt(selectedPerson.getEnergy()) >= Integer.parseInt(animal.getEnergy())){
                animalText += "The chosen weapon has enough energy to kill this animal.\n";
            } else {
                animalText += "The chosen weapon doesn't have enough energy to kill this animal.\n";
            }
            multilinePrint(g, animalText, 50, 50+(75*i));
        }
    }

    public void paintPersons(Graphics g){
        g.setColor(Color.black);
        for (int i=0; i<control.objects.getPersonsList().size(); i++){
            if (gunField.getSelectedItem().equals(control.objects.getPersonsList().get(i).getName())){
                Person person = control.objects.getPersonsList().get(i);
                String personText = "";
                personText = person.getName() + "\n" + person.getGun() + "\n" + person.getAmmunition() + "\n" + person.getEnergy();
                g.drawString(personText, 300, 300);
                return;
            }

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
            g.drawString(advice.get(i), getWidth()*2/3, getHeight()*2/3+(15*i));
        }

    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().contains("Change Location")) {
            control.current = control.currentWeather(longField.getText(), latField.getText());
        } else if (e.getActionCommand().contains("comboBoxChanged")){
            repaint();
        }
    }

    public void update(Observable obs, Object obj){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintAnimals(g);
        //paintPersons(g);
        paintAdvice(g);
        paintWeather(g);
    }


    }