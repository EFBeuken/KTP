package view;

import controller.HuntingControl;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emily on 30/11/2017.
 */
public class NewProfileFrame extends JFrame {

    private String name;
    private String gun;
    private String ammunition;
    private String energy;
    private String dog;
    private String driven;

    public NewProfileFrame(HuntingControl control){
        super("Add New Profile");

        JPanel newPanel = new JPanel(new GridLayout(0,2));
        newPanel.setPreferredSize(new Dimension(400, 300));

        newPanel.add(new JLabel("Name"));
        JTextField nameField = new JTextField("", 20);
        newPanel.add(nameField);

        newPanel.add(new JLabel("Gun"));
        JTextField gunField = new JTextField("Sauer 101", 50);
        newPanel.add(gunField);

        newPanel.add(new JLabel("Ammunition"));
        JTextField ammunitionField = new JTextField("7.62-60", 20);
        newPanel.add(ammunitionField);

        newPanel.add(new JLabel("Energy"));
        JTextField energyField = new JTextField("2000", 4);
        newPanel.add(energyField);

        newPanel.add(new JLabel("Using a dog"));
        JTextField dogField = new JTextField("yes/no", 3);
        newPanel.add(dogField);

        newPanel.add(new JLabel("Driven hunt"));
        JTextField drivenField = new JTextField("yes/no", 3);
        newPanel.add(drivenField);

        this.name = nameField.getText();
        this.gun = gunField.getText();
        this.ammunition = ammunitionField.getText();
        this.energy = energyField.getText();
        this.dog = dogField.getText();
        this.driven = drivenField.getText();

        newPanel.add(new JButton("Save"));
        newPanel.add(new JButton("Cancel"));

        add(newPanel);
        pack();
        setLocationRelativeTo(null);

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public String getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(String ammunition) {
        this.ammunition = ammunition;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getDog() {
        return dog;
    }

    public void setDog(String dog) {
        this.dog = dog;
    }

    public String getDriven() {
        return driven;
    }

    public void setDriven(String driven) {
        this.driven = driven;
    }
}
