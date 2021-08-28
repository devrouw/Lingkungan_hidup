package com.client.lingkungan_hidup;

public class Lokasi {

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public void setNama_lokasi(String nama_lokasi) {
        this.nama_lokasi = nama_lokasi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Lokasi(String id, String nama_lokasi, String alamat, String longitude, String latitude) {
        this.id = id;
        this.nama_lokasi = nama_lokasi;
        this.alamat = alamat;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    String nama_lokasi;
    String alamat;
    String longitude;
    String latitude;
}
