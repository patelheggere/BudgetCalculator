package com.example.admin.budgetcalculator.model;

public class DetailsModel {
    private String details;
    private String date;
    private String debit_credit;
    private String remarks;
    private String month;
    private String year;
    private long ID;

    public DetailsModel() {
    }

    public DetailsModel(String details, String date, String debit_credit, String remarks, String month, String year) {
        this.details = details;
        this.date = date;
        this.debit_credit = debit_credit;
        this.remarks = remarks;
        this.month = month;
        this.year = year;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDebit_credit() {
        return debit_credit;
    }

    public void setDebit_credit(String debit_credit) {
        this.debit_credit = debit_credit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
