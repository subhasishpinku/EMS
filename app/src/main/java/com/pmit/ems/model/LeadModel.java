package com.pmit.ems.model;

public class LeadModel {
    private String id;
    private String fname;
    private String mobile_no;
    private String whatsapp_no;
    private String email;
    private String course_id;
    private String source_id;
    private String session_id;
    private String lead_followups;
    private String status1;
    private String created_at;
    private String college_id;
    private String course_name;
    private String sessionname;
    private String counsellorname;
    private String check_payment_approval_status;
    private String check_srn;
    private String srn;
    private String srn_flag;
    private String rejectform;
    private String  check_booking;
    public LeadModel(String id, String fname,  String mobile_no, String whatsapp_no, String email, String course_id, String source_id,
                     String session_id, String lead_followups,  String status1,
                       String created_at, String college_id,  String course_name,String sessionname,String counsellorname,String check_payment_approval_status,
                     String check_srn,String srn,String srn_flag,String rejectform,String check_booking
   ){
     this.id=id;
     this.fname=fname;
     this.mobile_no=mobile_no;
     this.whatsapp_no=whatsapp_no;
     this.email=email;
     this.course_id=course_id;
     this.source_id=source_id;
     this.session_id=session_id;
     this.lead_followups=lead_followups;
     this.status1=status1;
     this.created_at=created_at;
     this.college_id=college_id;
     this.course_name=course_name;
     this.sessionname=sessionname;
     this.counsellorname=counsellorname;
     this.check_payment_approval_status=check_payment_approval_status;

        this.check_srn=check_srn;
        this.srn=srn;
        this.srn_flag=srn_flag;
        this.rejectform=rejectform;
        this.check_booking=check_booking;
   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getWhatsapp_no() {
        return whatsapp_no;
    }

    public void setWhatsapp_no(String whatsapp_no) {
        this.whatsapp_no = whatsapp_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getLead_followups() {
        return lead_followups;
    }

    public void setLead_followups(String lead_followups) {
        this.lead_followups = lead_followups;
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

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getSessionname() {
        return sessionname;
    }

    public void setSessionname(String sessionname) {
        this.sessionname = sessionname;
    }

    public String getCounsellorname() {
        return counsellorname;
    }

    public void setCounsellorname(String counsellorname) {
        this.counsellorname = counsellorname;
    }

    public String getCheck_payment_approval_status() {
        return check_payment_approval_status;
    }

    public void setCheck_payment_approval_status(String check_payment_approval_status) {
        this.check_payment_approval_status = check_payment_approval_status;
    }

    public String getCheck_srn() {
        return check_srn;
    }

    public void setCheck_srn(String check_srn) {
        this.check_srn = check_srn;
    }

    public String getSrn() {
        return srn;
    }

    public void setSrn(String srn) {
        this.srn = srn;
    }

    public String getSrn_flag() {
        return srn_flag;
    }

    public void setSrn_flag(String srn_flag) {
        this.srn_flag = srn_flag;
    }

    public String getRejectform() {
        return rejectform;
    }

    public void setRejectform(String rejectform) {
        this.rejectform = rejectform;
    }

    public String getCheck_booking() {
        return check_booking;
    }

    public void setCheck_booking(String check_booking) {
        this.check_booking = check_booking;
    }
}
