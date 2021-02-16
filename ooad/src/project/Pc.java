package project;

import project.parts.Case;
import project.parts.Cpu;
import project.parts.MotherBoard;
import project.parts.Part;

public class Pc {
    private Cpu cpu;
    private MotherBoard motherBoard;
    private Case aCase;

    public void setaCase(Case aCase) {
        this.aCase = aCase;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public void setMotherBoard(MotherBoard motherBoard) {
        this.motherBoard = motherBoard;
    }

    public Cpu[] selectCpu() {
        String whereString = "";
        if (motherBoard != null)
            whereString += " SOCKET = '" + motherBoard.getSocket().toString() + "'";
        if (!whereString.equals(""))
            whereString = "WHERE" + whereString;
        Cpu[] cpus = Cpu.selectCpus(whereString);
        for (Cpu cpu : cpus) {
            cpu.printCpu();
        }
        return cpus;
    }

    public MotherBoard[] selectMotherBoard() {
        String whereString = "";
        if (cpu != null)
            whereString += " SOCKET = '" + cpu.getSocket().toString() + "'";
        if (aCase != null)
            whereString += " FormFactor = '" + aCase.getFormFactor().toString() + "'";
        if (!whereString.equals(""))
            whereString = "WHERE" + whereString;
        MotherBoard[] motherBoards = MotherBoard.selectMotherBoards(whereString);
        for (MotherBoard motherBoard : motherBoards) {
            motherBoard.printMotherBoard();
        }
        return motherBoards;
    }

    public Case[] selectCase() {
        String whereString = "";
        if (motherBoard != null)
            whereString += " FormFactor = '" + motherBoard.getFormFactor() + "'";
        if (!whereString.equals(""))
            whereString = "WHERE" + whereString;
        Case[] cases = Case.selectCases(whereString);
        for (Case aCase : cases) {
            aCase.printCase();
        }
        return cases;
    }

    public void printPc() {
        if (cpu != null)
            cpu.printCpu();
        if (motherBoard != null)
            motherBoard.printMotherBoard();
        if (aCase != null)
            aCase.printCase();
    }

    public Cpu getCpu() {
        return cpu;
    }

    public Case getaCase() {
        return aCase;
    }

    public MotherBoard getMotherBoard() {
        return motherBoard;
    }

    public Pc() {
        cpu = null;
        motherBoard = null;
        aCase = null;
    }
}
