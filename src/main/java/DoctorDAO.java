import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    private final Connection connection;

    public DoctorDAO(Connection connection) {
        this.connection = connection;
        tabloOlustur();
    }

    // Tablo henüz yoksa otomatik oluşturur
    private void tabloOlustur() {
        String sql = "CREATE TABLE IF NOT EXISTS doktorlar (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "diploma_no VARCHAR(20) NOT NULL UNIQUE, " +
                "ad_soyad VARCHAR(100) NOT NULL, " +
                "uzmanlik_alani VARCHAR(50) NOT NULL" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Doktorlar tablosu oluşturulurken hata: " + e.getMessage());
        }
    }

    // Doktor Ekleme
    public boolean doktorEkle(Doctor doctor) {
        String sql = "INSERT INTO doktorlar (diploma_no, ad_soyad, uzmanlik_alani) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, doctor.getDiplomaNo());
            ps.setString(2, doctor.getAdSoyad());
            ps.setString(3, doctor.getUzmanlikAlani());

            ps.executeUpdate();
            System.out.println("Doktor başarıyla eklendi: Dr. " + doctor.getAdSoyad());
            return true;
        } catch (SQLException e) {
            System.err.println("Doktor eklenirken hata: " + e.getMessage());
            return false;
        }
    }

    // Tüm Doktorları Listeleme
    public List<Doctor> tumDoktorlar() {
        List<Doctor> doktorListesi = new ArrayList<>();
        String sql = "SELECT * FROM doktorlar";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Doctor d = new Doctor(
                        rs.getInt("id"),
                        rs.getString("diploma_no"),
                        rs.getString("ad_soyad"),
                        rs.getString("uzmanlik_alani")
                );
                doktorListesi.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Doktorlar listelenirken hata: " + e.getMessage());
        }
        return doktorListesi;
    }

    // Uzmanlık Alanına Göre Doktor Arama (Randevu alırken kullanışlı)
    public List<Doctor> bolumeGoreDoktorGetir(String uzmanlikAlani) {
        List<Doctor> doktorListesi = new ArrayList<>();
        String sql = "SELECT * FROM doktorlar WHERE LOWER(uzmanlik_alani) = LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uzmanlikAlani.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Doctor d = new Doctor(
                            rs.getInt("id"),
                            rs.getString("diploma_no"),
                            rs.getString("ad_soyad"),
                            rs.getString("uzmanlik_alani")
                    );
                    doktorListesi.add(d);
                }
            }
        } catch (SQLException e) {
            System.err.println("Bölüme göre doktor aranırken hata: " + e.getMessage());
        }
        return doktorListesi;
    }

    // ID'ye Göre Doktor Bulma
    public Doctor doktorBulById(int id) {
        String sql = "SELECT * FROM doktorlar WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                            rs.getInt("id"),
                            rs.getString("diploma_no"),
                            rs.getString("ad_soyad"),
                            rs.getString("uzmanlik_alani")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Doktor bulunurken hata: " + e.getMessage());
        }
        return null;
    }
}