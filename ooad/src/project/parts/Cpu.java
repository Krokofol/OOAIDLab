package project.parts;

import project.DataBase;
import project.parts.other.Socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cpu extends Part {

    public static void createCpuTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS CPU ("
                + "CpuName   VARCHAR(40) PRIMARY KEY,"  //0
                + "Socket    VARCHAR(40) NOT NULL,"     //1
                + "Cores     INTEGER     NOT NULL,"     //2
                + "Threads   INTEGER     NOT NULL,"     //3
                + "Tdp       INTEGER     NOT NULL,"     //4
                + "Rate      DOUBLE      NOT NULL,"     //5
                + "BoostRate DOUBLE      NOT NULL,"     //6
                + "L3        INTEGER     NOT NULL,"   //7
                + "FOREIGN KEY (Socket) REFERENCES SOCKET (Socket));";
        DataBase.executeSql(sql, "create cpu table if not exists\n");
        insertFromFile();
    }

    private static void insertFromFile() {
        File file = new File("cpuData.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNext()) {
                Cpu.insertCpu(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void insertCpu(String sqlInsertData) {
        String[] args = sqlInsertData.split(", ");
        args[0] = "'" + args[0] + "'";
        args[1] = "'" + args[1] + "'";
        StringBuilder sqlData = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sqlData.append(args[i]);
            if (args.length - 1 == i)
                break;
            sqlData.append(",");
        }
        String sql = "INSERT INTO cpu VALUES(" + sqlData.toString() + ");";
        DataBase.executeSql(sql, "insert into cpu data");
    }

    public static void printAllCpus() {
        String sql = "SELECT * FROM CPU";
        ArrayList<String> res = DataBase.selectSql(sql, "selected all from CPU\n");
        for (String string : res) {
            new Cpu(string).printCpu();
        }
    }

    public static Cpu[] selectCpus(String whereString) {
        String sql = "SELECT * FROM CPU " + whereString;
        ArrayList<String> res = DataBase.selectSql(sql, "select cpu " + whereString + "\n");
        ArrayList<Cpu> result = new ArrayList<>();
        for (String string : res) {
            result.add(new Cpu(string));
        }
        return result.toArray(Cpu[]::new);
    }

    private Socket socket;
    private Integer cores;
    private Integer threads;
    private Integer tdp;

    private Double rate;
    private Double boostRate;
    private Integer L3;

    public Cpu(String cpuArgs) {
        String[] args = cpuArgs.split("/");
        if (args.length != 8) {
            System.out.print("Error : bad cpu args\n");
            System.exit(1);
        }
        name = args[0];
        socket = Socket.valueOf(args[1]);
        cores = Integer.parseInt(args[2]);
        threads = Integer.parseInt(args[3]);
        tdp = Integer.parseInt(args[4]);
        rate = Double.parseDouble(args[5]);
        boostRate = Double.parseDouble(args[6]);
        L3 = Integer.parseInt(args[7]);
    }

    public void printCpu() {
        System.out.print(name + "/" + socket + "/" + cores + "/" + threads + "/" + tdp + "/" + rate + "/" + boostRate + "/" + L3 + "\n");
    }

    public Socket getSocket() {
        return socket;
    }

    public Double getBoostRate() {
        return boostRate;
    }

    public Integer getCores() {
        return cores;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getL3() {
        return L3;
    }

    public Integer getTdp() {
        return tdp;
    }

    public Integer getThreads() {
        return threads;
    }
}
