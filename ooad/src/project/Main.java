package project;

import project.ui.Ui;

public class Main {
    public static void main(String[] args) {
        DataBase.initialization();

        Ui.getBuildFrame().setVisible(true);

        //DataBase.ending();
    }
}
