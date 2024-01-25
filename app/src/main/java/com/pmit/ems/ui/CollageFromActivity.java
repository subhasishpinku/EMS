package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.pmit.ems.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class CollageFromActivity extends AppCompatActivity {
    ImageView circule_image;
    TextView collage_id,phone_id,web_id,led_tv5;
    TextInputEditText SfName,SmName,SlName,SfatherName,SmotherName,dobName,mobileNoId,paddress,stateId,
            sDistrict,cityId,pinID,paddress1,stateId1,sDistrict1,cityId1,pinID1,adherId,genderId;
    TextView submitId;
    String name,mPhone,wPhone,course1,date,email,editLeadId,courseIds;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_from_activity);
        circule_image = (ImageView)findViewById(R.id.circule_image);
        collage_id = (TextView)findViewById(R.id.collage_id);
        phone_id = (TextView)findViewById(R.id.phone_id);
        web_id = (TextView)findViewById(R.id.web_id);
        led_tv5 = (TextView)findViewById(R.id.led_tv5);
        SfName = (TextInputEditText)findViewById(R.id.SfName);
        SmName = (TextInputEditText)findViewById(R.id.SmName);
        SlName = (TextInputEditText)findViewById(R.id.SlName);
        SfatherName = (TextInputEditText)findViewById(R.id.SfatherName);
        SmotherName = (TextInputEditText)findViewById(R.id.SmotherName);
        dobName = (TextInputEditText)findViewById(R.id.dobName);
        mobileNoId = (TextInputEditText)findViewById(R.id.mobileNoId);
        paddress = (TextInputEditText)findViewById(R.id.paddress);
        stateId = (TextInputEditText)findViewById(R.id.stateId);
        sDistrict = (TextInputEditText)findViewById(R.id.sDistrict);
        cityId = (TextInputEditText)findViewById(R.id.cityId);
        pinID = (TextInputEditText)findViewById(R.id.pinID);
        paddress1 = (TextInputEditText)findViewById(R.id.paddress1);
        stateId1 = (TextInputEditText)findViewById(R.id.stateId1);
        sDistrict1 = (TextInputEditText)findViewById(R.id.sDistrict1);
        cityId1 = (TextInputEditText)findViewById(R.id.cityId1);
        pinID1 = (TextInputEditText)findViewById(R.id.pinID1);
        adherId = (TextInputEditText)findViewById(R.id.adherId);
        genderId = (TextInputEditText)findViewById(R.id.genderId);
        submitId = (TextView)findViewById(R.id.submitId);

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
        }
        college_form_fatch(editLeadId);
        submitId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        dobName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (text.length() == 2 || text.length() == 5) {
                    text.append('-');
                }
            }
        });
    }
    public void college_form_fatch(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,  BASEURL2+"college_form_fatch",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("college_form_fatch"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                JSONObject object = obj.getJSONObject("lead");
                                String id = object.getString("id");
                                String fname = object.getString("fname");
                                String mname = object.getString("mname");
                                String lname = object.getString("lname");
                                String collegename = object.getString("collegename");
                                String logo = object.getString("logo");
                                String contactno = object.getString("contactno");
                                String website = object.getString("website");

                                String mobile_no = object.getString("mobile_no");
                                SfName.setText(fname);
                                SmName.setText(mname);
                                SlName.setText(lname);
                                mobileNoId.setText(mobile_no);
                                phone_id.setText("Phone :"+contactno);
                                web_id.setText("Website :"+website);
                                collage_id.setText(collegename);
                                Glide.with(getApplicationContext())
                                        .load("https://developer.pmitcolleges.org/"+logo)
                                        .into(circule_image);
                                college_form_view();
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
                params.put("lead_id",editLeadId);
                System.out.println("college_form_fatch"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("college_form_fatch"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void college_form_view(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,  BASEURL2+"college_form_view",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("college_form_view"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                JSONObject object = obj.getJSONObject("college_form");
                                String id = object.getString("id");
                                String lead_id = object.getString("lead_id");
                                String father_name = object.getString("father_name");
                                String mother_name = object.getString("mother_name");
                                String dob = object.getString("dob");
                                String present_address = object.getString("present_address");
                                String present_state = object.getString("present_state");
                                String present_district = object.getString("present_district");
                                String present_city = object.getString("present_city");
                                String present_pin = object.getString("present_pin");
                                String same_address = object.getString("same_address");
                                String permanent_address = object.getString("permanent_address");
                                String permanent_state = object.getString("permanent_state");
                                String permanent_district = object.getString("permanent_district");
                                String permanent_city = object.getString("permanent_city");
                                String permanent_pin = object.getString("permanent_pin");
                                String adhar = object.getString("adhar");
                                String gender = object.getString("gender");
                                String fname = object.getString("fname");
                                String mname = object.getString("mname");
                                String lname = object.getString("lname");
                                String mobile_no = object.getString("mobile_no");
                                SfName.setText(fname);
                                SmName.setText(mname);
                                SlName.setText(lname);
                                mobileNoId.setText(mobile_no);
                                SfatherName.setText(father_name);
                                SmotherName.setText(mother_name);
                                dobName.setText(dob);
                                paddress.setText(present_address);
                                stateId.setText(present_state);
                                sDistrict.setText(present_district);
                                cityId.setText(present_city);
                                pinID.setText(present_pin);
                                paddress1.setText(permanent_address);
                                stateId1.setText(permanent_state);
                                sDistrict1.setText(permanent_district);
                                cityId1.setText(permanent_city);
                                pinID1.setText(permanent_pin);
                                adherId.setText(adhar);
                                genderId.setText(gender);
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
                params.put("lead_id",editLeadId);
                System.out.println("college_form_view"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("college_form_view"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
     public void save(){
        String SfNames = SfName.getText().toString();
        String SmNames = SmName.getText().toString();
        String SlNames = SlName.getText().toString();
        String SfatherNames = SfatherName.getText().toString();
        String SmotherNames = SmotherName.getText().toString();
        String mobileNoIds = mobileNoId.getText().toString();
        String dobNames = dobName.getText().toString();
        String paddresss1 =paddress.getText().toString();
        String paddress1s =paddress1.getText().toString();
        String stateIds = stateId.getText().toString();
        String sDistricts = sDistrict.getText().toString();
        String cityIds = cityId.getText().toString();
        String pinIDs = pinID.getText().toString();
        String stateId1s = stateId1.getText().toString();
        String sDistrict1s = sDistrict1.getText().toString();
        String cityId1s = cityId1.getText().toString();
        String pinID1s = pinID1.getText().toString();
        String adherIds = adherId.getText().toString();
        String genderIds = genderId.getText().toString();
         if (TextUtils.isEmpty(SfNames)) {
             SfName.setError("Enter Student Name");
             SfName.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(SmNames)) {
             SmName.setError("Enter Student Middle Name");
             SmName.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(SlNames)) {
             SlName.setError("Enter Student Last Name");
             SlName.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(SfatherNames)) {
             SfatherName.setError("Enter Father's Name");
             SfatherName.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(SmotherNames)) {
             SmotherName.setError("Enter Mother's Name");
             SmotherName.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(mobileNoIds)) {
             mobileNoId.setError("Enter Mobile No");
             mobileNoId.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(dobNames)) {
             dobName.setError("Enter DOB");
             dobName.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(paddresss1)) {
             paddress.setError("Enter Present Address");
             paddress.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(stateIds)) {
             stateId.setError("Enter State");
             stateId.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(sDistricts)) {
             sDistrict.setError("Enter District");
             sDistrict.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(cityIds)) {
             cityId.setError("Enter City");
             cityId.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(pinIDs)) {
             pinID.setError("Enter City");
             pinID.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(paddress1s)) {
             paddress1.setError("Enter Permanent Address");
             paddress1.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(stateId1s)) {
             stateId1.setError("Enter City");
             stateId1.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(sDistrict1s)) {
             sDistrict1.setError("Enter District");
             sDistrict1.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(cityId1s)) {
             cityId1.setError("Enter City");
             cityId1.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(pinID1s)) {
             pinID1.setError("Enter Pin");
             pinID1.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(adherIds)) {
             adherId.setError("Enter AdherNo");
             adherId.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(genderIds)) {
             genderId.setError("Enter Gender");
             genderId.requestFocus();
             return;
         }
         UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
         StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"college_form_save",
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         System.out.println("college_form_save"+response);
                         try {
                             JSONObject obj = new JSONObject(response);
                             String status = obj.getString("status");
                             if (status.equals("true")){
                                 String msg = obj.getString("msg");
                                 Intent intent = new Intent(getApplicationContext(),LeadTableActivity.class);
                                 startActivity(intent);
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
                 params.put("lead_id",editLeadId);
                 params.put("fname",SfNames);
                 params.put("mname",SmNames);
                 params.put("lname",SlNames);
                 params.put("father_name",SfatherNames);
                 params.put("mother_name",SmotherNames);
                 params.put("mobile_no",mobileNoIds);
                 params.put("dob",dobNames);
                 params.put("present_address",paddresss1);
                 params.put("present_state",stateIds);
                 params.put("present_district",sDistricts);
                 params.put("present_city",cityIds);
                 params.put("present_pin",pinIDs);
                 params.put("permanent_address",paddress1s);
                 params.put("permanent_state",stateId1s);
                 params.put("permanent_district",sDistrict1s);
                 params.put("permanent_city",cityId1s);
                 params.put("permanent_pin",pinID1s);
                 params.put("adhar",adherIds);
                 params.put("gender",genderIds);
                 System.out.println("college_form_save"+params.toString());
                 return params;
             }
             @Override
             public Map<String, String> getHeaders() throws AuthFailureError {
                 HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                 headers.put("Authorization", "Bearer " + user.getToken());
                 System.out.println("college_form_save"+user.getToken());
                 return headers;
             }
         };
         //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
         stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
         stringRequest.setShouldCache(false);
         volleySingleton.addToRequestQueue(stringRequest);
     }

}
