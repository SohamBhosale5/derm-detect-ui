package com.skin.skinapp2.models;

public class HosptialsNearby {
    private String name;
    private String speciality;
    private String address;
    private String latitude;
    private String logitude;
    private String doctor_name;
    private String contact;

    public HosptialsNearby(String name, String speciality, String address, String latitude, String logitude, String doctor_name, String contact) {
        this.name = name;
        this.speciality = speciality;
        this.address = address;
        this.latitude = latitude;
        this.logitude = logitude;
        this.doctor_name = doctor_name;
        this.contact = contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
