package view;

import controller.HuntingControl;
import controller.Rules;
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
    private Rules rules;
    private JButton newLoc;
    private JTextField longField;
    private JTextField latField;
    public JComboBox gunField;

    public Interface(HuntingControl control, Rules rules){
        this.control = control;
        this.rules = rules;
        control.addObserver(this);
        setBackground(Color.lightGray);
        setVisible(true);
        setOpaque(true);

        longField = new JTextField(control.current.getLongitude());
        latField = new JTextField(control.current.getLatitude());
        newLoc = new JButton("Change Location");

        String options = "";
        File folder = new File("./data/person");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                options += file.getName();
            }
        }
        String[] optionsList = options.split(".txt");

        gunField = new JComboBox(optionsList);
        gunField.setSelectedIndex(control.getSelectPerson());

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

    public void paintWeatherAdvice(Graphics g){
        g.setColor(Color.black);
        List<String> advice = rules.weatherRules();
        for (int i=0; i<advice.size(); i++){
            g.drawString(advice.get(i), getWidth()*2/3, getHeight()*2/3+(15*i));
        }
    }

    public void paintAnimalAdvice(Graphics g){
        g.setColor(Color.black);
        List<String> advice = rules.animalRules();
        for (int i=0; i<advice.size(); i++){
            g.drawString(advice.get(i), 50, getHeight()/6+(15*i));
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().contains("Change Location")) {
            control.current = control.currentWeather(longField.getText(), latField.getText());
        } else if (e.getActionCommand().contains("comboBoxChanged")){
            control.setSelectPerson(gunField.getSelectedIndex());
        }
        repaint();
    }

    public void update(Observable obs, Object obj){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paintAnimals(g);
        //paintPersons(g);
        paintWeatherAdvice(g);
        paintAnimalAdvice(g);
        paintWeather(g);
    }


    }