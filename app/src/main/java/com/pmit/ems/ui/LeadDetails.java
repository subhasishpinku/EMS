package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;
import static com.pmit.ems.api.Config.TAG_COLLAGELIST;
import static com.pmit.ems.api.Config.TAG_COURSELIST;
import static com.pmit.ems.api.Config.TAG_FOLLOWUPSTATUS;
import static com.pmit.ems.api.Config.TAG_LEADSTAGE;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pmit.ems.R;
import com.pmit.ems.api.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LeadDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ImageView edit_ID;
    Spinner spinner,spinner1,spinner_collage;
    String[] sp1 = {"Choose Followup Status"};
    String[] sp2 = {"Choose Lead Stage"};
    ImageView Call1,Call2;
    private static final int REQUEST_CALL = 1;
    TextView shareId;
    MaterialButton save;
    ImageView ImgCalender;
    DatePickerDialog picker;
    TextView dateId;
    TextInputEditText search_Id;
    RatingBar ratingBar;
    ConstraintLayout screenTouch;
    TextView led_name,led_name1,led_date,mobile_tv1,mobile_tv2,led_course,tv_email;
    String name,mPhone,wPhone,course,date,email,editLeadId,courseIds,collageId,session_id,source_id,course_id,sessionname_id,check_payment_approval_status,check_srn,srn,srn_flag,rejectform,check_booking,ListFlag;
    ArrayList<String> collageName;
    ArrayList<String> followupName;
    ArrayList<String> leadStageList;
    String collagesId ="";
    String collagesName = "";
    JSONArray collageList;
    JSONArray followupStatus;
    JSONArray leadStage;
    String followUpName = "";
    String followUpSlow = "";
    TextView apply;
    String lead_stageName="";
    String lead_stageSlog="";
    TextInputEditText textarea_Id;
    FloatingActionMenu material_design_android_floating_action_menu;
    FloatingActionButton edit_lead, send_flyer, send,addToList,addToYelowList,addToProList,removeyellow,removegreen;
    LinearLayout seend_pay;
    Spinner short_by_session;
    ArrayList<String> sessionName;
    String collegename="";
    LinearLayout sendInfoLv2;
    AlertDialog.Builder builder;
//  TextView led_tv3;
    JSONArray admissionLeads;
    String session_ids,session_names;
    LinearLayout sendInfoLv1;
    TextView srn1;
//  RelativeLayout rv;
    TextView applic,send_info;
    TextView coolage_from;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_details_activity);
        edit_ID = (ImageView)findViewById(R.id.edit_ID);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner_collage = (Spinner)findViewById(R.id.spinner_collage);
        mobile_tv1 = (TextView)findViewById(R.id.mobile_tv1);
        mobile_tv2 = (TextView)findViewById(R.id.mobile_tv2);
        led_name1 = (TextView)findViewById(R.id.led_name1);
        led_name = (TextView)findViewById(R.id.led_name);
        led_date = (TextView)findViewById(R.id.led_name2);
        led_course = (TextView)findViewById(R.id.led_tv5);
        tv_email  = (TextView)findViewById(R.id.tv_email);
        srn1  = (TextView)findViewById(R.id.srn);
        apply  = (TextView)findViewById(R.id.apply);
        textarea_Id = (TextInputEditText)findViewById(R.id.textarea_Id);
        Call1 = (ImageView)findViewById(R.id.Call1);
        Call2 = (ImageView)findViewById(R.id.Call2);
        shareId = (TextView)findViewById(R.id.shareId);
        save = (MaterialButton)findViewById(R.id.save);
        ImgCalender = (ImageView)findViewById(R.id.ImgCalender);
        dateId = (TextView)findViewById(R.id.dateId);
        applic = (TextView)findViewById(R.id.applic);
        send_info = (TextView)findViewById(R.id.send_info);
        search_Id = (TextInputEditText)findViewById(R.id.search_Id);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        screenTouch = (ConstraintLayout)findViewById(R.id.screenTouch);
        material_design_android_floating_action_menu =(FloatingActionMenu)findViewById(R.id.material_design_android_floating_action_menu);
        edit_lead = (FloatingActionButton)findViewById(R.id.edit_lead);
        send_flyer = (FloatingActionButton)findViewById(R.id.send_flyer);
        send = (FloatingActionButton)findViewById(R.id.send);
        removegreen = (FloatingActionButton)findViewById(R.id.removegreen);
        removeyellow  = (FloatingActionButton)findViewById(R.id.removeyellow);
        addToList = (FloatingActionButton)findViewById(R.id.addToList);
        addToYelowList = (FloatingActionButton)findViewById(R.id.addToYelowList);
        addToProList = (FloatingActionButton)findViewById(R.id.addToProList);
        seend_pay = (LinearLayout) findViewById(R.id.seend_pay);
        short_by_session = (Spinner)findViewById(R.id.short_by_session);
        sendInfoLv2 = (LinearLayout)findViewById(R.id.sendInfoLv2);
