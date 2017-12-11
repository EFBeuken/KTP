package controller;

import controller.HuntingControl;
import view.NewAnimalFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Emily on 30/11/2017.
 */
public class NewAnimal extends AbstractAction{

    private HuntingControl control;

    public NewAnimal(HuntingControl control){
        super("New");
        this.control = control;
    }

    public void actionPerformed(ActionEvent e){
        new NewAnimalFrame().setVisible(true);
        control.update();
    }

}
