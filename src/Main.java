
import controller.HuntingControl;
import controller.Rules;
import model.Weather;
import view.Interface;
import view.MenuOptions;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HuntingControl control = new HuntingControl();
        Rules  rules = new Rules(control);
        JFrame frame = new JFrame("Hunting101");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800,500));
        Interface panelInterface = new Interface(control, rules);
        frame.setJMenuBar(new MenuOptions(control));
        frame.getContentPane().add(panelInterface);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
