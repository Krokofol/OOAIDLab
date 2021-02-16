package project.parts.other;

import project.DataBase;

public class Creator {

    public static void createTable() {
        createSocketTable();
        createFormFactorTable();
        createChipsetTable();
        createCaseSizeTable();
    }

    private static void createChipsetTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS CHIPSET ("
                + "Chipset VARCHAR(40) PRIMARY KEY);";
        DataBase.executeSql(sql, "create chipset table if not exists\n");
        for (Chipset chipset : Chipset.values()) {
            DataBase.executeSql("INSERT INTO CHIPSET VALUES ('" + chipset.toString() + "');", "insert into chipset data\n");
        }
    }

    private static void createFormFactorTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS FORMFACTOR ("
                + "FormFactor VARCHAR(40) PRIMARY KEY);";
        DataBase.executeSql(sql, "create formfactor table if not exists\n");
        for (FormFactor formFactor : FormFactor.values()) {
            DataBase.executeSql("INSERT INTO FORMFACTOR VALUES ('" + formFactor.toString() + "');", "insert into formfactor data\n");
        }
    }

    private static void createSocketTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS SOCKET ("
                + "Socket VARCHAR(40) PRIMARY KEY);";
        DataBase.executeSql(sql, "create socket table if not exists\n");
        for (Socket socket : Socket.values()) {
            DataBase.executeSql("INSERT INTO SOCKET VALUES ('" + socket.toString() + "');", "insert into socket data\n");
        }
    }

    private static void createCaseSizeTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS CASESIZE ("
                + "CaseSize VARCHAR(40) PRIMARY KEY);";
        DataBase.executeSql(sql, "create casesize table if not exists\n");
        for (CaseSize caseSize : CaseSize.values()) {
            DataBase.executeSql("INSERT INTO CASESIZE VALUES ('" + caseSize.toString() + "');", "insert into casesize data\n");
        }
    }
}
