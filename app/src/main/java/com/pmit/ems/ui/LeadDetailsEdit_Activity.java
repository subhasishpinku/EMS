package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.pmit.ems.R;
import com.pmit.ems.api.Config;
import com.pmit.ems.api.Consts;
import com.pmit.ems.model.CheckOtp;
import com.pmit.ems.model.LeadEditDetails;
import com.pmit.ems.model.LeadEditDetails1;
import com.pmit.ems.model.PaymentSeduleListSend;
import com.pmit.ems.utility.HideSoftKeyboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class LeadDetailsEdit_Activity extends AppCompatActivity implements OtpBottomSheetDialog.BottomSheetListener{
    RecyclerView recyclerLead,recyclerLead1;
    List<LeadEditDetails> leadEditDetails;
    List<PaymentSeduleListSend> paymentSeduleListSends;
    List<LeadEditDetails1> leadEditDetails1;
    public LeadViewAdapter leadViewAdapter;
    public LeadViewAdapter1 leadViewAdapter1;
    String editLeadId;
    String courseIds,check_payment_approval_status;
    ArrayList<LeadEditDetails> arrlist = new ArrayList<LeadEditDetails>();
    ArrayList<LeadEditDetails1> arrlist1 = new ArrayList<LeadEditDetails1>();
    ArrayList<PaymentSeduleListSend> arrlist2 = new ArrayList<PaymentSeduleListSend>();
    ArrayList<String> arrlist3 = new ArrayList<String>();
    ArrayList<String> arrlist4 = new ArrayList<String>();
    ArrayList<Integer> arrlist5 = new ArrayList<Integer>();
    ArrayList<Integer> arrlist6 = new ArrayList<Integer>();
    ArrayList<Integer> arrlist7 = new ArrayList<Integer>();
    ArrayList<Integer> arrlist8 = new ArrayList<Integer>();

    ImageView Img1;
    MaterialButton send,edit;
//  JSONObject objMainList;
//  JSONArray arrForB;
//  JSONObject itemB;
    ArrayList<String> typeNameList;
    String PaymentTypeName;
    TextView led_name1,led_names2,led_names3,led_names4;
    DatePickerDialog picker;
    String detailstring2,amountValue33;
    SharedPreferences sp;
    JSONArray feesType;
    String frees_id = "";
    String leadId,courseId,scheduleDate;
    TextView led_tv3;
    private HideSoftKeyboard hideSoftKeyboard;
    int aa =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_details_edit_activity);
        send = (MaterialButton)findViewById(R.id.send);
        edit = (MaterialButton)findViewById(R.id.edit);
        Img1 = (ImageView)findViewById(R.id.Img1);
        led_name1 = (TextView)findViewById(R.id.led_name1);
        led_names2 = (TextView)findViewById(R.id.led_names2);
        led_names3 = (TextView)findViewById(R.id.led_names3);
        led_names4 = (TextView)findViewById(R.id.led_names4);
        led_tv3 = (TextView)findViewById(R.id.led_tv3);
        led_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FllowupLeadList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        Img1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
//                View linearLayout =  findViewById(R.id.info);
//                //LinearLayout layout = (LinearLayout) findViewById(R.id.info);
//
//                EditText valueTV = new EditText(getApplicationContext());
//                valueTV.setText("hallo hallo");
//                valueTV.setId(5);
//                valueTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//                ((LinearLayout) linearLayout).addView(valueTV);
                createDialog();


            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            editLeadId = extras.getString("editLeadId");
            courseIds = extras.getString("courseIds");
            check_payment_approval_status  = extras.getString("check_payment_approval_status");
            System.out.println("check_booking"+check_payment_approval_status+" "+editLeadId);
        }
        recyclerLead = (RecyclerView) findViewById(R.id.recyclerLead);
        recyclerLead1 = (RecyclerView) findViewById(R.id.recyclerLead1);
        leadEditDetails = new ArrayList<>();
        arrlist = new ArrayList<>();
        leadEditDetails1 = new ArrayList<>();
        arrlist1 = new ArrayList<>();
        arrlist2 = new ArrayList<>();
        arrlist3 = new ArrayList<>();
        arrlist4 = new ArrayList<>();
        arrlist5 = new ArrayList<>();
        arrlist6 = new ArrayList<>();
        arrlist7 = new ArrayList<>();
        arrlist8 = new ArrayList<>();
