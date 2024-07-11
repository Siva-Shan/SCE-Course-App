package com.example.sce.model;

import java.util.ArrayList;
import java.util.List;

public class Purchase {
    private String purchaseCode;
    private double totalPrice;
    private List<Enrollment> enrollments;

    public Purchase() {
        enrollments = new ArrayList<>();
    }

    public Purchase(String purchaseCode, double totalPrice, List<Enrollment> enrollments) {
        this.purchaseCode = purchaseCode;
        this.totalPrice = totalPrice;
        this.enrollments = enrollments;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
}
