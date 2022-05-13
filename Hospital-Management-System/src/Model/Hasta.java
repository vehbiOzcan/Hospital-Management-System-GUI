package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.Helper;

public class Hasta extends User {

    Connection connect = conn.connectDb();
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public Hasta() {

    }

    public Hasta(int id, String tcno, String name, String password, String type) {
        super(id, tcno, name, password, type);
        // TODO Auto-generated constructor stub
    }

    public boolean register(String name, String password, String tcno) { //Hasta kayýt metodu
        int key = 0;
        boolean dublicate = false;
        String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES " + "(?,?,?,?)";

        try {
            statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM user WHERE tcno = '" + tcno + "' AND type = 'hasta'");

            while (rs.next()) {
                dublicate = true;
                Helper.showMsg("Bu T.C. kimlik numaralý bir kayýt zaten mevcut.");
                break;
            }

            if (!dublicate) {
                preparedStatement = connect.prepareStatement(query);
                preparedStatement.setString(1, tcno);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, "hasta");
                preparedStatement.executeUpdate();
                key = 1;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean addAppointment(int doctor_id, String doctor_name, int hasta_id, String hasta_name, String app_date) {//randevu ekleme metodu
        int key = 0;
        String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES " + "(?,?,?,?,?)";

        try {

            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, doctor_id);
            preparedStatement.setString(2, doctor_name);
            preparedStatement.setInt(3, hasta_id);
            preparedStatement.setString(4, hasta_name);
            preparedStatement.setString(5, app_date);
            preparedStatement.executeUpdate();
            key = 1;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateWorkhStatus(int doctor_id, String wdate) {//doktorun alýnan randevusunu pasif yapan metod
        int key = 0;
        String query = "UPDATE workh SET status = ? WHERE doctor_id = ? AND wdate = ?";

        try {

            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, "p");
            preparedStatement.setInt(2, doctor_id);
            preparedStatement.setString(3, wdate);
            preparedStatement.executeUpdate();
            key = 1;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (key == 1) {
            return true;
        } else {
            return false;
        }

    }

    public void closeDb() {
        try {
            if (connect != null) {
                this.connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                this.statement.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                this.rs.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (preparedStatement != null) {
                this.preparedStatement.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
