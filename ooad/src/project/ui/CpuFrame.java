package project.ui;

import project.parts.Cpu;
import project.ui.Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CpuFrame extends JFrame {
    private JPanel centralPanel;
    private JScrollPane scroll;

    public CpuFrame(){
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName("Выбор процессора");
        setLayout(new BorderLayout());

        centralPanel = new JPanel();
        setLocationRelativeTo(null);
        centralPanel.setLayout(new GridBagLayout());

        scroll = new JScrollPane(centralPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new BackAction());

        add(scroll, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean b) {
        if (!b) {
            super.setVisible(false);
            return;
        }
        centralPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        Cpu[] cpus = Ui.getPc().selectCpu();
        int i = 0;
        for(Cpu cpu : cpus) {
            JPanel cpuPanel = new JPanel();
            cpuPanel.setLayout(new GridBagLayout());
            JPanel cpuInfo = new JPanel();
            cpuInfo.setLayout(new GridBagLayout());

            gbc.gridx = 0;
            gbc.gridy = 0;
            cpuInfo.add(new Label("Socket : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getSocket().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            cpuInfo.add(new Label("Cores : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getCores().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            cpuInfo.add(new Label("Threads : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getThreads().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            cpuInfo.add(new Label("Rate : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getRate().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            cpuInfo.add(new Label("Boost rate : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getBoostRate().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 5;
            cpuInfo.add(new Label("Tdp : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getTdp().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 6;
            cpuInfo.add(new Label("L3 : "), gbc);
            gbc.gridx = 1;
            cpuInfo.add(new Label(cpu.getL3().toString()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            cpuPanel.add(new JLabel(cpu.getName()), gbc);
            gbc.gridy = 1;
            cpuPanel.add(cpuInfo);
            gbc.gridy = 2;
            JButton selectCpuButton = new JButton("select");
            selectCpuButton.addActionListener(new SelectCpuAction(cpu));
            cpuPanel.add(selectCpuButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = i;
            centralPanel.add(cpuPanel, gbc);
            i++;
        }
        super.setVisible(true);
    }

    public static class BackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getCpuFrame().setVisible(false);
            Ui.getBuildFrame().setVisible(true);
        }
    }

    public static class SelectCpuAction implements ActionListener {
        private Cpu cpu;

        public SelectCpuAction(Cpu cpu) {
            this.cpu = cpu;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getPc().setCpu(cpu);
            Ui.getCpuFrame().setVisible(false);
            Ui.getBuildFrame().setVisible(true);
        }
    }

}
