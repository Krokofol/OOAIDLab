package airportInformationSystem.working;

import airportInformationSystem.Main;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Scripts {
    public static void run () {
        TicketType.createTableTicketTypeIfNotExists();
        Tickets.createTableTicketsIfNotExists();
    }

    public static class Tickets {
        public static void createTableTicketsIfNotExists() {
            if (!tableExistence("TICKETS")) {
                System.out.println("creating table tickets");
                createTicketsTable();
            }
        }
        public static Pair<ResultSet, Integer> createTicketsTable() {
            String sql = "create table TICKETS (" +
                    "TICKET_ID   INTEGER       PRIMARY KEY," +
                    "HUMAN_ID    INTEGER       NOT NULL," +
                    "TYPE_ID     INTEGER       NOT NULL," +
                    "FOREIGN KEY (TYPE_ID)  REFERENCES TICKET_TYPE (TYPE_ID))";
            return execute(sql);
        }
    }

    public static class TicketType {
        public static void createTableTicketTypeIfNotExists() {
            if (!tableExistence("TICKET_TYPE")) {
                System.out.println("creating table ticketType");
                createTicketTypeTable();
            }
        }
        public static Pair<ResultSet, Integer> createTicketTypeTable() {
            String sql = "create table TICKET_TYPE (" +
                    "TYPE_ID     INTEGER       PRIMARY KEY," +
                    "TYPE_NAME   VARCHAR(40)   NOT NULL UNIQUE)";
            return execute(sql);
        }
    }

    public static boolean dropAll() {
        boolean result;
        result = dropTable("TICKETS");
        if (!result) {
            return true;
        }
        result = dropTable("TICKET_TYPE");
        if (!result) {
            return true;
        }
        return false;
    }

    public static boolean dropTable(String tableName) {
        String sql = "DROP TABLE " + tableName;
        return execute(sql).getValue() == 0;
    }

    public static boolean tableExistence(String tableName) {
        return selectAll(tableName).getValue() == 0;
    }

    public static Pair<ResultSet, Integer> selectAll(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        return execute(sql);
    }

    public static Pair<ResultSet, Integer> execute(String sql) {
        System.out.println(sql);
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(sql);
            return new Pair<ResultSet, Integer>(preparedStatement.executeQuery(), 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Pair<ResultSet, Integer>(null, e.getErrorCode());
        }
    }
}
