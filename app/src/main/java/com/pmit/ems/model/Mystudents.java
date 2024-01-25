package com.pmit.ems.model;

public class Mystudents {
    private String id;
    private String fname;
    private String mname;
    private String lname;
    private String mobile_no;
    private String whatsapp_no;
    private String email;
    private String city_id;
    private String state_id;
    private String user_id;
    private String course_id;
    private String source_id;
    private String campaign_id;
    private String session_id;
    private String lead_followups;
    private String lead_stage;
    private String assign_type;
    private String assign_to;
    private String statuss;
    private String lead_view_status;
    private String comment;
    private String comment_date;
    private String counselor_id;
    private String created_at;
    private String updated_at;
    private String lead_update_date;
    private String reason_for_edit;
    private String lead_details_edit_date;
    private String lead_details_edited_by_type;
    private String lead_details_edited_by;
    private String is_followup;
    private String otp;
    private String is_revoked;
    private String revoked_date_time;
    private String lead_form_name;
    private String lead_form_location;
    private String qualification_id;
    private String revoke_reason_type;
    private String revoke_value;
    private String consultant_lead_status;
    private String admission_status;
    private String consultant_id;
    private String is_duplicate_from_consultant;
    private String reason_fr_suspend;
    private String is_refund;
    private String secondary_admit;
    private String councellor_end_admission_status;
    private String sorting_date;
    private String college_id;
    private String student_phoneno;
    private String list_property;
    private String srn_no;
    private String collegename;
    private String photo;
    private String courseId;
    private String course_name;
    private String course_slug;
    private String counsellor_id;
    private String course_stream_id;
    private String coursestatus;
    private String coursecreated_at;
    private String courseupdated_at;
    private String coursis_show_in_widge;
    private String coursduration;
    private String termsandcondition;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Mystudents(String id, String fname, String mname, String lname, String mobile_no, String whatsapp_no,
                      String email, String city_id, String state_id, String user_id, String course_id, String source_id, String campaign_id,
                      String session_id, String lead_followups, String lead_stage, String assign_type, String assign_to, String statuss,
                      String lead_view_status, String comment, String comment_date, String counselor_id, String created_at, String updated_at,
                      String lead_update_date, String reason_for_edit, String lead_details_edit_date, String lead_details_edited_by_type,
                      String lead_details_edited_by, String is_followup, String otp, String is_revoked, String revoked_date_time,
                      String lead_form_name, String lead_form_location, String qualification_id, String revoke_reason_type, String revoke_value,
                      String consultant_lead_status, String admission_status, String consultant_id, String is_duplicate_from_consultant,
                      String reason_fr_suspend, String is_refund, String secondary_admit, String councellor_end_admission_status,
                      String sorting_date, String college_id, String student_phoneno, String list_property, String srn_no, String collegename, String photo,
                      String courseId, String course_name, String course_slug, String counsellor_id, String course_stream_id, String coursestatus,
                      String coursecreated_at, String courseupdated_at, String coursis_show_in_widge, String coursduration, String termsandcondition){
        this.id=id;
        this.fname=fname;
        this.mname=mname;
        this.lname=lname;
        this.mobile_no=mobile_no;
        this.whatsapp_no=whatsapp_no;
        this.email=email;
        this.city_id=city_id;
        this.state_id=state_id;
        this.user_id=user_id;
        this.course_id=course_id;
        this.source_id=source_id;
        this.campaign_id=campaign_id;
        this.session_id=session_id;
        this.lead_followups=lead_followups;
        this.lead_stage=lead_stage;
        this.assign_type=assign_type;
        this.assign_to=assign_to;
        this.statuss=statuss;
        this.lead_view_status=lead_view_status;
        this.comment=comment;
        this.comment_date=comment_date;
        this.counselor_id=counselor_id;
        this.created_at=created_at;
        this.updated_at=updated_at;
        this.lead_update_date=lead_update_date;
        this.reason_for_edit = reason_for_edit;
        this.lead_details_edit_date=lead_details_edit_date;
        this.lead_details_edited_by_type=lead_details_edited_by_type;
        this.lead_details_edited_by=lead_details_edited_by;
        this.is_followup=is_followup;
        this.otp=otp;
        this.is_revoked=is_revoked;
        this.revoked_date_time=revoked_date_time;
        this.lead_form_name=lead_form_name;
        this.lead_form_location=lead_form_location;
        this.qualification_id=qualification_id;
        this.revoke_reason_type=revoke_reason_type;
        this.revoke_value=revoke_value;
        this.consultant_lead_status=consultant_lead_status;
        this.admission_status=admission_status;
        this.consultant_id=consultant_id;
        this.is_duplicate_from_consultant=is_duplicate_from_consultant;
        this.reason_fr_suspend=reason_fr_suspend;
        this.is_refund=is_refund;
        this.secondary_admit=secondary_admit;
        this.councellor_end_admission_status=councellor_end_admission_status;
        this.sorting_date=sorting_date;
        this.college_id=college_id;
        this.student_phoneno=student_phoneno;
        this.list_property=list_property;
        this.srn_no=srn_no;
        this.collegename=collegename;
        this.photo=photo;
        this.courseId=courseId;
        this.course_name=course_name;
        this.course_slug=course_slug;
        this.counsellor_id=counsellor_id;
        this.course_stream_id=course_stream_id;
        this.coursestatus=coursestatus;
        this.coursecreated_at=coursecreated_at;
        this.courseupdated_at=courseupdated_at;
        this.coursis_show_in_widge=coursis_show_in_widge;
        this.coursduration=coursduration;
        this.termsandcondition=termsandcondition;
    }