//        send.setVisibility(View.GONE);
//        edit.setVisibility(View.GONE);
        paymentSeduleListSends = new ArrayList<>();
        if (check_payment_approval_status.equals("0")) {
            Img1.setEnabled(true);
            send.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);        }
        else {
            Img1.setEnabled(false);
            send.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"You can not do this operation after Approval of Payment Schedule", Toast.LENGTH_LONG).show();
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
//                startActivity(intent);
            }
        });
//        ArrayList<PaymentSeduleListSend> myCustomList = new ArrayList<PaymentSeduleListSend>();
//        JSONArray jsArray = new JSONArray(myCustomList);
//        for (int i=0; i < jsArray.length(); i++) {
//            try {
//                JSONObject jsonObject = jsArray.getJSONObject(i);
//                String id = jsonObject.getString("id");
//                System.out.println("bbbb"+id);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            countPrice();
            String List = String.valueOf(arrlist3);
            String List1 = String.valueOf(arrlist4);
            String amountValue1 = List.replace("[", "");
            String amountValue2 = amountValue1.replace("]", "");
            String amountValue11 = List1.replace("[", "");
            String amountValue22 = amountValue11.replace("]", "");
            detailstring2 = amountValue2+","+amountValue22;
            amountValue33 = detailstring2.replace("/,", "/");
            System.out.println("Integer Number Added in ArrayList=" + amountValue33);
//                for(int i = 0; i < leadEditDetails.size(); i++) {
//                    System.out.println("Integer Number Added in ArrayList=" + leadEditDetails.get(i).getScheduleDate());
//                    if (leadEditDetails.get(i).getScheduleDate().equals("0000-00-00")) {
//                        Toast.makeText(getApplicationContext(),"schedule date",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                }
              send_otp_to_manager_app(editLeadId,courseIds);
            }
        });