//        led_tv3 = (TextView)findViewById(R.id.led_tv3);
        sendInfoLv1 = (LinearLayout)findViewById(R.id.sendInfoLv1);
//        rv  = (RelativeLayout) findViewById(R.id.rv);
        coolage_from = (TextView)findViewById(R.id.coolage_from);
        edit_lead.setVisibility(View.GONE);
        builder = new AlertDialog.Builder(this);
//      rv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),FllowupLeadList.class);
//                startActivity(intent);
//            }
//      });
        coolage_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check1();

            }
        });
        sendInfoLv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                sendInfoLv2.startAnimation(animation2);
                payment_approval_schedule(collagesId,editLeadId);

            }
        });
        seend_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                seend_pay.startAnimation(animation1);
                temp_payment_status(collagesId,editLeadId);
            }
        });
        edit_lead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
               System.out.println("Lead"+"0");
            }
        });
        send_flyer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"1");

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"2");
                request_college_admisation(editLeadId);
            }
        });
        addToList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"3");
//                if (ListFlag.equals("green-list")){
//
//                }
                lead_flag_change("green-list",editLeadId);
            }
        });
        addToYelowList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"4");
//                if (ListFlag.equals("yellow-list")){
//                    lead_flag_change(ListFlag,editLeadId);
//
//                }
                lead_flag_change("yellow-list",editLeadId);

            }
        });
        addToProList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"5");
//                if (ListFlag.equals("admission-projection")){
//                    lead_flag_change(ListFlag,editLeadId);
//                }
                lead_flag_change("admission-projection",editLeadId);

            }
        });
        removegreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"5");
//                if (ListFlag.equals("admission-projection")){
//                    lead_flag_change(ListFlag,editLeadId);
//                }
                lead_flag_change("remove-list",editLeadId);

            }
        });
        removeyellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                System.out.println("Lead"+"5");
