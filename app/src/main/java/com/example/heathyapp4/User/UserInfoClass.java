package com.example.heathyapp4.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInfoClass {

   private String CrNumber;

   private String CompanyName;

    private String Phone;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid = user.getUid();

    public UserInfoClass(String crNumber, String companyName, String phone, String city, String district, String streetName, String buildingNumber, String email) {
        uid = uid;
        CrNumber = crNumber;
        this.uid = uid;
        CompanyName = companyName;
        Phone = phone;
        this.city = city;
        District = district;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.email = email;
    }

    public String getCrNumber() {
        return CrNumber;
    }

    public void setCrNumber(String crNumber) {
        CrNumber = crNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String city;

    private String District;

    private String streetName;

    private String buildingNumber;


    private String email;

    public UserInfoClass(){

    }

    /*********************************************************/




}