//        leadEditDetails.add(
//                new LeadEditDetails(
//                        "Booking",
//                        "25-12-2022",
//                        "10,000.00",
//                        R.mipmap.edit_button,
//                        R.mipmap.plus_button));
//
//        leadEditDetails.add(
//                new LeadEditDetails(
//                        "Admission",
//                        "20-12-2023",
//                        "20,000.00",
//                        R.mipmap.edit_button,
//                        R.mipmap.plus_button));
//        leadEditDetails.add(
//                new LeadEditDetails(
//                        "1st Installment",
//                        "20-12-2023",
//                        "20,000.00",
//                        R.mipmap.edit_button,
//                        R.mipmap.plus_button));
//        leadEditDetails.add(
//                new LeadEditDetails(
//                        "2nd Installment",
//                        "20-12-2023",
//                        "20,000.00",
//                        R.mipmap.edit_button,
//                        R.mipmap.plus_button));
//        leadEditDetails.add(
//                new LeadEditDetails(
//                        "3rd Installment",
//                        "20-12-2023",
//                        "20,000.00",
//                        R.mipmap.edit_button,
//                        R.mipmap.plus_button));
//        leadEditDetails.add(
//                new LeadEditDetails(
//                        "4th Installment",
//                        "20-12-2023",
//                        "20,000.00",
//                        R.mipmap.edit_button,
//                        R.mipmap.plus_button));
       recyclerLead.setHasFixedSize(true);
       recyclerLead.setLayoutManager(new LinearLayoutManager(this));
       leads_payment_schedule(editLeadId);
       share_lead_info();
        pending_followup();
    }
    public void leads_payment_schedule(String id){
        System.out.println("leads_payment_schedule"+" "+id);
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"leads_payment_schedule",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("leads_payment_schedule"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
//                            String count = obj.getString("count");
                            if (status.equals("true")){
                                JSONArray paymentFess = obj.getJSONArray("payment_fees");
                                int clickcount=0;
                                for (int i = 0; i<paymentFess.length(); i++){
                                    JSONObject jsonObject = paymentFess.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    leadId = jsonObject.getString("lead_id");
                                    courseId = jsonObject.getString("course_id");
                                    String installmentType = jsonObject.getString("installment_type");
                                    String amount = jsonObject.getString("amount");
                                    scheduleDate = jsonObject.getString("schedule_date");
                                    String statusUs = jsonObject.getString("status");
                                    String createdAt = jsonObject.getString("created_at");
                                    String updatedAt = jsonObject.getString("updated_at");
                                    String rowNumber = jsonObject.getString("row_number");
                                    String collegeid = jsonObject.getString("college_id");
                                    String userid = jsonObject.getString("user_id");
                                    clickcount=clickcount+1;
                                    String count = String.valueOf(clickcount);
                                    System.out.println("pending_leads_list1"+count);
                                    if (scheduleDate.equals("")){
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        scheduleDate = sdf.format(new Date());
                                    }
                                    leadEditDetails.add(
                                            new LeadEditDetails(
                                                    id,leadId,courseId,installmentType,amount,scheduleDate,statusUs,createdAt,updatedAt,count,rowNumber));
                                   arrlist.addAll(leadEditDetails);
                                    paymentSeduleListSends.add(
                                                    new PaymentSeduleListSend(
                                                            id,installmentType,amount,scheduleDate));
                                    arrlist2.addAll(paymentSeduleListSends);
//                                  PaymentSeduleListSend send = new PaymentSeduleListSend();
//                                  send.setId(id);
//                                    send.setScheduleDate(installmentType);
//                                    send.setAmount(amount);
//                                    send.setScheduleDate(scheduleDate);
                                   leadViewAdapter = new LeadViewAdapter(getApplicationContext(), leadEditDetails,paymentSeduleListSends);
                                   recyclerLead.setAdapter(leadViewAdapter);

//                                    arrlist3.add(id);
//                                    arrlist3.add(installmentType);
//                                    arrlist3.add(amount);
//                                    arrlist3.add(scheduleDate);
//                                    arrlist3.add(editLeadId);
//                                    arrlist3.add(courseId+"/");
//                                    arrlist3.add("/");
                                     arrlist5.add(Integer.valueOf(amount));

                                }
                                countPrice();

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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("leads_payment_schedule"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
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

    public class LeadViewAdapter extends RecyclerView.Adapter<LeadViewAdapter.ViewHolder> {
        private Context mCtx;
        private List<LeadEditDetails> leadEditDetails;
        int listview;
        String listCount;
        String listCount1;
        String UpDate;
        boolean isCheck = false;
        private  List<PaymentSeduleListSend> paymentSeduleListSends;
        public LeadViewAdapter(Context mCtx, List<LeadEditDetails> leadEditDetails,List<PaymentSeduleListSend> paymentSeduleListSends) {
            this.mCtx = mCtx;
            this.leadEditDetails = leadEditDetails;
            this.paymentSeduleListSends = paymentSeduleListSends;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_details_table, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            LeadEditDetails leadModel = leadEditDetails.get(position);
            PaymentSeduleListSend paymentSeduleListSend = paymentSeduleListSends.get(position);
            listview = position;
            listCount = String.valueOf(recyclerLead.getAdapter().getItemCount());
            listCount1 = String.valueOf(leadEditDetails.size());
            System.out.println("ListSize"+"  "+leadEditDetails.size()+"   "+leadModel.getCount()+ " "+leadModel.getRownumber()+" "+recyclerLead.getAdapter().getItemCount());
//            objMainList = new JSONObject();
//            arrForB = new JSONArray();
//            itemB = new JSONObject();
//            String ids = paymentSeduleListSend.getId();
//            String installmenttype = paymentSeduleListSend.getInstallmentType();
//            String amount = paymentSeduleListSend.getAmount();
//            String scheduledate = paymentSeduleListSend.getScheduleDate();
//            for (int i =0; (i < ids.length()) && (i < installmenttype.length()) && (i < amount.length()) && (i < scheduledate.length()); i++ ) {
//                try {
//                    itemB.put("id", paymentSeduleListSend.getId());
//                    itemB.put("installment_type", paymentSeduleListSend.getInstallmentType());
//                    itemB.put("amount", paymentSeduleListSend.getAmount());
//                    itemB.put("schedule_date",paymentSeduleListSend.getScheduleDate());
//                    arrForB.put(itemB);
//                    objMainList.put("A", arrForB);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
            holder.bookingId.setText(leadModel.getInstallmentType());
            holder.dateId.setText(leadModel.getScheduleDate());
            holder.amountId.setText(leadModel.getAmount());
//          holder.Img.setImageDrawable(mCtx.getResources().getDrawable(R.mipmap.edit_button));
            holder.Img1.setImageDrawable(mCtx.getResources().getDrawable(R.mipmap.minus));
            holder.Img.setVisibility(View.VISIBLE);
            holder.Img1.setVisibility(View.VISIBLE);

//            if (listCount.equals(leadModel.getRownumber())){
//                holder.Img1.setVisibility(View.VISIBLE);
//                holder.Img1.setImageDrawable(mCtx.getResources().getDrawable(R.mipmap.plus_button));
//                System.out.println("ViewSource"+"0");
//            }
//            else {
//                holder.Img1.setVisibility(View.GONE);
//                System.out.println("ViewSource"+"1");
//            }
            holder.Img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (listCount.equals(leadModel.getRownumber())){
//                        Intent intent = new Intent(mCtx,LeadDetailsFromActivity.class);
//                        startActivity(intent);
//                    }
//                    else {
//                    }
                }
            });
            holder.amountId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        String pos = String.valueOf(position+1);
                        String pos1 = leadModel.getRownumber();
                        if (pos.equals(pos1)) {
                            arrlist7.add(Integer.valueOf(leadModel.getAmount()));
                            String amo = holder.amountId.getText().toString();
                            leadModel.setAmount(amo);
//                      leadViewAdapter = new LeadViewAdapter(getApplicationContext(), leadEditDetails,paymentSeduleListSends);
//                      recyclerLead.setAdapter(leadViewAdapter);
                        }
                        arrlist8.add(Integer.valueOf(holder.amountId.getText().toString()));

                        ArrayList<Integer> amountCount = arrlist7;
                        ArrayList<Integer> amountCount1 = arrlist8;
                        ArrayList<Integer> amountCount2 = arrlist5;

                        int sum=0;
                        int sum1=0;
                        int sum2=0;
                        int a=0;
                        for(int i=0;i<amountCount.toArray().length;i++){
                            sum=sum+Integer.valueOf(amountCount.get(i));
                        }
                        for(int i=0;i<amountCount1.toArray().length;i++){
                            sum1=sum1+Integer.valueOf(amountCount1.get(i));
                        }
                        if (Math.abs(sum)>sum1) {
                             a = sum-sum1;
                            System.out.println("ActionDone"+" "+a);
                            for(int i=0;i<amountCount2.toArray().length;i++){
                                sum2=sum2+Integer.valueOf(amountCount2.get(i));
                                aa = sum2 - a;
                                System.out.println("ActionDone"+" "+aa);
                                led_names3.setText("Total Course Frees"+" "+aa);

                            }
                        }
                        else {
                            int aa = sum1 - sum;
                            arrlist5.add(aa);
                            System.out.println("ActionDone1"+" "+sum +"  "+sum1+" "+aa);
                            countPrice();
                        }
                        hideSoftKeyboard = new HideSoftKeyboard(mCtx);
                        hideSoftKeyboard.hideSoftKeyboard((EditText) holder.amountId);
                        isCheck =true;
                        return true;
                    }
                    return false;
                }
            });
