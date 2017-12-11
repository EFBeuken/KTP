package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Emily on 30/11/2017.
 */
public class NewProfileFrame extends JFrame {

    public NewProfileFrame(){
        super("Add New Profile");
        JPanel newPanel = new JPanel(new GridLayout(0,2));
        newPanel.setPreferredSize(new Dimension(400, 300));

        newPanel.add(new JButton("Save"));
        newPanel.add(new JButton("Cancel"));

        add(newPanel);
        pack();
        setLocationRelativeTo(null);
    }

}
