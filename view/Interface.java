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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.awt.Graphics2D;

import java.awt.GradientPaint;

public class Interface extends JPanel implements Observer, ActionListener {

    private HuntingControl control;
    private Rules rules;
    private JButton newLoc;
    private JTextField longField;
    private JTextField latField;
    public JComboBox gunField;
    
    Color grey = new Color(118, 118, 118);
    Color googleGrey = new Color(237,237,237);
    Color lightGrey = new Color(222,222,222);

    public Interface(HuntingControl control, Rules rules){
        this.control = control;
        this.rules = rules;
        control.addObserver(this);
        setBackground(googleGrey);
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
        Font font = new Font("LucidaTypewriterRegular", Font.PLAIN, 12);
        return font;
    }

    public Font titleFont(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("LucidaTypewriterRegular", Font.BOLD, 20);
        return font;
    }
    
    public Font extraFont(Graphics g){
        g.setColor(grey);
        Font font = new Font("LucidaTypewriterRegular", Font.PLAIN, 16);
        return font;
    }

    void multilinePrint(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void paintMap(Graphics g){
        try {
            Weather current = control.current;
            String latitude = current.getLatitude();
            String longitude = current.getLongitude();
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                    + latitude
                    + ","
                    + longitude
                    + "&zoom=11&size=330x220&scale=1&maptype=roadmap";
            String destinationFile = "image.jpg";
            // read the map image from Google
            // then save it to a local file: image.jpg
            //
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();

            BufferedImage img = ImageIO.read(new URL(imageUrl));
            int w = 330;
            int h = 220;
            
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            g.drawImage(img, 650, 20, null);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void paintWeather(Graphics g){
        //current weather
        //on start up take one of the locs (random) and get the weather for that loc
        Weather current = control.current;
        g.setFont(titleFont(g));
        g.setColor(Color.white);
        String title = current.getTemperature() + "\u00b0"+ "C";
        g.drawString(title, 670, 251);
    
        String text = "";
        text += "Longitude: " + current.getLongitude() + " \n";
        text += "Latitude: " + current.getLatitude() + " \n";
        text += "Wind Speed: " + current.getWindSpeed() + " \n";
        text += "Wind Direction: " + current.getWindDirection() + " \n";
        text += "Moon Phase: " + current.getMoonPhase() + "\n";
        text += "Description: " + current.getDescription() + "\n";
        text += "Sunrise: " + current.getSunrise() + "\n";
        text += "Sunset: " + current.getSunset() + "\n";
        text += "Humidity: " + current.getHumidity() + " %\n";
        text += "Visibility: " + current.getVisibility() + " m\n";
        text += "CloudCover: " + current.getCloudCover() + " %\n";
        g.setFont(extraFont(g));
        multilinePrint(g, text, getWidth()*2/3, 290);

        String url = "https://raw.githubusercontent.com/EFBeuken/KTP/master/PNG/" + current.getIcon() + ".png";
        //String url = "http://openweathermap.org/img/w/" + current.getIcon() + ".png";
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            int w = 20;
            int h = 20;
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            g.drawImage(img, getWidth()-110, 215, 68, 70, null);
        } catch (Exception ex){
            System.out.println(ex);
        }

    }

    public void paintGun(Graphics g){
        g.setFont(titleFont(g));
        String title = "Gun";
        g.drawString(title, getWidth()*3/4-title.length()/2-300, getHeight()-120);
        Person person = control.objects.getPersonsList().get(control.getSelectPerson());
        String text = "";
        text += person.getGun() + "\n";
        text += person.getAmmunition() + "\n";
        text += "Driven? " + person.getDriven() + "\n";
        text += "Dogs? " + person.getDog() + "\n";
        g.setFont(standardFont(g));
        multilinePrint(g, text, getWidth()*2/3-300, getHeight()-110);
    }

    public void paintWeatherAdvice(Graphics g){
        g.setColor(Color.black);
        List<String> advice = rules.weatherRules();
        g.setFont(standardFont(g));
        g.setColor(Color.white);
        for (int i=0; i<advice.size(); i++){
            g.drawString(advice.get(i), getWidth()*2/3, getHeight()*3/5+(17*i)+130);
        }
    }

    public void paintAnimalAdvice(Graphics g){
        g.setColor(Color.black);
        for (int i=0; i<control.objects.animalsList.size(); i++){
            List<String> advice = rules.animalRules(i);
            g.setFont(titleFont(g));
            g.drawString(advice.get(0), 20, 20+(100*i));
            g.setFont(standardFont(g));
            for (int j=1; j<advice.size(); j++){
                g.drawString(advice.get(j), 20, 20+(15*j)+(100*i));
            }
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
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        
        g2d.setColor(new Color(249,249,249));
        g2d.fillRect(650, 20, 330, 495);
        paintMap(g);
        g2d.setColor(new Color(33, 150, 243));
        g2d.fillRect(650, 210, 330, 70);
        g2d.setColor(new Color(33, 150, 243));
        g2d.fillRect(650, 515, 330, 100);
        g2d.setColor(lightGrey);
        g2d.drawRect(650, 20, 330, 595);
        
        
        
        paintWeatherAdvice(g);
        paintAnimalAdvice(g);
        paintWeather(g);
        paintGun(g);
    }
    
    
    

}