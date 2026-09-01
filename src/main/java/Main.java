import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Veritabanı tablolarını oluştur/kontrol et
        DatabaseHelper.createTables();

        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DatabaseHelper.getConnection()) {
            HastaDao hastaDao = new HastaDao();
            DoctorDAO doctorDao = new DoctorDAO(connection);

            while (true) {
                System.out.println("\n==========================================");
                System.out.println("     HASTANE YÖNETİM SİSTEMİ ANA MENÜ     ");
                System.out.println("==========================================");
                System.out.println("--- HASTA İŞLEMLERİ ---");
                System.out.println("1  - Yeni Hasta Kaydı Ekle");
                System.out.println("2  - Tüm Hastaları Listele");
                System.out.println("3  - Hasta Bölümünü Güncelle");
                System.out.println("4  - Hasta Kaydı Sil");
                System.out.println("5  - TC No ile Hasta Ara");
                System.out.println("6  - Bölüme Göre Hastaları Filtrele");
                System.out.println("\n--- DOKTOR İŞLEMLERİ ---");
                System.out.println("7  - Yeni Doktor Ekle");
                System.out.println("8  - Tüm Doktorları Listele");
                System.out.println("9  - Uzmanlık Alanına Göre Doktor Ara");
                System.out.println("10 - ID ile Doktor Bilgisi Getir");
                System.out.println("\n0  - Sistemden Çıkış");
                System.out.print("Seçiminiz: ");

                int secim = scanner.nextInt();
                scanner.nextLine(); // Satır sonu karakterini temizle

                if (secim == 0) {
                    System.out.println("Sistemden çıkılıyor... İyi günler dileriz.");
                    break;
                }

                switch (secim) {
                    // --- HASTA MODÜLÜ ---
                    case 1:
                        System.out.println("\n[YENİ HASTA EKLE]");
                        System.out.print("TC No: ");
                        String tc = scanner.nextLine().trim();
                        System.out.print("Ad Soyad: ");
                        String ad = scanner.nextLine().trim();
                        System.out.print("Poliklinik/Bölüm: ");
                        String bolum = scanner.nextLine().trim();
                        System.out.print("Yaş: ");
                        int yas = scanner.nextInt();
                        scanner.nextLine();

                        Hasta yeniHasta = new Hasta(tc, ad, bolum, yas);
                        if (hastaDao.hastaEkle(yeniHasta)) {
                            System.out.println("-> Hasta başarıyla kaydedildi.");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- KAYITLI HASTALAR ---");
                        List<Hasta> hastalar = hastaDao.tumHastalariGetir();
                        if (hastalar.isEmpty()) {
                            System.out.println("-> Kayıtlı hasta bulunamadı.");
                        } else {
                            for (Hasta h : hastalar) {
                                System.out.println(h);
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n[HASTA BÖLÜMÜ GÜNCELLE]");
                        System.out.print("Güncellenecek Hasta ID: ");
                        int guncelId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Yeni Poliklinik/Bölüm: ");
                        String yeniBolum = scanner.nextLine().trim();

                        if (hastaDao.hastaBolumGuncelle(guncelId, yeniBolum)) {
                            System.out.println("-> Hasta polikliniği başarıyla güncellendi.");
                        } else {
                            System.out.println("-> Belirtilen ID ile hasta bulunamadı.");
                        }
                        break;

                    case 4:
                        System.out.println("\n[HASTA SİL]");
                        System.out.print("Silinecek Hasta ID: ");
                        int silId = scanner.nextInt();
                        scanner.nextLine();

                        if (hastaDao.hastaSil(silId)) {
                            System.out.println("-> Hasta kaydı silindi.");
                        } else {
                            System.out.println("-> Belirtilen ID ile hasta bulunamadı.");
                        }
                        break;

                    case 5:
                        System.out.println("\n[TC İLE HASTA ARA]");
                        System.out.print("Aranacak TC No: ");
                        String arananTc = scanner.nextLine().trim();
                        Hasta bulunanHasta = hastaDao.hastaGetirByTc(arananTc);

                        if (bulunanHasta != null) {
                            System.out.println("\n--- HASTA BİLGİSİ ---");
                            System.out.println(bulunanHasta);
                        } else {
                            System.out.println("-> Bu TC numarasına ait hasta kaydı bulunamadı.");
                        }
                        break;

                    case 6:
                        System.out.println("\n[BÖLÜME GÖRE HASTALARI LİSTELE]");
                        System.out.print("Filtrelenecek Bölüm (Örn: Kardiyoloji, Dahiliye): ");
                        String arananBolum = scanner.nextLine().trim();
                        List<Hasta> bolumHastalari = hastaDao.hastalariGetirByBolum(arananBolum);

                        if (bolumHastalari.isEmpty()) {
                            System.out.println("-> Bu bölümde kayıtlı hasta bulunamadı.");
                        } else {
                            System.out.println("\n--- " + arananBolum.toUpperCase() + " BÖLÜMÜ HASTALARI ---");
                            for (Hasta h : bolumHastalari) {
                                System.out.println(h);
                            }
                        }
                        break;

                    // --- DOKTOR MODÜLÜ ---
                    case 7:
                        System.out.println("\n[YENİ DOKTOR EKLE]");
                        System.out.print("Diploma / Sicil No: ");
                        String diplomaNo = scanner.nextLine().trim();
                        System.out.print("Doktor Ad Soyad: ");
                        String docAdSoyad = scanner.nextLine().trim();
                        System.out.print("Uzmanlık Alanı / Bölüm: ");
                        String uzmanlik = scanner.nextLine().trim();

                        Doctor yeniDoktor = new Doctor(diplomaNo, docAdSoyad, uzmanlik);
                        doctorDao.doktorEkle(yeniDoktor);
                        break;

                    case 8:
                        System.out.println("\n--- KAYITLI DOKTORLAR ---");
                        List<Doctor> doktorlar = doctorDao.tumDoktorlar();
                        if (doktorlar.isEmpty()) {
                            System.out.println("-> Kayıtlı doktor bulunamadı.");
                        } else {
                            for (Doctor d : doktorlar) {
                                System.out.println(d);
                            }
                        }
                        break;

                    case 9:
                        System.out.println("\n[UZMANLIĞA GÖRE DOKTOR ARA]");
                        System.out.print("Aranacak Uzmanlık Alanı: ");
                        String arananUzmanlik = scanner.nextLine().trim();
                        List<Doctor> uzmanDoktorlar = doctorDao.bolumeGoreDoktorGetir(arananUzmanlik);

                        if (uzmanDoktorlar.isEmpty()) {
                            System.out.println("-> Bu uzmanlık alanında doktor bulunamadı.");
                        } else {
                            System.out.println("\n--- " + arananUzmanlik.toUpperCase() + " DOKTORLARI ---");
                            for (Doctor d : uzmanDoktorlar) {
                                System.out.println(d);
                            }
                        }
                        break;

                    case 10:
                        System.out.println("\n[ID İLE DOKTOR BUL]");
                        System.out.print("Doktor ID: ");
                        int docId = scanner.nextInt();
                        scanner.nextLine();

                        Doctor bulunanDoktor = doctorDao.doktorBulById(docId);
                        if (bulunanDoktor != null) {
                            System.out.println("\n--- DOKTOR BİLGİSİ ---");
                            System.out.println(bulunanDoktor);
                        } else {
                            System.out.println("-> Belirtilen ID ile doktor bulunamadı.");
                        }
                        break;

                    default:
                        System.out.println("Geçersiz seçim! Lütfen menüdeki numaralardan birini girin.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}