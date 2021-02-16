package project.ui;

import project.ui.Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuildFrame extends JFrame {

    private JPanel centralPanel;
    private JScrollPane scroll;

    private ArrayList<JLabel> labels = new ArrayList<>();

    public BuildFrame() {
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(new BorderLayout());
        setName("Сборка компьютера");

        centralPanel = new JPanel();
        setLocationRelativeTo(null);
        //centralPanel.setBackground(new Color(250, 250, 200));
        centralPanel.setLayout(new GridBagLayout());

        scroll = new JScrollPane(centralPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton savePcButton = new JButton("Save Pc");
        savePcButton.addActionListener(new SavePcAction());

        add(scroll, BorderLayout.CENTER);
        //add(centralPanel, BorderLayout.CENTER);
        add(savePcButton, BorderLayout.SOUTH);

        addCpuInfo();
        addMotherBoardInfo();
    }

    @Override
    public void setVisible(boolean b) {
        if (!b) {
            super.setVisible(false);
            return;
        }
        update();
        super.setVisible(true);
    }

    public void update() {
        for (JLabel label : labels) {
            label.setVisible(false);
            centralPanel.remove(label);
        }
        labels = new ArrayList<>();
        GridBagConstraints gbc = new GridBagConstraints();
        addCpuInfo(gbc);
        addMbInfo(gbc);
    }

    private void addMotherBoardInfo() {
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel mbLabel = new JLabel("MB : ");
        JButton mbSelectButton = new JButton("select MB");
        JButton mbDropButton = new JButton("drop MB");
        mbSelectButton.addActionListener(new SelectMBAction());
        mbDropButton.addActionListener(new DropMBAction());
        gbc.gridx = 0;
        gbc.gridy = 2;
        centralPanel.add(mbLabel, gbc);
        addMbInfo(gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        centralPanel.add(mbSelectButton, gbc);
        gbc.gridy = 3;
        gbc.gridx = 1;
        centralPanel.add(mbDropButton, gbc);
    }

    private void addMbInfo(GridBagConstraints gbc) {
        String text;
        if (Ui.getPc().getMotherBoard() == null)
            text = "NO MB";
        else
            text = Ui.getPc().getMotherBoard().getName();
        JLabel mbNameLabel = new JLabel(text);
        gbc.gridy = 2;
        gbc.gridx = 1;
        centralPanel.add(mbNameLabel,gbc);
        labels.add(mbNameLabel);
    }

    private void addCpuInfo() {
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel cpuLabel = new JLabel("CPU : ");
        JButton cpuSelectButton = new JButton("select CPU");
        cpuSelectButton.addActionListener(new SelectCpuAction());
        JButton cpuDropButton = new JButton("drop CPU");
        cpuDropButton.addActionListener(new DropCpuAction());
        gbc.gridx = 0;
        gbc.gridy = 0;
        centralPanel.add(cpuLabel, gbc);
        addCpuInfo(gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centralPanel.add(cpuSelectButton, gbc);
        gbc.gridx = 1;
        centralPanel.add(cpuDropButton, gbc);
    }

    private void addCpuInfo(GridBagConstraints gbc) {
        String text;
        if (Ui.getPc().getCpu() == null)
            text = "NO CPU";
        else
            text = Ui.getPc().getCpu().getName();
        System.out.println(text);
        JLabel cpuNameLabel = new JLabel(text);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centralPanel.add(cpuNameLabel,gbc);
        labels.add(cpuNameLabel);
    }

    private static class SelectMBAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getBuildFrame().setVisible(false);
            Ui.getMbFrame().setVisible(true);
        }
    }

    private static class DropMBAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getPc().setMotherBoard(null);
            Ui.getBuildFrame().update();
        }
    }

    private static class SelectCpuAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getBuildFrame().setVisible(false);
            Ui.getCpuFrame().setVisible(true);
        }
    }

    private static class DropCpuAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getPc().setCpu(null);
            Ui.getBuildFrame().update();
        }
    }

    private static class SavePcAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getPc().printPc();
        }
    }

}
