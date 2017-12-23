package view;

import controller.HuntingControl;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Emily on 30/11/2017.
 */
public class DeleteAnimalFrame extends JFrame implements ActionListener {

    HuntingControl control;

    private JComboBox typeField;

    private JButton deleteAnimal;

    public DeleteAnimalFrame(HuntingControl control) {
        super("Delete Animal");

        this.control = control;

        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        newPanel.setPreferredSize(new Dimension(400, 300));

        String options = "";
        File folder = new File("./data/animal");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                options += file.getName();
            }
        }
        String[] optionsList = options.split(".txt");

        newPanel.add(typeField = new JComboBox(optionsList));
        typeField.setSelectedIndex(0);

        newPanel.add(deleteAnimal = new JButton("Delete"));
        deleteAnimal.addActionListener(this);

        add(newPanel);
        pack();
        setLocationRelativeTo(null);

    }

    public void actionPerformed(ActionEvent e) {
        File folder = new File("./data/animal");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().equals(typeField.getSelectedItem() +".txt")) {
                Path deleteFile = Paths.get("./data/animal/" + typeField.getSelectedItem() + ".txt");
                try {
                    Files.deleteIfExists(deleteFile);
                    control.objects = control.loadObjects();
                    control.update();
                    dispose();
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(new JFrame(), ex, "Error",JOptionPane.INFORMATION_MESSAGE);

                }
            }
        }
    }
}
