import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/1150502test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Taipei";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "bao2220";

    public static Connection getConnection() throws SQLException {
        String url = getEnvOrDefault("MYSQL_URL", DEFAULT_URL);
        String user = getEnvOrDefault("MYSQL_USER", DEFAULT_USER);
        String password = getEnvOrDefault("MYSQL_PASSWORD", DEFAULT_PASSWORD);

        return DriverManager.getConnection(url, user, password);
    }

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
