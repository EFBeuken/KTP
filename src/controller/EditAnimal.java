package controller;

import controller.HuntingControl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Emily on 30/11/2017.
 */
public class EditAnimal extends AbstractAction{

    private HuntingControl control;

    public EditAnimal(HuntingControl control){
        super("Edit");
        this.control = control;
    }

    public void actionPerformed(ActionEvent e){
        int reply = JOptionPane.showConfirmDialog(null, "Edit animal", "Edit Animal", JOptionPane.OK_CANCEL_OPTION);
        control.update();
    }

}
