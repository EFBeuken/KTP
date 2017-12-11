package controller;

import controller.HuntingControl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Emily on 30/11/2017.
 */
public class DeleteAnimal extends AbstractAction{

    private HuntingControl control;

    public DeleteAnimal(HuntingControl control){
        super("Delete");
        this.control = control;
    }

    public void actionPerformed(ActionEvent e){
        int reply = JOptionPane.showConfirmDialog(null, "Delete animal", "Delete Animal", JOptionPane.OK_CANCEL_OPTION);
        control.update();
    }

}
