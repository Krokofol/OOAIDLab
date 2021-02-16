package project.parts;

import project.DataBase;
import project.parts.other.Chipset;
import project.parts.other.FormFactor;
import project.parts.other.Socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MotherBoard extends Part {

    public static void createMotherBoardTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS MOTHERBOARD ("
                + "MotherBoardName VARCHAR(40) PRIMARY KEY,"
                + "Socket          VARCHAR(40) NOT NULL,"
                + "Chipset         VARCHAR(40) NOT NULL,"
                + "PciExpress      DOUBLE      NOT NULL,"
                + "FormFactor      VARCHAR(40) NOT NULL,"
                + "FOREIGN KEY (FormFactor) REFERENCES FORMFACTOR (FormFactor),"
                + "FOREIGN KEY (Socket) REFERENCES SOCKET (Socket),"
                + "FOREIGN KEY (Chipset) REFERENCES CHIPSET (Chipset));";
        DataBase.executeSql(sql, "create motherboard table if not exists\n");
        insertFromFile();
    }

    private static void insertFromFile() {
        File file = new File("motherBoardData.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNext()) {
                MotherBoard.insertMotherBoard(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void insertMotherBoard(String sqlInsertData) {
        String[] args = sqlInsertData.split(", ");
        args[0] = "'" + args[0] + "'";
        args[1] = "'" + args[1] + "'";
        args[2] = "'" + args[2] + "'";
        args[4] = "'" + args[4] + "'";
        StringBuilder sqlData = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sqlData.append(args[i]);
            if (args.length - 1 == i)
                break;
            sqlData.append(",");
        }
        String sql = "INSERT INTO MOTHERBOARD VALUES(" + sqlData.toString() + ");";
        DataBase.executeSql(sql, "insert into motherboard data");
    }

    public static void printAllMotherBoards() {
        String sql = "SELECT * FROM MOTHERBOARD";
        ArrayList<String> res = DataBase.selectSql(sql, "select all from motherboards");
        for (String string : res) {
            new MotherBoard(string).printMotherBoard();
        }
    }

    public static MotherBoard[] selectMotherBoards(String whereString) {
        String sql = "SELECT * FROM MOTHERBOARD " + whereString;
        ArrayList<String> res = DataBase.selectSql(sql, "select motherboard " + whereString + "\n");
        ArrayList<MotherBoard> result = new ArrayList<>();
        for (String string : res) {
            result.add(new MotherBoard(string));
        }
        return result.toArray(MotherBoard[]::new);
    }

    private Socket socket;
    private Chipset chipset;
    private Double pciExpress;
    private FormFactor formFactor;

    public MotherBoard(String motherBoardArgs) {
        String[] args = motherBoardArgs.split("/");
        if (args.length != 5) {
            System.out.print("Error : bad motherboard args\n");
            System.exit(1);
        }
        name = args[0];
        socket = Socket.valueOf(args[1]);
        chipset = Chipset.valueOf(args[2]);
        pciExpress = Double.parseDouble(args[3]);
        formFactor = FormFactor.valueOf(args[4]);
    }

    public void printMotherBoard() {
        System.out.print(name + "/" + socket + "/" + chipset + "/" + pciExpress + "/" + formFactor + "\n");
    }

    public Socket getSocket() {
        return this.socket;
    }

    public FormFactor getFormFactor() {
        return formFactor;
    }

    public Chipset getChipset() {
        return chipset;
    }

    public Double getPciExpress() {
        return pciExpress;
    }
}
