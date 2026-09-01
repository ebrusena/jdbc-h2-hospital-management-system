import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    // Proje ana dizininde hastanedb.mv.db adında gömülü (embedded) dosya oluşturur

    private static final String URL = "jdbc:h2:./hastanedb;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    // Veritabanı bağlantısı sağlayan metot
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Uygulama başladığında tabloları otomatik oluşturan metot
    public static void createTables() {
        String hastaSql = "CREATE TABLE IF NOT EXISTS hastalar (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "tc_no VARCHAR(11) NOT NULL UNIQUE, " +
                "ad_soyad VARCHAR(100) NOT NULL, " +
                "bolum VARCHAR(50) NOT NULL, " +
                "yas INT NOT NULL" +
                ");";

        String doktorSql = "CREATE TABLE IF NOT EXISTS doktorlar (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "diploma_no VARCHAR(20) NOT NULL UNIQUE, " +
                "ad_soyad VARCHAR(100) NOT NULL, " +
                "uzmanlik_alani VARCHAR(50) NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(hastaSql);
            stmt.execute(doktorSql);
            System.out.println("Veritabanı tabloları başarıyla kontrol edildi / oluşturuldu.");

        } catch (SQLException e) {
            System.err.println("Tablolar oluşturulurken hata oluştu: " + e.getMessage());
        }
    }
}