//            holder.amountId.addTextChangedListener(new TextWatcher() {
//
//                public void afterTextChanged(Editable s) {}
//
//                public void beforeTextChanged(CharSequence s, int start,
//                                              int count, int after) {
//                }
//
//                public void onTextChanged(CharSequence s, int start,
//                                          int before, int count) {
//
//
//                }
//            });
            holder.Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(LeadDetailsEdit_Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    if (isCheck==true) {
//                                    dateId.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        UpDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                        String pos = String.valueOf(position + 1);
                                        String pos1 = leadModel.getRownumber();
                                        if (pos.equals(pos1)) {
//                                        System.out.println("Integer Number Added in ArrayList="+arrlist3);
                                            //arrlist3.set(3,UpDate);
//                                          String amo = holder.amountId.getText().toString();
//                                          System.out.println("HoderAmo "+amo);
                                            leadModel.setScheduleDate(UpDate);
//                                          leadModel.setAmount(amo);
                                            holder.dateId.setText(UpDate);
//                                          arrlist3.clear();
                                            arrlist3.add(leadModel.getId());
                                            arrlist3.add(leadModel.getInstallmentType());
                                            arrlist3.add(leadModel.getAmount());
                                            arrlist3.add(leadModel.getScheduleDate());
                                            arrlist3.add(editLeadId);
                                            arrlist3.add(courseId + "/");
                                            leadViewAdapter = new LeadViewAdapter(getApplicationContext(), leadEditDetails, paymentSeduleListSends);
                                            recyclerLead.setAdapter(leadViewAdapter);
                                        }
//                                    leadEditDetails.add(
//                                            new LeadEditDetails(
//                                                    leadModel.getId(),leadModel.getLeadId(),leadModel.getCourseId(),leadModel.getInstallmentType(),leadModel.getAmount(),leadModel.getScheduleDate(),leadModel.getStatusUs(),leadModel.getCreatedAt(),leadModel.getUpdatedAt(),leadModel.getCount(),leadModel.getRownumber()));
//                                    arrlist.addAll(leadEditDetails);
//                                    paymentSeduleListSends.add(
//                                            new PaymentSeduleListSend(
//                                                    leadModel.getId(),leadModel.getInstallmentType(),leadModel.getAmount(),leadModel.getScheduleDate()));
//                                    arrlist2.addAll(paymentSeduleListSends);
                                        isCheck =false;
                                    }else {
                                        Toast.makeText(mCtx,"Enter Amount Click Done",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, year, month, day);
//                  picker.create();
                    picker.show();
                    picker.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//
                }
            });
            holder.Img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pos = String.valueOf(position+1);
                    String pos1 = leadModel.getRownumber();
                    String id = leadModel.getId();
                    String dates = holder.dateId.getText().toString();
                    System.out.println("ShowViewList"+" "+pos+" "+pos1+" "+id+" "+position);
                    arrlist3.clear();
                    arrlist5.clear();
                    removeAt(position,dates);
                    String ids = leadModel.getId();
                    delete_manual_payment_row(ids);
                    System.out.println("delete_manual_payment_row"+ids);
                    if (arrlist5.size()==0){
                        recyclerLead.setVisibility(View.GONE);
                    }
