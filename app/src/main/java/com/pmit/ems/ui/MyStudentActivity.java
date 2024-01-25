package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;
import static com.pmit.ems.api.Config.TAG_COLLAGELIST;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.pmit.ems.R;
import com.pmit.ems.adapter.LeadCountAdapter;
import com.pmit.ems.model.LeadCount;
import com.pmit.ems.model.Mystudents;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MyStudentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    RecyclerView recyclerLead,recyclerCount;
    List<Mystudents> mystudents;
    ArrayList<Mystudents> arrlist = new ArrayList<Mystudents>();
    public LeadViewAdapter homeItemViewAdapter;
    Spinner short_by_session;
    String[] session = { "Session"};
    FrameLayout progressBarHolder;
    ArrayList<String> sessionName;
    String sessionNameValue ="";
    JSONArray jsonArrayleadlist;
    ArrayList<LeadCount> arrlist1 = new ArrayList<LeadCount>();
    List<LeadCount> leadCounts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_student);
        recyclerLead = (RecyclerView) findViewById(R.id.recyclerLead);
        recyclerCount = (RecyclerView) findViewById(R.id.recyclerCount);
        short_by_session = (Spinner)findViewById(R.id.short_by_session);
        progressBarHolder = (FrameLayout)findViewById(R.id.progressBarHolder);
        short_by_session.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,session);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        short_by_session.setAdapter(aa);
        mystudents = new ArrayList<>();
