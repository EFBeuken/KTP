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
import java.nio.file.spi.FileSystemProvider;

/**
 * Created by Emily on 30/11/2017.
 */
public class DeleteProfileFrame extends JFrame implements ActionListener {

    HuntingControl control;

    private JComboBox nameField;

    private JButton deleteProfile;

    public DeleteProfileFrame(HuntingControl control) {
        super("Delete Profile");

        this.control = control;

        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        newPanel.setPreferredSize(new Dimension(400, 300));

        String options = "";
        File folder = new File("./data/person");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                options += file.getName();
            }
        }
        String[] optionsList = options.split(".txt");

        newPanel.add(nameField = new JComboBox(optionsList));
        nameField.setSelectedIndex(0);

        newPanel.add(deleteProfile = new JButton("Delete"));
        deleteProfile.addActionListener(this);

        add(newPanel);
        pack();
        setLocationRelativeTo(null);

    }

    public void actionPerformed(ActionEvent e) {
        File folder = new File("./data/person");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().equals(nameField.getSelectedItem() +".txt")) {
                Path deleteFile = Paths.get("./data/person/" + nameField.getSelectedItem() + ".txt");
                try {
                    Files.deleteIfExists(deleteFile);
                    JOptionPane.showMessageDialog(new JFrame(), "Deleted!\nPlease restart the program to remove the profile.", "Delete",JOptionPane.INFORMATION_MESSAGE);
                    control.objects = control.loadObjects();
                    control.update();
                    dispose();
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();


                }
            }
        }
    }
}
