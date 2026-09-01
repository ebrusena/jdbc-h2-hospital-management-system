# 🏥 JDBC H2 Hospital Management System

Java ve JDBC kullanılarak geliştirilmiş, gömülü (embedded) H2 veritabanı ile çalışan konsol tabanlı bir Hastane Yönetim Sistemi projesidir.

---

## 📌 Özellikler

### 👤 Hasta Yönetimi
* **Yeni Hasta Kaydı (Create):** TC Kimlik No, Ad Soyad, Bölüm ve Yaş bilgileriyle sisteme hasta ekleme.
* **Hasta Listeleme (Read):** Kayıtlı tüm hastaların detaylarını listeleme.
* **Bölüm Güncelleme (Update):** Hasta ID'sine göre poliklinik/bölüm güncelleme.
* **Hasta Silme (Delete):** ID bazlı hasta kaydı silme.
* **Arama ve Filtreleme:**
  * TC Kimlik No ile tekil hasta sorgulama.
  * Tıbbi birime (Poliklinik/Bölüm) göre hastaları filtreleme.

### 🩺 Doktor Yönetimi
* **Yeni Doktor Kaydı:** Diploma/Sicil Numarası, Ad Soyad ve Uzmanlık Alanı ile doktor ekleme.
* **Doktor Listeleme:** Kayıtlı tüm hekimleri görüntüleme.
* **Uzmanlık Alanına Göre Filtreleme:** İlgili branştaki doktorları listeleme.
* **ID ile Doktor Sorgulama:** ID üzerinden doktor detaylarına erişim.

---

## 🛠️ Kullanılan Teknolojiler

* **Dil:** Java 17
* **Veritabanı:** H2 Database (Gömülü / Embedded mod)
* **Veri Erişimi:** Core JDBC (`PreparedStatement`, `Statement`, `ResultSet`)
* **Bağımlılık Yönetimi:** Apache Maven

---

## 📂 Proje Mimarisi

```text
src/main/java/
├── DatabaseHelper.java      # H2 veritabanı bağlantısı ve tablo başlatma (DDL)
├── Hasta.java               # Hasta veri modeli (Entity)
├── HastaDao.java            # Hasta CRUD ve filtreleme sorguları (DAO)
├── Doctor.java              # Doktor veri modeli (Entity)
├── DoctorDAO.java           # Doktor veri erişim katmanı (DAO)
└── Main.java                # Konsol arayüzü ve akış yönetimi
