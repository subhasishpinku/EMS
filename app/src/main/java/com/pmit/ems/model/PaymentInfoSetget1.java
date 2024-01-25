package com.pmit.ems.model;

public class PaymentInfoSetget1 {
    private int id;
    private String paymentType;
    private String booking;
    private String amount;
    private String date;
    private String bank;
    private String bankIds;
    private String trngactionId;

    private String accHolderName;
    private String checkDDNo;
    private String SenderBankName;
    private String branch;
    private String checkdates;
    private String installType;
    private String tcol;
    private String temp_fees_structure_id;
    private String student_fees_structure_id;
    private String imageLink;
    public PaymentInfoSetget1(int id, String paymentType, String booking, String amount,
                              String date, String bank,String bankIds,String trngactionId,String accHolderName,
                              String checkDDNo,String SenderBankName,String branch,String checkdates,String installType,
                              String tcol,String temp_fees_structure_id,String student_fees_structure_id,String imageLink){
        this.id=id;
        this.paymentType=paymentType;
        this.booking=booking;
        this.amount=amount;
        this.date=date;
        this.bank=bank;
        this.bankIds=bankIds;
        this.trngactionId=trngactionId;
        this.accHolderName=accHolderName;
        this.checkDDNo=checkDDNo;
        this.SenderBankName=SenderBankName;
        this.branch=branch;
        this.checkdates=checkdates;
        this.installType =installType;
        this.tcol=tcol;
        this.temp_fees_structure_id=temp_fees_structure_id;
        this.student_fees_structure_id=student_fees_structure_id;
        this.imageLink=imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTrngactionId() {
        return trngactionId;
    }

    public void setTrngactionId(String trngactionId) {
        this.trngactionId = trngactionId;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    public String getCheckDDNo() {
        return checkDDNo;
    }

    public void setCheckDDNo(String checkDDNo) {
        this.checkDDNo = checkDDNo;
    }

    public String getSenderBankName() {
        return SenderBankName;
    }

    public void setSenderBankName(String senderBankName) {
        SenderBankName = senderBankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankIds() {
        return bankIds;
    }

    public void setBankIds(String bankIds) {
        this.bankIds = bankIds;
    }

    public String getCheckdates() {
        return checkdates;
    }

    public void setCheckdates(String checkdates) {
        this.checkdates = checkdates;
    }

    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }

    public String getTcol() {
        return tcol;
    }

    public void setTcol(String tcol) {
        this.tcol = tcol;
    }

    public String getTemp_fees_structure_id() {
        return temp_fees_structure_id;
    }

    public void setTemp_fees_structure_id(String temp_fees_structure_id) {
        this.temp_fees_structure_id = temp_fees_structure_id;
    }

    public String getStudent_fees_structure_id() {
        return student_fees_structure_id;
    }

    public void setStudent_fees_structure_id(String student_fees_structure_id) {
        this.student_fees_structure_id = student_fees_structure_id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
