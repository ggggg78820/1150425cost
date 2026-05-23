import java.sql.*;
	public class mysqltest {
	    public static void main(String[] args) {
	        String url = "jdbc:mysql://localhost:3306/1150502test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Taipei";
	        String user = "root";
	        String password = "bao2220";
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection connection = DriverManager.getConnection(url, user, password);
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT VERSION() AS version");
	            if (resultSet.next()) {
	                System.out.println("MySQL connected.");
	                System.out.println("Server version: " + resultSet.getString("version"));
	            }
	            resultSet.close();
	            statement.close();
	            connection.close();
	        } catch (ClassNotFoundException e) {
	            System.out.println("MySQL JDBC Driver not found.");
	            System.out.println("Put mysql-connector-j-*.jar into the lib folder.");
	        } catch (SQLException e) {
	            System.out.println("MySQL connection failed.");
	            System.out.println("Reason: " + e.getMessage());
	        }
	    }
	}