package main;


import boxEngine.BoxGame1;
import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;

public class JboxMain {

    public static void main(String[] args) {
        System.out.println("we in there");
        TestbedModel model = new TestbedModel();         // create our model

// add tests
        TestList.populateModel(model);                   // populate the provided testbed tests
        model.addCategory("My Super Tests");             // add a category
        model.addTest(new VerticalStack());                // add our test
        model.addTest(new BoxGame1());
        // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));

        TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

        JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
// etc
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
