package com.example.sce.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Enrollment implements Parcelable {
    private String purchaseCode;
    private int userId;
    private int courseId;
    private String branch;
    private String purchasedDate;

    public Enrollment() {
    }

    protected Enrollment(Parcel in) {
        purchaseCode = in.readString();
        userId = in.readInt();
        courseId = in.readInt();
        branch = in.readString();
        purchasedDate = in.readString();
    }

    public static final Creator<Enrollment> CREATOR = new Creator<Enrollment>() {
        @Override
        public Enrollment createFromParcel(Parcel in) {
            return new Enrollment(in);
        }

        @Override
        public Enrollment[] newArray(int size) {
            return new Enrollment[size];
        }
    };

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(String purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Enrollment(String purchaseCode, int userId, int courseId, String branch, String purchasedDate) {
        this.purchaseCode = purchaseCode;
        this.userId = userId;
        this.courseId = courseId;
        this.branch = branch;
        this.purchasedDate = purchasedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(purchaseCode);
        dest.writeInt(userId);
        dest.writeInt(courseId);
        dest.writeString(branch);
        dest.writeString(purchasedDate);
    }
}
