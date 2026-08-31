import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    // Veritabanı URL'si: Proje klasörünün içine 'hastanedb' adında bir veritabanı dosyası oluşturur.
    private static final String DB_URL = "jdbc:h2:./hastanedb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    // 1. Veritabanı Bağlantısını Getiren Metot
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // 2. Tabloyu Otomatik Oluşturan Metot
    public static void createTables() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS hastalar (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "tc_no VARCHAR(11) UNIQUE NOT NULL, " +
                "ad_soyad VARCHAR(100) NOT NULL, " +
                "bolum VARCHAR(50) NOT NULL, " +
                "yas INT NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // SQL sorgusunu çalıştırıyoruz
            stmt.execute(createTableSQL);
            System.out.println("-> Veritabanı tablosu hazır (hastalar).");

        } catch (SQLException e) {
            System.err.println("Tablo oluşturulurken hata oluştu: " + e.getMessage());
        }
    }
}