//                    if (pos.equals(pos1)){
//                        for(int i = 0 ; i < leadEditDetails.size() ; i++){
//                            if(id.equalsIgnoreCase(leadEditDetails.get(i).id)){
//                                leadEditDetails.remove(i);
//                                notifyItemRemoved(position);
//                                notifyItemChanged(position);
//
////                                holder.rvList.setVisibility(View.GONE);
//                            }
//                        }
//                        for(int j = 0 ; j < paymentSeduleListSends.size() ; j++){
//                            if(id.equalsIgnoreCase(paymentSeduleListSends.get(j).id)){
//                                paymentSeduleListSends.remove(j);
//                            }
//                        }
//                    }
//
//                    System.out.println("Integer Number Added in ArrayList= " + arrlist3);
//                    System.out.println("ShowViewList"+" "+ids+" "+installmenttype+" "+amount+" "+scheduledate);

                }
            });
        }
        @Override
        public int getItemCount() {
            return leadEditDetails.size();
        }
        public void removeAt(int position,String dates){
            leadEditDetails.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, leadEditDetails.size());
            for (int i = 0; i<leadEditDetails.size(); i++){
                String ids = leadEditDetails.get(i).getId();
                String installmenttype = leadEditDetails.get(i).getInstallmentType();
                String amount = leadEditDetails.get(i).getAmount();
                String scheduledate = leadEditDetails.get(i).getScheduleDate();
                String leadId = leadEditDetails.get(i).getLeadId();
                String courseId = leadEditDetails.get(i).getCourseId();
                System.out.println("LoopS"+" "+ids+" "+installmenttype+" "+amount+" "+scheduledate+" "+leadId+" "+courseId);
                arrlist3.add(ids);
                arrlist3.add(installmenttype);
                arrlist3.add(amount);
                arrlist3.add(scheduledate);
                arrlist3.add(editLeadId);
                arrlist3.add(courseId+"/");
//              arrlist3.add("/");
                arrlist5.add(Integer.valueOf(amount));
                System.out.println("ShowViewList"+" "+ids+" "+installmenttype+" "+amount+" "+scheduledate);
                leadViewAdapter = new LeadViewAdapter(getApplicationContext(), leadEditDetails,paymentSeduleListSends);
                recyclerLead.setAdapter(leadViewAdapter);
            }
                countPrice();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView bookingId, dateId,amountId;
            private ImageView Img,Img1;
            private RelativeLayout rvList;
            public ViewHolder(View view) {
                super(view);
                bookingId = (TextView) view.findViewById(R.id.bookingId);
                dateId = (TextView) view.findViewById(R.id.dateId);
                amountId = (TextView) view.findViewById(R.id.amountId);
                Img = (ImageView) view.findViewById(R.id.Img);
                Img1 = (ImageView) view.findViewById(R.id.Img1);
                rvList = (RelativeLayout) view.findViewById(R.id.rvList);
            }
        }
    }
    public void delete_manual_payment_row(String ids){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"delete_manual_payment_row",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("delete_manual_payment_row"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){

                            }else {
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
                params.put("temp_id",ids);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("delete_manual_payment_row "+ user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public class LeadViewAdapter1 extends RecyclerView.Adapter<LeadViewAdapter1.ViewHolder> {
        private Context mCtx;
        private List<LeadEditDetails1> leadEditDetails1;
        int listview;
        String listCount;
        String listCount1;
        String[] installment = { "3ard Installment", "5th Installment", "6th Installment"};
        DatePickerDialog picker;
        private  List<PaymentSeduleListSend> paymentSeduleListSends;

        public LeadViewAdapter1(Context mCtx, List<LeadEditDetails1> leadEditDetails1) {
            this.mCtx = mCtx;
            this.leadEditDetails1 = leadEditDetails1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_details_table, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            LeadEditDetails1 leadModel = leadEditDetails1.get(position);
            listview = position;
            System.out.println("LodingData"+" "+leadModel.getId());
            holder.Img1.setImageDrawable(mCtx.getResources().getDrawable(R.mipmap.minus));
            holder.bookingId.setText(leadModel.getInstallmentType());
            holder.dateId.setText(leadModel.getScheduleDate());
            holder.amountId.setText(leadModel.getAmount());
            String amount = leadModel.getAmount();
//            holder.dateId.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Calendar cldr = Calendar.getInstance();
//                    int day = cldr.get(Calendar.DAY_OF_MONTH);
//                    int month = cldr.get(Calendar.MONTH);
//                    int year = cldr.get(Calendar.YEAR);
//                    // date picker dialog
//                    DatePickerDialog picker = new DatePickerDialog(mCtx,
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                    holder.dateId.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                }
//                            }, year, month, day);
//                    picker.show();
//                    picker.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//
//
//                }
//
//            });
            holder.Img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrlist4.clear();
                    arrlist6.clear();
                    removeAt(position);

                }
            });
//            typeNameList = new ArrayList<>();
//            admissionFeesSelect(holder);
//            holder.collageList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    PaymentTypeName = holder.collageList.getSelectedItem().toString();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return leadEditDetails1.size();
        }
        public void removeAt(int position) {
            leadEditDetails1.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, leadEditDetails1.size());
            for (int i = 0; i<leadEditDetails1.size(); i++){
                System.out.println("LoopS"+i);
                String ids = leadEditDetails1.get(i).getId();
                String installmenttype = leadEditDetails1.get(i).getInstallmentType();
                String amount = leadEditDetails1.get(i).getAmount();
                String scheduledate = leadEditDetails1.get(i).getScheduleDate();
                arrlist4.add(ids);
                arrlist4.add(installmenttype);
                arrlist4.add(amount);
                arrlist4.add(scheduledate);
                arrlist4.add(editLeadId);
                arrlist4.add(courseId+"/");
//                arrlist4.add("/");
//                arrlist5.add(Integer.valueOf(amount));
                arrlist6.add(Integer.valueOf(amount));
                System.out.println("ShowViewList"+" "+ids+" "+installmenttype+" "+amount+" "+scheduledate);
            }
            countPrice();

        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView bookingId,amountId;
            private ImageView Img,Img1;
            private RelativeLayout rvList;
