package airportInformationSystem.graphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class AuthorizationWindow {
    private static AuthorizationWindow exemplar;
    private static boolean isCreated = false;

    public static void create() {
        if (!isCreated) {
            exemplar = new AuthorizationWindow();
            isCreated = true;
        }
    }

    public static void free() {
        if (isCreated) {
            exemplar.frame.setVisible(false);
        }
        exemplar.frame = null;
        exemplar = null;
        isCreated = false;
    }

    private AuthorizationFrame frame;

    private AuthorizationWindow() {
        frame = new AuthorizationFrame(400, 400);
    }

    private static class AuthorizationFrame extends JFrame {
        private final JTextField login;
        private final JPasswordField password;

        public AuthorizationFrame(int sizeX, int sizeY) {
            super("Authorization Window");
            this.getContentPane().setBackground(new Color(147, 255, 255));
            this.setSize(sizeX, sizeY);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setLayout(null);
            JPanel panel = new JPanel();
            panel.setLayout(null);

            class AuthorizationButton extends JButton {
                public AuthorizationButton(String name, int posX, int posY, int sizeX, int sizeY) {
                    super(name);
                    this.setBounds(posX, posY, sizeX, sizeY);
                    this.setText(name);
                    this.setVisible(true);

                    class AuthorizatuionAction implements ActionListener {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                try {
                                    Class.forName("oracle.jdbc.driver.OracleDriver");
                                } catch (ClassNotFoundException error) {
                                    error.printStackTrace();
                                }

                                String url = "jdbc:oracle:thin:@84.237.50.81:1521:";
                                Properties props = new Properties();
                                props.setProperty("user", login.getText());
                                props.setProperty("password", password.getText());


                                TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
                                TimeZone.setDefault(timeZone);
                                Locale.setDefault(Locale.ENGLISH);

                                Connection conn = DriverManager.getConnection(url, props);
                                conn.close();

                                AuthorizationWindow.free();
                                System.out.print("\nDONE\n");
                                System.exit(0);
                            } catch (SQLException error) {
                                System.out.println(login.getText() + " \n " + password.getText());
                                System.out.print("\n!!!bad Authorization!!!\n");
                                AuthorizationWindow.free();
                                System.exit(1);
                            }
                        }
                    }
                    this.addActionListener(new AuthorizatuionAction());
                }
            }


            class AuthorizationTextField extends JTextField {
                public AuthorizationTextField(String name, int posX, int posY, int sizeX, int sizeY) {
                    super(name);
                    //this.setBounds(90, 173, 100, 25);
                    this.setBounds(posX, posY, sizeX, sizeY);
                    this.setVisible(true);
                }
            }

            class AuthorizationPasswordField extends JPasswordField {
                public AuthorizationPasswordField(String name, int posX, int posY, int sizeX, int sizeY) {
                    super(name);
                    this.setBounds(posX, posY, sizeX, sizeY);
                    this.setEchoChar('*');
                    this.setVisible(true);
                }
            }

            login = new AuthorizationTextField("", 90, 173, 100, 25);
            password = new AuthorizationPasswordField("", 90, 202, 100, 25);
            this.add(login);
            this.add(password);

            this.add(new AuthorizationButton("login",192, 185, 100, 25));
            this.setVisible(true);
        }

    }
}