    public String getId() {
        return id;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
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

    public String getLead_stage() {
        return lead_stage;
    }

    public void setLead_stage(String lead_stage) {
        this.lead_stage = lead_stage;
    }

    public String getAssign_type() {
        return assign_type;
    }

    public void setAssign_type(String assign_type) {
        this.assign_type = assign_type;
    }

    public String getAssign_to() {
        return assign_to;
    }

    public void setAssign_to(String assign_to) {
        this.assign_to = assign_to;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public String getLead_view_status() {
        return lead_view_status;
    }

    public void setLead_view_status(String lead_view_status) {
        this.lead_view_status = lead_view_status;
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

    public String getCounselor_id() {
        return counselor_id;
    }

    public void setCounselor_id(String counselor_id) {
        this.counselor_id = counselor_id;
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

    public String getLead_update_date() {
        return lead_update_date;
    }

    public void setLead_update_date(String lead_update_date) {
        this.lead_update_date = lead_update_date;
    }

    public String getReason_for_edit() {
        return reason_for_edit;
    }

    public void setReason_for_edit(String reason_for_edit) {
        this.reason_for_edit = reason_for_edit;
    }

    public String getLead_details_edit_date() {
        return lead_details_edit_date;
    }

    public void setLead_details_edit_date(String lead_details_edit_date) {
        this.lead_details_edit_date = lead_details_edit_date;
    }

    public String getLead_details_edited_by_type() {
        return lead_details_edited_by_type;
    }

    public void setLead_details_edited_by_type(String lead_details_edited_by_type) {
        this.lead_details_edited_by_type = lead_details_edited_by_type;
    }

    public String getLead_details_edited_by() {
        return lead_details_edited_by;
    }

    public void setLead_details_edited_by(String lead_details_edited_by) {
        this.lead_details_edited_by = lead_details_edited_by;
    }

    public String getIs_followup() {
        return is_followup;
    }

    public void setIs_followup(String is_followup) {
        this.is_followup = is_followup;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getIs_revoked() {
        return is_revoked;
    }

    public void setIs_revoked(String is_revoked) {
        this.is_revoked = is_revoked;
    }

    public String getRevoked_date_time() {
        return revoked_date_time;
    }

    public void setRevoked_date_time(String revoked_date_time) {
        this.revoked_date_time = revoked_date_time;
    }

    public String getLead_form_name() {
        return lead_form_name;
    }

    public void setLead_form_name(String lead_form_name) {
        this.lead_form_name = lead_form_name;
    }

    public String getLead_form_location() {
        return lead_form_location;
    }

    public void setLead_form_location(String lead_form_location) {
        this.lead_form_location = lead_form_location;
    }

    public String getQualification_id() {
        return qualification_id;
    }

    public void setQualification_id(String qualification_id) {
        this.qualification_id = qualification_id;
    }

    public String getRevoke_reason_type() {
        return revoke_reason_type;
    }

    public void setRevoke_reason_type(String revoke_reason_type) {
        this.revoke_reason_type = revoke_reason_type;
    }

    public String getRevoke_value() {
        return revoke_value;
    }

    public void setRevoke_value(String revoke_value) {
        this.revoke_value = revoke_value;
    }

    public String getConsultant_lead_status() {
        return consultant_lead_status;
    }

    public void setConsultant_lead_status(String consultant_lead_status) {
        this.consultant_lead_status = consultant_lead_status;
    }

    public String getAdmission_status() {
        return admission_status;
    }

    public void setAdmission_status(String admission_status) {
        this.admission_status = admission_status;
    }

    public String getConsultant_id() {
        return consultant_id;
    }

    public void setConsultant_id(String consultant_id) {
        this.consultant_id = consultant_id;
    }

    public String getIs_duplicate_from_consultant() {
        return is_duplicate_from_consultant;
    }

    public void setIs_duplicate_from_consultant(String is_duplicate_from_consultant) {
        this.is_duplicate_from_consultant = is_duplicate_from_consultant;
    }

    public String getReason_fr_suspend() {
        return reason_fr_suspend;
    }

    public void setReason_fr_suspend(String reason_fr_suspend) {
        this.reason_fr_suspend = reason_fr_suspend;
    }

    public String getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(String is_refund) {
        this.is_refund = is_refund;
    }

    public String getSecondary_admit() {
        return secondary_admit;
    }

    public void setSecondary_admit(String secondary_admit) {
        this.secondary_admit = secondary_admit;
    }

    public String getCouncellor_end_admission_status() {
        return councellor_end_admission_status;
    }

    public void setCouncellor_end_admission_status(String councellor_end_admission_status) {
        this.councellor_end_admission_status = councellor_end_admission_status;
    }

    public String getSorting_date() {
        return sorting_date;
    }

    public void setSorting_date(String sorting_date) {
        this.sorting_date = sorting_date;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getStudent_phoneno() {
        return student_phoneno;
    }

    public void setStudent_phoneno(String student_phoneno) {
        this.student_phoneno = student_phoneno;
    }

    public String getList_property() {
        return list_property;
    }

    public void setList_property(String list_property) {
        this.list_property = list_property;
    }

    public String getSrn_no() {
        return srn_no;
    }

    public void setSrn_no(String srn_no) {
        this.srn_no = srn_no;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_slug() {
        return course_slug;
    }

    public void setCourse_slug(String course_slug) {
        this.course_slug = course_slug;
    }

    public String getCounsellor_id() {
        return counsellor_id;
    }

    public void setCounsellor_id(String counsellor_id) {
        this.counsellor_id = counsellor_id;
    }

    public String getCourse_stream_id() {
        return course_stream_id;
    }

    public void setCourse_stream_id(String course_stream_id) {
        this.course_stream_id = course_stream_id;
    }

    public String getCoursestatus() {
        return coursestatus;
    }

    public void setCoursestatus(String coursestatus) {
        this.coursestatus = coursestatus;
    }

    public String getCoursecreated_at() {
        return coursecreated_at;
    }

    public void setCoursecreated_at(String coursecreated_at) {
        this.coursecreated_at = coursecreated_at;
    }

    public String getCourseupdated_at() {
        return courseupdated_at;
    }

    public void setCourseupdated_at(String courseupdated_at) {
        this.courseupdated_at = courseupdated_at;
    }

    public String getCoursis_show_in_widge() {
        return coursis_show_in_widge;
    }

    public void setCoursis_show_in_widge(String coursis_show_in_widge) {
        this.coursis_show_in_widge = coursis_show_in_widge;
    }

    public String getCoursduration() {
        return coursduration;
    }

    public void setCoursduration(String coursduration) {
        this.coursduration = coursduration;
    }

    public String getTermsandcondition() {
        return termsandcondition;
    }

    public void setTermsandcondition(String termsandcondition) {
        this.termsandcondition = termsandcondition;
    }
}
