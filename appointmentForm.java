
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class appointmentForm extends JDialog {
    private JPanel appointmentPanel;
    private JTextField tfName;
    private JTextField tfNumber;
    private JTextField tfEmail_id;
    private JTextField tfDate;
    private JTextField tfTime;
    private JTextField tfDepartment;
    private JTextField tfSymptoms;
    private JButton btnAppointment;
    private JButton btnCancel;

    public appointmentForm(JFrame parent) {
        super(parent);
        setTitle("Create a new Appointment");
        setContentPane(appointmentPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                appointmentFormUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);

        
    }

    private void appointmentFormUser() {
        String name =tfName.getText();
        String email = tfEmail_id.getText();
        String phone = tfNumber.getText();
        String Date = tfDate.getText();
        String Time = tfTime.getText();
        String Department = tfDepartment.getText();
        String Symptoms = tfSymptoms.getText();
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || Date.isEmpty() ||Time.isEmpty() ||Department.isEmpty() ||Symptoms.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


  user=  addUserToDatabase(name, email, phone, Date, Time, Department, Symptoms);
        if (user != null) {
            dispose();
        }

}
public User user;
    private User addUserToDatabase(String name, String email, String phone, String Date, String Time,String Department,String Symptoms) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/MyData?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, email, phone, Date, Time,Department,Symptoms) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, Date);
            preparedStatement.setString(5, Time);
            preparedStatement.setString(6, Department);
            preparedStatement.setString(7, Symptoms);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.phone =phone;
                user.Date = Date;
                user.Time = Time;
                user.Department=Department;
                user.Symptoms=Symptoms;

            }
            stmt.close();
            conn.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
            return user;
    }
    public static void main(String[] args) {
        appointmentForm myForm = new appointmentForm(null);
        User user = myForm.user;
        if (user != null) {
            System.out.println("Successfully making Appointment of: " + user.name);
        }
        else {
            System.out.println("Appointment is disabled.");
        }
    }

  }
