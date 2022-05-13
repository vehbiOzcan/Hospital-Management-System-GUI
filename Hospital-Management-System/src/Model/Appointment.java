package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DbConnection;

public class Appointment {

    private int id, doctorId, hastaId;
    private String doctorName, hastaName, appDate;

    DbConnection conn = new DbConnection();
    Connection connect = conn.connectDb();
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public Appointment() {

    }

    public Appointment(int id, int doctorId, int hastaId, String doctorName, String hastaName, String appDate) {

        this.id = id;
        this.doctorId = doctorId;
        this.hastaId = hastaId;
        this.doctorName = doctorName;
        this.hastaName = hastaName;
        this.appDate = appDate;
    }

    public boolean deleteAppointment(int doctor_id, String date) {

        String queryA = "DELETE FROM appointment WHERE doctor_id =" + doctor_id + " AND app_date = '" + date + "'";
        String queryB = "UPDATE workh SET status = 'a' WHERE doctor_id =" + doctor_id + " AND wdate = '" + date + "'";
        boolean key = false;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            preparedStatement = connect.prepareStatement(queryA);
            preparedStatement.executeUpdate();
            preparedStatement = connect.prepareStatement(queryB);
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

    public ArrayList<Appointment> getHastaAppointmentList(int hasta_id) {//Hasta randevularýný listeleme metodu
        ArrayList<Appointment> list = null;
        Appointment obj;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            list = new ArrayList<>();
            rs = statement.executeQuery("SELECT * FROM appointment WHERE hasta_id = " + hasta_id);
            while (rs.next()) {
                obj = new Appointment();
                obj.setId(rs.getInt("id"));
                obj.setDoctorId(rs.getInt("doctor_id"));
                obj.setDoctorName(rs.getString("doctor_name"));
                obj.setHastaId(rs.getInt("hasta_id"));
                obj.setHastaName(rs.getString("hasta_name"));
                obj.setAppDate(rs.getString("app_date"));
                list.add(obj);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Appointment> getDoctorAppointmentList(int doctor_id) {//Doktora ait randevularý listeleme metodu
        ArrayList<Appointment> list = null;
        Appointment obj;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            list = new ArrayList<>();
            rs = statement.executeQuery("SELECT * FROM appointment WHERE doctor_id = " + doctor_id);
            while (rs.next()) {
                obj = new Appointment();
                obj.setId(rs.getInt("id"));
                obj.setDoctorId(rs.getInt("doctor_id"));
                obj.setDoctorName(rs.getString("doctor_name"));
                obj.setHastaId(rs.getInt("hasta_id"));
                obj.setHastaName(rs.getString("hasta_name"));
                obj.setAppDate(rs.getString("app_date"));
                list.add(obj);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Appointment> getAppointmentList() {//Tüm randevularý listeleme metodu
        ArrayList<Appointment> list = null;
        Appointment obj;
        try {
            if (statement == null) {
                statement = connect.createStatement();
            }
            list = new ArrayList<>();
            rs = statement.executeQuery("SELECT * FROM appointment");
            while (rs.next()) {
                obj = new Appointment();
                obj.setId(rs.getInt("id"));
                obj.setDoctorId(rs.getInt("doctor_id"));
                obj.setDoctorName(rs.getString("doctor_name"));
                obj.setHastaId(rs.getInt("hasta_id"));
                obj.setHastaName(rs.getString("hasta_name"));
                obj.setAppDate(rs.getString("app_date"));
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getHastaId() {
        return hastaId;
    }

    public void setHastaId(int hastaId) {
        this.hastaId = hastaId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHastaName() {
        return hastaName;
    }

    public void setHastaName(String hastaName) {
        this.hastaName = hastaName;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public void dbClose() {
        try {
            statement.close();
            connect.close();
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
