package net.agusharyanto.aplikasidatamahasiswa;

/**
 * Created by agus on 5/16/17.
 */

public class Mahasiswa {

    private String id="";
    private String nim="";
    private String nama="";
    private String jurusan="";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

}