package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DbConnection;

public class Workh {

    private int id, doctor_id;
    private String doctor_name, wdate, status;

    DbConnection conn = new DbConnection();
    Connection connect = conn.connectDb();
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public Workh() {

    }

    public Workh(int id, int doctor_id, String doctor_name, String wdate, String status) {
        super();
        this.id = id;
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.wdate = wdate;
        this.status = status;

    }
   

    public ArrayList<Workh> getWorkhList(int doctor_id) {//çalýþma saatlerini listeleyen metod
        ArrayList<Workh> list = null;
        Workh obj;
        try {
            list = new ArrayList<>();

            statement = connect.createStatement();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
