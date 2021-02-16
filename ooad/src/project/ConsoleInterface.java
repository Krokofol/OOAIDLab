package project;

import project.parts.Case;
import project.parts.Cpu;
import project.parts.MotherBoard;

import java.util.Scanner;

public class ConsoleInterface {

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        Pc pc = new Pc();
        Cpu[] cpus;
        MotherBoard[] motherBoards;
        Case[] cases;
        int id;
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            switch (input) {
                case("cpu") :
                    cpus = pc.selectCpu();
                    id = scanner.nextInt();
                    if (id == -1)
                        break;
                    pc.setCpu(cpus[id]);
                    break;
                case("mb") :
                    motherBoards = pc.selectMotherBoard();
                    id = scanner.nextInt();
                    if (id == -1)
                        break;
                    pc.setMotherBoard(motherBoards[id]);
                    break;
                case("case") :
                    cases = pc.selectCase();
                    id = scanner.nextInt();
                    if (id == -1)
                        break;
                    pc.setaCase(cases[id]);
                    break;
                case("exit") :
                    pc.printPc();
                    return;
                case("drop cpu") :
                    pc.setCpu(null);
                    break;
                case("drop mb") :
                    pc.setMotherBoard(null);
                    break;
                case("drop case") :
                    pc.setaCase(null);
                    break;
            }
        }
    }


}
