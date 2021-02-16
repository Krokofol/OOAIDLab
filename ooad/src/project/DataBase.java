package project;

import project.parts.Case;
import project.parts.Cpu;
import project.parts.MotherBoard;
import project.parts.other.Creator;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static Connection connection;
    private static String dataBasePath;
    private static String driver;

    public static void initialization() {
        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper.substring(0, helper.length() - 1);
        boolean preload = !(new File(currentDir + "db/parts.mv.db").exists());
        preload("jdbc:h2:/" + currentDir + "db/parts", "org.h2.Driver");
        if (preload) {
            Creator.createTable();
            Cpu.createCpuTable();
            MotherBoard.createMotherBoardTable();
            Case.createCaseTable();
        }
    }

    public static void preload(String dataBasePath, String driver){
        if(!checkDriver(driver)) {
            System.exit(1);
        };
        DataBase.dataBasePath = dataBasePath;
        try {
            connection = DriverManager.getConnection(dataBasePath);
            System.out.print("DataBase created/opened\n");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean checkDriver(String driver) {
        try {
            Class.forName(driver);
            DataBase.driver = driver;
            System.out.print("Driver was found\n");
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.print("Driver was not found\n");
            ex.printStackTrace();
            return false;
        }
    }

    public static void executeSql(String sql, String description) {
        if (description.toCharArray()[description.length() - 1] != '\n')
            description += "\n";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            System.out.print(description);
        } catch (SQLException e) {
            System.out.print("Error : " + description);
            e.printStackTrace();
        }
    }

    public static ArrayList<String> selectSql(String sql, String description) {
        ArrayList<String> result = new ArrayList<String>();
        if (description.toCharArray()[description.length() - 1] != '\n')
            description += "\n";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(resultSet.getString(i)).append("/");
                }
                result.add(row.toString());
            }
            statement.close();
            System.out.print(description);
        } catch (SQLException e) {
            System.out.print("Error : " + description);
            e.printStackTrace();
        }
        return result;
    }

    public static void ending() {
        try {
            connection.close();
            System.out.print("DataBase closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