//                if (ListFlag.equals("admission-projection")){
//                    lead_flag_change(ListFlag,editLeadId);
//                }
                lead_flag_change("remove-list",editLeadId);

            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            mPhone = extras.getString("mPhone");
            wPhone = extras.getString("wPhone");
            course = extras.getString("course");
            date = extras.getString("date");
            email = extras.getString("email");
            editLeadId = extras.getString("editLeadId");
            courseIds = extras.getString("courseIds");
            collageId  = extras.getString("collageId");
            collegename = extras.getString("collegename");
            session_id = extras.getString("session_id");
            source_id = extras.getString("source_id");
            course_id = extras.getString("course_id");
            sessionname_id = extras.getString("sessionname_id");
            check_payment_approval_status = extras.getString("check_payment_approval_status");
            check_srn = extras.getString("check_srn");
            srn = extras.getString("srn");
            srn_flag = extras.getString("srn_flag");
            rejectform = extras.getString("rejectform");
            check_booking = extras.getString("rejectform");
            ListFlag = extras.getString("ListFlag");
            led_name1.setText(name);
            mobile_tv1.setText(mPhone);
            mobile_tv2.setText(wPhone);
            led_course.setText(course);
            led_date.setText(date);
            tv_email.setText(email);
            srn1.setText("    "+srn);
            if ("admission-projection".equals(ListFlag)){
                addToList.setVisibility(View.GONE);
                addToYelowList.setVisibility(View.GONE);
                addToProList.setVisibility(View.GONE);
                removegreen.setVisibility(View.GONE);
                removeyellow.setVisibility(View.GONE);
            }
            if("green-list".equals(ListFlag)){
                addToList.setVisibility(View.GONE);
                removeyellow.setVisibility(View.GONE);
            }
            if("yellow-list".equals(ListFlag)){
                addToYelowList.setVisibility(View.GONE);
                removegreen.setVisibility(View.GONE);

            }
         System.out.println("session_update"+" "+session_id+ " "+sessionname_id);
            System.out.println("check_status"+" "+check_payment_approval_status+" "+course);

        }
        if (check_payment_approval_status.equals("1")) {
            sendInfoLv1.setBackgroundResource(R.drawable.backgrount_coror);
            shareId.setTextColor(Color.WHITE);

        }
        if (check_booking.equals("1")){
            seend_pay.setBackgroundResource(R.drawable.backgrount_corors);
            send_info.setTextColor(Color.WHITE);
        }
        if (srn_flag.equals("1")){
            applic.setTextColor(Color.WHITE);
            sendInfoLv2.setBackgroundResource(R.drawable.backgrount_cororss);
        }
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        sendInfoLv1.startAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        seend_pay.startAnimation(animation1);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        sendInfoLv2.startAnimation(animation2);
        String content = led_name1.getText().toString();
        char first = content.charAt(0);
        String f = String.valueOf(first);
        led_name.setText(f);
        screenTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    String rating=String.valueOf(ratingBar.getRating());
//                    Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                    // Do what you want
                    return true;
                }
                return false;
            }
        });
        search_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("Search"+"  "+s);

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Search"+"  "+s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("Search"+"  "+s);

            }
        });

        shareId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("SpinnerName"+short_by_session.getSelectedItem());
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                sendInfoLv1.startAnimation(animation);
                college_session_check(collagesId,editLeadId);
            }
        });
        edit_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_payment_approval_status.equals("0")) {
                    Intent intent = new Intent(getApplicationContext(),EditLead_activity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("mPhone",mPhone);
                    intent.putExtra("wPhone",wPhone);
                    intent.putExtra("course",course);
                    intent.putExtra("date",date);
                    intent.putExtra("email",email);
                    intent.putExtra("editLeadId",editLeadId);
                    intent.putExtra("courseIds",courseIds);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You can not do this operation after Approval of Payment Schedule", Toast.LENGTH_LONG).show();
                }

            }
        });
        Call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + mobile_tv1.getText().toString()));
//                startActivity(intent);
                makePhoneCall(mobile_tv1.getText().toString());
            }
        });
        Call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              makePhoneCall(mobile_tv2.getText().toString());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://wa.me/"+wPhone));
                startActivity(i);
            }
        });
