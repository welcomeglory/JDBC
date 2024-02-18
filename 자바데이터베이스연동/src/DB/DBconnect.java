package DB;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	 public static String databaseDriver = "com.mysql.jdbc.Driver";
	 public static final String databaseUrl = "jdbc:mysql://localhost:3306/pos?severTimezone=UTC";
	 public static final String databaseUser = "root";
	 public static final String databasePassword = "1234";
	 public static Connection connection = null;
    
    public static void main(String[] args) {
       connect();
       close();
    }
    public static Connection connect() {
    	try {
    		Class.forName(databaseDriver);
    		connection = DriverManager.getConnection(databaseUrl,databaseUser,databasePassword);
    		if(connection !=null)  System.out.println("Connection Succeed");
    		else System.out.println("Connection Failed");    			
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "데이터베이스가 연결되지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
    		System.out.println("Connection Error! : "+e.getMessage());
    		e.printStackTrace();
    	}
    	return connection;     	
    }
    public static void close() {
    	try {
    		if(connection !=null) {
    			System.out.println("Connection Close");
    			connection.close();
    		}
    	}catch(	SQLException e) {
    		System.out.println("Connection Closing Failed! : "+e.getMessage());
    		e.printStackTrace();
    	}
    }
}
