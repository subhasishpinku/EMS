package com.pmit.ems.model;

public class LeadEditDetails {
    public String id;
    private String leadId;
    private String courseId;
    private String installmentType;
    private String amount;
    private String scheduleDate;
    private String statusUs;
    private String createdAt;
    private String updatedAt;
    private String count;
    private String rownumber;
    public LeadEditDetails(String id,String leadId,String courseId,String installmentType,String amount,String scheduleDate,String statusUs,String createdAt,String updatedAt,String count,String rownumber){
        this.id=id;
        this.leadId=leadId;
        this.courseId=courseId;
        this.installmentType=installmentType;
        this.amount=amount;
        this.scheduleDate=scheduleDate;
        this.statusUs=statusUs;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.count=count;
        this.rownumber=rownumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getStatusUs() {
        return statusUs;
    }

    public void setStatusUs(String statusUs) {
        this.statusUs = statusUs;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRownumber() {
        return rownumber;
    }

    public void setRownumber(String rownumber) {
        this.rownumber = rownumber;
    }
}
