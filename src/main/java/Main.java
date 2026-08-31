import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseHelper.createTables();

        HastaDao dao = new HastaDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HASTANE HASTA YÖNETİM SİSTEMİ ===");
            System.out.println("1 - Yeni Hasta Kaydı Ekle (Create)");
            System.out.println("2 - Tüm Hastaları Listele (Read)");
            System.out.println("3 - Hasta Bölümünü Güncelle (Update)");
            System.out.println("4 - Hasta Kaydı Sil (Delete)");
            System.out.println("5 - TC No ile Hasta Ara");
            System.out.println("6 - Bölüme Göre Hastaları Filtrele");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");

            int secim = scanner.nextInt();
            scanner.nextLine();

            if (secim == 0) {
                System.out.println("Sistemden çıkılıyor...");
                break;
            }

            switch (secim) {
                case 1:
                    System.out.print("TC No: ");
                    String tc = scanner.nextLine();
                    System.out.print("Ad Soyad: ");
                    String ad = scanner.nextLine();
                    System.out.print("Poliklinik/Bölüm: ");
                    String bolum = scanner.nextLine();
                    System.out.print("Yaş: ");
                    int yas = scanner.nextInt();

                    Hasta yeniHasta = new Hasta(tc, ad, bolum, yas);
                    if (dao.hastaEkle(yeniHasta)) {
                        System.out.println("-> Hasta başarıyla kaydedildi.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- KAYITLI HASTALAR ---");
                    for (Hasta h : dao.tumHastalariGetir()) {
                        System.out.println(h);
                    }
                    break;

                case 3:
                    System.out.print("Güncellenecek Hasta ID: ");
                    int guncelId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Yeni Poliklinik/Bölüm: ");
                    String yeniBolum = scanner.nextLine();

                    if (dao.hastaBolumGuncelle(guncelId, yeniBolum)) {
                        System.out.println("-> Hasta polikliniği güncellendi.");
                    } else {
                        System.out.println("-> Belirtilen ID ile hasta bulunamadı.");
                    }
                    break;

                case 4:
                    System.out.print("Silinecek Hasta ID: ");
                    int silId = scanner.nextInt();

                    if (dao.hastaSil(silId)) {
                        System.out.println("-> Hasta kaydı silindi.");
                    } else {
                        System.out.println("-> Belirtilen ID ile hasta bulunamadı.");
                    }
                    break;

                case 5:
                    System.out.print("Aranacak TC No: ");
                    String arananTc = scanner.nextLine();
                    Hasta bulunanHasta = dao.hastaGetirByTc(arananTc);

                    if (bulunanHasta != null) {
                        System.out.println("\n--- HASTA BİLGİSİ ---");
                        System.out.println(bulunanHasta);
                    } else {
                        System.out.println("-> Bu TC numarasına ait hasta kaydı bulunamadı.");
                    }
                    break;

                case 6:
                    System.out.print("Filtrelenecek Bölüm (Örn: Kardiyoloji, Dahiliye): ");
                    String arananBolum = scanner.nextLine();
                    List<Hasta> bolumHastaları = dao.hastalariGetirByBolum(arananBolum);

                    if (bolumHastaları.isEmpty()) {
                        System.out.println("-> Bu bölümde kayıtlı hasta bulunamadı.");
                    } else {
                        System.out.println("\n--- " + arananBolum.toUpperCase() + " BÖLÜMÜ HASTALARI ---");
                        for (Hasta h : bolumHastaları) {
                            System.out.println(h);
                        }
                    }
                    break;

                default:
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
            }
        }
        scanner.close();
    }
}