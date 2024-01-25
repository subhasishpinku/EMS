package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;
import static com.pmit.ems.api.Config.TAG_COURSELIST;
import static com.pmit.ems.api.Config.TAG_SOURCELIST;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pmit.ems.R;
import com.pmit.ems.api.Config;
import com.pmit.ems.model.LeadCount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditLead_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner course,source;
    String[] courses = { "D.Pharma", "BCA", "B.Ed", "BMLT", "B.Pharma"};
    TextInputEditText names,pnone1,pnone2,emails,resonText,middle_name,last_name,student_phone,alternative_phone;
    MaterialButton send,back;
    String name,mPhone,wPhone,course1,date,email,editLeadId,courseIds;
    String courseId="";
    String courseName="";
    String sourseId="";
    String sourseName="";
    public JSONArray jsonArrayCourse;
    public JSONArray jsonArraysource;
    ArrayList<String> coursesName;
    ArrayList<String> sourceName;
    LinearLayout lvSource;
    String course_name="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_lead_activity);
        names = (TextInputEditText)findViewById(R.id.names);
        pnone1 = (TextInputEditText)findViewById(R.id.pnone1);
        pnone2 = (TextInputEditText)findViewById(R.id.pnone2);
        emails = (TextInputEditText)findViewById(R.id.emails);
        send = (MaterialButton)findViewById(R.id.send);
        middle_name = (TextInputEditText)findViewById(R.id.middle_name);
        last_name = (TextInputEditText)findViewById(R.id.last_name);
        student_phone = (TextInputEditText)findViewById(R.id.student_phone);
        alternative_phone = (TextInputEditText)findViewById(R.id.alternative_phone);
//      back = (MaterialButton)findViewById(R.id.back);
        resonText = (TextInputEditText)findViewById(R.id.resonText);
        lvSource = (LinearLayout)findViewById(R.id.lvSource);
        lvSource.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            mPhone = extras.getString("mPhone");
            wPhone = extras.getString("wPhone");
            course1 = extras.getString("course");
            date = extras.getString("date");
            email = extras.getString("email");
            editLeadId = extras.getString("editLeadId");
            courseIds = extras.getString("courseIds");
            System.out.println("lead_list"+" "+name+" "+mPhone+" "+wPhone+" "
                    +course1+" "+date+" "+email+" "+editLeadId+" "+courseIds);
            names.setText(name);
            pnone1.setText(mPhone);
            pnone2.setText(wPhone);
            emails.setText(email);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditLead(courseId,sourseId,editLeadId);
            }
        });
        course = (Spinner) findViewById(R.id.course);
        source = (Spinner) findViewById(R.id.source);
