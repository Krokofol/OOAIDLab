package airportInformationSystem.working;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkingWindow {

    public static WorkingFrame exemplar = null;
    private static boolean isCreated = false;

    public static void create() {
        if (isCreated) {
            return;
        }
        exemplar = new WorkingFrame(800, 600, "Working Window");
        isCreated = true;
    }

    public static void free() {
        if (!isCreated) {
            return;
        }
        exemplar.setVisible(false);
        exemplar = null;
        isCreated = false;
    }

    private static class WorkingFrame extends JFrame {
        public static JComponent data = null;

        public WorkingFrame(int sizeX, int sizeY, String name) {
            super(name);
            Scripts.run();
            this.getContentPane().setBackground(new Color(147, 255, 255));
            this.setSize(sizeX, sizeY);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setLayout(null);
            class DropAllTablesButton extends JButton {
                public DropAllTablesButton(int posX, int posY, int sizeX, int sizeY) {
                    super("drop all");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("drop all");
                    setVisible(true);
                    class DropAction implements ActionListener {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (WorkingFrame.data != null) {
                                WorkingFrame.data.setVisible(false);
                            }
                            boolean result = Scripts.dropAll();
                            String text = "drop success";
                            if (result) {
                                text = "drop error";
                            }
                            JLabel textField = new JLabel(text);
                            textField.setBounds(200, 50, 100, 25);
                            textField.setVisible(true);
                            WorkingFrame.data = textField;
                            WorkingWindow.exemplar.add(textField);
                        }
                    }
                    addActionListener(new DropAction());
                }
            }
            class CreateAllTablesButton extends JButton {
                public CreateAllTablesButton(int posX, int posY, int sizeX, int sizeY) {
                    super("create all");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("create all");
                    setVisible(true);
                    class CreateAction implements ActionListener {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (WorkingFrame.data != null) {
                                WorkingFrame.data.setVisible(false);
                            }
                            Scripts.run();
                            JLabel textField = new JLabel("creat success");
                            textField.setBounds(200, 50, 100, 25);
                            textField.setVisible(true);
                            WorkingFrame.data = textField;
                            WorkingWindow.exemplar.add(textField);
                        }
                    }
                    addActionListener(new CreateAction());
                }
            }
            class ShowTicketsTableData extends JButton{
                public ShowTicketsTableData(int posX, int posY, int sizeX, int sizeY) {
                    super("show tickets");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("show tickets");
                    setVisible(true);
                    class ShowTicketsDataAction implements ActionListener{
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (WorkingFrame.data != null) {
                                WorkingFrame.data.setVisible(false);
                            }
                            data = new JPanel(null);
                            data.setBounds(200, 0, 600, 600);
                            String sql;
                            sql = "SELECT * FROM TICKETS " +
                                    "INNER JOIN TICKET_TYPE ON TICKETS.TYPE_ID = TICKET_TYPE.TYPE_ID";
                            ResultSet resultSet = Scripts.execute(sql).getKey();
                            ArrayList<String> result = new ArrayList<>();
                            try {
                                while (resultSet.next()) {
                                    String row = resultSet.getString("TICKET_ID") + "/" +
                                            resultSet.getString("HUMAN_ID") + "/" +
                                            resultSet.getString("TYPE_NAME") + "/";
                                    result.add(row);
                                }
                            } catch (SQLException error) {
                                error.printStackTrace();
                            }
                            int id = 0;
                            int posX = 0;
                            int posY = 50;
                            class UpdateButton extends JButton {
                                public final int firstId;
                                public UpdateButton(int posX, int posY, int sizeX, int sizeY, int firstId) {
                                    super("show tickets");
                                    this.firstId = firstId;
                                    setBounds(posX, posY, sizeX, sizeY);
                                    setText("show tickets");
                                    setVisible(true);
                                    System.out.println("new update button");
                                    class UpdateAction implements ActionListener {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            System.out.println("try to find ticket_type where type_name is : " + WorkingFrame.data.getComponent(firstId + 2).getName());
                                            String sql1 = "SELECT * FROM TICKET_TYPE WHERE TYPE_NAME = '" +
                                                    WorkingFrame.data.getComponent(firstId + 2).getName() + "'";
                                            ResultSet resultSet = Scripts.execute(sql1).getKey();
                                            String typeId = "null";
                                            try {
                                                if (resultSet.next()) {
                                                    typeId = resultSet.getString("TYPE_ID");
                                                }
                                            } catch (SQLException error) {
                                                error.printStackTrace();
                                            }
                                            System.out.println("human id is : " + WorkingFrame.data.getComponent(firstId + 1).getName());
                                            String sql = "UPDATE TICKETS SET HUMAN_ID = " +
                                                    WorkingFrame.data.getComponent(firstId + 1).getName() +
                                                    ", TYPE_ID = " +
                                                    typeId +
                                                    " WHERE TICKET_ID = " +
                                                    WorkingFrame.data.getComponent(firstId).getName();
                                            Scripts.execute(sql);
                                        }
                                    }
                                    addActionListener(new UpdateAction());
                                }
                            }
                            for (String line : result) {
                                String[] args = line.split("/");
                                for (String arg : args) {
                                    out(posX, posY, arg);
                                    id++;
                                    posX += 110;
                                }
                                WorkingFrame.data.add(new UpdateButton(posX, posY, 100, 25, id - 3));
                                id++;
                                posX = 0;
                                posY += 30;
                            }
                            WorkingFrame.data.setVisible(true);
                            WorkingWindow.exemplar.add(data);
                        }

                        public void out(int posX, int posY, String text) {
                            class MyTextField extends JTextField {
                                public MyTextField(String text) {
                                    super(text);
                                }
                                @Override
                                public String getName() {
                                    return getText();
                                }
                            }
                            MyTextField textField = new MyTextField(text);
                            textField.setBounds(posX, posY, 100, 25);
                            textField.setVisible(true);
                            WorkingFrame.data.add(textField);
                        }
                    }
                    addActionListener(new ShowTicketsDataAction());
                }
            }
            class AddStartData extends JButton {
                public AddStartData(int posX, int posY, int sizeX, int sizeY) {
                    super("add data");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("add data");
                    setVisible(true);
                    class AddData implements ActionListener {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String sql;
                            sql = "INSERT INTO TICKET_TYPE  VALUES (1,'business')";
                            Scripts.execute(sql);
                            System.out.println("first added");
                            sql = "INSERT INTO TICKETS      VALUES (1,1,1)";
                            Scripts.execute(sql);
                            System.out.println("second added");
                            sql = "INSERT INTO TICKET_TYPE  VALUES (2,'econom')";
                            Scripts.execute(sql);
                            System.out.println("third added");
                        }
                    }
                    addActionListener(new AddData());
                }
            }
            class AddTicketTypeButton extends JButton {
                public AddTicketTypeButton(int posX, int posY, int sizeX, int sizeY) {
                    super("add type");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("add type");
                    setVisible(true);
                    class AddTicketTypeAction implements ActionListener {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            AddTicketType.Create();
                        }
                    }
                    addActionListener(new AddTicketTypeAction());
                }
            }
            class ShowTicketTypeTableData extends JButton{
                public ShowTicketTypeTableData(int posX, int posY, int sizeX, int sizeY) {
                    super("show type");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("show type");
                    setVisible(true);
                    class ShowTicketsDataAction implements ActionListener{
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (WorkingFrame.data != null) {
                                WorkingFrame.data.setVisible(false);
                            }
                            data = new JPanel(null);
                            data.setBounds(200, 0, 600, 600);
                            String sql;
                            sql = "SELECT * FROM TICKET_TYPE";
                            ResultSet resultSet = Scripts.execute(sql).getKey();
                            ArrayList<String> result = new ArrayList<>();
                            try {
                                while (resultSet.next()) {
                                    String row = resultSet.getString("TYPE_ID") + "/" +
                                            resultSet.getString("TYPE_NAME") + "/";
                                    result.add(row);
                                }
                            } catch (SQLException error) {
                                error.printStackTrace();
                            }
                            int id = 0;
                            int posX = 0;
                            int posY = 50;
                            class UpdateButton extends JButton {
                                public final int firstId;
                                public UpdateButton(int posX, int posY, int sizeX, int sizeY, int firstId) {
                                    super("show tickets");
                                    this.firstId = firstId;
                                    setBounds(posX, posY, sizeX, sizeY);
                                    setText("show tickets");
                                    setVisible(true);
                                    System.out.println("new update button");
                                    class UpdateAction implements ActionListener {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String sql = "UPDATE TICKET_TYPE SET TYPE_NAME = '" +
                                                    WorkingFrame.data.getComponent(firstId + 1).getName() +
                                                    "' WHERE TYPE_ID = " +
                                                    WorkingFrame.data.getComponent(firstId).getName();
                                            Scripts.execute(sql);
                                        }
                                    }
                                    addActionListener(new UpdateAction());
                                }
                            }
                            for (String line : result) {
                                String[] args = line.split("/");
                                for (String arg : args) {
                                    out(posX, posY, arg);
                                    id++;
                                    posX += 110;
                                }
                                WorkingFrame.data.add(new UpdateButton(posX, posY, 100, 25, id - 2));
                                id++;
                                posX = 0;
                                posY += 30;
                            }
                            WorkingFrame.data.setVisible(true);
                            WorkingWindow.exemplar.add(data);
                        }

                        public void out(int posX, int posY, String text) {
                            class MyTextField extends JTextField {
                                public MyTextField(String text) {
                                    super(text);
                                }
                                @Override
                                public String getName() {
                                    return getText();
                                }
                            }
                            MyTextField textField = new MyTextField(text);
                            textField.setBounds(posX, posY, 100, 25);
                            textField.setVisible(true);
                            WorkingFrame.data.add(textField);
                        }
                    }
                    addActionListener(new ShowTicketsDataAction());
                }
            }
            class AddTicketButton extends JButton {
                public AddTicketButton(int posX, int posY, int sizeX, int sizeY) {
                    super("add ticket");
                    setBounds(posX, posY, sizeX, sizeY);
                    setText("add ticket");
                    setVisible(true);
                    class AddTicketTypeAction implements ActionListener {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            AddTicket.Create();
                        }
                    }
                    addActionListener(new AddTicketTypeAction());
                }
            }
            add(new DropAllTablesButton(50, 50, 100, 25));
            add(new CreateAllTablesButton(50, 80, 100, 25));
            add(new ShowTicketsTableData(50, 110, 100, 25));
            add(new ShowTicketTypeTableData(50, 140, 100, 25));
            add(new AddStartData(50, 170, 100, 25));
            add(new AddTicketButton(50, 200, 100, 25));
            add(new AddTicketTypeButton(50, 230, 100, 25));
            this.setVisible(true);
        }
    }
}
