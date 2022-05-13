package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Helper.*;

public class Bashekim extends User {

    Connection connect = conn.connectDb();
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public Bashekim() {

    }

    public Bashekim(int id, String tcno, String name, String password, String type) {
        super(id, tcno, name, password, type);
    }

    public ArrayList<User> getDoctorList() throws SQLException {// Doktorlarý database den çeken ve liste olarak döndüren metod

        ArrayList<User> list = new ArrayList<>();
        User obj;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery("SELECT * FROM user WHERE type = 'doktor'");
            while (rs.next()) {
                obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),
                        rs.getString("type"));
                list.add(obj);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {//seçilen klinikteki doktorlarý liste olarak döndüren metod

        ArrayList<User> list = new ArrayList<>();
        User obj;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery("SELECT u.id ,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id=" + clinic_id);
            while (rs.next()) {
                obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),
                        rs.getString("type"));
                list.add(obj);
            }
        } catch (SQLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public boolean addDoctor(String tcno, String password, String name) {

        String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
        boolean key = false;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, tcno);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, "doktor");
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

    public boolean deleteDoctor(int id) {

        String query = "DELETE FROM user WHERE id = ?";
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

    public boolean deleteWorker(int id) {

        String query = "DELETE FROM worker WHERE user_id = ?";
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

    public boolean updateDoctor(int id, String name, String password, String tcno) {

        String query = "UPDATE user SET name=?,tcno=?,password=? WHERE id =?";
        boolean key = false;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, tcno);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, id);
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

    public boolean addWorker(int user_id, int clinic_id) {

        String query = "INSERT INTO worker (user_id,clinic_id) VALUES (?,?)";
        String queryC = "SELECT * FROM worker WHERE user_id =" + user_id;
        boolean key = false;
        int count = 0;

        try {

            // ayný çalýþandan var mý yok mu diye kontrol
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery(queryC);
            while (rs.next()) {
                count++;// ayný çaýþandan varsa 
            }
            if (count == 0) {// Yoksa ekler
                preparedStatement = connect.prepareStatement(query);
                preparedStatement.setInt(1, user_id);
                preparedStatement.setInt(2, clinic_id);
                preparedStatement.executeUpdate();
            } else {
                Helper.showMsg("Bu doktor zaten bir poliklinikte ekli");
            }
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
