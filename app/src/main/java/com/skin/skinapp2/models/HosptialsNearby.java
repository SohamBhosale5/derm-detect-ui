package com.skin.skinapp2.models;

import java.util.ArrayList;
import java.util.List;

public class HosptialsNearby {
    //private List<HospitalDetails> hospitalDetails;
    private String name;
    private String speciality;
    private String address;
    private String latitude;
    private String longitute;
    private String doctor_name;
    private String contact;

    public HosptialsNearby(String name, String speciality, String address, String latitude, String logitude, String doctor_name, String contact) {
        //List<HospitalDetails> hospitalDetails;
        this.name = name;
        this.speciality = speciality;
        this.address = address;
        this.latitude = latitude;
        this.longitute = logitude;
        this.doctor_name = doctor_name;
        this.contact = contact;
    }

    public HosptialsNearby(){

    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitute;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getContact() {
        return contact;
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

    public void setLongitude(String longitude) {
        this.longitute = longitude;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}