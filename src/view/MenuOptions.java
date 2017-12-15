package view;

import controller.EditProfile;
import controller.HuntingControl;
import controller.*;
import controller.NewProfile;

import javax.swing.*;

/**
 * Created by Emily on 13/08/2017.
 */
public class MenuOptions extends JMenuBar {

    public MenuOptions(HuntingControl control){
        JMenu profileMenu = new JMenu("Profile");
        JMenu animalMenu = new JMenu("Animals");
        JMenu helpMenu = new JMenu(new Help(control));

        //JMenuItem profileEdit = new JMenuItem(new EditProfile(control));
        JMenuItem profileNew = new JMenuItem( new NewProfile(control));
        JMenuItem profileDel = new JMenuItem( new DeleteProfile(control));

        //JMenuItem animalEdit = new JMenuItem(new EditAnimal(control));
        JMenuItem animalNew = new JMenuItem( new NewAnimal(control));
        JMenuItem animalDel = new JMenuItem( new DeleteAnimal(control));

        //profileMenu.add(profileEdit);
        profileMenu.add(profileNew);
        profileMenu.add(profileDel);

        //animalMenu.add(animalEdit);
        animalMenu.add(animalNew);
        animalMenu.add(animalDel);

        add(profileMenu);
        add(animalMenu);
        add(helpMenu);
    }
}
