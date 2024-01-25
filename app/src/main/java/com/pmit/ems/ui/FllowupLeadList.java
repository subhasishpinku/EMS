package com.pmit.ems.ui;

import static com.pmit.ems.api.Config.BASEURL2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
import com.pmit.ems.R;
import com.pmit.ems.adapter.LeadCountAdapter;
import com.pmit.ems.api.ApiToken;
import com.pmit.ems.model.LeadCount;
import com.pmit.ems.model.LeadModel;
import com.pmit.ems.model.Leadfollowuphistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class FllowupLeadList extends AppCompatActivity {
    RecyclerView recyclerLead,recyclerCount;
    List<LeadModel> leadModels;
    List<LeadCount> leadCounts;
    List<Leadfollowuphistory> leadfollowuphistories;
    ArrayList<Leadfollowuphistory> arrlist2 = new ArrayList<Leadfollowuphistory>();

    ArrayList<LeadModel> arrlist = new ArrayList<LeadModel>();
    ArrayList<LeadCount> arrlist1 = new ArrayList<LeadCount>();

    public LeadViewAdapter homeItemViewAdapter;
    public LeadViewAdapter2 followup;
    JSONArray jsonArrayleadlist;
    FrameLayout progressBarHolder;
    String collegename="";
    String id;
    String name,mPhone,wPhone,course,date,email,editLeadId,courseIds,collageId,status,session_id,source_id,course_id,subject_id,sessionname_id,check_payment_approval_status;
    String courseName,sessionname;
    TextView led_tv3;
    SearchView search_Id;
    RelativeLayout rv;
    private ArrayList<LeadModel> courseModelArrayList;
    ImageView ImgZip;
    TextView load_more;
    int clickcount= 1;
    String searchName="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fllowup_lead_list);
        //swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
//      swipeRefresh.setOnRefreshListener(this);
        load_more = (TextView)findViewById(R.id.load_more);
        ImgZip = (ImageView)findViewById(R.id.ImgZip);
        recyclerLead = (RecyclerView) findViewById(R.id.recyclerLead);
        recyclerCount = (RecyclerView) findViewById(R.id.recyclerCount);
        rv = (RelativeLayout) findViewById(R.id.rv);
        progressBarHolder  = (FrameLayout) findViewById(R.id.progressBarHolder);
        led_tv3 = (TextView)findViewById(R.id.led_tv3);
        search_Id = (SearchView)findViewById(R.id.search_Id);
        leadModels = new ArrayList<>();
        leadCounts = new ArrayList<>();
        arrlist = new ArrayList<>();
        arrlist1 = new ArrayList<>();
        leadfollowuphistories = new ArrayList<>();
//        leadModels.add(
//                new LeadModel(
//                        "Akash Mondal",
//                        "9585886392",
//                        "9585886392",
//                        "D PHARMA",
//                        "#ffcc00",
//                        R.mipmap.tag1,
//                        "20-12-2022 10:20Pm",
//                        "N"
//                        ));
//        leadModels.add(
//                new LeadModel(
//                        "Somnath Banerjee",
//                        "9585886392",
//                        "9585886392",
//                        "GNM NURSING",
//                        "#b3f177",
//                        R.mipmap.tag2,
//                        "19-12-2022 02:40 Am",
//                        "o"));
//        leadModels.add(
//                new LeadModel(
//                        "Sumita Sarkar",
//                        "9585886392",
//                        "9585886392",
//                        "BBAHM",
//                        "#f7a8c8",
//                        R.mipmap.tag1,
//                        "20-12-2022 06:30 Pm",
//                        "N"));
//        leadModels.add(
//                new LeadModel(
//                        "Sharda Shaw",
//                        "9585886392",
//                        "9585886392",
//                        "BBAHM",
//                        "#f7a8c8",
//                        R.mipmap.tag2,
//                        "18-12-2022 09:30 Am",
//                        "o"));
//        leadModels.add(
//                new LeadModel(
//                        "Dinabandhu Bhattacharya",
//                        "9585886392",
//                        "9585886392",
//                        "D PHARMA",
//                        "#f7a8c8",
//                        R.mipmap.tag2,
//                        "17-12-2022 15:30 Am",
//                        "o"));
//        leadModels.add(
//                new LeadModel(
//                        "Paritosh Kumer Chakraborty",
//                        "9585886392",
//                        "9585886392",
//                        "D PHARMA",
//                        "#ffcb00",
//                        R.mipmap.tag2,
//                        "16-12-2022 16:30 Pm",
//                        "o"));
        recyclerLead.setHasFixedSize(true);
        recyclerLead.setLayoutManager(new LinearLayoutManager(this));
//      recyclerCount.setHasFixedSize(true);
//      recyclerCount.setLayoutManager(new LinearLayoutManager(this));
        courseModelArrayList = new ArrayList<>();
        load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchName ="";
                clickcount=clickcount+1;
                System.out.println("Load_more "+clickcount);
                followup_get_lead(String.valueOf(clickcount),searchName);
            }
        });

        search_Id.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("SEARCHVIEW"+"1");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
