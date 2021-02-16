package project.ui;

import project.parts.MotherBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MbFrame extends JFrame {
    private JPanel centralPanel;
    private JScrollPane scroll;

    public MbFrame(){
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName("Выбор процессора");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        centralPanel = new JPanel();
        //centralPanel.setBackground(new Color(250, 250, 200));
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
        MotherBoard[] motherBoards = Ui.getPc().selectMotherBoard();
        int i = 0;
        for(MotherBoard motherBoard : motherBoards) {
            JPanel mbPanel = new JPanel();
            mbPanel.setLayout(new GridBagLayout());
            JPanel mbInfo = new JPanel();
            mbInfo.setLayout(new GridBagLayout());

            gbc.gridx = 0;
            gbc.gridy = 0;
            mbInfo.add(new Label("Socket : "), gbc);
            gbc.gridx = 1;
            mbInfo.add(new Label(motherBoard.getSocket().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            mbInfo.add(new Label("Chipset : "), gbc);
            gbc.gridx = 1;
            mbInfo.add(new Label(motherBoard.getChipset().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            mbInfo.add(new Label("PciExpress : "), gbc);
            gbc.gridx = 1;
            mbInfo.add(new Label(motherBoard.getPciExpress().toString()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            mbInfo.add(new Label("FormFactor : "), gbc);
            gbc.gridx = 1;
            mbInfo.add(new Label(motherBoard.getFormFactor().toString()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            mbPanel.add(new JLabel(motherBoard.getName()), gbc);
            gbc.gridy = 1;
            mbPanel.add(mbInfo);
            gbc.gridy = 2;
            JButton selectCpuButton = new JButton("select");
            selectCpuButton.addActionListener(new SelectMbAction(motherBoard));
            mbPanel.add(selectCpuButton, gbc);
            gbc.gridx = 0;
            gbc.gridy = i;
            centralPanel.add(mbPanel, gbc);
            i++;
        }
        super.setVisible(true);
    }

    public static class BackAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getMbFrame().setVisible(false);
            Ui.getBuildFrame().setVisible(true);
        }
    }

    public static class SelectMbAction implements ActionListener {
        private MotherBoard motherBoard;

        public SelectMbAction(MotherBoard motherBoard) {
            this.motherBoard = motherBoard;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Ui.getPc().setMotherBoard(motherBoard);
            Ui.getMbFrame().setVisible(false);
            Ui.getBuildFrame().setVisible(true);
        }
    }

}
