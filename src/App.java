import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final GameDAO GAME_DAO = new GameDAO();

    public static void main(String[] args) {
        try {
            GAME_DAO.createTableIfNotExists();
            runMenu();
        } catch (SQLException e) {
            System.out.println("資料庫操作失敗: " + e.getMessage());
        }
    }

    private static void runMenu() throws SQLException {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("請選擇功能: ");

            switch (choice) {
                case 1 -> addGame();
                case 2 -> listGames();
                case 3 -> findGame();
                case 4 -> updateGame();
                case 5 -> deleteGame();
                case 0 -> {
                    running = false;
                    System.out.println("已離開系統。");
                }
                default -> System.out.println("沒有這個選項，請重新輸入。");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Game Database Manager ===");
        System.out.println("1. 新增遊戲");
        System.out.println("2. 查詢所有遊戲");
        System.out.println("3. 用 ID 查詢遊戲");
        System.out.println("4. 修改遊戲資料");
        System.out.println("5. 刪除遊戲");
        System.out.println("0. 離開");
    }

    private static void addGame() throws SQLException {
        Game game = readGameInfo(0);
        GAME_DAO.addGame(game);
        System.out.println("新增完成。");
    }

    private static void listGames() throws SQLException {
        List<Game> games = GAME_DAO.findAllGames();

        if (games.isEmpty()) {
            System.out.println("目前沒有遊戲資料。");
            return;
        }

        for (Game game : games) {
            System.out.println(game);
        }
    }

    private static void findGame() throws SQLException {
        int id = readInt("請輸入遊戲 ID: ");
        Optional<Game> game = GAME_DAO.findGameById(id);

        if (game.isPresent()) {
            System.out.println(game.get());
        } else {
            System.out.println("找不到 ID 為 " + id + " 的遊戲。");
        }
    }

    private static void updateGame() throws SQLException {
        int id = readInt("請輸入要修改的遊戲 ID: ");

        if (GAME_DAO.findGameById(id).isEmpty()) {
            System.out.println("找不到 ID 為 " + id + " 的遊戲。");
            return;
        }

        Game game = readGameInfo(id);
        boolean updated = GAME_DAO.updateGame(game);
        System.out.println(updated ? "修改完成。" : "修改失敗。");
    }

    private static void deleteGame() throws SQLException {
        int id = readInt("請輸入要刪除的遊戲 ID: ");
        boolean deleted = GAME_DAO.deleteGame(id);
        System.out.println(deleted ? "刪除完成。" : "找不到這筆遊戲資料。");
    }

    private static Game readGameInfo(int id) {
        String name = readRequiredText("遊戲名稱: ");
        String genre = readRequiredText("遊戲類型: ");
        String platform = readRequiredText("遊戲平台: ");
        BigDecimal price = readDecimal("價格: ");
        LocalDate releaseDate = readDate("發售日期 (yyyy-MM-dd): ");
        BigDecimal rating = readDecimal("評分 (0.0 - 10.0): ");

        if (id == 0) {
            return new Game(name, genre, platform, price, releaseDate, rating);
        }

        return new Game(id, name, genre, platform, price, releaseDate, rating);
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("此欄位不可空白。");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("請輸入整數。");
            }
        }
    }

    private static BigDecimal readDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();

            try {
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                System.out.println("請輸入數字，例如 1290 或 9.5。");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();

            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("日期格式請使用 yyyy-MM-dd，例如 2026-05-23。");
            }
        }
    }
}
