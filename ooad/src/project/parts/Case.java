package project.parts;

import project.DataBase;
import project.Helper;
import project.parts.other.CaseSize;
import project.parts.other.FormFactor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Handler;

public class Case extends Part {

    public static void createCaseTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS CASES ("
                + "CaseName        VARCHAR(40) PRIMARY KEY,"
                + "CaseSize        VARCHAR(40) NOT NULL,"
                + "FormFactor      VARCHAR(40) NOT NULL,"
                + "FOREIGN KEY (FormFactor) REFERENCES FORMFACTOR (FormFactor),"
                + "FOREIGN KEY (CaseSize) REFERENCES CASESIZE (CaseSize));";
        DataBase.executeSql(sql, "create case table if not exists\n");
        insertFromFile();
    }

    private static void insertFromFile() {
        File file = new File("caseData.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNext()) {
                Case.insertCase(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void insertCase(String sqlInsertData) {
        String[] args = sqlInsertData.split(", ");
        args[0] = Helper.toSqlString(args[0]);
        args[1] = Helper.toSqlString(args[1]);
        args[2] = Helper.toSqlString(args[2]);
        StringBuilder sqlData = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sqlData.append(args[i]);
            if (args.length - 1 == i)
                break;
            sqlData.append(",");
        }
        String sql = "INSERT INTO CASES VALUES(" + sqlData.toString() + ");";
        DataBase.executeSql(sql, "insert into case data");
    }

    public static void printAllCases() {
        String sql = "SELECT * FROM CASES";
        ArrayList<String> res = DataBase.selectSql(sql, "select all from case");
        for (String string : res) {
            new Case(string).printCase();
        }
    }

    public static Case[] selectCases(String whereString) {
        String sql = "SELECT * FROM CASES " + whereString;
        ArrayList<String> res = DataBase.selectSql(sql, "select case " + whereString + "\n");
        ArrayList<Case> result = new ArrayList<>();
        for (String string : res) {
            result.add(new Case(string));
        }
        return result.toArray(Case[]::new);
    }

    private CaseSize caseSize;
    private FormFactor formFactor;

    public Case(String caseArgs) {
        String[] args = caseArgs.split("/");
        if (args.length != 3) {
            System.out.print("Error : bad case args\n");
            System.exit(1);
        }
        name = args[0];
        caseSize = CaseSize.valueOf(args[1]);
        formFactor = FormFactor.valueOf(args[2]);
    }

    public void printCase() {
        System.out.print(name + "/" + caseSize + "/" + formFactor + "\n");
    }

    public FormFactor getFormFactor() {
        return formFactor;
    }
}
