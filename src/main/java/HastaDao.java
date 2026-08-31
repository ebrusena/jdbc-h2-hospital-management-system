import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HastaDao {

    // 1. CREATE - Yeni Hasta Ekleme
    public boolean hastaEkle(Hasta hasta) {
        String sql = "INSERT INTO hastalar (tc_no, ad_soyad, bolum, yas) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hasta.getTcNo());
            pstmt.setString(2, hasta.getAdSoyad());
            pstmt.setString(3, hasta.getBolum());
            pstmt.setInt(4, hasta.getYas());

            int etkilenenSatir = pstmt.executeUpdate();
            return etkilenenSatir > 0;

        } catch (SQLException e) {
            System.err.println("Hasta Eklenirken Hata: " + e.getMessage());
            return false;
        }
    }

    // 2. READ - Tüm Hastaları Listeleme
    public List<Hasta> tumHastalariGetir() {
        List<Hasta> liste = new ArrayList<>();
        String sql = "SELECT * FROM hastalar ORDER BY id ASC";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Hasta hasta = new Hasta(
                        rs.getInt("id"),
                        rs.getString("tc_no"),
                        rs.getString("ad_soyad"),
                        rs.getString("bolum"),
                        rs.getInt("yas")
                );
                liste.add(hasta);
            }

        } catch (SQLException e) {
            System.err.println("Hastalar Listelenirken Hata: " + e.getMessage());
        }
        return liste;
    }

    // 3. UPDATE - Hasta Bölümünü Güncelleme
    public boolean hastaBolumGuncelle(int id, String yeniBolum) {
        String sql = "UPDATE hastalar SET bolum = ? WHERE id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, yeniBolum);
            pstmt.setInt(2, id);

            int etkilenenSatir = pstmt.executeUpdate();
            return etkilenenSatir > 0;

        } catch (SQLException e) {
            System.err.println("Hasta Güncellenirken Hata: " + e.getMessage());
            return false;
        }
    }

    // 4. DELETE - Hasta Silme
    public boolean hastaSil(int id) {
        String sql = "DELETE FROM hastalar WHERE id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int etkilenenSatir = pstmt.executeUpdate();
            return etkilenenSatir > 0;

        } catch (SQLException e) {
            System.err.println("Hasta Silinirken Hata: " + e.getMessage());
            return false;
        }
    }
}