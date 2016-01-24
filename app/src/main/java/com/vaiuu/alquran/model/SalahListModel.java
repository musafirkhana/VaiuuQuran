package com.vaiuu.alquran.model;

/**
 * Created by Ali PC on 6/15/2015.
 */
public class SalahListModel {

    private String fajr;
    private String sunrise;
    private String zuhr;
    private String asr;
    private String maghrib;
    private String isha;

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getZuhr() {
        return zuhr;
    }

    public void setZuhr(String zuhr) {
        this.zuhr = zuhr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }

    @Override
    public String toString() {
        return "SalahListModel{" +
                "fajr='" + fajr + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", zuhr='" + zuhr + '\'' +
                ", asr='" + asr + '\'' +
                ", maghrib='" + maghrib + '\'' +
                ", isha='" + isha + '\'' +
                '}';
    }
}
