package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;
import static com.pmit.ems.api.Config.TAG_CITYLIST;
import static com.pmit.ems.api.Config.TAG_COURSELIST;
import static com.pmit.ems.api.Config.TAG_SOURCELIST;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;
public class LeadEntryScreen extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener,Spinner.OnItemSelectedListener{
    MaterialButton save;
    TextInputEditText fname,mname,lname,mobileNo,whatsappno,emailId,stuName,stuPhone;
    Spinner spinner,spinner1,spinner2,spinner3,spinner4;
    ArrayList<String> coursesName;
    ArrayList<String> sourceName;
    ArrayList<String> stateName;
    ArrayList<String> cityName;
    public JSONArray  jsonArrayState;
    public JSONArray  jsonArrayCourse;
    public JSONArray  jsonArraysource;
    public JSONArray  jsonArrayCity;
    String stateId="";
    String courseId="";
    String courseName="";
    String sourseId="";
    String sourseName="";
    String statesId="";
    String stateNames="";
    String cityId="";
    String cityNames="";
    LinearLayout StudentLv;
    TextView verify;
    GifImageView loaderId;
    TextView select_file;
    SharedPreferences sp;
    boolean isCheck = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leadenter_activity);
        fname = (TextInputEditText)findViewById(R.id.fname);
        mname = (TextInputEditText)findViewById(R.id.mname);
        lname = (TextInputEditText)findViewById(R.id.lname);
        mobileNo = (TextInputEditText)findViewById(R.id.mobileNo);
        whatsappno = (TextInputEditText)findViewById(R.id.whatsappno);
        emailId = (TextInputEditText)findViewById(R.id.emailId);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        save = (MaterialButton)findViewById(R.id.save);
        StudentLv = (LinearLayout)findViewById(R.id.StudentLv);
        stuName = (TextInputEditText)findViewById(R.id.stuName);
        stuPhone = (TextInputEditText)findViewById(R.id.stuPhone);
        verify = (TextView)findViewById(R.id.verify);
        loaderId = (GifImageView)findViewById(R.id.loaderId);
        select_file = (TextView)findViewById(R.id.select_file);
        StudentLv.setVisibility(View.GONE);
        save.setEnabled(true);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLead();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
