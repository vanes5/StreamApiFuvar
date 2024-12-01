package hu.petrik.streamapifuvar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fuvar {
    private int taxi_azon;
    private LocalDateTime indulas;
    private int idotartam;
    private float tav;
    private float dij;
    private float borravalo;
    private String fiz_mod;

    public Fuvar(int taxi_azon, LocalDateTime indulas, int idotartam, float tav, float dij, float borravalo, String fiz_mod) {
        this.taxi_azon = taxi_azon;
        this.indulas = indulas;
        this.idotartam = idotartam;
        this.tav = tav;
        this.dij = dij;
        this.borravalo = borravalo;
        this.fiz_mod = fiz_mod;
    }

    public Fuvar(String line) {
        String[] data = line.split(";");

        taxi_azon = Integer.parseInt(data[0]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        indulas = LocalDateTime.parse(data[1], formatter);
        idotartam = Integer.parseInt(data[2]);
        tav = Float.parseFloat(data[3].replace(",","."));
        dij = Float.parseFloat(data[4].replace(",","."));
        borravalo = Float.parseFloat(data[5].replace(",","."));
        fiz_mod = data[6];
    }

    public int getTaxi_azon() {
        return taxi_azon;
    }

    public void setTaxi_azon(int taxi_azon) {
        this.taxi_azon = taxi_azon;
    }

    public LocalDateTime getIndulas() {
        return indulas;
    }

    public void setIndulas(LocalDateTime indulas) {
        this.indulas = indulas;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }

    public float getTav() {
        return tav;
    }

    public void setTav(float tav) {
        this.tav = tav;
    }

    public float getDij() {
        return dij;
    }

    public void setDij(float dij) {
        this.dij = dij;
    }

    public float getBorravalo() {
        return borravalo;
    }

    public void setBorravalo(float borravalo) {
        this.borravalo = borravalo;
    }

    public String getFiz_mod() {
        return fiz_mod;
    }

    public void setFiz_mod(String fiz_mod) {
        this.fiz_mod = fiz_mod;
    }

    @Override
    public String toString() {
        return "Fuvar{" +
                "taxi_azon=" + taxi_azon +
                ", indulas=" + indulas +
                ", idotartam=" + idotartam +
                ", tav=" + tav +
                ", dij=" + dij +
                ", borravalo=" + borravalo +
                ", fiz_mod='" + fiz_mod + '\'' +
                '}';
    }
}
