package airportInformationSystem.working;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddTicket extends JFrame{
    static AddTicket frame = null;

    private JComponent data;

    public static void Create() {
        frame = new AddTicket();
    }

    public AddTicket() {
        super("add ticket type");
        class MyTextField extends JTextField {
            public MyTextField(String text) {
                super(text);
                setBounds(50, 70, 100,25);
            }
            @Override
            public String getName() {
                return getText();
            }
        }
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, 200, 250);
        data = panel;
        add(panel);
        panel.setBackground(new Color(147, 255, 255));
        this.getContentPane().setBackground(new Color(147, 255, 255));
        setSize(200, 200);
        MyTextField textField = new MyTextField("");
        setLayout(null);
        textField.setBounds(50, 70, 100,25);
        add(textField);
        data.add(textField);
        textField = new MyTextField("");
        setLayout(null);
        textField.setBounds(50, 100, 100, 25);
        add(textField);
        data.add(textField);
        class AddButton extends JButton{
            public AddButton(int posX, int posY, int sizeX, int sizeY) {
                super("add data");
                setBounds(posX, posY, sizeX, sizeY);
                setText("add data");
                setVisible(true);
                class AddData implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String sql;
                        sql = "SELECT COUNT(*) FROM TICKETS";
                        ResultSet result = Scripts.execute(sql).getKey();
                        int count = 0;
                        try {
                            if (result.next()) {
                                count = result.getInt(1);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        sql =
                                "INSERT INTO TICKETS VALUES (" + (count + 1) + ", " + AddTicket.frame.data.getComponent(0).getName() + ", " + AddTicket.frame.data.getComponent(1).getName() +")";
                        Scripts.execute(sql);
                        AddTicketType.frame.setVisible(false);
                    }
                }
                addActionListener(new AddData());
            }
        }
        add(new AddButton(50, 130, 100, 25));
        setVisible(true);
    }
}