//                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
//                bottomSheet.setCancelable(false);
                  sendOtp();
            }
        });
        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeadTableActivity.class);
                startActivity(intent);
            }
        });
        coursesName = new ArrayList<>();
        sourceName = new ArrayList<>();
        stateName = new ArrayList<>();
        cityName = new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseId = getCourseId(position);
                courseName = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourseId = getSourceId(position);
                sourseName = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId = getCityId(position);
                cityNames = spinner4.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        courses();
        source();
        states();
    }
    @Override
    public void onButtonClicked(String text) {

    }
    public void courses(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"course_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("course_list"+response);
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
                                getCourse(jsonArrayCourse);
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
    private void getCourse(JSONArray i){
        for(int I=0;I<i.length();I++){
            try {
                JSONObject json = i.getJSONObject(I);
                coursesName.add(json.getString(Config.TAG_COURSELISTNAME));
                Log.e("coursesName"," "+coursesName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this, android.R.layout.simple_spinner_dropdown_item, coursesName));
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
        spinner1.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this, android.R.layout.simple_spinner_dropdown_item, sourceName));
    }
    private String getSourceId(int position){
        try {
            JSONObject json = jsonArraysource.getJSONObject(position);
            sourseId = json.getString(Config.TAG_SOURCELISTID);
            if (sourseId.equals("20")){
                StudentLv.setVisibility(View.VISIBLE);
            }
            else {
                StudentLv.setVisibility(View.GONE);
            }
//            Toast.makeText(getApplicationContext(),sourseId, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sourseId;
    }
    public void states(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"state_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj = null;
                        try {
                            //converting the string to json array object
                            obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            jsonArrayState = obj.getJSONArray(Config.STATE_ARRAY);
//                            for (int i = 0; i<jsonArrayState.length(); i++){
//                                JSONObject jsonObject = jsonArrayState.getJSONObject(i);
//                                String id = jsonObject.getString("id");
//                                String state_name = jsonObject.getString("state_name");
//                                stateName.add(state_name);
//                                System.out.println("state_list"+id+" "+state_name);
////                                spinner3.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this,  android.R.layout.simple_spinner_dropdown_item, stateName));
//                            }
                            getdis(jsonArrayState);
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
    private void getdis(JSONArray k){
        for(int K=0;K<k.length();K++){
            try {
                JSONObject json = k.getJSONObject(K);
                stateName.add(json.getString(Config.TAG_STATENAME));
                Log.e("stateName"," "+stateName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner3.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this, android.R.layout.simple_spinner_dropdown_item, stateName));
    }
    private String getStateId(int position){
        try {
            JSONObject json = jsonArrayState.getJSONObject(position);
            stateId = json.getString(Config.TAG_ID);
            city(stateId);
//            Toast.makeText(getApplicationContext(),json.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stateId;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cityName.clear();
        statesId = getStateId(position);
        stateNames = spinner3.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void city(String stateId){
        System.out.println("SelectState1"+" "+stateId);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"city_list/"+stateId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("city_List  "+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                             jsonArrayCity = obj.getJSONArray(TAG_CITYLIST);
//                            for (int i = 0; i<jsonArrayCity.length(); i++){
//                                JSONObject jsonObject = jsonArrayCity.getJSONObject(i);
//                                String id = jsonObject.getString("id");
//                                String city_name = jsonObject.getString("city_name");
//                                cityName.add(city_name);
//                                System.out.println("city_name"+id+" "+city_name);
//                                spinner4.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this,  android.R.layout.simple_spinner_dropdown_item, cityName));
//
//                            }
                            getcity(jsonArrayCity);
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
    private void getcity(JSONArray l){
        for(int L=0;L<l.length();L++){
            try {
                JSONObject json = l.getJSONObject(L);
                cityName.add(json.getString(Config.TAG_CITYLISTNAME));
                Log.e("cityName"," "+cityName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner4.setAdapter(new ArrayAdapter<String>(LeadEntryScreen.this, android.R.layout.simple_spinner_dropdown_item, cityName));
    }
    private String getCityId(int position){
        try {
            JSONObject json = jsonArrayCity.getJSONObject(position);
            cityId = json.getString(Config.TAG_ID);
//            Toast.makeText(getApplicationContext(),cityId, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityId;
    }
    public void sendOtp(){
        String MobileNo = mobileNo.getText().toString().trim();
        if (TextUtils.isEmpty(MobileNo)) {
            mobileNo.setError("Enter Mobile Number");
            mobileNo.requestFocus();
            return;

        }
        loaderId.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"send_otp?mobileno="+MobileNo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("sendOtp  "+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String message = obj.getString("message");
                            if(status.equals("true")){
                                loaderId.setVisibility(View.GONE);
                                mobileNo.setEnabled(false);
                                isCheck = true;
                                save.setEnabled(true);
                                UserPhone user = new UserPhone(
                                        MobileNo
                                );
                                SharedPrefManagerSaveValue.getInstance(getApplicationContext()).UserPhones(user);
                                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
                                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                                bottomSheet.setCancelable(false);
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }else{
                                if (message.equals("Mobile already Registered")) {
                                    loaderId.setVisibility(View.GONE);
                                    save.setEnabled(true);
                                }
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loaderId.setVisibility(View.GONE);

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobileno", MobileNo);
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
    public void saveLead(){
        String Fname = fname.getText().toString().trim();
        String Mname = mname.getText().toString().trim();
        String Lname = lname.getText().toString().trim();
        String MobileNo = mobileNo.getText().toString().trim();
        String Whatsappno = whatsappno.getText().toString().trim();
        String EmailId = emailId.getText().toString().trim();
        String stuname = stuName.getText().toString().trim();
        String stuphone = stuPhone.getText().toString().trim();

        if (TextUtils.isEmpty(Fname)) {
            fname.setError("Enter First Name");
            fname.requestFocus();
            return;
        }
//        if (TextUtils.isEmpty(Mname)) {
//            mname.setError("Enter Middle Name");
//            mname.requestFocus();
//            return;
//        }
        if (spinner.getSelectedItem().toString().equals("Select Curse")){
            Toast.makeText(getApplicationContext(),"Select Curse",Toast.LENGTH_SHORT).show();

            return;
        }
//        if (spinner1.getSelectedItem().toString().equals("")){
//            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
//
//            return;
//        }
        if (spinner3.getSelectedItem().toString().equals("Select State")){
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinner4.getSelectedItem().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Select City",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Lname)) {
            lname.setError("Enter Last Name");
            lname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(MobileNo)) {
            mobileNo.setError("Enter Mobile Number");
            mobileNo.requestFocus();
            return;
        }
       if (sourseId.equals("20")) {
           if (TextUtils.isEmpty(stuname)) {
               stuName.setError("Enter Student Name");
               stuName.requestFocus();
               return;
           }
           if (TextUtils.isEmpty(stuphone)) {
               stuPhone.setError("Enter Student Phone Number");
               stuPhone.requestFocus();
               return;
           }
       }
//        if (TextUtils.isEmpty(Whatsappno)) {
//            whatsappno.setError("Enter Whatsapp Number");
//            whatsappno.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(EmailId)) {
//            emailId.setError("Enter Email Id");
//            emailId.requestFocus();
//            return;
//        }
        if (isCheck==false){
            Toast.makeText(getApplicationContext(),"Verify Phone Number",Toast.LENGTH_SHORT).show();
            return;
        }
        loaderId.setVisibility(View.VISIBLE);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"add_lead",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("SaveData1  "+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String message = obj.getString("message");
                            if (status.equals("true")){
                                loaderId.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loaderId.setVisibility(View.GONE);
                    }}) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname", Fname);
                params.put("mname", Mname);
                params.put("lname",Lname);
                params.put("mobile_no", MobileNo);
                params.put("whatsapp_no", Whatsappno);
                params.put("email", EmailId);
                params.put("course_id", courseId);
                params.put("source_id", sourseId);
                params.put("state_id", stateId);
                params.put("city_id", cityId);
                if (sourseId.equals("20")) {
                    params.put("ref_student_name", stuname);
                    params.put("ref_student_contact", stuphone);
                }
                else {
                    params.put("ref_student_name", "");
                    params.put("ref_student_contact", "");
                }
                System.out.println("SaveData"+" "+params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);

    }
}
