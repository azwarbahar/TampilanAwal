package belajar.project.tampilanawal.Model;

public class UserModel {
    String nama, email, nohp, plat, kendaraan;

    public UserModel(String nama, String email, String nohp, String plat, String kendaraan) {
        this.nama = nama;
        this.email = email;
        this.nohp = nohp;
        this.plat = plat;
        this.kendaraan = kendaraan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }
}
