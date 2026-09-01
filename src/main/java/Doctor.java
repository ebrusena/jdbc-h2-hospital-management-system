public class Doctor {
    private int id;
    private String diplomaNo;
    private String adSoyad;
    private String uzmanlikAlani;

    public Doctor() {}

    public Doctor(String diplomaNo, String adSoyad, String uzmanlikAlani) {
        this.diplomaNo = diplomaNo;
        this.adSoyad = adSoyad;
        this.uzmanlikAlani = uzmanlikAlani;
    }

    public Doctor(int id, String diplomaNo, String adSoyad, String uzmanlikAlani) {
        this.id = id;
        this.diplomaNo = diplomaNo;
        this.adSoyad = adSoyad;
        this.uzmanlikAlani = uzmanlikAlani;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiplomaNo() {
        return diplomaNo;
    }

    public void setDiplomaNo(String diplomaNo) {
        this.diplomaNo = diplomaNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getUzmanlikAlani() {
        return uzmanlikAlani;
    }

    public void setUzmanlikAlani(String uzmanlikAlani) {
        this.uzmanlikAlani = uzmanlikAlani;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Diploma No: " + diplomaNo + " | Dr. " + adSoyad + " | Uzmanlık: " + uzmanlikAlani;
    }
}