//        mystudents.add(
//                new Mystudents(
//                        "Akash Mondal",
//                        "Vidysbharath Collage of Pharmacy",
//                        "D PHARMA(2022-23)",
//                        R.mipmap.students
//                ));
//
//        mystudents.add(
//                new Mystudents(
//                        "Sharbani Chakraborty",
//                        "Mother Teresa Institution of Education & Research",
//                        "B.ED(2022-23)",
//                        R.mipmap.students1
//                ));
        arrlist = new ArrayList<>();
        recyclerLead.setHasFixedSize(true);
        recyclerLead.setLayoutManager(new LinearLayoutManager(this));
        sessionName = new ArrayList<>();
        arrlist1 = new ArrayList<>();
        leadCounts = new ArrayList<>();
        Session();
        myAdmission();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sessionNameValue = short_by_session.getSelectedItem().toString();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void Session(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"session_select",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("session_select"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                JSONArray admissionLeads = obj.getJSONArray("admission_leads");
                                for (int i = 0; i<admissionLeads.length(); i++){
                                    JSONObject jsonObject = admissionLeads.getJSONObject(i);
                                    String sessionValue = jsonObject.getString("session_value");
                                    sessionName.add(sessionValue);
                                    System.out.println("sessionValue"+" "+sessionValue);
                                    ArrayAdapter sessonData = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,sessionName);
                                    sessonData.setDropDownViewResource(R.layout.spinner_text_view);
                                    short_by_session.setAdapter(sessonData);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Empty Session Data",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("", "");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("session_select"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void myAdmission(){
        progressBarHolder.setVisibility(View.VISIBLE);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"admission_leads_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("admission_leads_list"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            progressBarHolder.setVisibility(View.GONE);
                            if (status.equals("true")){
                                leadCourseCount();
                               JSONArray admissionLeads = obj.getJSONArray("admission_leads");
                                for (int i = 0; i<admissionLeads.length(); i++){
                                    JSONObject jsonObject = admissionLeads.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String fname = jsonObject.getString("fname");
                                    String mname = jsonObject.getString("mname");
                                    String lname = jsonObject.getString("lname");
                                    String mobile_no = jsonObject.getString("mobile_no");
                                    String whatsapp_no = jsonObject.getString("whatsapp_no");
                                    String email = jsonObject.getString("email");
                                    String city_id = jsonObject.getString("city_id");
                                    String state_id = jsonObject.getString("state_id");
                                    String user_id = jsonObject.getString("user_id");
                                    String course_id = jsonObject.getString("course_id");
                                    String source_id = jsonObject.getString("source_id");
                                    String campaign_id = jsonObject.getString("campaign_id");
                                    String session_id = jsonObject.getString("session_id");
                                    String lead_followups = jsonObject.getString("lead_followups");
                                    String lead_stage = jsonObject.getString("lead_stage");
                                    String assign_type = jsonObject.getString("assign_type");
                                    String assign_to = jsonObject.getString("assign_to");
                                    String statuss = jsonObject.getString("status");
                                    String lead_view_status = jsonObject.getString("lead_view_status");
                                    String comment = jsonObject.getString("comment");
                                    String comment_date = jsonObject.getString("comment_date");
                                    String counselor_id = jsonObject.getString("counselor_id");
                                    String created_at = jsonObject.getString("created_at");
                                    String updated_at = jsonObject.getString("updated_at");
                                    String lead_update_date = jsonObject.getString("lead_update_date");
                                    String reason_for_edit = jsonObject.getString("reason_for_edit");
                                    String lead_details_edit_date = jsonObject.getString("lead_details_edit_date");
                                    String lead_details_edited_by_type = jsonObject.getString("lead_details_edited_by_type");
                                    String lead_details_edited_by = jsonObject.getString("lead_details_edited_by");
                                    String is_followup = jsonObject.getString("is_followup");
                                    String otp = jsonObject.getString("otp");
                                    String is_revoked = jsonObject.getString("is_revoked");
                                    String revoked_date_time = jsonObject.getString("revoked_date_time");
                                    String lead_form_name = jsonObject.getString("lead_form_name");
                                    String lead_form_location = jsonObject.getString("lead_form_location");
                                    String qualification_id = jsonObject.getString("qualification_id");
                                    String revoke_reason_type = jsonObject.getString("revoke_reason_type");
                                    String revoke_value = jsonObject.getString("revoke_value");
                                    String consultant_lead_status = jsonObject.getString("consultant_lead_status");
                                    String admission_status = jsonObject.getString("admission_status");
                                    String consultant_id = jsonObject.getString("consultant_id");
                                    String is_duplicate_from_consultant = jsonObject.getString("is_duplicate_from_consultant");
                                    String reason_fr_suspend = jsonObject.getString("reason_fr_suspend");
                                    String is_refund = jsonObject.getString("is_refund");
                                    String secondary_admit = jsonObject.getString("secondary_admit");
                                    String councellor_end_admission_status = jsonObject.getString("councellor_end_admission_status");
                                    String sorting_date = jsonObject.getString("sorting_date");
                                    String college_id = jsonObject.getString("college_id");
                                    String student_phoneno = jsonObject.getString("student_phoneno");
                                    String alternative_phoneno = jsonObject.getString("alternative_phoneno");
                                    String list_property = jsonObject.getString("list_property");
                                    String srn_no = jsonObject.getString("srn_no");
                                    String collegename = jsonObject.getString("collegename");
                                    String photo = jsonObject.getString("photo");
                                    JSONObject jsonObjectCourse = jsonObject.getJSONObject("course");
                                    String courseId = jsonObjectCourse.getString("id");
                                    String course_name = jsonObjectCourse.getString("course_name");
                                    String course_slug = jsonObjectCourse.getString("course_slug");
                                    String counsellor_id = jsonObjectCourse.getString("counsellor_id");
                                    String course_stream_id = jsonObjectCourse.getString("course_stream_id");
                                    String coursestatus = jsonObjectCourse.getString("status");
                                    String coursecreated_at = jsonObjectCourse.getString("created_at");
                                    String courseupdated_at= jsonObjectCourse.getString("updated_at");
                                    String coursis_show_in_widge= jsonObjectCourse.getString("is_show_in_widge");
                                    String coursduration= jsonObjectCourse.getString("duration");
                                    String termsandcondition= jsonObjectCourse.getString("termsandcondition");
                                    System.out.println("termsandcondition"+termsandcondition);
                                    mystudents.add(
                                                new Mystudents(
                                                         id,  fname,  mname,  lname,  mobile_no,  whatsapp_no,
                                                         email,  city_id,  state_id,  user_id,  course_id,  source_id,  campaign_id,
                                                         session_id,  lead_followups,  lead_stage,  assign_type,  assign_to,  statuss,
                                                         lead_view_status,  comment,  comment_date,  counselor_id,  created_at,  updated_at,
                                                         lead_update_date,  reason_for_edit,  lead_details_edit_date,  lead_details_edited_by_type,
                                                         lead_details_edited_by,  is_followup,  otp,  is_revoked,  revoked_date_time,
                                                         lead_form_name,  lead_form_location,  qualification_id,  revoke_reason_type,  revoke_value,
                                                         consultant_lead_status,  admission_status,  consultant_id,  is_duplicate_from_consultant,
                                                         reason_fr_suspend,  is_refund,  secondary_admit,  councellor_end_admission_status,
                                                         sorting_date,  college_id,  student_phoneno,  list_property,  srn_no,collegename,photo,
                                                         courseId,  course_name,  course_slug,  counsellor_id,  course_stream_id,  coursestatus,
                                                         coursecreated_at,  courseupdated_at,  coursis_show_in_widge,  coursduration,  termsandcondition));

                                    arrlist.addAll(mystudents);
                                    homeItemViewAdapter = new LeadViewAdapter(getApplicationContext(), mystudents);
                                    recyclerLead.setAdapter(homeItemViewAdapter);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Empty Admission List",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("", "");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("admission_leads_list"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class LeadViewAdapter extends RecyclerView.Adapter<LeadViewAdapter.ViewHolder> {
        private Context mCtx;
        private List<Mystudents> mystudents;
        int listview;
        public LeadViewAdapter(Context mCtx, List<Mystudents> mystudents) {
            this.mCtx = mCtx;
            this.mystudents = mystudents;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_student_card, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Mystudents mystudentss = mystudents.get(position);
            listview = position;
            holder.led_tv.setText(mystudentss.getFname());
            holder.collageName.setText(mystudentss.getCollegename());
            holder.led_tv5.setText(mystudentss.getCourse_name());
//            holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(mystudentss.getProfileImage()));
            String collageUrl= "https://developer.pmitcolleges.org/"+mystudentss.getPhoto();
            Glide.with(mCtx)
                    .load(collageUrl)
                    .into(holder.Img_Id);
            if (position==0){
                holder.viewLine.setVisibility(View.GONE);
            }
            holder.rv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editLeadId = mystudentss.getId();
                    String collagesId = mystudentss.getCollege_id();
                    String session_id = mystudentss.getSession_id();
                    String source_id = mystudentss.getSource_id();
                    String course_id = mystudentss.getCourse_id();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivityRejection.class);
                    intent.putExtra("editLeadId",editLeadId);
                    intent.putExtra("collagesId",collagesId);
                    intent.putExtra("session_id",session_id);
                    intent.putExtra("source_id",source_id);
                    intent.putExtra("course_id",course_id);
                    intent.putExtra("flag","1");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            });
        }
        @Override
        public int getItemCount() {
            return mystudents.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView led_tv, collageName,led_tv5;
            private ImageView Img_Id;
            private View viewLine;
            private RelativeLayout rv;
            public ViewHolder(View view) {
                super(view);
                led_tv = (TextView) view.findViewById(R.id.led_tv);
                collageName = (TextView) view.findViewById(R.id.collageName);
                led_tv5 = (TextView) view.findViewById(R.id.led_tv5);
                Img_Id = (ImageView) view.findViewById(R.id.Img_Id);
                viewLine = (View) view.findViewById(R.id.viewLine);
                rv = (RelativeLayout) view.findViewById(R.id.rv);
            }
        }
    }
    public void leadCourseCount(){
        progressBarHolder.setVisibility(View.VISIBLE);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"get_lead_course_count",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("course_count"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            progressBarHolder.setVisibility(View.GONE);
                            if (status.equals("true")){
                                jsonArrayleadlist = obj.getJSONArray("leadlist");
                                for (int i = 0; i<jsonArrayleadlist.length(); i++){
                                    JSONObject jsonObject = jsonArrayleadlist.getJSONObject(i);
                                    String total_leads = jsonObject.getString("total_leads");
                                    String course_name = jsonObject.getString("course_name");
                                    System.out.println("course_count"+" "+total_leads+" "+course_name);
                                    leadCounts.add(
                                            new LeadCount(
                                                    total_leads,course_name
                                            ));
                                    arrlist1.addAll(leadCounts);
                                    LeadCountAdapter adapter = new LeadCountAdapter(getApplication(), leadCounts);
                                    recyclerCount.setAdapter(adapter);
                                    recyclerCount.setLayoutManager(new LinearLayoutManager(getApplication(),
                                            LinearLayoutManager.HORIZONTAL, true));
//                                    PagerSnapHelper snapHelper = new PagerSnapHelper();
//                                    snapHelper.attachToRecyclerView(recyclerCount);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Empty Count List",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("", "");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("leadCourseCount"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}
