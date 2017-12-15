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
public class NewProfileFrame extends JFrame implements ActionListener {

    HuntingControl control;

    private JTextField nameField;
    private JTextField gunField;
    private JTextField ammunitionField;
    private JTextField energyField;
    private JTextField dogField;
    private JTextField drivenField;

    private JButton saveProfile;

    public NewProfileFrame(HuntingControl control) {
        super("Add New Profile");

        this.control = control;

        JPanel newPanel = new JPanel(new GridLayout(0, 2));
        newPanel.setPreferredSize(new Dimension(400, 300));

        nameField = new JTextField("", 20);
        gunField = new JTextField("Sauer 101", 50);
        ammunitionField = new JTextField("7.62-60", 20);
        energyField = new JTextField("2000", 4);
        dogField = new JTextField("yes/no", 3);
        drivenField = new JTextField("yes/no", 3);

        saveProfile = new JButton("Save");
        saveProfile.addActionListener(this);

        newPanel.add(new JLabel("Name"));
        newPanel.add(nameField);
        newPanel.add(new JLabel("Gun"));
        newPanel.add(gunField);
        newPanel.add(new JLabel("Ammunition"));
        newPanel.add(ammunitionField);
        newPanel.add(new JLabel("Energy"));
        newPanel.add(energyField);
        newPanel.add(new JLabel("Using a dog"));
        newPanel.add(dogField);
        newPanel.add(new JLabel("Driven hunt"));
        newPanel.add(drivenField);
        newPanel.add(saveProfile);

        add(newPanel);
        pack();
        setLocationRelativeTo(null);

    }

    public void actionPerformed(ActionEvent e) {
        File folder = new File("./src/data/person");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().equals(nameField.getText()+".txt")) {
                JOptionPane.showMessageDialog(new JFrame(), "Another file exists with this name. Choose another file name.", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter( new FileWriter("./src/data/person/" + nameField.getText() + ".txt"));
            writer.write(nameField.getText() + "\n");
            writer.write(gunField.getText() + "\n");
            writer.write(ammunitionField.getText() + "\n");
            writer.write(energyField.getText() + "\n");
            writer.write(dogField.getText() + "\n");
            writer.write(drivenField.getText() + "\n");
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
