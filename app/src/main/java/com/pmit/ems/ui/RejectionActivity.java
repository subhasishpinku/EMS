package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
import com.pmit.ems.model.RejectionList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RejectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    RecyclerView recyclerLead,recyclerCount;
    JSONArray jsonArrayleadlist;
    ArrayList<LeadCount> arrlist1 = new ArrayList<LeadCount>();
    ArrayList<RejectionList> arrlist = new ArrayList<RejectionList>();
    List<LeadCount> leadCounts;
    FrameLayout progressBarHolder;
    Spinner short_by_session;
    String sessionNameValue ="";
    ArrayList<String> sessionName;
    List<RejectionList> rejectionLists;
    public LeadViewAdapter homeItemViewAdapter;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recection_activity);
        recyclerLead = (RecyclerView)findViewById(R.id.recyclerLead);
        recyclerCount = (RecyclerView)findViewById(R.id.recyclerCount);
        progressBarHolder = (FrameLayout)findViewById(R.id.progressBarHolder);
        short_by_session = (Spinner)findViewById(R.id.short_by_session);
        short_by_session.setOnItemSelectedListener(this);
        arrlist1 = new ArrayList<>();
        leadCounts = new ArrayList<>();
        sessionName = new ArrayList<>();
        rejectionLists = new ArrayList<>();
        arrlist = new ArrayList<>();
        builder = new AlertDialog.Builder(this);
        recyclerLead.setHasFixedSize(true);
        recyclerLead.setLayoutManager(new LinearLayoutManager(this));
        Session();
        RejectList();
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
    public void RejectList(){
        progressBarHolder.setVisibility(View.VISIBLE);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"reject_leads_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("reject_leads_list"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            progressBarHolder.setVisibility(View.GONE);
                            if (status.equals("true")){
                                leadCourseCount();
                                JSONArray rejectList = obj.getJSONArray("reject_list");
                                for (int i = 0; i<rejectList.length(); i++){
                                    JSONObject jsonObject = rejectList.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String fname = jsonObject.getString("fname");
                                    String mname = jsonObject.getString("mname");
                                    String lname = jsonObject.getString("lname");
                                    String mobile_no = jsonObject.getString("mobile_no");
                                    String whatsapp_no = jsonObject.getString("whatsapp_no");
                                    String email = jsonObject.getString("email");
                                    String srn_no = jsonObject.getString("srn_no");
                                    String father_name = jsonObject.getString("father_name");
                                    String guardian_name = jsonObject.getString("guardian_name");
                                    String mother_name = jsonObject.getString("mother_name");
                                    String dob = jsonObject.getString("dob");
                                    String photo = jsonObject.getString("photo");
                                    String course_name = jsonObject.getString("course_name");
                                    String collegename= jsonObject.getString("collegename");
                                    String session_value= jsonObject.getString("session_value");
                                    String booking_amount = jsonObject.getString("booking_amount");
                                    String transaction_id = jsonObject.getString("transaction_id");
                                    String source_id = jsonObject.getString("source_id");
                                    String course_id = jsonObject.getString("course_id");
                                    String session_id = jsonObject.getString("session_id");
                                    String college_id = jsonObject.getString("college_id");

                                    System.out.println("reject_leads_list"+transaction_id);
                                    rejectionLists.add(
                                            new RejectionList(
                                                     id,  fname,  mname,  lname,  mobile_no,  whatsapp_no,  email,srn_no,  father_name, guardian_name, mother_name,
                                                     dob,photo,course_name,collegename,session_value,
                                                    booking_amount,transaction_id,source_id,course_id,session_id,college_id));

                                    arrlist.addAll(rejectionLists);
                                    homeItemViewAdapter = new LeadViewAdapter(getApplicationContext(), rejectionLists);
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
                System.out.println("reject_leads_list"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sessionNameValue = short_by_session.getSelectedItem().toString();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public class LeadViewAdapter extends RecyclerView.Adapter<LeadViewAdapter.ViewHolder> {
        private Context mCtx;
        private List<RejectionList> rejectionLists;
        int listview;
        public LeadViewAdapter(Context mCtx, List<RejectionList> rejectionLists) {
            this.mCtx = mCtx;
            this.rejectionLists = rejectionLists;
        }
        @Override
        public LeadViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_rejection_card, parent, false);
            return new LeadViewAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final LeadViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            RejectionList rejectionList = rejectionLists.get(position);
            listview = position;
            holder.led_tv.setText(rejectionList.getFname());
            if(rejectionList.getCollegename() != null) {
                holder.collageName.setText(rejectionList.getCollegename());
            }
            else {
                holder.collageName.setText("Empty College Name");
            }
            holder.led_tv5.setText(rejectionList.getCourse_name());
//            holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(mystudentss.getProfileImage()));
            String collageUrl= "https://developer.pmitcolleges.org/"+rejectionList.getPhoto();
            Glide.with(mCtx)
                    .load(collageUrl)
                    .placeholder(R.drawable.user_image_empty)
                    .into(holder.Img_Id);
            if (position==0){
                holder.viewLine.setVisibility(View.GONE);
            }
            holder.rv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editLeadId = rejectionList.getId();
                    String collagesId = rejectionList.getCollege_id();
                    String session_id = rejectionList.getSession_id();
                    String source_id = rejectionList.getSource_id();
                    String course_id = rejectionList.getCourse_id();
                    Intent intent = new Intent(getApplicationContext(),ProfileRejection.class);
                    intent.putExtra("editLeadId",editLeadId);
                    intent.putExtra("collagesId",collagesId);
                    intent.putExtra("session_id",session_id);
                    intent.putExtra("source_id",source_id);
                    intent.putExtra("course_id",course_id);
                    intent.putExtra("flag","0");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            });
            holder.led_tv6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editLeadId = rejectionList.getId();
                    reject_message(editLeadId);
                }
            });
        }
        @Override
        public int getItemCount() {
            return rejectionLists.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView led_tv, collageName,led_tv5,led_tv6;
            private ImageView Img_Id;
            private View viewLine;
            RelativeLayout rv;
            public ViewHolder(View view) {
                super(view);
                led_tv = (TextView) view.findViewById(R.id.led_tv);
                collageName = (TextView) view.findViewById(R.id.collageName);
                led_tv5 = (TextView) view.findViewById(R.id.led_tv5);
                led_tv6 = (TextView) view.findViewById(R.id.led_tv6);
                Img_Id = (ImageView) view.findViewById(R.id.Img_Id);
                viewLine = (View) view.findViewById(R.id.viewLine);
                rv = (RelativeLayout)view.findViewById(R.id.rv);
            }
        }
    }
    public void reject_message(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"reject_message",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("reject_message"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            if (status.equals("1")){
                                String result = obj.getString("result");
                                builder.setMessage(result)
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
//                                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.setTitle("Reject Reason");
                                alert.show();
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
                        progressBarHolder.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                 params.put("lead_id", editLeadId);
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
