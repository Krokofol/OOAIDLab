package project.ui;

import project.Pc;

public class Ui {

    private static Pc pc = new Pc();
    private static CpuFrame cpuFrame = new CpuFrame();
    private static BuildFrame buildFrame = new BuildFrame();
    private static MbFrame mbFrame = new MbFrame();

    public static MbFrame getMbFrame() {
        return mbFrame;
    }

    public static CpuFrame getCpuFrame() {
        return cpuFrame;
    }

    public static BuildFrame getBuildFrame() {
        return buildFrame;
    }

    public static Pc getPc() {
        return pc;
    }
}
