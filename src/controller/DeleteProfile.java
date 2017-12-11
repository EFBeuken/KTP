package controller;

import controller.HuntingControl;

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
        int reply = JOptionPane.showConfirmDialog(null, "Profile", "Delete Profile", JOptionPane.OK_CANCEL_OPTION);
        control.update();
    }

}
