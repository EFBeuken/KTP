package controller;

import view.NewProfileFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Emily on 13/12/2017.
 */
public class NewProfileAction extends AbstractAction implements Observer{

    private HuntingControl control;
    private NewProfileFrame frame;

    public NewProfileAction(HuntingControl control, NewProfileFrame frame){
        super("Save");
        this.control = control;
        this.frame = frame;
        control.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(frame.getName());
        System.out.println(frame.getGun());
    }

    @Override
    public void update(Observable observed, Object message) {

    }
}
