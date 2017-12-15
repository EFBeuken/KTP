package controller;

import controller.HuntingControl;
import view.DeleteProfileFrame;
import view.NewProfileFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Emily on 30/11/2017.
 */
public class DeleteProfile extends AbstractAction{

    private HuntingControl control;

    public DeleteProfile(HuntingControl control){
        super("Delete");
        this.control = control;
    }

    public void actionPerformed(ActionEvent e){
        DeleteProfileFrame delProfile = new DeleteProfileFrame(control);
        delProfile.setVisible(true);
        control.update();
    }

}
