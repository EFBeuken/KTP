package view;

import controller.HuntingControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Emily on 30/11/2017.
 */
public class NewAnimalFrame extends JFrame implements ActionListener {

    private HuntingControl control;

    private JTextField typeField;
    private JTextField energyField;
    private JTextField weightField;
    private JTextField maleStartField;
    private JTextField maleEndField;
    private JTextField femaleStartField;
    private JTextField femaleEndField;
    private JTextField bestTimeField;
    private JTextField foodField;
    private JTextField callersField;
    private JTextField environmentField;

    private JButton saveAnimal;

    public NewAnimalFrame(HuntingControl control) {
        super("Add New Animal");

        this.control = control;

        JPanel newPanel = new JPanel(new GridLayout(0,2));
        newPanel.setPreferredSize(new Dimension(400, 300));
        
        typeField = new JTextField("deer", 20);
        energyField = new JTextField("2000", 4);
        weightField = new JTextField("15", 3);
        maleStartField = new JTextField("0101", 4);
        maleEndField = new JTextField("3112", 4);
        femaleStartField = new JTextField("0101", 4);
        femaleEndField = new JTextField("3112", 4);
        bestTimeField = new JTextField("0400", 4);
        foodField = new JTextField("grass", 20);
        callersField = new JTextField("null", 20);
        environmentField = new JTextField("forest clearing, fields", 50);

        saveAnimal = new JButton("Save");
        saveAnimal.addActionListener(this);

        newPanel.add(new JLabel("Type"));
        newPanel.add(typeField);
        newPanel.add(new JLabel("Energy Required"));
        newPanel.add(energyField);
        newPanel.add(new JLabel("Weight (kg)"));
        newPanel.add(weightField);
        newPanel.add(new JLabel("Male Hunting Season Start"));
        newPanel.add(maleStartField);
        newPanel.add(new JLabel("Male Hunting Season End"));
        newPanel.add(maleEndField);
        newPanel.add(new JLabel("Female Hunting Season Start"));
        newPanel.add(femaleStartField);
        newPanel.add(new JLabel("Female Hunting Season End"));
        newPanel.add(femaleEndField);
        newPanel.add(new JLabel("Best Time"));
        newPanel.add(bestTimeField);
        newPanel.add(new JLabel("Food"));
        newPanel.add(foodField);
        newPanel.add(new JLabel("Callers"));
        newPanel.add(callersField);
        newPanel.add(new JLabel("Best Environment"));
        newPanel.add(environmentField);
        newPanel.add(saveAnimal);

        add(newPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        File folder = new File("./src/data/animal");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().equals(typeField.getText()+".txt")) {
                JOptionPane.showMessageDialog(new JFrame(), "Another file exists with this name. Choose another file name.", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter( new FileWriter("./src/data/animal/" + typeField.getText() + ".txt"));
            writer.write(typeField.getText() + "\n");
            writer.write(energyField.getText() + "\n");
            writer.write(weightField.getText() + "\n");
            writer.write(maleStartField.getText() + "\n");
            writer.write(maleEndField.getText() + "\n");
            writer.write(femaleStartField.getText() + "\n");
            writer.write(femaleEndField.getText() + "\n");
            writer.write(bestTimeField.getText() + "\n");
            writer.write(foodField.getText() + "\n");
            writer.write(callersField.getText() + "\n");
            writer.write(environmentField.getText() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(new JFrame(), "Saved!", "Dialog",JOptionPane.INFORMATION_MESSAGE);
            control.objects = control.loadObjects();
            control.update();
            dispose();
        }
        catch ( IOException ex){
            System.out.println("Failed to save!");
            ex.printStackTrace();
        }
    }
}

