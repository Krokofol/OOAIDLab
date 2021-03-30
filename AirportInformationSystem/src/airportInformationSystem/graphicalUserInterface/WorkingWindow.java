package airportInformationSystem.graphicalUserInterface;

import javax.swing.*;
import java.awt.*;

public class WorkingWindow extends JFrame {

    public WorkingWindow(String text) {
        super("window2");
        setSize(300, 200);

        Label label = new Label(text);
        label.setBounds(150, 100, 200, 100);
        add(label);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