//        spinner.setOnItemSelectedListener(this);
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sp1);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(aa);
//        spinner1.setOnItemSelectedListener(this);
//        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sp2);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner1.setAdapter(aaa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                followUpSlow = getFlowUpId(position);
                followUpName = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lead_stageSlog = getLeadStageId(position);
                lead_stageName = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        short_by_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                session_ids = getSessionId(position);
                session_names = short_by_session.getSelectedItem().toString();
                if (check_payment_approval_status.equals("0")) {
                    session_update(session_ids,editLeadId);
                }
                else {
                    //Toast.makeText(getApplicationContext(),"You can not do this operation after Approval of Payment Schedule", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ImgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LeadDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateId.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

            }
        });
        spinner_collage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collagesId = getCourseId(position);
                collagesName = spinner_collage.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        collageName = new ArrayList<>();
        followupName  = new ArrayList<>();
        leadStageList  = new ArrayList<>();
        sessionName = new ArrayList<>();
        CollageList(courseIds,collegename);
        FlowUpStage();
        LeadStage();
        Session();
        pending_followup();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_followup_status(editLeadId,followUpSlow,lead_stageSlog);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_followup_status1(editLeadId,followUpSlow,lead_stageSlog);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(LeadDetails.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LeadDetails.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(LeadDetails.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void pending_followup(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"pending_followup",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("pending_followup"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                String followup = obj.getString("followup");
//                                led_tv3.setText(followup);
                            }else {
                                Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();
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
                System.out.println("pending_followup"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public void CollageList(String courseIds,String collegename){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"course_college_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("course_college_list"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                collageList = obj.getJSONArray(TAG_COLLAGELIST);
//                                for (int i = 0; i<collageList.length(); i++){
//                                    JSONObject jsonObject = collageList.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String collegename = jsonObject.getString("collegename");
//                                    System.out.println("CourseCollegeList"+id+" "+collegename);
//                                    collageName.add(collegename);
//                                    spinner_collage.setAdapter(new ArrayAdapter<String>(LeadDetails.this,  android.R.layout.simple_spinner_dropdown_item, collageName));
//
//                                }
                                getCourse(collageList,collegename);
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
                params.put("course_id", courseIds);
                System.out.println("course_college_list"+params.toString());

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

    private void getCourse(JSONArray i,String collegename){
        for(int I=0;I<i.length();I++){
            try {
                JSONObject json = i.getJSONObject(I);
//                collageName.add(collegename);
                collageName.add(json.getString(Config.TAG_COLLAGENAME));
                Log.e("collageName"," "+collageName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        spinner_collage.setAdapter(new ArrayAdapter<String>(LeadDetails.this, android.R.layout.simple_spinner_dropdown_item, collageName));
        ArrayAdapter spinnerCollage = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,collageName);
        spinnerCollage.setDropDownViewResource(R.layout.spinner_text_view);
        spinner_collage.setAdapter(spinnerCollage);
//        spinner_collage.setSelection(1);
//        int spinnerPosition = spinnerCollage.getPosition("SAHAJPATH");
//        spinner_collage.setSelection(spinnerPosition);
        String compareValue = collegename;
        if (compareValue != null) {
            int spinnerPosition = spinnerCollage.getPosition(compareValue);
            spinner_collage.setSelection(spinnerPosition);
        }
    }
    private String getCourseId(int position){
        try {
            JSONObject json = collageList.getJSONObject(position);
            collagesId = json.getString(Config.TAG_COLLAGEID);
            if(spinner_collage.getSelectedItem().equals("Select College")){

            }
            else{
                if (check_payment_approval_status.equals("0")) {
                    college_update_api(collagesId, editLeadId);
                }
                else {
                   // Toast.makeText(getApplicationContext(),"You can not do this operation after Approval of Payment Schedule", Toast.LENGTH_LONG).show();
                }
            }

//         Toast.makeText(getApplicationContext(),collagesId, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return collagesId;
    }
    public void FlowUpStage(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"followup_status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("followup_status"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                followupStatus = obj.getJSONArray(TAG_FOLLOWUPSTATUS);
//                                for (int i = 0; i<collageList.length(); i++){
//                                    JSONObject jsonObject = collageList.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String collegename = jsonObject.getString("collegename");
//                                    System.out.println("CourseCollegeList"+id+" "+collegename);
//                                    collageName.add(collegename);
//                                    spinner_collage.setAdapter(new ArrayAdapter<String>(LeadDetails.this,  android.R.layout.simple_spinner_dropdown_item, collageName));
//
//                                }
                                getFlowUp(followupStatus);
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
//                params.put("course_id", courseIds);
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
    private void getFlowUp(JSONArray j){
        for(int J=0;J<j.length();J++){
            try {
                JSONObject json = j.getJSONObject(J);
                followupName.add(json.getString(Config.TAG_FOLLOWUPNAME));
                Log.e("followupName"," "+followupName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        spinner.setAdapter(new ArrayAdapter<String>(LeadDetails.this, android.R.layout.simple_spinner_dropdown_item, followupName));
        ArrayAdapter flowUpLead = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,followupName);
        flowUpLead.setDropDownViewResource(R.layout.spinner_text_view);
        spinner.setAdapter(flowUpLead);
    }
    private String getFlowUpId(int position){
        try {
            JSONObject json = followupStatus.getJSONObject(position);
            followUpSlow = json.getString(Config.TAG_FOLLOWUPSLUG);
//         Toast.makeText(getApplicationContext(),followUpSlow, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return followUpSlow;
    }
    public void LeadStage(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASEURL2+"lead_stages",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("lead_stages"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                leadStage = obj.getJSONArray(TAG_LEADSTAGE);
                                getLeadStage(leadStage);
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
//                params.put("course_id", courseIds);
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
    private void getLeadStage(JSONArray k){
        for(int K=0;K<k.length();K++){
            try {
                JSONObject json = k.getJSONObject(K);
                leadStageList.add(json.getString(Config.TAG_LEADSTAGENAME));
                Log.e("leadStageList"," "+leadStageList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner1.setAdapter(new ArrayAdapter<String>(LeadDetails.this, android.R.layout.simple_spinner_dropdown_item, leadStageList));
        ArrayAdapter leadStage = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,leadStageList);
        leadStage.setDropDownViewResource(R.layout.spinner_text_view);
        spinner1.setAdapter(leadStage);
    }
    private String getLeadStageId(int position){
        try {
            JSONObject json = leadStage.getJSONObject(position);
            lead_stageSlog = json.getString(Config.TAG_LEADSTAGESLOG);
//          Toast.makeText(getApplicationContext(),lead_stageSlog, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lead_stageSlog;
    }
    public void update_followup_status(String editLeadId,String followUpSlow,String lead_stageSlog){
        String date = dateId.getText().toString();
        String command = textarea_Id.getText().toString().trim();
        if (date.equals("Date")) {
            Toast.makeText(getApplicationContext(),"Select Date",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(command)) {
            textarea_Id.setError("Enter Command");
            textarea_Id.requestFocus();
            return;
        }
        if (spinner.getSelectedItem().toString().equals("Select Followup Name")){
            Toast.makeText(getApplicationContext(),"Select Followup Name",Toast.LENGTH_SHORT).show();
            return;
        }
//        if (spinner1.getSelectedItem().toString().equals("Select Lead Stage")){
//            Toast.makeText(getApplicationContext(),"Select Lead Stage",Toast.LENGTH_SHORT).show();
//            return;
//        }
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"update_followup_leadstage_status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("update_followup_status"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);

                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            } else {
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

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", editLeadId);
                params.put("lead_followups", followUpSlow);
                params.put("comment", command);
                params.put("lead_stage", "");
                params.put("comment_date", date);
                System.out.println("update_followup_status"+params.toString());
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
    public void update_followup_status1(String editLeadId,String followUpSlow,String lead_stageSlog){
        String date = dateId.getText().toString();
        String command = textarea_Id.getText().toString().trim();
        if (date.equals("Date")) {
            Toast.makeText(getApplicationContext(),"Select Date",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(command)) {
            textarea_Id.setError("Enter Command");
            textarea_Id.requestFocus();
            return;
        }
        if (spinner.getSelectedItem().toString().equals("Select Followup Name")){
            Toast.makeText(getApplicationContext(),"Select Followup Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinner1.getSelectedItem().toString().equals("Select Lead Stage")){
            Toast.makeText(getApplicationContext(),"Select Lead Stage",Toast.LENGTH_SHORT).show();
            return;
        }
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"update_followup_leadstage_status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("update_followup_status1"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);

                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                            else {
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

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", editLeadId);
                params.put("lead_followups", followUpSlow);
                params.put("comment", command);
                params.put("lead_stage", lead_stageSlog);
                params.put("comment_date", date);
                System.out.println("update_followup_status1"+params.toString());
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
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.menu_new_content_twitter){
//            // do something
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
                                admissionLeads = obj.getJSONArray("admission_leads");
//                                for (int i = 0; i<admissionLeads.length(); i++){
//                                    JSONObject jsonObject = admissionLeads.getJSONObject(i);
//                                    String sessionValue = jsonObject.getString("session_value");
//                                    sessionName.add(sessionValue);
//                                    System.out.println("sessionValue"+" "+sessionValue);
//
//                                }
                                getSession(admissionLeads);
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
    private void getSession(JSONArray s){
        for(int S=0;S<s.length();S++){
            try {
                JSONObject json = s.getJSONObject(S);
                sessionName.add(json.getString(Config.ADMISSION_LEADS_VALUE));
                Log.e("session_update"," "+sessionName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter sessonData = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,sessionName);
        sessonData.setDropDownViewResource(R.layout.spinner_text_view);
        short_by_session.setAdapter(sessonData);

        String compareValue = sessionname_id;
        if (compareValue != null) {
            int spinnerPosition = sessonData.getPosition(compareValue);
            short_by_session.setSelection(spinnerPosition);
        }
    }
    private String getSessionId(int position){
        try {
            JSONObject json = admissionLeads.getJSONObject(position);
            session_ids = json.getString(Config.ADMISSION_LEADS_ID);
//            Toast.makeText(getApplicationContext(),session_ids, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session_ids;
    }
    public void session_update(String session_ids,String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"session_update",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("session_update"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");

                            if (status.equals("true")){
//                                Intent intent = new Intent(getApplicationContext(),LeadTableActivity.class);
//                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
                params.put("id", editLeadId);
                params.put("sessionid", session_ids);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("session_update"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public void college_update_api(String collagesId,String editLeadId){
          UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
          StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"college_update_api",
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          System.out.println("college_update_api"+response);
                          try {
                              JSONObject obj = new JSONObject(response);
                              String status = obj.getString("status");
                              String msg = obj.getString("msg");
                              if (status.equals("true")){
                                  String result = obj.getString("result");
                                  Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

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
                  params.put("college_id",collagesId);
                  params.put("lead_id", editLeadId);
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

    public void payment_approval_schedule(String collagesId,String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"payment_approval_schedule_check",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("payment_approval_schedule_check"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                check();

                            }
                            else {
//                              builder.setMessage("Message") .setTitle("Before fill up the form you have to complete payment share info");
                              builder.setMessage(" Can't form fill up before payment share info done ")
                                        .setCancelable(false)
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
//                                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.setTitle("Message Box");
                                alert.show();
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
                params.put("id",editLeadId);
//                params.put("lead_id", editLeadId);
                System.out.println("payment_approval_schedule_check"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("payment_approval_schedule_check"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public android.app.AlertDialog check(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.check_dioloag, null);
        builder.setView(dialogView);
        final android.app.AlertDialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ((AppCompatButton)dialogView.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("editLeadId",editLeadId);
                intent.putExtra("collagesId",collagesId);
                intent.putExtra("session_id",session_id);
                intent.putExtra("source_id",source_id);
                intent.putExtra("course_id",course_id);
                startActivity(intent);
            }
        });
        ((AppCompatButton)dialogView.findViewById(R.id.btn_submit)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                send_form_to_student(editLeadId);
                dialog.dismiss();
            }
        });

//        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    public android.app.AlertDialog check1(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.check_dioloag, null);
        builder.setView(dialogView);
        final android.app.AlertDialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ((AppCompatButton)dialogView.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(spinner_collage.getSelectedItem().equals("Select College")){
                    Toast.makeText(getApplicationContext(),"Select College",Toast.LENGTH_SHORT).show();
                }
                else{
                    if ( short_by_session.getSelectedItem().equals("Select Session")){
                        Toast.makeText(getApplicationContext(),"Select Session",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(),CollageFromActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("mPhone",mPhone);
                        intent.putExtra("wPhone",wPhone);
                        intent.putExtra("course",course);
                        intent.putExtra("date",date);
                        intent.putExtra("email",email);
                        intent.putExtra("editLeadId",editLeadId);
                        intent.putExtra("courseIds",courseIds);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                    }

                }

            }
        });
        ((AppCompatButton)dialogView.findViewById(R.id.btn_submit)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                send_form_to_student(editLeadId);
                dialog.dismiss();
            }
        });

//        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }

    public void send_form_to_student(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"send_form_to_student",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("send_form_to_student"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getApplicationContext(),msg,
                                                      Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getApplicationContext(),msg,
                                        Toast.LENGTH_SHORT).show();
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
                params.put("formtype", "admission");
                System.out.println("send_form_to_student"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("send_form_to_student"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }

    public void temp_payment_status(String collagesId,String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"temp_payment_status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("temp_payment_status"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
//                            String msg = obj.getString("msg");
                            if (status.equals("true")){
//                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                if(spinner_collage.getSelectedItem().equals("Select College")){
                                    Toast.makeText(getApplicationContext(),"Select College",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if ( short_by_session.getSelectedItem().equals("Select Session")){
                                        Toast.makeText(getApplicationContext(),"Select Session",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(),SendPaymentInfo.class);
                                        intent.putExtra("editLeadId",editLeadId);
                                        intent.putExtra("collagesId",collagesId);
                                        intent.putExtra("session_id",session_id);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right,
                                                R.anim.slide_out_left);
                                    }
                                }
                            }else {
//                              builder.setMessage("Message") .setTitle("Before fill up the form you have to complete payment share info");
                                builder.setMessage("Can't send payment share info before payment schedule approve")
                                        .setCancelable(false)
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                               finish();
////                                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
////                                                        Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        })
                                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
//                                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.setTitle("Message Box");
                                alert.show();
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
                params.put("id",editLeadId);
//                params.put("lead_id", editLeadId);
                System.out.println("temp_payment_status"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("temp_payment_status"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void temp_save_manual_payment(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"temp_save_manual_payment",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("temp_save_manual_payment"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                Intent intent = new Intent(getApplicationContext(),LeadDetailsEdit_Activity.class);
                                intent.putExtra("editLeadId",editLeadId);
                                intent.putExtra("courseIds",courseIds);
                                intent.putExtra("check_payment_approval_status",check_payment_approval_status);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
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
//              params.put("lead_id", editLeadId);
                System.out.println("temp_save_manual_payment"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("temp_save_manual_payment"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void college_session_check(String collagesId,String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"college_session_check",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("college_session_check"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
//                            String msg = obj.getString("msg");
                            if (status.equals("true")){
//                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                if(spinner_collage.getSelectedItem().equals("Select College")){
                                    Toast.makeText(getApplicationContext(),"Select College",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if ( short_by_session.getSelectedItem().equals("Select Session")){
                                        Toast.makeText(getApplicationContext(),"Select Session",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        temp_save_manual_payment(editLeadId);

                                    }

                                }

                            }else {
//                              builder.setMessage("Message") .setTitle("Before fill up the form you have to complete payment share info");
                                builder.setMessage("Can't send payment schedule before select college and session ")
                                        .setCancelable(false)
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                finish();
////                                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
////                                                        Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        })
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
//                                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.setTitle("Message Box");
                                alert.show();
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
                params.put("id",editLeadId);
//              params.put("lead_id", editLeadId);
                System.out.println("college_session_check"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("college_session_check"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void lead_flag_change(String status_type,String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"lead_flag_change",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("lead_flag_change"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);

                            }else {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                params.put("status_type", status_type);
                System.out.println("lead_flag_change"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("lead_flag_change"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void request_college_admisation(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"request_college_admisation",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("request_college_admisation"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);

                            }else {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
//                params.put("status_type", status_type);
                System.out.println("request_college_admisation"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("request_college_admisation"+user.getToken());
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
        Intent intent = new Intent(getApplicationContext(),LeadTableActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}
