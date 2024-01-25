package com.pmit.ems.model;

public class Leadfollowuphistory {
    private String lead_followups;
    private String comment;
    private String comment_date;
    private String lead_stage;

    public Leadfollowuphistory(String lead_followups,String comment,String comment_date,String lead_stage){
        this.lead_followups=lead_followups;
        this.comment=comment;
        this.comment_date=comment_date;
        this.lead_stage=lead_stage;
    }

    public String getLead_followups() {
        return lead_followups;
    }

    public void setLead_followups(String lead_followups) {
        this.lead_followups = lead_followups;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getLead_stage() {
        return lead_stage;
    }

    public void setLead_stage(String lead_stage) {
        this.lead_stage = lead_stage;
    }
}
