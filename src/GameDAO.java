import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDAO {
    public void createTableIfNotExists() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS games (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    genre VARCHAR(50),
                    platform VARCHAR(50),
                    price DECIMAL(10,2),
                    release_date DATE,
                    rating DECIMAL(3,1),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void addGame(Game game) throws SQLException {
        String sql = """
                INSERT INTO games (name, genre, platform, price, release_date, rating)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            fillGameStatement(statement, game);
            statement.executeUpdate();
        }
    }

    public List<Game> findAllGames() throws SQLException {
        String sql = """
                SELECT id, name, genre, platform, price, release_date, rating
                FROM games
                ORDER BY id
                """;
        List<Game> games = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(toGame(resultSet));
            }
        }

        return games;
    }

    public Optional<Game> findGameById(int id) throws SQLException {
        String sql = """
                SELECT id, name, genre, platform, price, release_date, rating
                FROM games
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toGame(resultSet));
                }
            }
        }

        return Optional.empty();
    }

    public boolean updateGame(Game game) throws SQLException {
        String sql = """
                UPDATE games
                SET name = ?, genre = ?, platform = ?, price = ?, release_date = ?, rating = ?
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            fillGameStatement(statement, game);
            statement.setInt(7, game.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteGame(int id) throws SQLException {
        String sql = "DELETE FROM games WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private void fillGameStatement(PreparedStatement statement, Game game) throws SQLException {
        statement.setString(1, game.getName());
        statement.setString(2, game.getGenre());
        statement.setString(3, game.getPlatform());
        statement.setBigDecimal(4, game.getPrice());
        statement.setDate(5, Date.valueOf(game.getReleaseDate()));
        statement.setBigDecimal(6, game.getRating());
    }

    private Game toGame(ResultSet resultSet) throws SQLException {
        Date releaseDate = resultSet.getDate("release_date");
        BigDecimal price = resultSet.getBigDecimal("price");
        BigDecimal rating = resultSet.getBigDecimal("rating");

        return new Game(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("genre"),
                resultSet.getString("platform"),
                price,
                releaseDate == null ? null : releaseDate.toLocalDate(),
                rating);
    }
}
