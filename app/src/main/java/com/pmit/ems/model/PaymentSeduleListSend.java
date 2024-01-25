package com.pmit.ems.model;

public class PaymentSeduleListSend {
    public String id;
    private String installmentType;
    private String amount;
    private String scheduleDate;
    public PaymentSeduleListSend(String id,String installmentType,String amount,String scheduleDate){
        this.id=id;
        this.installmentType=installmentType;
        this.amount=amount;
        this.scheduleDate=scheduleDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstallmentType() {
        return installmentType;
    }

    public void setInstallmentType(String installmentType) {
        this.installmentType = installmentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}
