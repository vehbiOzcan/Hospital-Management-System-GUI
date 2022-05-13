package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DbConnection;

public class Clinic {

    private int id;
    private String name;
    DbConnection con = new DbConnection();
    Connection connect = con.connectDb();
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public Clinic() {

    }

    public Clinic(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public ArrayList<Clinic> getClinicList() throws SQLException {// Klinikleri database den çeken ve liste olarak döndüren metod
        ArrayList<Clinic> list = new ArrayList<>();
        Clinic obj;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery("SELECT * FROM clinic");
            while (rs.next()) {
                obj = new Clinic();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                list.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public Clinic getFetch(int id) {//id deki deðeri getiren fonksiyon

        Clinic c = new Clinic();

        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            rs = statement.executeQuery("SELECT * FROM clinic WHERE id =" + id);
            while (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public boolean addClinic(String name) {

        String query = "INSERT INTO clinic" + "(name) VALUES" + "(?)";
        boolean key = false;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, name);
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

    public boolean deleteClinic(int id) {

        String query = "DELETE FROM clinic WHERE id = ?";
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

    public boolean updateClinic(int id, String name) {

        String query = "UPDATE clinic SET name=? WHERE id =?";
        boolean key = false;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
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
    //Klinik id ye göre doktorlarý listeleyen metod

    public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
