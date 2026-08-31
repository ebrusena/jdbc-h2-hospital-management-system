public class Hasta {
    private int id;
    private String tcNo;
    private String adSoyad;
    private String bolum;
    private int yas;

    public Hasta(int id,String tcNo, String adSoyad, String bolum,int yas){
        this.id=id;
        this.tcNo =tcNo;
        this.adSoyad = adSoyad;
        this.bolum = bolum;
        this.yas = yas;
    }
    public Hasta(String tcNo, String adSoyad, String bolum, int yas) {
        this.tcNo = tcNo;
        this.adSoyad = adSoyad;
        this.bolum = bolum;
        this.yas = yas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getBolum() {
        return bolum;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | TC: %-11s | Ad Soyad: %-20s | Bölüm: %-15s | Yaş: %d",
                id, tcNo, adSoyad, bolum, yas);
    }
}


