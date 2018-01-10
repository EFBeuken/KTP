package controller;

import controller.HuntingControl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Emily on 30/11/2017.
 */
public class Help extends AbstractAction{

    private HuntingControl control;

    public Help(HuntingControl control){
        super("Help");
        this.control = control;
    }

    public void actionPerformed(ActionEvent e){
        int reply = JOptionPane.showConfirmDialog(null, "This text will help you!", "Help", JOptionPane.OK_CANCEL_OPTION);
        control.update();
    }

}
