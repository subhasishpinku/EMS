package com.pmit.ems.model;

public class PendingList {
    private String id;
    private String fname;
    private String mname;
    private String lname;
    private String mobile_no;
    private String whatsapp_no;
    private String email;
    private String srn_no;
    private String father_name;
    private String guardian_name;
    private String mother_name;
    private String dob;
    private String photo;
    private String course_name;
    private String collegename;
    private String session_value;
    private String booking_amount;
    private String transaction_id;
    private String college_id;
    private String session_id;
    private String course_id;
    private String source_id;
    public PendingList(String id, String fname, String mname, String lname, String mobile_no, String whatsapp_no, String email, String srn_no,
                         String father_name, String guardian_name, String mother_name, String dob, String photo, String course_name, String collegename,
                         String session_value, String booking_amount, String transaction_id,String college_id,String session_id,String course_id,String source_id){
        this.id=id;
        this.fname=fname;
        this.mname=mname;
        this.lname=lname;
        this.mobile_no=mobile_no;
        this.whatsapp_no=whatsapp_no;
        this.email=email;
        this.srn_no=srn_no;
        this.father_name=father_name;
        this.guardian_name=guardian_name;
        this.mother_name=mother_name;
        this.dob=dob;
        this.photo=photo;
        this.course_name=course_name;
        this.collegename=collegename;
        this.session_value=session_value;
        this.booking_amount=booking_amount;
        this.transaction_id=transaction_id;
        this.college_id=college_id;
        this.session_id=session_id;
        this.course_id=course_id;
        this.source_id=source_id;
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

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getSrn_no() {
        return srn_no;
    }

    public void setSrn_no(String srn_no) {
        this.srn_no = srn_no;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getGuardian_name() {
        return guardian_name;
    }

    public void setGuardian_name(String guardian_name) {
        this.guardian_name = guardian_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getSession_value() {
        return session_value;
    }

    public void setSession_value(String session_value) {
        this.session_value = session_value;
    }

    public String getBooking_amount() {
        return booking_amount;
    }

    public void setBooking_amount(String booking_amount) {
        this.booking_amount = booking_amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
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
}
