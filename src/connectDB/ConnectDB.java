package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection getConnection() {
        String serverName = "localhost";
        String databaseName = "COFFEESHOP";	
        String user = "sa";
        String password = "sapassword";
        String port = "1433";
        
        String url = "jdbc:sqlserver://" + serverName + ":" + port + 
                     ";databaseName=" + databaseName + 
                     ";encrypt=true;trustServerCertificate=true;";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        if (getConnection() != null) {
            System.out.println("Kết nối SQL Server thành công!");
        } else {
            System.out.println("Kết nối thất bại.");
        }
    }
}
