package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User {

    Connection connect = conn.connectDb();
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public Doctor() {
        super();

    }

    public Doctor(int id, String tcno, String name, String password, String type) {
        super(id, tcno, name, password, type);

    }

    public ArrayList<Workh> getWorkhList(int doctor_id) { //Çalýþma saatlerini döndüren metod
        ArrayList<Workh> list = null;
        Workh obj;
        try {
            list = new ArrayList<>();
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery("SELECT * FROM workh WHERE status = 'a' AND doctor_id = " + doctor_id);
            while (rs.next()) {
                obj = new Workh();
                obj.setId(rs.getInt("id"));
                obj.setDoctor_id(rs.getInt("doctor_id"));
                obj.setDoctor_name(rs.getString("doctor_name"));
                obj.setStatus(rs.getString("status"));
                obj.setWdate(rs.getString("wdate"));
                list.add(obj);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public boolean addWorkh(int doctor_id, String doctor_name, String wdate) {//Çalýþma saati ekleyen metod
        int key = 0;
        int count = 0;
        String query = "INSERT INTO workh" + "(doctor_id,doctor_name,wdate) VALUES " + "(?,?,?)";

        try {
            //o saatte baþka randavusu var mý kontrol
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery("SELECT * FROM workh WHERE status = 'a' AND doctor_id =" + doctor_id + " AND wdate ='" + wdate + "'");
            while (rs.next()) {
                count++;
                break;
            }

            if (count == 0) {
                if (statement == null) {
                    statement = connect.createStatement();
                }
                preparedStatement = connect.prepareStatement(query);
                preparedStatement.setInt(1, doctor_id);
                preparedStatement.setString(2, doctor_name);
                preparedStatement.setString(3, wdate);
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

    public boolean deleteWorkh(int id) {

        String query = "DELETE FROM workh WHERE id = ?";
        boolean key = false;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            key = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (key) {
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