//                filter(newText);
//                System.out.println("SEARCHVIEW"+"0");
                return false;
            }
        });
        ImgZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchName = search_Id.getQuery().toString();
                leadModels.clear();
                clickcount=1;
                followup_get_lead(String.valueOf(clickcount),searchName);

                System.out.println("searchName"+searchName);
            }
        });
        followup_get_lead(String.valueOf(clickcount),searchName);
        pending_followup();
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
                                led_tv3.setText(followup);
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
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<LeadModel> filteredlist = new ArrayList<LeadModel>();

        // running a for loop to compare elements.
        for (LeadModel item : courseModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getFname().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.clear();
                filteredlist.add(item);
                System.out.println("SEARCHVIEW"+"    "+item.getFname());

            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
//            followup_get_lead();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            homeItemViewAdapter.filterList(filteredlist);

        }
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerLead.setLayoutManager(layoutManager);
//        adapter = new PostRecyclerAdapter(getApplicationContext(),leadModels);
//        recyclerLead.setAdapter(adapter);

//        recyclerLead.addOnScrollListener(new PaginationListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage++;
//                System.out.println("Count_Data "+currentPage++);
//
////                doApiCall(id,fname,mobile_no,whatsapp_no,email,
////                        course_id,source_id,session_id,lead_followups,
////                        status1,
////                        created_at,college_id,
////                        courseName,sessionname,counsellorname);
//            }
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
    }
    public void followup_get_leads(){
        progressBarHolder.setVisibility(View.VISIBLE);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"followup_get_lead",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("get_lead"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
//                          JSONObject Object = obj.getJSONObject("leadlist");
//                          System.out.println("get_lead_status"+Object);
//                          String currentpage = Object.getString("current_page");
                            progressBarHolder.setVisibility(View.GONE);
                            if (status.equals("true")){
                                leadCourseCount();
                                jsonArrayleadlist = obj.getJSONArray("leadlist");
                                for (int i = 0; i<jsonArrayleadlist.length(); i++){
                                    JSONObject jsonObject = jsonArrayleadlist.getJSONObject(i);
                                    System.out.println("leadlist1"+jsonObject);
                                    id = jsonObject.getString("id");
                                    String fname = jsonObject.getString("fname");
                                    String mobile_no = jsonObject.getString("mobile_no");
                                    String whatsapp_no = jsonObject.getString("whatsapp_no");
                                    String created_at = jsonObject.getString("created_at");
                                    String lead_followups = jsonObject.getString("lead_followups");
                                    String email = jsonObject.getString("email");
                                    String college_id = jsonObject.getString("college_id");
                                    String status1 = jsonObject.getString("status");
                                    String session_id = jsonObject.getString("session_id");
                                    String source_id = jsonObject.getString("source_id");
                                    String course_id = jsonObject.getString("course_id");
                                    String counsellorname = jsonObject.getString("counsellorname");
                                    String check_payment_approval_status = jsonObject.getString("check_status");
                                    courseName = jsonObject.getString("coursename");
                                    sessionname = jsonObject.getString("sessionname");
                                    String check_srn = jsonObject.getString("check_srn");
                                    String srn = jsonObject.getString("srn");
                                    String check_booking = jsonObject.getString("check_booking");
                                    String srn_flag = jsonObject.getString("srn_flag");
                                    String rejectform = jsonObject.getString("rejectform");

                                    if (courseName.equals("null")){
                                        courseName = "NA";
                                    }
                                    else {
                                        courseName = jsonObject.getString("coursename");

                                    }
                                    if (sessionname.equals("null")){
                                        sessionname = "NA";
                                    }
                                    else {
                                        sessionname = jsonObject.getString("sessionname");

                                    }
                                    leadModels.add(
                                            new LeadModel(
                                                    id,fname,mobile_no,whatsapp_no,email,
                                                    course_id,source_id,session_id,lead_followups,
                                                    status1,
                                                    created_at,college_id,
                                                    courseName,sessionname,counsellorname,check_payment_approval_status,check_srn,srn,srn_flag,rejectform,check_booking
                                            ));
                                    arrlist.addAll(leadModels);

                                    courseModelArrayList.addAll(leadModels);
                                    homeItemViewAdapter = new LeadViewAdapter(getApplicationContext(), leadModels);
                                    recyclerLead.setAdapter(homeItemViewAdapter);
//                                    doApiCall(id,fname,mobile_no,whatsapp_no,email,
//                                            course_id,source_id,session_id,lead_followups,
//                                            status1,
//                                            created_at,college_id,
//                                            courseName,sessionname,counsellorname);
//                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                                    recyclerLead.setLayoutManager(layoutManager);
//                                    adapter = new PostRecyclerAdapter(getApplicationContext(), leadModels);
//                                    recyclerLead.setAdapter(adapter);

                                }
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
//                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("leadlists"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void followup_get_lead(String clickcount,String searchName){
        progressBarHolder.setVisibility(View.VISIBLE);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        Call<ResponseBody> call = ApiToken.getInstance().followupGetLead(clickcount,searchName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    System.out.println("followup_get_lead"+res);
                    JSONObject obj = new JSONObject(res);
                    String status = obj.getString("status");
//                  JSONObject Object = obj.getJSONObject("leadlist");
//                  System.out.println("get_lead_status"+Object);
//                  String currentpage = Object.getString("current_page");
                    progressBarHolder.setVisibility(View.GONE);
                    if (status.equals("true")){
                        leadCourseCount();
                        jsonArrayleadlist = obj.getJSONArray("leadlist");
                        for (int i = 0; i<jsonArrayleadlist.length(); i++){
                            JSONObject jsonObject = jsonArrayleadlist.getJSONObject(i);
                            System.out.println("leadlist1"+jsonObject);
                            id = jsonObject.getString("id");
                            String fname = jsonObject.getString("fname");
                            String mobile_no = jsonObject.getString("mobile_no");
                            String whatsapp_no = jsonObject.getString("whatsapp_no");
                            String created_at = jsonObject.getString("created_at");
                            String lead_followups = jsonObject.getString("lead_followups");
                            String email = jsonObject.getString("email");
                            String college_id = jsonObject.getString("college_id");
                            String status1 = jsonObject.getString("status");
                            String session_id = jsonObject.getString("session_id");
                            String source_id = jsonObject.getString("source_id");
                            String course_id = jsonObject.getString("course_id");
                            String counsellorname = jsonObject.getString("counsellorname");
                            String check_payment_approval_status = jsonObject.getString("check_status");
                            courseName = jsonObject.getString("coursename");
                            sessionname = jsonObject.getString("sessionname");
                            String check_srn = jsonObject.getString("check_srn");
                            String srn = jsonObject.getString("srn");
                            String check_booking = jsonObject.getString("check_booking");
                            String srn_flag = jsonObject.getString("srn_flag");
                            String rejectform = jsonObject.getString("rejectform");
                            if (courseName.equals("null")){
                                courseName = "NA";
                            }
                            else {
                                courseName = jsonObject.getString("coursename");
                            }
                            if (sessionname.equals("null")){
                                sessionname = "NA";
                            }
                            else {
                                sessionname = jsonObject.getString("sessionname");
                            }
                            leadModels.add(
                                    new LeadModel(
                                            id,fname,mobile_no,whatsapp_no,email,
                                            course_id,source_id,session_id,lead_followups,
                                            status1,
                                            created_at,college_id,
                                            courseName,sessionname,counsellorname,check_payment_approval_status,check_srn,srn,srn_flag,rejectform,check_booking
                                    ));
                            arrlist.addAll(leadModels);
                            courseModelArrayList.addAll(leadModels);
                            homeItemViewAdapter = new LeadViewAdapter(getApplicationContext(), leadModels);
                            recyclerLead.setAdapter(homeItemViewAdapter);
//                                    doApiCall(id,fname,mobile_no,whatsapp_no,email,
//                                            course_id,source_id,session_id,lead_followups,
//                                            status1,
//                                            created_at,college_id,
//                                            courseName,sessionname,counsellorname);
//                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                                    recyclerLead.setLayoutManager(layoutManager);
//                                    adapter = new PostRecyclerAdapter(getApplicationContext(), leadModels);
//                                    recyclerLead.setAdapter(adapter);

                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Empty source",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBarHolder.setVisibility(View.GONE);
            }
        });
    }

    public void share_lead_info(String name,String mPhone,String wPhone,String course,String date,String email,
                                String editLeadId,String courseIds,String collageId,
                                String status,String session_id,String source_id,String course_id,String sessionname_id,String check_payment_approval_status,String check_srn,String srn,String srn_flag,String rejectform,String check_booking){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"share_lead_info",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("share_lead_info"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status1 = obj.getString("status");
                            String error = obj.getString("error");
                            if (status1.equals("true")){
                                JSONArray personaldetail = obj.getJSONArray("personal_detail");
                                for (int i = 0; i<personaldetail.length(); i++){
                                    JSONObject jsonObject = personaldetail.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String fname = jsonObject.getString("fname");
                                    String mname = jsonObject.getString("mname");
                                    String lname = jsonObject.getString("lname");
                                    String mobile_no = jsonObject.getString("mobile_no");
                                    String whatsapp_no = jsonObject.getString("whatsapp_no");
                                    String email = jsonObject.getString("email");
                                    String coursename = jsonObject.getString("course_name");
                                    collegename = jsonObject.getString("collegename");

                                }
//                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                                if (status.equals("active")){
                                    Intent intent = new Intent(getApplicationContext(),LeadDetails.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("mPhone",mPhone);
                                    intent.putExtra("wPhone",wPhone);
                                    intent.putExtra("course",course);
                                    intent.putExtra("date",date);
                                    intent.putExtra("email",email);
                                    intent.putExtra("editLeadId",editLeadId);
                                    intent.putExtra("courseIds",courseIds);
                                    intent.putExtra("collageId",collageId);
                                    intent.putExtra("collegename",collegename);

                                    intent.putExtra("session_id",session_id);
                                    intent.putExtra("source_id",source_id);
                                    intent.putExtra("course_id",course_id);
                                    intent.putExtra("sessionname_id",sessionname_id);
                                    intent.putExtra("check_payment_approval_status",check_payment_approval_status);

                                    intent.putExtra("check_srn",check_srn);
                                    intent.putExtra("srn",srn);
                                    intent.putExtra("srn_flag",srn_flag);
                                    intent.putExtra("rejectform",rejectform);
                                    intent.putExtra("check_booking",check_booking);
                                    intent.putExtra("ListFlag","fllowup");
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);
                                }else{
                                    Toast.makeText(getApplicationContext(),"This is a Inactive Lead",Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
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
                System.out.println("share_lead_info"+editLeadId);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("share_lead_info"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
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
    public class LeadViewAdapter extends RecyclerView.Adapter<LeadViewAdapter.ViewHolder> {
        private Context mCtx;
        private List<LeadModel> leadModels;
        int listview;
        public LeadViewAdapter(Context mCtx, List<LeadModel> leadModels) {
            this.mCtx = mCtx;
            this.leadModels = leadModels;
        }
        public void filterList(ArrayList<LeadModel> filterlist) {
            // below line is to add our filtered
            // list in our course array list.
            leadModels = filterlist;
            // below line is to notify our adapter
            // as change in recycler view data.
            notifyDataSetChanged();
        }
        @Override
        public LeadViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leadmodel_activity, parent, false);
            return new LeadViewAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final LeadViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            LeadModel leadModel = leadModels.get(position);
            listview = position;
            holder.led_tv.setText(leadModel.getFname());
            holder.led_tv2.setText(leadModel.getMobile_no());
            holder.led_tv4.setText(leadModel.getWhatsapp_no());
            holder.led_tv5.setText(leadModel.getCourse_name());
            holder.councilername.setText(leadModel.getCounsellorname());
//            holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(leadModel.getFlagImg()));
            holder.date_tv1.setText(leadModel.getCreated_at());
//            holder.led_tv5.setBackgroundColor(Color.parseColor(leadModel.getColorCode()));
            System.out.println("getLead_followups"+"  "+leadModel.getLead_followups());
            status = leadModel.getStatus1();
            if (status.equals("active")){
                if (leadModel.getLead_followups().equals("1")){
                    holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.tag3));

                }
                if (leadModel.getLead_followups().equals("0")) {
//                    holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.tag4));
                    holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.tag_gray));

                }

            }else {
                holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.tag4));
            }


            holder.Img_Id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String followups = leadModel.getLead_followups();
                    System.out.println("followups"+followups);
                    if (followups.equals("1") && leadModel.getStatus1().equals("active")){
                        editLeadId = leadModel.getId();
                        leadfollowuphistories.clear();
                        lead_followup_history(editLeadId);
                    }

                }
            });
            holder.rv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = holder.led_tv.getText().toString();
                    mPhone = holder.led_tv2.getText().toString();
                    wPhone = holder.led_tv4.getText().toString();
                    course = holder.led_tv5.getText().toString();
                    date = holder.date_tv1.getText().toString();
                    email = leadModel.getEmail();
                    editLeadId = leadModel.getId();
                    courseIds = leadModel.getCourse_id();
                    collageId = leadModel.getCollege_id();
                    status = leadModel.getStatus1();
                    session_id = leadModel.getSession_id();
                    source_id = leadModel.getSource_id();
                    course_id = leadModel.getCourse_id();
                    sessionname_id = leadModel.getSessionname();
                    check_payment_approval_status = leadModel.getCheck_payment_approval_status();
                    String check_srn = leadModel.getCheck_srn();
                    String srn  = leadModel.getSrn();
                    String srn_flag = leadModel.getSrn_flag();
                    String rejectform = leadModel.getRejectform();
                    String check_booking  = leadModel.getCheck_booking();
                    System.out.println("LeadUserData"+status+" "+editLeadId+" "+collageId+" "+collegename);
                    share_lead_info(name,mPhone,wPhone,course,date,email,editLeadId,courseIds,collageId,status,session_id,source_id,course_id,sessionname_id,check_payment_approval_status,check_srn,srn,srn_flag,rejectform,check_booking);
                }
            });
        }
        @Override
        public int getItemCount() {
            return leadModels.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView led_tv, led_tv2,led_tv4,led_tv5,date_tv1,councilername;
            private ImageView Img_Id;
            RelativeLayout rv;
            public ViewHolder(View view) {
                super(view);
                led_tv = (TextView) view.findViewById(R.id.led_tv);
                led_tv2 = (TextView) view.findViewById(R.id.led_tv2);
                led_tv4 = (TextView) view.findViewById(R.id.led_tv4);
                led_tv5 = (TextView) view.findViewById(R.id.led_tv5);
                date_tv1  = (TextView) view.findViewById(R.id.date_tv1);
                Img_Id = (ImageView) view.findViewById(R.id.Img_Id);
                rv = (RelativeLayout) view.findViewById(R.id.rv);
                councilername = (TextView) view.findViewById(R.id.councilername);
            }
        }
    }
    public class LeadViewAdapter2 extends RecyclerView.Adapter<LeadViewAdapter2.ViewHolder> {
        private Context mCtx;
        private List<Leadfollowuphistory> leadfollowuphistories;
        int listview;

        public LeadViewAdapter2(Context mCtx, List<Leadfollowuphistory> leadfollowuphistories) {
            this.mCtx = mCtx;
            this.leadfollowuphistories = leadfollowuphistories;
        }

        @Override
        public LeadViewAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_followup_history_layout, parent, false);
            return new LeadViewAdapter2.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final LeadViewAdapter2.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Leadfollowuphistory leadfollowuphistoriess = leadfollowuphistories.get(position);
            listview = position;
            holder.follwup.setText(leadfollowuphistoriess.getLead_followups());
            holder.follwupdate.setText(leadfollowuphistoriess.getComment_date());
            holder.comment.setText(leadfollowuphistoriess.getComment());
