package com.example.sce.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String name;
    private String password;
    private String address;
    private String livingCity;
    private String dateOfBirth;
    private String NIC;
    private String emailAddress;
    private String gender;
    private String mobilePhoneNumber;
    private String profilePicturePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(long id, String name, String password, String address, String livingCity, String dateOfBirth, String NIC, String emailAddress, String gender, String mobilePhoneNumber, String profilePicturePath) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.livingCity = livingCity;
        this.dateOfBirth = dateOfBirth;
        this.NIC = NIC;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.profilePicturePath = profilePicturePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", livingCity='" + livingCity + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", NIC='" + NIC + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", gender='" + gender + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                '}';
    }

    public User(String name, String password, String address, String livingCity, String dateOfBirth, String NIC, String emailAddress, String gender, String mobilePhoneNumber, String profilePicturePath) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.livingCity = livingCity;
        this.dateOfBirth = dateOfBirth;
        this.NIC = NIC;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.profilePicturePath = profilePicturePath;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
