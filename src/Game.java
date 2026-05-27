import java.math.BigDecimal;
import java.time.LocalDate;

public class Game {
    private int id;
    private String name;
    private String genre;
    private String platform;
    private BigDecimal price;
    private LocalDate releaseDate;
    private BigDecimal rating;

    public Game(String name, String genre, String platform, BigDecimal price, LocalDate releaseDate, BigDecimal rating) {
        this.name = name;
        this.genre = genre;
        this.platform = platform;
        this.price = price;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public Game(int id, String name, String genre, String platform, BigDecimal price, LocalDate releaseDate, BigDecimal rating) {
        this(name, genre, platform, price, releaseDate, rating);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | 名稱: %s | 類型: %s | 平台: %s | 價格: %s | 發售日: %s | 評分: %s",
                id,
                name,
                genre,
                platform,
                price,
                releaseDate,
                rating);
    }
}
