package view;

import controller.HuntingControl;
import controller.Rules;
import model.Animal;
import model.Person;
import model.Weather;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

public class Interface extends JPanel implements Observer, ActionListener {

    private HuntingControl control;
    private Rules rules;
    private JButton newLoc;
    private JTextField longField;
    private JTextField latField;
    public JComboBox gunField;
    public JTextArea sampleTextArea;

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
        setLayout(new GridBagLayout());

        JPanel locationSetting = new JPanel(new GridBagLayout());
        JPanel locationSetting2 = new JPanel(new GridBagLayout());
        JPanel animalText = new JPanel(new GridBagLayout());

        longField = new JTextField(control.current.getLongitude());
        latField = new JTextField(control.current.getLatitude());
        newLoc = new JButton("Change Location");
        sampleTextArea = new JTextArea("text", 5, 50);
        gunField = new JComboBox(getGuns());
        gunField.setSelectedIndex(control.getSelectPerson());

        newLoc.addActionListener(this);
        gunField.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        locationSetting.add(longField, gbc);
        gbc.gridy++;
        locationSetting.add(latField, gbc);
        gbc.gridy++;
        locationSetting.add(newLoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(-265, 100, 0, 0);
        add(locationSetting, gbc);
        
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 0;
        locationSetting2.add(gunField, gdc);
        gdc.insets = new Insets(-570, -420, 0, 0);
        add(locationSetting2, gdc);

		GridBagConstraints gtc = new GridBagConstraints();
        gtc.gridx = 0;
        gtc.gridy = 0;
        gtc.fill = GridBagConstraints.HORIZONTAL;
        animalText.add(new JScrollPane(sampleTextArea), gtc);
        gtc.insets = new Insets(-300, -280, 0, 0);
        add(animalText, gtc);
        
        //gbc.insets = new Insets(50, 50, 50, 50);
        //gbc.gridx++;
        //gbc.gridy = 0;
        //gbc.fill = GridBagConstraints.BOTH;
        //add(new JScrollPane(sampleTextArea), gbc);

        //add(gunField);
        //add(newLoc);

        //JScrollPane sampleScrollPane = new JScrollPane (sampleTextArea,     JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //add(sampleScrollPane);
    }

    public String[] getGuns(){
        String options = "";
        File folder = new File("./data/person");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                options += file.getName();
            }
        }
        String[] optionsList = options.split(".txt");
        return optionsList;
    }

    public Font standardFont(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("LucidaTypewriterRegular", Font.PLAIN, 16);
        return font;
    }

    public Font titleFont(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("LucidaTypewriterRegular", Font.BOLD, 20);
        return font;
    }

    public Font extraFont(Graphics g){
        g.setColor(grey);
        Font font = new Font("LucidaTypewriterRegular", Font.PLAIN, 12);
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
            JOptionPane.showMessageDialog(new JFrame(), "Cannot display map!", "Error",JOptionPane.INFORMATION_MESSAGE);
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
        text += "Description: " + current.getDescription() + "\n";
        text += "Wind Speed: " + current.getWindSpeed() + " mph\n";
        text += "Wind Direction: " + current.getWindDirection() + " \n";
        text += "Sunrise: " + current.getSunrise() + "\n";
        text += "Sunset: " + current.getSunset() + "\n";
        text += "Humidity: " + current.getHumidity() + " %\n";
        text += "Visibility: " + current.getVisibility() + " m\n";
        text += "CloudCover: " + current.getCloudCover() + " %\n";
        text += "Longitude: " + current.getLongitude() + " \n";
        text += "Latitude: " + current.getLatitude() + " \n";
        g.setFont(extraFont(g));
        multilinePrint(g, text, 660, 290);

        String url = "https://raw.githubusercontent.com/EFBeuken/KTP/master/PNG/" + current.getIcon() + ".png";
        //String url = "http://openweathermap.org/img/w/" + current.getIcon() + ".png";
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            int w = 20;
            int h = 20;
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            g.drawImage(img, 900, 215, 68, 70, null);
        } catch (Exception ex){
            System.out.println(ex);
        }

    }

    public void paintGun(Graphics g){
    
        g.setFont(titleFont(g));
        g.setColor(Color.white);
        String title = "Gun";
        g.drawString(title, 30, 50);
        Person person = control.objects.getPersonsList().get(control.getSelectPerson());
        String text = "";
        text += person.getGun() + "\n";
        text += person.getAmmunition() + "\n";
        text += "Driven? " + person.getDriven() + "\n";
        text += "Dogs? " + person.getDog() + "\n";
        g.setFont(standardFont(g));
        g.setColor(Color.white);
        multilinePrint(g, text, 30, 60);
    }

    public void paintWeatherAdvice(Graphics g){
        g.setColor(Color.black);
        List<String> advice = rules.weatherRules();
        g.setFont(standardFont(g));
        g.setColor(Color.white);
        for (int i=0; i<advice.size(); i++){
            g.drawString(advice.get(i), 660, 530+(17*i));
        }
    }

    public void paintAnimalAdvice(Graphics g){
        g.setColor(Color.black);
        for (int i=0; i<control.objects.animalsList.size(); i++){
            List<String> advice = rules.animalRules(i);
            g.setFont(titleFont(g));
            if (advice.get(1).contains("true")){
                g.setColor(Color.green);
            } else {
                g.setColor(Color.red);
            }
            g.drawString(advice.get(0), 20, 20+(100*i)+160);
            g.setFont(standardFont(g));
            g.setColor(grey);
            for (int j=2; j<advice.size(); j++){
                g.drawString(advice.get(j), 20, 20+(15*(j-1))+(100*i)+160);
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
        g2d.fillRect(650, 515, 330, 110);
        g2d.setColor(lightGrey);
        g2d.drawRect(650, 20, 330, 605);
        
        g2d.setColor(new Color(33, 150, 243));
        g2d.fillRect(20, 20, 630, 130);
        g2d.setColor(lightGrey);
        g2d.drawRect(20, 20, 315, 130);
        g2d.setColor(lightGrey);
        g2d.drawRect(335, 20, 315, 130);
        
        
        g.setFont(standardFont(g));
        g.setColor(Color.white);
        String longt = "Longitude:";
        g.drawString(longt, 370, 67);
        String lat = "Latitude:";
        g.drawString(lat, 370, 87);
        



        paintWeatherAdvice(g);
        paintAnimalAdvice(g);
        paintWeather(g);
        paintGun(g);
    }




}
