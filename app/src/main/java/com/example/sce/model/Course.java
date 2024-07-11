package com.example.sce.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Course implements Serializable, Parcelable {
    private long id;
    private String courseName;
    private double courseFee;
    private String[] branches;
    private int duration; // Duration in days
    private String publishedDate;
    private String registrationCloseDate;
    private String startDate;
    private int maxParticipants;

    public Course(String courseName, double courseFee, String[] branches, int duration, String publishedDate, String registrationCloseDate, String startDate, int maxParticipants) {
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.branches = branches;
        this.duration = duration;
        this.publishedDate = publishedDate;
        this.registrationCloseDate = registrationCloseDate;
        this.startDate = startDate;
        this.maxParticipants = maxParticipants;
    }

    public Course(long id, String courseName, double courseFee, String[] branches, int duration, String publishedDate, String registrationCloseDate, String startDate, int maxParticipants) {
        this.id = id;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.branches = branches;
        this.duration = duration;
        this.publishedDate = publishedDate;
        this.registrationCloseDate = registrationCloseDate;
        this.startDate = startDate;
        this.maxParticipants = maxParticipants;
    }

    public Course() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public String[] getBranches() {
        return branches;
    }

    public void setBranches(String[] branches) {
        this.branches = branches;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    public void setRegistrationCloseDate(String registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(courseName);
        parcel.writeDouble(courseFee);
        parcel.writeStringArray(branches);
        parcel.writeInt(duration);
        parcel.writeString(publishedDate);
        parcel.writeString(registrationCloseDate);
        parcel.writeString(startDate);
        parcel.writeInt(maxParticipants);
    }

    protected Course(Parcel in) {
        id = in.readLong();
        courseName = in.readString();
        courseFee = in.readDouble();
        branches = in.createStringArray();
        duration = in.readInt();
        publishedDate = in.readString();
        registrationCloseDate = in.readString();
        startDate = in.readString();
        maxParticipants = in.readInt();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
