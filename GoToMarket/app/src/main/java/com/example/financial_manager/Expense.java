package com.example.financial_manager;

public class Expense {

    private int id;

    private String name;

    private String category;

    private long value;

    private String startDate;

    private long installments;

    private long installment;

    private long month;

    private long year;

    public Expense(int id, String name, String category, long value, String startDate,
                   long installments, long installment, long month, long year){
        this.id = id;
        this.name = name;
        this.category = category;
        this.value = value;
        this.startDate = startDate;
        this.installments = installments;
        this.installment = installment;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public long getValue() {
        return value;
    }

    public String getStartDate() {
        return startDate;
    }

    public long getInstallments() {
        return installments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Id:" + this.id +
                " - Name: " + this.name +
                "\n" +
                //" Category: " + this.category +
                " Installment: " + this.installment + "/" + this.installments +
                "\n" +
                " Settle: " + this.month + "/" + this.year +
                " $" + this.value;
    }

    public long getInstallment() {
        return installment;
    }

    public long getMonth() {
        return month;
    }

    public long getYear() {
        return year;
    }

}
