package com.pmit.ems.model;

public class LeadCount {
    private String totalLeads;
    private String courseName;
    public LeadCount(String totalLeads,String courseName){
        this.totalLeads=totalLeads;
        this.courseName=courseName;
    }

    public String getTotalLeads() {
        return totalLeads;
    }

    public void setTotalLeads(String totalLeads) {
        this.totalLeads = totalLeads;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
