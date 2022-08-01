import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends  JDialog{
    private JTextField emailTF;
    private JPasswordField passwordTF;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel loginPanel;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(640,480));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setDefaultLookAndFeelDecorated();
        setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String email = emailTF.getText();
            String password = String.valueOf(passwordTF.getPassword());

            user = getAuthenticationUser(email,password);
            if(user!= null){
                dispose();
            }else {
                JOptionPane.showMessageDialog(LoginForm.this,"email o password incorrectos","Intente Nuevamente",JOptionPane.ERROR_MESSAGE);
            }
            }
        });
        // elementos de autenticacion //

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();;
            }
        });
        setVisible(true);
    }

    public User user;
    private User getAuthenticationUser(String email,String password){
        User user = null;

        final String DB_URL="jdbc:mysql://localhost/mitienda?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD= "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                user= new User();
                /*
                user.nombre=resultSet.getString(nombre);
                user.email=resultSet.getString(email);
                user.celular=resultSet.getString(celuar);
                user.direccion=resultSet.getString(direccion);
                user.password=resultSet.getString(password);

                 */
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;

        if(user!=null){
            System.out.println("Atenticacion correcta:"+user.nombre);
            System.out.println("email: "+user.email);
            System.out.println("celular:"+user.celular);
            System.out.println("direccion :"+user.direccion);
        }else{
            System.out.println("Autenticacion fallida");
        }
    }
}
