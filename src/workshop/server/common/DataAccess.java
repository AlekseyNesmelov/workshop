package workshop.server.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import workshop.common.Constants;

public class DataAccess implements IDataAccess {
    private static IDataAccess instance_ = new DataAccess();
    private Connection mConnection;
    private final Object mLock = new Object();
    private DataAccess() {
    }
    
    public static IDataAccess getInstance() {
        return instance_;
    }
    
    @Override
    public boolean connect () {
        try{       
            Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
            mConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/workshop","root", "1234");
            return true;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public void disconnect() {
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean addUser(String username, String password) {
        synchronized (mLock) {
            try {
                String query = "select count(*) from users WHERE username='" + username + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getInt(1) == 0) {
                    query = "INSERT INTO users (username, password) \n" +
                   " VALUES ('" + username + "', '" + password + "');";
                    statement.executeUpdate(query);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }

    @Override
    public boolean checkUser(String username, String password) {
        synchronized (mLock) {
            try {
                String query = "select count(*) from users WHERE username='" + username + "' AND "
                        + "password='" + password + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getInt(1) > 0) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }

    @Override
    public String getShedule() {
        synchronized (mLock) {
            try {
                String result = "";
                String query = "select time from orders;";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    result+= resultSet.getString(1) + ";";
                }
                return result;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return null;
        }
    }

    @Override
    public boolean makeOrder(String username, String description, String phone, String time) {
        synchronized (mLock) {
            try {
                String query = "select count(*) from orders WHERE time='" + time + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getInt(1) > 0) {
                    return false;
                } 
                query = "select id from users WHERE username='" + username + "';";
                resultSet = statement.executeQuery(query);
                resultSet.next();                              
                int uid = resultSet.getInt(1);
 
                query = "INSERT INTO orders (uid, time, description, status, phone) \n" +
                   " VALUES ('" + uid + "', '" + time + "', '" + description + "', '" + 
                        Constants.STATUS_WAIT_FOR_CAR + "', '" + phone + "');";
                statement.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }

    @Override
    public String getOrders(String username) {
        synchronized (mLock) {
            try {
                String result = "";               
                String query = "select id from users WHERE username='" + username + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();                              
                int uid = resultSet.getInt(1);
                query = "select time, phone, status, description,"
                        + " statusdescription from orders where uid='" + uid + "';";
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    result+= resultSet.getString(1) + "=" + resultSet.getString(2) +
                            "=" + resultSet.getString(3) + "=" + resultSet.getString(4) + 
                            "=" + resultSet.getString(5) +";";
                }
                return result;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return null;
        }
    }
}
