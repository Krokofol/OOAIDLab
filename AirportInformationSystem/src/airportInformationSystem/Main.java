package airportInformationSystem;

import airportInformationSystem.graphicalUserInterface.AuthorizationWindow;

import java.sql.*;

public class Main {
    public static Connection connection = null;

    public static void main(String[] args) {
        AuthorizationWindow.create();
    }
}
