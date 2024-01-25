package com.pmit.ems.model;

public class PaymentInfoSetget {
    private String id;
    private String lead_id;
    private String installment_type;
    private String amount;
    private String schedule_date;
    private String payment_date;
    private String status1;
    private String created_at;
    private String updated_at;
    private String temp_fees_structure_id;
    private String student_fees_structure_id;
    private String approved_status;
    private String tcol;
//    private String course_id;
    private String checking_flag;
    private String rowmsg;
    public PaymentInfoSetget(String id,String lead_id,String installment_type,String amount,
                             String schedule_date,String payment_date,String status1,String created_at,
                             String updated_at,String temp_fees_structure_id,
                             String student_fees_structure_id,String approved_status,String tcol,String checking_flag,String rowmsg){
        this.id=id;
        this.lead_id=lead_id;
        this.installment_type=installment_type;
        this.amount=amount;
        this.schedule_date=schedule_date;
        this.payment_date=payment_date;
        this.status1=status1;
        this.created_at=created_at;
        this.updated_at=updated_at;
        this.temp_fees_structure_id=temp_fees_structure_id;
        this.student_fees_structure_id=student_fees_structure_id;
        this.approved_status=approved_status;
        this.tcol=tcol;
//        this.course_id=course_id;
        this.checking_flag=checking_flag;
        this.rowmsg=rowmsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getInstallment_type() {
        return installment_type;
    }

    public void setInstallment_type(String installment_type) {
        this.installment_type = installment_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public String getApproved_status() {
        return approved_status;
    }

    public void setApproved_status(String approved_status) {
        this.approved_status = approved_status;
    }

    public String getTcol() {
        return tcol;
    }

    public void setTcol(String tcol) {
        this.tcol = tcol;
    }

    public String getChecking_flag() {
        return checking_flag;
    }

    public void setChecking_flag(String checking_flag) {
        this.checking_flag = checking_flag;
    }

    public String getRowmsg() {
        return rowmsg;
    }

    public void setRowmsg(String rowmsg) {
        this.rowmsg = rowmsg;
    }
}
