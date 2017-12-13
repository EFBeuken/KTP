package controller;

import view.NewProfileFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Emily on 30/11/2017.
 */
public class NewProfile extends AbstractAction{

    private HuntingControl control;

    public NewProfile(HuntingControl control){
        super("New");
        this.control = control;
    }

    public void actionPerformed(ActionEvent e){
        NewProfileFrame newProfile = new NewProfileFrame(control);
        newProfile.setVisible(true);
        control.update();
    }

}
