package com.nikimnafis.bridgeorganizer;

public class CrewHelper {
    private String namaCrew, statusCrew;
    private int imgCrew;

    public CrewHelper(String namaCrew, String statusCrew, int imgCrew) {
        this.namaCrew = namaCrew;
        this.statusCrew = statusCrew;
        this.imgCrew = imgCrew;
    }

    public String getNamaCrew() {
        return namaCrew;
    }

    public void setNamaCrew(String namaCrew) {
        this.namaCrew = namaCrew;
    }

    public String getStatusCrew() {
        return statusCrew;
    }

    public void setStatusCrew(String statusCrew) {
        this.statusCrew = statusCrew;
    }

    public int getImgCrew() {
        return imgCrew;
    }

    public void setImgCrew(int imgCrew) {
        this.imgCrew = imgCrew;
    }
}
