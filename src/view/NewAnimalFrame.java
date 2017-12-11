package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emily on 30/11/2017.
 */
public class NewAnimalFrame extends JFrame {

    public NewAnimalFrame(){
        super("Add New Animal");
        JPanel newPanel = new JPanel(new GridLayout(0,2));
        newPanel.setPreferredSize(new Dimension(400, 300));

        newPanel.add(new JLabel("Type"));
        newPanel.add(new JTextField("deer", 20));
        newPanel.add(new JLabel("Weight (kg)"));
        newPanel.add(new JTextField("15", 3));
        newPanel.add(new JLabel("Male Hunting Season Start"));
        newPanel.add(new JTextField("0101", 4));
        newPanel.add(new JLabel("Male Hunting Season End"));
        newPanel.add(new JTextField("3112", 4));
        newPanel.add(new JLabel("Female Hunting Season Start"));
        newPanel.add(new JTextField("0101", 4));
        newPanel.add(new JLabel("Female Hunting Season End"));
        newPanel.add(new JTextField("3112", 4));
        newPanel.add(new JLabel("Best Time"));
        newPanel.add(new JTextField("0400", 4));
        newPanel.add(new JLabel("Food"));
        newPanel.add(new JTextField("grass", 20));
        newPanel.add(new JLabel("Callers"));
        newPanel.add(new JTextField("null", 20));
        newPanel.add(new JLabel("Best Environment"));
        newPanel.add(new JTextField("forest clearing, fields", 50));

        newPanel.add(new JButton("Save"));
        newPanel.add(new JButton("Cancel"));

        add(newPanel);
        pack();
        setLocationRelativeTo(null);
    }

}