//        course.setOnItemSelectedListener(this);
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,courses);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        course.setAdapter(aa);
        coursesName = new ArrayList<>();
        sourceName = new ArrayList<>();
        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseId = getCourseId(position);
                courseName = course.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourseId = getSourceId(position);
                sourseName = source.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getLead(editLeadId);
        courses();
        source();
        courseIds(courseIds);
    }
    public void getLead(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"get_lead_edit",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("get_lead_edit"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                               JSONObject jsonObject = obj.getJSONObject("lead");
                                    String fname = jsonObject.getString("fname");
                                    String lname = jsonObject.getString("lname");
                                    String mobile_no = jsonObject.getString("mobile_no");
                                    String course_id = jsonObject.getString("course_id");
                                    String source_id = jsonObject.getString("source_id");
                                    String mname = jsonObject.getString("mname");
                                    String student_phoneno = jsonObject.getString("student_phoneno");
                                    String alternative_phoneno = jsonObject.getString("alternative_phoneno");
                                    String whatsapp_no = jsonObject.getString("whatsapp_no");
                                    String email = jsonObject.getString("email");
                                    String reason_for_edit = jsonObject.getString("reason_for_edit");
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("course");
                                    course_name = jsonObject1.getString("course_name");

                                   System.out.println("get_lead_edit"+" "+fname+" "+lname+" "+mobile_no+" "+course_id+" "+source_id);
                                    student_phone.setText(student_phoneno);
                                    alternative_phone.setText(alternative_phoneno);
                                    middle_name.setText(mname);
                                    last_name.setText(lname);

                                    names.setText(fname);
                                    pnone1.setText(mobile_no);
                                    pnone2.setText(whatsapp_no);
                                    emails.setText(email);
//                                  resonText.setText(reason_for_edit);

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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", editLeadId);
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
    public void courses(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"course_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                jsonArrayCourse = obj.getJSONArray(TAG_COURSELIST);
//                                for (int i = 0; i<jsonArray.length(); i++){
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String course_name = jsonObject.getString("course_name");
//                                    System.out.println("courses"+id+" "+course_name);
//                                    coursesName.add(course_name);
//                                    spinner.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this,  android.R.layout.simple_spinner_dropdown_item, coursesName));
//
//                                }
                                getCourse(jsonArrayCourse,course1);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Empty course",Toast.LENGTH_SHORT).show();
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

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("", "");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", "Bearer " + access_token);
                return headers;
            }
        };
        //   VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getCourse(JSONArray i,String course1){
        for(int I=0;I<i.length();I++){
            try {
                JSONObject json = i.getJSONObject(I);
                coursesName.add(json.getString(Config.TAG_COURSELISTNAME));
                Log.e("coursesName"," "+coursesName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        course.setAdapter(new ArrayAdapter<String>(EditLead_activity.this, android.R.layout.simple_spinner_dropdown_item, coursesName));
        ArrayAdapter courses = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,coursesName);
        courses.setDropDownViewResource(R.layout.spinner_text_view);
        course.setAdapter(courses);
        String compareValue = course1;
        if (compareValue != null) {
            int spinnerPosition = courses.getPosition(compareValue);
            course.setSelection(spinnerPosition);
        }
    }
    private String getCourseId(int position){
        try {
            JSONObject json = jsonArrayCourse.getJSONObject(position);
            courseId = json.getString(Config.TAG_COURSELISTID);
//            Toast.makeText(getApplicationContext(),courseId, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courseId;
    }
    public void source(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"source_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Source"+response);

                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                jsonArraysource = obj.getJSONArray(TAG_SOURCELIST);
//                                for (int i = 0; i<jsonArray.length(); i++){
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String source_name = jsonObject.getString("source_name");
//                                    sourceName.add(source_name);
//                                    System.out.println("source_list"+id+" "+source_name);
//                                    spinner1.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this,  android.R.layout.simple_spinner_dropdown_item, sourceName));
//
//                                }
                                getSource(jsonArraysource);
                            }else {
                                Toast.makeText(getApplicationContext(),"Empty source",Toast.LENGTH_SHORT).show();

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
//                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("Source"+user.getToken());

                return headers;
            }
        };
        //   VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getSource(JSONArray j){
        for(int J=0;J<j.length();J++){
            try {
                JSONObject json = j.getJSONObject(J);
                sourceName.add(json.getString(Config.TAG_SOURCELISTNAME));
                Log.e("sourceName"," "+sourceName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        source.setAdapter(new ArrayAdapter<String>(EditLead_activity.this, android.R.layout.simple_spinner_dropdown_item, sourceName));
    }
    private String getSourceId(int position){
        try {
            JSONObject json = jsonArraysource.getJSONObject(position);
            sourseId = json.getString(Config.TAG_SOURCELISTID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sourseId;
    }
    public void EditLead(String courseId,String sourseId,String editLeadId){
        String Fname = names.getText().toString().trim();
        String middele = middle_name.getText().toString().trim();
        String lastname = last_name.getText().toString().trim();
        String studentphone =student_phone.getText().toString().trim();
        String alterphone=alternative_phone.getText().toString().trim();
        String MobileNo = pnone1.getText().toString().trim();
        String Whatsappno = pnone2.getText().toString().trim();
        String EmailId = emails.getText().toString().trim();
        String resontext = resonText.getText().toString().trim();
        if (TextUtils.isEmpty(Fname)) {
            names.setError("Enter First Name");
            names.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(MobileNo)) {
            pnone1.setError("Enter Mobile Number");
            pnone1.requestFocus();
            return;
        }
//        if (TextUtils.isEmpty(Whatsappno)) {
//            pnone2.setError("Enter Whatsapp Number");
//            pnone2.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(EmailId)) {
//            emails.setError("Enter Email");
//            emails.requestFocus();
//            return;
//        }
        if (TextUtils.isEmpty(resontext)) {
            resonText.setError("Enter Reason Lead");
            resonText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            last_name.setError("Enter Last Name");
            last_name.requestFocus();
            return;
        }
//        if (TextUtils.isEmpty(studentphone)) {
//            student_phone.setError("Enter Student Phone Number");
//            student_phone.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(alterphone)) {
//            alternative_phone.setError("Enter AlterNative Phone Number");
//            alternative_phone.requestFocus();
//            return;
//        }
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"update_lead",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("update_lead"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("true")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                            }else {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
                params.put("fname", Fname);
                params.put("mname", middele);
                params.put("lname", lastname);
                params.put("student_phoneno", studentphone);
                params.put("alternative_phoneno", alterphone);
                params.put("mobile_no", MobileNo);
                params.put("whatsapp_no", Whatsappno);
                params.put("email", EmailId);
                params.put("course_id", courseId);
//              params.put("source_id", sourseId);
                params.put("reason_for_edit", resontext);
                params.put("id", editLeadId);
                System.out.println("update_lead_view"+params.toString());
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
    public void courseIds(String courseIds){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"course_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Empty course",Toast.LENGTH_SHORT).show();
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

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("", courseIds);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", "Bearer " + access_token);
                return headers;
            }
        };
        //   VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
