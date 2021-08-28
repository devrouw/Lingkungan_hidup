package com.client.lingkungan_hidup;

public class Satwa {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    String id;
    String nama;

    public Satwa(String id, String nama, String spesies, String asal, String deskripsi, String gambar) {
        this.id = id;
        this.nama = nama;
        this.spesies = spesies;
        this.asal = asal;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    String spesies;
    String asal;
    String deskripsi;
    String gambar;


}