//            holder.lead_stage.setText(leadfollowuphistoriess.getLead_stage());

        }
        @Override
        public int getItemCount() {
            return leadfollowuphistories.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView follwup,follwupdate,comment;

            public ViewHolder(View view) {
                super(view);
                follwup = (TextView) view.findViewById(R.id.follwup);
                follwupdate = (TextView) view.findViewById(R.id.follwupdate);
                comment = (TextView) view.findViewById(R.id.comment);
//                lead_stage = (TextView) view.findViewById(R.id.lead_stage);

            }
        }
    }

    public void lead_followup_history(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"lead_followup_history",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("lead_followup_history"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                JSONArray jsonArray = obj.getJSONArray("followupDetails");
                                for (int i =0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String lead_followups = jsonObject.getString("lead_followups");
                                    String comment = jsonObject.getString("comment");
                                    String comment_date = jsonObject.getString("comment_date");
                                    String lead_stage = jsonObject.getString("lead_stage");
                                    leadfollowuphistories.add(
                                            new Leadfollowuphistory(
                                                    lead_followups,
                                                    comment,
                                                    comment_date,
                                                    lead_stage
                                            ));
                                    arrlist2.addAll(leadfollowuphistories);
                                    createDialog5();
                                }

                            }else {
                                Toast.makeText(getApplicationContext(),"No Follow-up to Show",Toast.LENGTH_SHORT).show();
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
                params.put("lead_id", editLeadId);
                System.out.println("lead_followup_history"+params.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("lead_followup_history"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public AlertDialog createDialog5(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.flowup_alert, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        RecyclerView recyclerflowups =(RecyclerView)dialogView.findViewById(R.id.recyclerflowups);
        recyclerflowups.setHasFixedSize(true);
        recyclerflowups.setLayoutManager(new LinearLayoutManager(this));
        followup = new LeadViewAdapter2(getApplicationContext(), leadfollowuphistories);
        recyclerflowups.setAdapter(followup);
//        ((ImageView)dialogView.findViewById(R.id.openCamera)).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//                    ActivityCompat.requestPermissions(LeadTableActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
//                } else {
//                }
//                dialog.dismiss();
//            }
//        });
//        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//                    ActivityCompat.requestPermissions(LeadTableActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
//                } else {
//
//                }
//
//                dialog.dismiss();
//            }
//        });
////        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                dialog.dismiss();
//
//            }
//        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);

    }
}
