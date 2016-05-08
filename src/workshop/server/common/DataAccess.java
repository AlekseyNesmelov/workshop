package workshop.server.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import workshop.common.Constants;

public class DataAccess implements IDataAccess {
    private static final IDataAccess instance_ = new DataAccess(); 
    private Connection mConnection;
    private final ILogger mLogger = Logger.getInstance();
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
    public boolean addUser(final String username, final String password) { 
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
                    mLogger.log("New user was added: " + username);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }

    @Override
    public boolean checkUser(final String username, final String password) {
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
    public boolean makeOrder(final String username, final String description, 
            final String phone, final String time) {
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
                StringBuilder sb = new StringBuilder();
                sb.append("New order was made by ").append(username).append(".\n")
                        .append("Time: ").append(time).append("\n")
                        .append("Phone: ").append(phone).append("\n")
                        .append("Description: ").append(description);
                mLogger.log(sb.toString());
                return true;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return false;
        }
    }

    @Override
    public String getOrders(final String username) {
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
	
	@Override  
    public boolean acceptOrder(final String username, final String time) {  
        synchronized (mLock) {  
            try {  
                String query = "select id from users WHERE username='" + username + "';";  
                Statement statement = mConnection.createStatement();  
                ResultSet resultSet = statement.executeQuery(query);  
                resultSet.next();                                
                int uid = resultSet.getInt(1);  
                query = "UPDATE orders SET status= '" +   
                        Constants.STATUS_USER_AGREE + "' WHERE uid='" + uid + "' "  
                        + "AND time='" + time + "';";  
                statement.executeUpdate(query);
                StringBuilder sb = new StringBuilder();
                sb.append("User ").append(username).append(" accepted agreement.\n")
                        .append("Time of order: ").append(time).append(".");
                mLogger.log(sb.toString());
                return true;  
            } catch (SQLException e) {  
                System.out.println(e.toString());  
            }  
            return false;  
        }  
    }  
  
    @Override  
    public boolean rejectOrder(final String username, final String time) {  
            synchronized (mLock) {  
            try {                 
                String query = "select id from users WHERE username='" + username + "';";  
                Statement statement = mConnection.createStatement();  
                ResultSet resultSet = statement.executeQuery(query);  
                resultSet.next();                                
                int uid = resultSet.getInt(1);  
                query = "UPDATE orders SET status= '" +   
                        Constants.STATUS_USER_DENIED + "' WHERE uid='" + uid + "' "  
                        + "AND time='" + time + "';";  
                statement.executeUpdate(query);
                StringBuilder sb = new StringBuilder();
                sb.append("User ").append(username).append(" rejected agreement.\n")
                    .append("Time of order: ").append(time).append(".");
                mLogger.log(sb.toString());
                return true;  
            } catch (SQLException e) {  
                System.out.println(e.toString());  
            }  
            return false;  
        }  
    } 
 
	@Override
    public String getInfo(final String time) {
        synchronized (mLock) {
            try {
                String query = "select * from orders WHERE time='" + time + "';";
                Statement statement = mConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(resultSet.next()) {
                    String result = "";
                    result += resultSet.getString(4) + ";" + resultSet.getString(5) + ";" + resultSet.getString(6) + ";" + resultSet.getString(7);
                    query = "select username from users WHERE id='" + resultSet.getString(2) + "';";
                    statement = mConnection.createStatement();
                    resultSet = statement.executeQuery(query);
                    resultSet.next();
                    result = resultSet.getString(1) + ";" + result;
                    return result;
                }
                else return "Nothing here";
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            return "Test Info";
        }
    }
}
