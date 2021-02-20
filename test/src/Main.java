import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class Main {

    private static class LoginTextField extends JTextField {
        public LoginTextField(String name, int sizeX, int sizeY, int posX, int posY) {
            super(name);
            this.setBounds(posX - sizeX / 2, posY - sizeY / 2, sizeX, sizeY);
            this.setVisible(true);
        }
    }

    private static class PasswordTextField extends JPasswordField {
        public PasswordTextField (String name, int sizeX, int sizeY, int posX, int posY) {
            super(name);
            this.setBounds(posX - sizeX / 2, posY - sizeY / 2, sizeX, sizeY);
            this.setEchoChar('*');
            this.setVisible(true);
        }
    }

    private static class EnterButton extends JButton {
        public EnterButton(String name, int sizeX, int sizeY, int posX, int posY, LoginTextField loginTextField, PasswordTextField passwordTextField) {
            super(name);
            this.setBounds(posX - sizeX / 2, posY - sizeY / 2, sizeX, sizeY);
            this.addActionListener(new LoginAction(loginTextField, passwordTextField));
            this.setVisible(true);
        }

        private static class LoginAction implements ActionListener {
            private final LoginTextField loginTextField;
            private final PasswordTextField passwordTextField;

            public LoginAction (LoginTextField loginTextField, PasswordTextField passwordTextField) {
                this.loginTextField = loginTextField;
                this.passwordTextField = passwordTextField;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(loginTextField.getText());
                    System.out.println(passwordTextField.getText());

                    try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                    } catch (ClassNotFoundException error) {
                        error.printStackTrace();
                    }

                    String url = "jdbc:oracle:thin:@0.0.0.0:1521:";
                    Properties props = new Properties();
                    props.setProperty("user", loginTextField.getText());
                    props.setProperty("password", passwordTextField.getText());

                    TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
                    TimeZone.setDefault(timeZone);
                    Locale.setDefault(Locale.ENGLISH);

                    Connection conn = DriverManager.getConnection(url, props);

                    String sql = "select count(*) from EMP";
                    PreparedStatement preStatement = conn.prepareStatement(sql);
                    ResultSet result = preStatement.executeQuery();
                    while (result.next()) {
                        int count = result.getInt(1);
                        System.out.println("Total number of records in EMP table: " + count);
                    }
                    System.out.println("!!!");
                    conn.close();
                    System.exit(0);
                } catch (SQLException error) {
                    System.out.println("Error");
                    System.exit(1);
                }
            }


        }

    }

    private static class MyFrame extends JFrame {
        private LoginTextField loginTextField;
        private PasswordTextField passwordTextField;
        private EnterButton enterButton;


        public MyFrame(String name, int sizeX, int sizeY) {
            super(name);
            this.setLayout(null);
            this.setSize(sizeX, sizeY);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            loginTextField = new LoginTextField("", 100, 40, 400, 200);
            this.add(loginTextField);
            passwordTextField = new PasswordTextField("", 100, 40, 400, 300);
            this.add(passwordTextField);
            enterButton = new EnterButton("login", 100, 40, 400, 400, loginTextField, passwordTextField);
            this.add(enterButton);

            this.setVisible(true);
        }
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("test", 800, 600);
    }


}