//            private Spinner collageList;
            private TextView dateId;
            public ViewHolder(View view) {
                super(view);
                bookingId = (TextView) view.findViewById(R.id.bookingId);
                dateId = (TextView) view.findViewById(R.id.dateId);
                amountId = (TextView) view.findViewById(R.id.amountId);
                Img = (ImageView) view.findViewById(R.id.Img);
                Img1 = (ImageView) view.findViewById(R.id.Img1);
                rvList = (RelativeLayout) view.findViewById(R.id.rvList);
//                collageList = (Spinner) view.findViewById(R.id.collageList);
            }
        }
    }

    public AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alertdiolog_msg, null);
        Spinner paymentList =(Spinner)dialogView.findViewById(R.id.paymentList);
        TextView dateId =(TextView)dialogView.findViewById(R.id.dateId);
        EditText amountId =(EditText)dialogView.findViewById(R.id.amountId);
        AppCompatButton send =(AppCompatButton)dialogView.findViewById(R.id.send);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        typeNameList = new ArrayList<>();
        dateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LeadDetailsEdit_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateId.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
//              picker.create();
                picker.show();
                picker.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//

            }

        });
        admissionFeesSelect(paymentList);
        paymentList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PaymentTypeName = paymentList.getSelectedItem().toString();
                frees_id = getFreesId(position);
                System.out.println("PaymentTypeName"+PaymentTypeName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String date = dateId.getText().toString().trim();
                String amount = amountId.getText().toString().trim();
                if (TextUtils.isEmpty(date)) {
                    dateId.setError("Enter Your Date");
                    dateId.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    amountId.setError("Enter Your Amount");
                    amountId.requestFocus();
                    return;
                }
                final int random = new Random().nextInt(61) + 20;
                String id = String.valueOf(random);
                leadEditDetails1.add(
                new LeadEditDetails1(
                        id,PaymentTypeName,amount,date
                     ));
                arrlist4.add("");
                arrlist4.add(PaymentTypeName);
                arrlist4.add(amount);
                arrlist4.add(date);
                arrlist4.add(editLeadId);
                arrlist4.add(courseId+"/");
//                arrlist4.add("/");922, Booking, 15000, 2023-4-22, 19835, 1/ 923, Admission2, 5000, 2023-04-19, 19835, 1/ 924, Admission2, 5000, 2023-04-19, 19835, 1/ 925, 3rd Installment, 50000, 2023-04-20, 19835, 1/ 926, 3rd Installment, 50000, 2023-04-20, 19835, 1/, 4th Installment, 5000, 2023-4-23, 19835, 1/
                arrlist6.add(Integer.valueOf(amount));
                recyclerLead1.setHasFixedSize(true);
                recyclerLead1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                leadViewAdapter1 = new LeadViewAdapter1(getApplicationContext(), leadEditDetails1);
                recyclerLead1.setAdapter(leadViewAdapter1);
                //
                arrlist5.clear();
                arrlist5.add(Integer.valueOf(aa));

                countPrice();
                dialog.dismiss();
            }
        });
        builder.setView(dialogView);
        dialog.show();
        return dialog;
    }
    public void admissionFeesSelect(Spinner paymentList){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"admission_fees_select",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("admission_fees_select"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                 feesType = obj.getJSONArray(Config.TAG_FEESTYPE);
//                                for (int i = 0; i<feesType.length(); i++){
//                                    JSONObject jsonObject = feesType.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String typeName = jsonObject.getString("type_name");
//                                    typeNameList.add(typeName);
//                                    System.out.println("admission_fees_select"+typeName);
//                                    paymentList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, typeNameList));
//                                    ArrayAdapter Spinstallment = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,typeNameList);
//                                    Spinstallment.setDropDownViewResource(R.layout.spinner_text_view1);
//                                    paymentList.setAdapter(Spinstallment);
//                                }
                                getFeesStage(feesType,paymentList);
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                     params.put("id",id);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("admission_fees_select"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getFeesStage(JSONArray k, Spinner paymentList){
        for(int K=0;K<k.length();K++){
            try {
                JSONObject json = k.getJSONObject(K);
                typeNameList.add(json.getString(Config.TAG_FEESTYPE_NAME));
                Log.e("typeNameList"," "+typeNameList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        paymentList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, typeNameList));
        ArrayAdapter Spinstallment = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,typeNameList);
        Spinstallment.setDropDownViewResource(R.layout.spinner_text_view1);paymentList.setAdapter(Spinstallment);
    }
    private String getFreesId(int position){
        try {
            JSONObject json = feesType.getJSONObject(position);
            frees_id = json.getString(Config.TAG_FEESTYPE_ID);
//         Toast.makeText(getApplicationContext(),frees_id, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return frees_id;
    }
    public void manual_payment_schedule(String detailstring){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"manual_payment_schedule",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("manual_payment_schedule1"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String statusReturn = obj.getString("status_return");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                Toast.makeText(getApplicationContext(),statusReturn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LeadTableActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                            }else {
                                Toast.makeText(getApplicationContext(),statusReturn,Toast.LENGTH_SHORT).show();
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
                params.put("detail_string",detailstring);
                System.out.println("manual_payment_schedule"+detailstring);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//              headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("manual_payment_schedule"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
     public void share_lead_info(){
            led_name1.setText("NA"+" "+"NA");
            led_names2.setText("NA");
            led_names4.setText("NA");
            UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"share_lead_info",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("share_lead_info"+response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("status");
                                String error = obj.getString("error");
                                if (status.equals("true")){
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
                                        String collegename = jsonObject.getString("collegename");
                                        led_name1.setText(fname+" "+lname);
                                        led_names2.setText(coursename);
                                        led_names4.setText(collegename);
                                    }
//                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();

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
        public void countPrice(){
            ArrayList<Integer> amountCount = arrlist5;
            ArrayList<Integer> amountCount1 = arrlist6;
            int sum=0;
            int sum1=0;
            for(int i=0;i<amountCount.toArray().length;i++){
                sum=sum+Integer.valueOf(amountCount.get(i));
            }
            for(int i=0;i<amountCount1.toArray().length;i++){
                sum1=sum1+Integer.valueOf(amountCount1.get(i));
            }
            int sums =sum+sum1;
            led_names3.setText("Total Course Frees"+" "+sums);
            System.out.println("Integer Number=" +"0"+ arrlist5+" "+arrlist6);
        }
        public void send_otp_to_manager_app(String editLeadId,String courseIds){
            CheckOtp checkOtp = new CheckOtp();
            checkOtp.setLead(editLeadId);
            checkOtp.setCourse(courseIds);
            System.out.println("DataIs"+checkOtp.getLead());
            sp = getSharedPreferences(Consts.LEADID, MODE_PRIVATE);
            SharedPreferences.Editor edit  = sp.edit();
            edit.putString("LEADID",editLeadId);
            edit.putString("COURSE",courseIds);
            edit.commit();
            UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"send_otp_to_manager_app",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("send_otp_to_manager_app"+response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("status");
                                String error = obj.getString("error");
                                String responce = obj.getString("responce");
                                if (status.equals("true")){
                                    Toast.makeText(getApplicationContext(),responce,Toast.LENGTH_SHORT).show();
                                    OtpBottomSheetDialog bottomSheet = new OtpBottomSheetDialog();
                                    bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                                    bottomSheet.setCancelable(false);
                                }else {
                                    Toast.makeText(getApplicationContext(),responce,Toast.LENGTH_SHORT).show();
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
                    params.put("lead",editLeadId);
                    params.put("course",courseIds);
                    params.put("user_id",user.getUser_id());
                    System.out.println("send_otp_to_manager_app"+params.toString());

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + user.getToken());
                    System.out.println("send_otp_to_manager_app"+user.getToken());
                    return headers;
                }
            };
            //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
            stringRequest.setShouldCache(false);
            volleySingleton.addToRequestQueue(stringRequest);
        }
        @Override
        public void onButtonClicked(String text) {
         System.out.println("GGGGGGGGG"+text);
         if (text.equals("Done")){
             manual_payment_schedule(amountValue33);
         }
        }
        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {
            super.onPointerCaptureChanged(hasCapture);
        }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(),LeadDetails.class);
//        startActivity(intent);
    }
}
