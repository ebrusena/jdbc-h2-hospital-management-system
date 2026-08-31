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

        try (Connection conn = DatabaseHelper.getConnection(); //Dışarıdan bir Hasta nesnesi alır. PreparedStatement ile ? işaretlerinin yerini doldurur ve executeUpdate() çalıştırarak yeni kaydı veritabanına ekler.
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
    // 5. READ - TC Kimlik Numarasına Göre Tek Bir Hasta Getirme
    public Hasta hastaGetirByTc(String tcNo) {
        String sql = "SELECT * FROM hastalar WHERE tc_no = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tcNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Hasta(
                            rs.getInt("id"),
                            rs.getString("tc_no"),
                            rs.getString("ad_soyad"),
                            rs.getString("bolum"),
                            rs.getInt("yas")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("TC ile Hasta Ararken Hata: " + e.getMessage());
        }
        return null; // Hasta bulunamazsa null döner
    }

    // 6. READ - Poliklinik/Bölüme Göre Hastaları Filtreleme
    public List<Hasta> hastalariGetirByBolum(String bolum) {
        List<Hasta> liste = new ArrayList<>();
        String sql = "SELECT * FROM hastalar WHERE UPPER(bolum) = UPPER(?) ORDER BY ad_soyad ASC";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bolum);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Hasta(
                            rs.getInt("id"),
                            rs.getString("tc_no"),
                            rs.getString("ad_soyad"),
                            rs.getString("bolum"),
                            rs.getInt("yas")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Bölüme Göre Listelerken Hata: " + e.getMessage());
        }
        return liste;
    }

}
