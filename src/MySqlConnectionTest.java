import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnectionTest {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=Asia/Taipei&allowPublicKeyRetrieval=true";

    public static void main(String[] args) {
        String url = getEnvOrDefault("MYSQL_URL", DEFAULT_URL);
        String user = getEnvOrDefault("MYSQL_USER", "root");
        String password = getEnvOrDefault("MYSQL_PASSWORD", "");

        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT VERSION()")) {

            if (resultSet.next()) {
                System.out.println("MySQL connected successfully.");
                System.out.println("Version: " + resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("MySQL connection failed.");
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
