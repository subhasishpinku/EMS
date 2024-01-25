package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.pmit.ems.R;
import com.pmit.ems.api.Config;
import com.pmit.ems.api.DataPart;
import com.pmit.ems.api.VolleyMultipartRequest;
import com.pmit.ems.manager.FileUtil;
import com.pmit.ems.model.PaymentInfoSetget;
import com.pmit.ems.model.PaymentInfoSetget1;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SendPaymentInfo extends AppCompatActivity{
    RecyclerView recyclerLead,recyclerLead1;
    String editLeadId,collagesId,session_id;
    TextView led_tv,collageName_tv,courseName_tv,phone_tv,wphone_tv,email_tv;
    public PaymentList paymentList;
    public AddPaymentList addPaymentList;
    ArrayList<PaymentInfoSetget> arrayList = new ArrayList<PaymentInfoSetget>();
    List<PaymentInfoSetget> paymentInfoSetgetList;
    List<PaymentInfoSetget1> paymentInfoSetget1s;
    TextView add_row;
    int clickcount= -1;
    JSONArray bankslists;
    ArrayList<String> bankNames;
    Spinner bankNamss;
    String bank_ids,bank_name;
    ArrayList<String> booking = new ArrayList<String>();

    ArrayList<String> bookingAmount = new ArrayList<String>();
    ArrayList<String> paymentmethod = new ArrayList<String>();
    ArrayList<String> amouts = new ArrayList<String>();
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<String> banksData = new ArrayList<String>();
    ArrayList<String> banksDataId = new ArrayList<String>();

    ArrayList<String> trangactionIdss = new ArrayList<String>();
    ArrayList<String> accountholder = new ArrayList<String>();
    ArrayList<String> ddcheckno = new ArrayList<String>();
    ArrayList<String> bankname = new ArrayList<String>();
    ArrayList<String> branchname = new ArrayList<String>();

    ArrayList<String> temp_fees_structure_id1 = new ArrayList<String>();
    ArrayList<String> student_fees_structure_id1 = new ArrayList<String>();
    ArrayList<String> installment_type1 = new ArrayList<String>();
    ArrayList<String> checkDates = new ArrayList<String>();


    TextView trangactionId,Pamout,Pdates;
    RadioGroup radioGroup;
    RadioButton cashId,Chequeid,OnlineId;
    DatePickerDialog picker;
    String payCheck= "";
    LinearLayout tableRow4,tableRow44;
    EditText accHolderName,checkDDNo,SenderBankName,branch;
    MaterialButton send;
    String lead_id,temp_fees_structure_id,student_fees_structure_id,installment_type,tcol;
    boolean isAdd= false;
    boolean isCheck = false;
    TextView checkDate;
    Double calculerAmount= 0.0;
    int PamoutsInt = 0;
    String Pamouts;
    String student_amount = "";
    String insType = "";
    ArrayList<Double> arrlist = new ArrayList<Double>();
    TextView led_tv3;
    RelativeLayout rv;
    TextView select_file;
    private static final int Image_Capture_Code = 2;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];
    private static final int STORAGE_PERMISSION_CODE = 123;
    private File actualImage;
    private Bitmap bitmap;
    String file_extn = "pmit";
    String url;
    ArrayList<File> imageLink = new ArrayList<File>();

    private RequestQueue rQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_payment_info);
        recyclerLead = (RecyclerView)findViewById(R.id.recyclerLead);
        recyclerLead1 = (RecyclerView)findViewById(R.id.recyclerLead1);
        led_tv = (TextView)findViewById(R.id.led_tv);
        collageName_tv = (TextView)findViewById(R.id.collageName_tv);
        courseName_tv = (TextView)findViewById(R.id.courseName_tv);
        phone_tv = (TextView)findViewById(R.id.phone_tv);
        wphone_tv = (TextView)findViewById(R.id.wphone_tv);
        email_tv = (TextView)findViewById(R.id.email_tv);
        add_row = (TextView)findViewById(R.id.add_row);
        trangactionId = (TextView)findViewById(R.id.trangactionId);
        Pamout = (TextView)findViewById(R.id.Pamout);
        Pdates = (TextView)findViewById(R.id.Pdates);
        cashId = (RadioButton)findViewById(R.id.cashId);
        Chequeid = (RadioButton)findViewById(R.id.Chequeid);
        OnlineId = (RadioButton)findViewById(R.id.OnlineId);
        bankNamss = (Spinner)findViewById(R.id.bankNamss);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        cashId = (RadioButton)findViewById(R.id.cashId);
        Chequeid = (RadioButton)findViewById(R.id.Chequeid);
        OnlineId = (RadioButton)findViewById(R.id.OnlineId);
        tableRow4 = (LinearLayout)findViewById(R.id.tableRow4);
        tableRow44 = (LinearLayout)findViewById(R.id.tableRow44);
        accHolderName = (EditText)findViewById(R.id.accHolderName);
        checkDDNo = (EditText)findViewById(R.id.checkDDNo);
        SenderBankName = (EditText)findViewById(R.id.SenderBankName);
        send = (MaterialButton)findViewById(R.id.send);
        branch = (EditText)findViewById(R.id.branch);
        checkDate = (TextView)findViewById(R.id.checkDate);
        led_tv3 = (TextView)findViewById(R.id.led_tv3);
        rv = (RelativeLayout) findViewById(R.id.rv);
        select_file = (TextView)findViewById(R.id.select_file);
        tableRow44.setVisibility(View.GONE);
        tableRow4.setVisibility(View.GONE);
        accHolderName.setVisibility(View.GONE);
        checkDDNo.setVisibility(View.GONE);
        SenderBankName.setVisibility(View.GONE);
        branch.setVisibility(View.GONE);
        checkDate.setVisibility(View.GONE);
        trangactionId.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            editLeadId = extras.getString("editLeadId");
            collagesId  = extras.getString("collagesId");
            session_id = extras.getString("session_id");
        }
        System.out.println("payment_acknowledgement_save_app"+editLeadId);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(SendPaymentInfo.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
        } else {
        }
        requestStoragePermission();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        Pdates.setText(currentDateandTime);
        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FllowupLeadList.class);
                startActivity(intent);
            }
        });
        Pdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SendPaymentInfo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                Pdates.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                Pdates.setText(dateString);
                            }
                        }, year, month, day);
                picker.show();
            }

        });
        checkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SendPaymentInfo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                checkDate.setText(dateString);
//                                checkDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }

        });
        arrayList =new ArrayList<>();
        paymentInfoSetgetList = new ArrayList<>();
        paymentInfoSetget1s = new ArrayList<>();
        bankNames = new ArrayList<>();

        booking = new ArrayList<>();
        paymentmethod = new ArrayList<>();
        bookingAmount = new ArrayList<>();
        amouts = new ArrayList<>();
        dates = new ArrayList<>();
        banksData = new ArrayList<>();
        banksDataId = new ArrayList<>();

        trangactionIdss  = new ArrayList<>();
        accountholder = new ArrayList<>();
        ddcheckno = new ArrayList<>();
        bankname = new ArrayList<>();
        branchname = new ArrayList<>();
        arrlist  = new ArrayList<>();
        installment_type1 = new ArrayList<>();
        temp_fees_structure_id1 = new ArrayList<>();
        student_fees_structure_id1 = new ArrayList<>();
        checkDates = new ArrayList<>();
        imageLink = new ArrayList<>();
        recyclerLead.setHasFixedSize(true);
        recyclerLead.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerLead1.setHasFixedSize(true);
        recyclerLead1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        share_payment_info_app(editLeadId);
        banks_list();
        pending_followup();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.cashId){
                    payCheck = "Cash";
                    trangactionId.setVisibility(View.GONE);
                    tableRow44.setVisibility(View.GONE);
                    tableRow4.setVisibility(View.GONE);
                    accHolderName.setVisibility(View.GONE);
                    checkDDNo.setVisibility(View.GONE);
                    SenderBankName.setVisibility(View.GONE);
                    branch.setVisibility(View.GONE);
                    checkDate.setVisibility(View.GONE);
                }
                else if (checkedId== R.id.Chequeid){
                    payCheck = "Cheque/DD";
                    trangactionId.setVisibility(View.GONE);
                    tableRow4.setVisibility(View.VISIBLE);
                    tableRow44.setVisibility(View.VISIBLE);
                    accHolderName.setVisibility(View.VISIBLE);
                    checkDDNo.setVisibility(View.VISIBLE);
                    SenderBankName.setVisibility(View.VISIBLE);
                    branch.setVisibility(View.VISIBLE);
                    checkDate.setVisibility(View.VISIBLE);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());
                    checkDate.setText(currentDateandTime);
                }
                else if (checkedId== R.id.OnlineId){
                    payCheck = "UPI/NEFT/ Online/ RTGS";
                    trangactionId.setVisibility(View.VISIBLE);
                    tableRow4.setVisibility(View.GONE);
                    tableRow44.setVisibility(View.GONE);
                    accHolderName.setVisibility(View.GONE);
                    checkDDNo.setVisibility(View.GONE);
                    SenderBankName.setVisibility(View.GONE);
                    branch.setVisibility(View.GONE);
                    checkDate.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Selected",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
        add_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck == true){
                    if (cashId.isChecked() || Chequeid.isChecked() || OnlineId.isChecked()) {
                        String trangactionIds = trangactionId.getText().toString();
                        Pamouts = Pamout.getText().toString();
                        String Pdatess = Pdates.getText().toString();
                        String accHolderNames = accHolderName.getText().toString();
                        String checkDDNos = checkDDNo.getText().toString();
                        String SenderBankNames = SenderBankName.getText().toString();
                        String branchs = branch.getText().toString();
                        String ScheckDate = checkDate.getText().toString();
                        if (TextUtils.isEmpty(Pamouts)) {
                            Pamout.setError("Enter Amount");
                            Pamout.requestFocus();
                            return;
                        }
//                        if (calculerAmount > PamoutsInt){
//                            Toast.makeText(getApplicationContext(),"Amout Range Right",Toast.LENGTH_SHORT).show();
//                            return;
//                        }else {Pamouts = Pamout.getText().toString();}
                        if(payCheck.equals("UPI/NEFT/ Online/ RTGS")){
                            if (TextUtils.isEmpty(trangactionIds)) {
                                trangactionId.setError("Enter Payment Type");
                                trangactionId.requestFocus();
                                return;
                            }
                            accHolderNames = "NA";
                            checkDDNos = "NA";
                            SenderBankNames = "NA";
                            branchs = "NA";
                            ScheckDate = "NA";
                        }else if (payCheck.equals("Cash")){
                            trangactionIds = "NA";
                            accHolderNames = "NA";
                            checkDDNos = "NA";
                            SenderBankNames = "NA";
                            branchs = "NA";
                            ScheckDate = "NA";
                        }else if (payCheck.equals("Cheque/DD")){
                            if (TextUtils.isEmpty(accHolderNames)) {
                                accHolderName.setError("Enter Acc Holder Name");
                                accHolderName.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(checkDDNos)) {
                                checkDDNo.setError("Enter Check DD No");
                                checkDDNo.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(SenderBankNames)) {
                                SenderBankName.setError("Enter Bank Name");
                                SenderBankName.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(branchs)) {
                                branch.setError("Enter Branch");
                                branch.requestFocus();
                                return;
                            }
                            if (ScheckDate.equals("")){
                                Toast.makeText(getApplicationContext(),"Enter Check Date",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            trangactionIds = "NA";
                        }
                        else {
                            trangactionIds = "NA";
                            accHolderNames = "NA";
                            checkDDNos = "NA";
                            SenderBankNames = "NA";
                            branchs = "NA";
                            ScheckDate = "NA";
                        }
                        if (bankNamss.getSelectedItem().toString().equals("Select Bank Name")){
                            Toast.makeText(getApplicationContext(),"Select Bank Name",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String documents = String.valueOf(actualImage);
                        if (documents.equals("null")){
                            Toast.makeText(getApplicationContext(),"Upload documents",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        clickcount=clickcount+1;
                        paymentInfoSetget1s.add(
                                new PaymentInfoSetget1(
                                        clickcount,
                                        payCheck,
                                        "NA",
                                        Pamouts,
                                        Pdatess,
                                        bank_name,
                                        bank_ids,
                                        trangactionIds,
                                        accHolderNames,
                                        checkDDNos,
                                        SenderBankNames,
                                        branchs,
                                        ScheckDate,insType,tcol,temp_fees_structure_id,student_fees_structure_id,String.valueOf(actualImage)
                                ));
                        arrayList.addAll(paymentInfoSetgetList);
                        installment_type1.add(insType);
                        booking.add(insType);
                        paymentmethod.add(payCheck);
                        amouts.add(Pamouts);
                        dates.add(Pdatess);
                        banksData.add(bank_name);
                        banksDataId.add(bank_ids);
                        trangactionIdss.add(trangactionIds);
                        accountholder.add(accHolderNames);
                        ddcheckno.add(checkDDNos);
                        bankname.add(SenderBankNames);
                        branchname.add(branchs);
                        checkDates.add(ScheckDate);
//                        if(tcol.equals("temp")){
//                            temp_fees_structure_id1.add(temp_fees_structure_id);
//
//                        }else{
//                            student_fees_structure_id1.add("");
//                        }
//                        if(tcol.equals("student")){
//                            student_fees_structure_id1.add(student_fees_structure_id);
//                        }
//                        else{temp_fees_structure_id1.add("");
//                        }
                        System.out.println("payment_acknowledgement_save_app"+" "+temp_fees_structure_id+"  "+student_fees_structure_id);

                        //
                        trangactionId.setText("");
                        Pamout.setText("");
                        Pdates.setText("");
                        accHolderName.setText("");
                        checkDDNo.setText("");
                        SenderBankName.setText("");
                        branch.setText("");
                        checkDate.setText("");
                        cashId.setChecked(false);
                        Chequeid.setChecked(false);
                        OnlineId.setChecked(false);
                        isAdd = true;
                        isCheck = true;
                        arrlist.clear();
                        //
                        addPaymentList = new AddPaymentList(getApplicationContext(), paymentInfoSetget1s);
                        recyclerLead1.setAdapter(addPaymentList);
                        radioGroup.clearCheck();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select PaymentType", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Select Check Payent",Toast.LENGTH_SHORT).show();
                }

            }
        });
        bankNamss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank_ids = getBankIds(position);
                bank_name = bankNamss.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sbooking = String.valueOf(booking);
                String Sbooking1 = Sbooking.replace("[", "");
                String Sbooking2 = Sbooking1.replace("]", "");
                //
                String SbookingAmount = String.valueOf(bookingAmount);
                String SbookingAmount1 = SbookingAmount.replace("[", "");
                String SbookingAmount12 = SbookingAmount1.replace("]", "");
                //
                String Spaymentmethod = String.valueOf(paymentmethod);
                String Spaymentmethod1 = Spaymentmethod.replace("[", "");
                String Spaymentmethod12 = Spaymentmethod1.replace("]", "");
                //
                String Samouts = String.valueOf(amouts);
                String Samouts1 = Samouts.replace("[", "");
                String Samouts12 = Samouts1.replace("]", "");
                //
                String Sdates = String.valueOf(dates);
                String Sdates1 = Sdates.replace("[", "");
                String Sdates12 = Sdates1.replace("]", "");
                //
                String SbanksData = String.valueOf(banksData);
                String SbanksData1 = SbanksData.replace("[", "");
                String SbanksData12 = SbanksData1.replace("]", "");
                //
                String SbanksDataId = String.valueOf(banksDataId);
                String SbanksDataId1 = SbanksDataId.replace("[", "");
                String SbanksDataId12 = SbanksDataId1.replace("]", "");
                //
                String StrangactionIdss = String.valueOf(trangactionIdss);
                String StrangactionIdss1 = StrangactionIdss.replace("[", "");
                String StrangactionIdss12 = StrangactionIdss1.replace("]", "");
                //
                String Saccountholder = String.valueOf(accountholder);
                String Saccountholder1 = Saccountholder.replace("[", "");
                String Saccountholder12 = Saccountholder1.replace("]", "");
                //
                String Sddcheckno = String.valueOf(ddcheckno);
                String Sddcheckno1 = Sddcheckno.replace("[", "");
                String Sddcheckno12 = Sddcheckno1.replace("]", "");
                //
                String Sbankname = String.valueOf(bankname);
                String Sbankname1 = Sbankname.replace("[", "");
                String Sbankname12 = Sbankname1.replace("]", "");
                //
                String Sbranchname = String.valueOf(branchname);
                String Sbranchname1 = Sbranchname.replace("[", "");
                String Sbranchname12 = Sbranchname1.replace("]", "");
                //
                String Stemp_fees_structure_id1 = String.valueOf(temp_fees_structure_id1);
                String Stemp_fees_structure_id11 = Stemp_fees_structure_id1.replace("[", "");
                String Stemp_fees_structure_id112 = Stemp_fees_structure_id11.replace("]", "");
                //
                String Sstudent_fees_structure_id1 = String.valueOf(student_fees_structure_id1);
                String Sstudent_fees_structure_id11 = Sstudent_fees_structure_id1.replace("[", "");
                String Sstudent_fees_structure_id112 = Sstudent_fees_structure_id11.replace("]", "");
                //
                String Sinstallment_type1 = String.valueOf(installment_type1);
                String Sinstallment_type11 = Sinstallment_type1.replace("[", "");
                String Sinstallment_type112 = Sinstallment_type11.replace("]", "");
                //
                String chequedddate = String.valueOf(checkDates);
                String chequedddate1 = chequedddate.replace("[", "");
                String chequedddate12 = chequedddate1.replace("]", "");
                String chequedddate13 = chequedddate12.replace(", ", ",");

                //
                System.out.println("Sstudent_fees_structure_id1"+" "+Sstudent_fees_structure_id1);
                if (isAdd == true) {
                    if (isCheck == true) {
//                        isAdd = false;
                        ArrayList<Double> amountCount = arrlist;
                        System.out.println("calculerAmount" +arrlist);

                        Double sum=0.0;
                        for(int i=0;i<amountCount.toArray().length;i++){
                            sum=sum+amountCount.get(i);
                            System.out.println("calculerAmount" +calculerAmount+"   "+sum);
                        }
                        if (Math.abs(calculerAmount)==sum) {
                            if (tcol.equals("temp")){
//                                params.put("installment_type[]",Sinstallment_type112);
//                                params.put("selected_row_temp_fees[]",Stemp_fees_structure_id112); //temp_fees_structure_id
//                                params.put("payment_type[]","");//installment_type temp value
//                                params.put("student_fees_structure_id[]","");//student_amount
                                Sstudent_fees_structure_id112 = "";
                                saveData(lead_id, session_id, Sbooking2, SbookingAmount12, Spaymentmethod12, Samouts12, Sdates12, SbanksData12, SbanksDataId12, StrangactionIdss12,
                                        Saccountholder12, Sddcheckno12, Sbankname12, Sbranchname12, Stemp_fees_structure_id112,
                                        Sstudent_fees_structure_id112, Sinstallment_type112,chequedddate13);
                            }
                            if (tcol.equals("student")){
                                Stemp_fees_structure_id112 = "";
                                saveData(lead_id, session_id, Sbooking2, SbookingAmount12, Spaymentmethod12, Samouts12, Sdates12, SbanksData12, SbanksDataId12, StrangactionIdss12,
                                        Saccountholder12, Sddcheckno12, Sbankname12, Sbranchname12, Stemp_fees_structure_id112,
                                        Sstudent_fees_structure_id112, Sinstallment_type112,chequedddate13);
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Add Total Amount",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Select Check Payent",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Select ADD",Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void banks_list(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"banks_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("banks_list"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
//                                JSONArray jsonArray = obj.getJSONArray("banks_list");
//                                for (int i =0; i<jsonArray.length(); i++){
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String bank_name = jsonObject.getString("bank_name");
//                                    String account_no = jsonObject.getString("account_no");
//                                    String branch = jsonObject.getString("branch");
//                                    String ifsc = jsonObject.getString("ifsc");
//                                    String status1 = jsonObject.getString("status");
//                                    System.out.println("val_ids"+id);
//                                }
                                bankslists = obj.getJSONArray(Config.BANK_LIST);
                                getBank(bankslists);
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
//                params.put("lead_id",editLeadId);
//                System.out.println("share_payment_info_app"+params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("banks_list"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getBank(JSONArray i){
        for(int I=0;I<i.length();I++){
            try {
                JSONObject json = i.getJSONObject(I);
                bankNames.add(json.getString(Config.BANK_LIST_NAME));
                Log.e("subject"," "+bankNames);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        bankNamss.setAdapter(new ArrayAdapter<String>(SendPaymentInfo.this, android.R.layout.simple_spinner_dropdown_item, bankNames));
        ArrayAdapter leadStage = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,bankNames);
        leadStage.setDropDownViewResource(R.layout.spinner_text_view);
        bankNamss.setAdapter(leadStage);
    }
    private String getBankIds(int position){
        try {
            JSONObject json = bankslists.getJSONObject(position);
            bank_ids = json.getString(Config.BANK_LIST_ID);
//            Toast.makeText(getApplicationContext(),bank_ids, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bank_ids;
    }
    public void share_payment_info_app(String editLeadId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"share_payment_info_app",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("share_payment_info_app"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String name = obj.getString("name");
                            String mobileno = obj.getString("mobile_no");
                            String email = obj.getString("email");
                            String whatsapp_no = obj.getString("whatsapp_no");
                            String course_name = obj.getString("course_name");
                            int status = obj.getInt("status");
                            String msg = obj.getString("msg");
                            led_tv.setText(name);
                            courseName_tv.setText(course_name);
                            phone_tv.setText(mobileno);
                            wphone_tv.setText(whatsapp_no);
                            email_tv.setText(email);
                            if (status == 1){
                               JSONArray jsonArray = obj.getJSONArray("result");
                               for (int i =0; i<jsonArray.length(); i++){
                                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                                   String id = jsonObject.getString("id");
                                   String lead_id = jsonObject.getString("lead_id");
//                                   String course_id = jsonObject.getString("course_id");
                                   installment_type = jsonObject.getString("installment_type");
                                   String amount = jsonObject.getString("amount");
                                   String schedule_date = jsonObject.getString("schedule_date");
                                   String payment_date = jsonObject.getString("payment_date");
                                   String status1 = jsonObject.getString("status");
                                   String created_at = jsonObject.getString("created_at");
                                   String updated_at = jsonObject.getString("updated_at");
                                   String temp_fees_structure_id = jsonObject.getString("temp_fees_structure_id");
                                   String   student_fees_structure_id = jsonObject.getString("student_fees_structure_id");
                                   String approved_status = jsonObject.getString("approved_status");
                                   String tcol = jsonObject.getString("tcol");
                                   String checking_flag = jsonObject.getString("checking_flag");
                                   String rowmsg = jsonObject.getString("rowmsg");
                                   System.out.println("share_payment_info_app"+" "+installment_type+" "+amount+"  "+schedule_date);
//                                 temp_fees_structure_id1.add(temp_fees_structure_id);
//                                 student_fees_structure_id1.add(student_fees_structure_id);
                                   bookingAmount.add(amount);
                                   paymentInfoSetgetList.add(
                                           new PaymentInfoSetget(
                                                   id,
                                                   lead_id,
                                                   installment_type,
                                                   amount,
                                                   schedule_date,
                                                   payment_date,
                                                   status1,
                                                   created_at,updated_at,temp_fees_structure_id,student_fees_structure_id,approved_status,tcol,checking_flag,rowmsg
                                           ));
                                   arrayList.addAll(paymentInfoSetgetList);
                                   paymentList = new PaymentList(getApplicationContext(), paymentInfoSetgetList);
                                   recyclerLead.setAdapter(paymentList);
                               }
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
                params.put("lead_id",editLeadId);
                System.out.println("share_payment_info_app"+params.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("share_payment_info_app"+user.getToken());
                return headers;
            }
        };
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class PaymentList extends RecyclerView.Adapter<PaymentList.ViewHolder> {
        private Context mCtx;
        private List<PaymentInfoSetget> paymentInfoSetgetList;
        int listview;
        public PaymentList(Context mCtx, List<PaymentInfoSetget> paymentInfoSetgetList) {
            this.mCtx = mCtx;
            this.paymentInfoSetgetList = paymentInfoSetgetList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_info_list, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            PaymentInfoSetget leadModel = paymentInfoSetgetList.get(position);
            listview = position;
            holder.bookingId.setText(leadModel.getInstallment_type());
            holder.dateId.setText(leadModel.getSchedule_date());
            holder.amountId.setText(leadModel.getAmount());
            holder.checkBox2.setVisibility(View.GONE);
            holder.Img.setVisibility(View.GONE);
            holder.Img1.setVisibility(View.GONE);
//          if (leadModel.getChecking_flag().equals("1") && leadModel.getStatus1().equals("Pending") && leadModel.getApproved_status().equals("")){
            if (leadModel.getChecking_flag().equals("1")){
                holder.checkBox2.setVisibility(View.VISIBLE);
//                holder.checkBox2.setChecked(true);
            }else {
                holder.checkBox2.setVisibility(View.GONE);
//                holder.checkBox2.setChecked(false);
            }
            if (leadModel.getChecking_flag().equals("0") && leadModel.getStatus1().equals("Pending") && leadModel.getApproved_status().equals("Pending")){
                holder.Img.setVisibility(View.VISIBLE);
                holder.Img1.setVisibility(View.GONE);
            }else {
//                holder.Img.setVisibility(View.GONE);
//                holder.Img1.setVisibility(View.GONE);
            }
            if (leadModel.getChecking_flag().equals("0") && leadModel.getStatus1().equals("Done") && leadModel.getApproved_status().equals("Approved")){
                holder.Img.setVisibility(View.GONE);
                holder.Img1.setVisibility(View.VISIBLE);
            }else {
//                holder.Img.setVisibility(View.GONE);
//                holder.Img1.setVisibility(View.GONE);
            }
            holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)  {
                        lead_id = leadModel.getLead_id();
                        Pamout.setText(leadModel.getAmount());
//                        student_fees_structure_id1.add(leadModel.getAmount());
                        calculerAmount = Double.valueOf(leadModel.getAmount());
                        student_amount = leadModel.getAmount();
                        insType = leadModel.getInstallment_type();
                        tcol = leadModel.getTcol();
//                        temp_fees_structure_id =leadModel.getTemp_fees_structure_id();
                        temp_fees_structure_id =leadModel.getId();
                        student_fees_structure_id =leadModel.getStudent_fees_structure_id();
                        System.out.println("payment_acknowledgement_save_app"+" "+temp_fees_structure_id+"  "+student_fees_structure_id);
//                        installment_type1.add(insType);
//                        booking.add(insType);
                        if(tcol.equals("temp")){
//                            student_fees_structure_id1.clear();
//                            temp_fees_structure_id1.add(temp_fees_structure_id);
                              temp_fees_structure_id1.add(leadModel.getId());
                        }else{
//                            student_fees_structure_id1.add("");
                        }
                        if(tcol.equals("student")){
//                            temp_fees_structure_id1.clear();
//                            student_fees_structure_id1.add(student_fees_structure_id);
                              student_fees_structure_id1.add(leadModel.getId());
                        }
                        else{
//                            temp_fees_structure_id1.add("");
                        }
                        isCheck = true;
                    } else {
                        // Your code
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return paymentInfoSetgetList.size();
        }
        public void removeAt(int position) {
            paymentInfoSetgetList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, paymentInfoSetgetList.size());
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView bookingId,amountId,dateId;
            CheckBox checkBox2;
            ImageView Img,Img1;
            public ViewHolder(View view) {
                super(view);
                bookingId = (TextView) view.findViewById(R.id.bookingId);
                dateId = (TextView) view.findViewById(R.id.dateId);
                amountId = (TextView) view.findViewById(R.id.amountId);
                checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
                Img  = (ImageView) view.findViewById(R.id.Img);
                Img1  = (ImageView) view.findViewById(R.id.Img1);

            }
        }
    }
    public class AddPaymentList extends RecyclerView.Adapter<AddPaymentList.ViewHolder> {
        private Context mCtx;
        JSONArray bankslists1;
        ArrayList<String> bankNames1;
        String bank_ids1,bank_name1;
        String payCheck1 ="";
        String payCheck2 ="";
        String banksNames = "";
        private List<PaymentInfoSetget1> paymentInfoSetget1s;
        int listview;
        public AddPaymentList(Context mCtx, List<PaymentInfoSetget1> paymentInfoSetget1s) {
            this.mCtx = mCtx;
            this.paymentInfoSetget1s = paymentInfoSetget1s;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_payment_info_list, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            PaymentInfoSetget1 leadModel = paymentInfoSetget1s.get(position);
            listview = position;
            holder.trangactionId1.setText(leadModel.getTrngactionId());
            holder.amountId1.setText(leadModel.getAmount());
            holder.dateId1.setText(leadModel.getDate());
            payCheck2 = leadModel.getPaymentType();

            holder.accHolderName1.setText(leadModel.getAccHolderName());
            holder.checkDDNo1.setText(leadModel.getCheckDDNo());
            holder.SenderBankName1.setText(leadModel.getSenderBankName());
            holder.branch1.setText(leadModel.getBranch());
            holder.checkDate1.setText(leadModel.getCheckdates());
            imageLink.add(new File(leadModel.getImageLink()));
            System.out.println("Imagelink "+leadModel.getImageLink());
            holder.trangactionId1.setVisibility(View.GONE);
            holder.ltableRow4.setVisibility(View.GONE);
            holder.tableRows44.setVisibility(View.GONE);
            holder.accHolderName1.setVisibility(View.GONE);
            holder.checkDDNo1.setVisibility(View.GONE);
            holder.SenderBankName1.setVisibility(View.GONE);
            holder.branch1.setVisibility(View.GONE);
            holder.checkDate1.setVisibility(View.GONE);
            arrlist.add(Double.valueOf(leadModel.getAmount()));
            Glide.with(getApplicationContext())
                    .load(leadModel.getImageLink())
                    .into(holder.show_row);
            banksNames = leadModel.getBank();
            if (payCheck2.equals("Cash")){
                holder.trangactionId1.setVisibility(View.GONE);
                holder.ltableRow4.setVisibility(View.GONE);
                holder.tableRows44.setVisibility(View.GONE);
                holder.accHolderName1.setVisibility(View.GONE);
                holder.checkDDNo1.setVisibility(View.GONE);
                holder.SenderBankName1.setVisibility(View.GONE);
                holder.branch1.setVisibility(View.GONE);
                holder.checkDate1.setVisibility(View.GONE);
                holder.cashId1.setChecked(true);
            }else if (payCheck2.equals("Cheque/DD")){
                holder.trangactionId1.setVisibility(View.GONE);
                holder.ltableRow4.setVisibility(View.VISIBLE);
                holder.tableRows44.setVisibility(View.VISIBLE);
                holder.accHolderName1.setVisibility(View.VISIBLE);
                holder.checkDDNo1.setVisibility(View.VISIBLE);
                holder.SenderBankName1.setVisibility(View.VISIBLE);
                holder.branch1.setVisibility(View.VISIBLE);
                holder.checkDate1.setVisibility(View.VISIBLE);
                holder.Chequeid1.setChecked(true);

            }else if (payCheck2.equals("UPI/NEFT/ Online/ RTGS")){

                holder.trangactionId1.setVisibility(View.VISIBLE);
                holder.ltableRow4.setVisibility(View.GONE);
                holder.tableRows44.setVisibility(View.GONE);
                holder.accHolderName1.setVisibility(View.GONE);
                holder.checkDDNo1.setVisibility(View.GONE);
                holder.SenderBankName1.setVisibility(View.GONE);
                holder.branch1.setVisibility(View.GONE);
                holder.checkDate1.setVisibility(View.GONE);
                holder.OnlineId1.setChecked(true);
            }

//            holder.checkBox2.setVisibility(View.GONE);
//            if (leadModel.getApproved_status().equals("Approved")){
//                holder.checkBox2.setVisibility(View.VISIBLE);
//                holder.checkBox2.setChecked(true);
//            }else {
//                holder.checkBox2.setVisibility(View.GONE);
//                holder.checkBox2.setChecked(false);
//            }
            bankNames1 = new ArrayList<>();
            holder.delete_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentmethod.clear();
                    amouts.clear();
                    dates.clear();
                    banksData.clear();
                    banksDataId.clear();
                    trangactionIdss.clear();
                    accountholder.clear();
                    ddcheckno.clear();
                    bankname.clear();
                    branchname.clear();
                    checkDates.clear();
                    arrlist.clear();
                    installment_type1.clear();
                    booking.clear();
                    imageLink.clear();
//                    temp_fees_structure_id1.clear();
//                    student_fees_structure_id1.clear();
                    removeAt(position);
                }
            });
            banks_list1(holder,banksNames);
            holder.bank_list1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    bank_ids1 = getBankIds1(position);
                    bank_name1 = bankNamss.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            holder.bank_list1.setEnabled(false);
            holder.radioGroup1.setEnabled(false);
            holder.cashId1.setEnabled(false);
            holder.Chequeid1.setEnabled(false);
            holder.OnlineId1.setEnabled(false);
            holder.radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId== R.id.cashId1){
                        payCheck1 = "Cash";
                        holder.trangactionId1.setVisibility(View.GONE);
                        holder.ltableRow4.setVisibility(View.GONE);
                        holder.accHolderName1.setVisibility(View.GONE);
                        holder.checkDDNo1.setVisibility(View.GONE);
                        holder.SenderBankName1.setVisibility(View.GONE);
                        holder.branch1.setVisibility(View.GONE);
                        holder.checkDate1.setVisibility(View.GONE);
                    }
                    else if (checkedId== R.id.Chequeid1){
                        payCheck1 = "Cheque/DD";
                        holder.trangactionId1.setVisibility(View.GONE);
                        holder.ltableRow4.setVisibility(View.VISIBLE);
                        holder.accHolderName1.setVisibility(View.VISIBLE);
                        holder.checkDDNo1.setVisibility(View.VISIBLE);
                        holder.SenderBankName1.setVisibility(View.VISIBLE);
                        holder.branch1.setVisibility(View.VISIBLE);
                        holder.checkDate1.setVisibility(View.VISIBLE);

                    }
                    else if (checkedId== R.id.OnlineId1){
                        payCheck1 = "UPI/NEFT/ Online/ RTGS";

                        holder.trangactionId1.setVisibility(View.VISIBLE);
                        holder.ltableRow4.setVisibility(View.GONE);
                        holder.accHolderName1.setVisibility(View.GONE);
                        holder.checkDDNo1.setVisibility(View.GONE);
                        holder.SenderBankName1.setVisibility(View.GONE);
                        holder.branch1.setVisibility(View.GONE);
                        holder.checkDate1.setVisibility(View.GONE);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Selected",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        @Override
        public int getItemCount() {
            return paymentInfoSetget1s.size();
        }
        public void removeAt(int position) {
            paymentInfoSetget1s.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, paymentInfoSetget1s.size());
            for (int i = 0; i<paymentInfoSetget1s.size(); i++){
                System.out.println("LoopS"+i);
                int ids = paymentInfoSetget1s.get(i).getId();
                String paymentType = paymentInfoSetget1s.get(i).getPaymentType();
                String booking1 = paymentInfoSetget1s.get(i).getBooking();
                String amount = paymentInfoSetget1s.get(i).getAmount();
                String date = paymentInfoSetget1s.get(i).getDate();
                String bank = paymentInfoSetget1s.get(i).getBank();
                String bankidss = paymentInfoSetget1s.get(i).getBankIds();

                String trngactionId = paymentInfoSetget1s.get(i).getTrngactionId();
                String accHolderName = paymentInfoSetget1s.get(i).getAccHolderName();
                String checkDDNo = paymentInfoSetget1s.get(i).getCheckDDNo();
                String SenderBankName = paymentInfoSetget1s.get(i).getSenderBankName();
                String branch = paymentInfoSetget1s.get(i).getBranch();
                String checkdatess = paymentInfoSetget1s.get(i).getCheckdates();
                String bookingTypes = paymentInfoSetget1s.get(i).getInstallType();
                String tcols = paymentInfoSetget1s.get(i).getTcol();
                String temp_fees_structure_ids = paymentInfoSetget1s.get(i).getTemp_fees_structure_id();
                String student_fees_structure_id1s = paymentInfoSetget1s.get(i).getStudent_fees_structure_id();
                String imglineline = paymentInfoSetget1s.get(i).getImageLink();
                paymentmethod.add(paymentType);
                amouts.add(amount);
                dates.add(date);
                banksData.add(bank);
                banksDataId.add(bankidss);
                trangactionIdss.add(trngactionId);
                accountholder.add(accHolderName);
                ddcheckno.add(checkDDNo);
                bankname.add(SenderBankName);
                branchname.add(branch);
                checkDates.add(checkdatess);
                arrlist.add(Double.valueOf(amount));
                installment_type1.add(bookingTypes);
                booking.add(bookingTypes);
                imageLink.add(new File(imglineline));
//                if(tcols.equals("temp")){
//                    temp_fees_structure_id1.add(temp_fees_structure_ids);
//                }else{
//                    student_fees_structure_id1.add("");
//                }
//                if(tcols.equals("student")){
//                    student_fees_structure_id1.add(student_fees_structure_id1s);
//                }
//                else{temp_fees_structure_id1.add("");
//                }

            }
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView trangactionId1,amountId1,dateId1,accHolderName1,checkDDNo1,SenderBankName1,branch1,checkDate1;
            Spinner bank_list1;
            ImageView delete_row,show_row;
            LinearLayout ltableRow4,tableRows44;
            RadioGroup radioGroup1;
            RadioButton cashId1,Chequeid1,OnlineId1;
            public ViewHolder(View view) {
                super(view);
                trangactionId1 = (TextView) view.findViewById(R.id.trangactionId1);
                dateId1 = (TextView) view.findViewById(R.id.dateId1);
                amountId1 = (TextView) view.findViewById(R.id.amountId1);
                bank_list1 = (Spinner) view.findViewById(R.id.bank_list);
                delete_row = (ImageView) view.findViewById(R.id.delete_row);
                show_row = (ImageView) view.findViewById(R.id.show_row);
                ltableRow4 = (LinearLayout) view.findViewById(R.id.ltableRow4);
                tableRows44  = (LinearLayout) view.findViewById(R.id.tableRows44);
                accHolderName1 = (TextView) view.findViewById(R.id.accHolderName1);
                checkDDNo1 = (TextView) view.findViewById(R.id.checkDDNo1);
                SenderBankName1 = (TextView) view.findViewById(R.id.SenderBankName1);
                branch1 = (TextView) view.findViewById(R.id.branch1);
                checkDate1 = (TextView) view.findViewById(R.id.checkDate1);
                radioGroup1 = (RadioGroup) view.findViewById(R.id.radioGroup1);
                cashId1 = (RadioButton)view.findViewById(R.id.cashId1);
                Chequeid1 = (RadioButton)view.findViewById(R.id.Chequeid1);
                OnlineId1 = (RadioButton)view.findViewById(R.id.OnlineId1);
            }
        }
        public void banks_list1(ViewHolder holder,String banksNames){
            UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"banks_list",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("banks_list"+response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("status");
                                if (status.equals("true")){
//                                JSONArray jsonArray = obj.getJSONArray("banks_list");
//                                for (int i =0; i<jsonArray.length(); i++){
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    String id = jsonObject.getString("id");
//                                    String bank_name = jsonObject.getString("bank_name");
//                                    String account_no = jsonObject.getString("account_no");
//                                    String branch = jsonObject.getString("branch");
//                                    String ifsc = jsonObject.getString("ifsc");
//                                    String status1 = jsonObject.getString("status");
//                                    System.out.println("val_ids"+id);
//                                }
                                    bankslists1 = obj.getJSONArray(Config.BANK_LIST);
                                    getBank1(bankslists1,holder,banksNames);
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
//                params.put("lead_id",editLeadId);
//                System.out.println("share_payment_info_app"+params.toString());
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + user.getToken());
                    System.out.println("banks_list"+user.getToken());
                    return headers;
                }
            };
          //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
            stringRequest.setShouldCache(false);
            volleySingleton.addToRequestQueue(stringRequest);
        }
        private void getBank1(JSONArray k, ViewHolder holder,String banksNames){
            for(int K=0;K<k.length();K++){
                try {
                    JSONObject json = k.getJSONObject(K);
                    bankNames1.add(json.getString(Config.BANK_LIST_NAME));
                    Log.e("subject1"," "+bankNames1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            holder.bank_list1.setAdapter(new ArrayAdapter<String>(SendPaymentInfo.this, android.R.layout.simple_spinner_dropdown_item, bankNames1));
            ArrayAdapter leadStage = new ArrayAdapter(mCtx,android.R.layout.simple_spinner_dropdown_item,bankNames1);
            leadStage.setDropDownViewResource(R.layout.spinner_text_view);
            holder.bank_list1.setAdapter(leadStage);

            String compareValue = banksNames;
            if (compareValue != null) {
                int spinnerPosition = leadStage.getPosition(compareValue);
                holder.bank_list1.setSelection(spinnerPosition);
            }
        }
        private String getBankIds1(int position){
            try {
                JSONObject json = bankslists1.getJSONObject(position);
                bank_ids1 = json.getString(Config.BANK_LIST_ID);
//              Toast.makeText(getApplicationContext(),bank_ids1, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return bank_ids1;
        }
      }
      public void saveData(String lead_id,String session_id,String Sbooking2,String SbookingAmount12, String Spaymentmethod12,String Samouts12, String Sdates12,
                           String SbanksData12, String SbanksDataId12,String StrangactionIdss12,
                           String Saccountholder12, String Sddcheckno12,String Sbankname12, String Sbranchname12,String Stemp_fees_structure_id112,
                           String Sstudent_fees_structure_id112, String Sinstallment_type112,String chequedddate13){
          UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();


//          StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"payment_acknowledgement_save_app",
//                  new Response.Listener<String>() {
//                      @Override
//                      public void onResponse(String response) {
//                          System.out.println("payment_acknowledgement_save_app"+response);
//                          try {
//                              JSONObject obj = new JSONObject(response);
//                              String status = obj.getString("status");
//                              String msg = obj.getString("msg");
////                            String count = obj.getString("count");
//                              if (status.equals("success")){
//                                  Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
//                                  startActivity(intent);
//                                  Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
//                              }else {
//                                  Toast.makeText(getApplicationContext(),"Empty Admission List",Toast.LENGTH_SHORT).show();
//                              }
//                          } catch (JSONException e) {
//                              e.printStackTrace();
//                          }
//                      }
//                  },
//                  new Response.ErrorListener() {
//                      @Override
//                      public void onErrorResponse(VolleyError error) {
//                      }
//                  }
//          ) {
//              @Override
//              protected Map<String, String> getParams() throws AuthFailureError {
//                  Map<String, String> params = new HashMap<>();
//                  params.put("paymentdoc[]", String.valueOf(imageLink));///lead_id
//                  params.put("lead_id_from_payment",lead_id);///lead_id
//                  params.put("student_fees[]",Sstudent_fees_structure_id112);//student_fees_structure_id
//                  if (tcol.equals("temp")){
//                      params.put("installment_type[]",Sinstallment_type112);
//                      params.put("payment_type[]","");//installment_type temp value
//                      params.put("selected_row_temp_fees[]",Stemp_fees_structure_id112); //temp_fees_structure_id
//                      params.put("student_fees_structure_id[]","");//student_amount
//                  }
//                  if (tcol.equals("student")){
//                      params.put("payment_type[]",Sinstallment_type112);//installment_type
//                      params.put("installment_type[]","");
//                      params.put("student_fees_structure_id[]",Sstudent_fees_structure_id112);//student_amount
//                      params.put("selected_row_temp_fees[]",""); //temp_fees_structure_id
//                  }
//                      params.put("paymentmethod[]",Spaymentmethod12);
//                      params.put("sessions",session_id);
//                      params.put("payment_amount[]",Samouts12);
//                      params.put("payment_date[]",Sdates12);
//                      params.put("chequeaccountholdername[]",Saccountholder12);
//                      params.put("chequeddno[]",Sddcheckno12);
//                      params.put("bank_name[]",Sbankname12);
//                      params.put("bank_branch[]",Sbranchname12);
//                      params.put("chequedddate[]",chequedddate13);
//                      params.put("transaction_id[]",StrangactionIdss12);
//    //                params.put("selected_row_temp_fees[]","");
//    //                params.put("student_fees[]","");
//                      params.put("account_to[]",SbanksDataId12);
//                      params.put("college_consellor","");
//                      params.put("tcol",tcol);
//                      System.out.println("payment_acknowledgement_save_app"+" "+params.toString());
//                      return params;
//              }
//              @Override
//              public Map<String, String> getHeaders() throws AuthFailureError {
//                  HashMap<String, String> headers = new HashMap<String, String>();
////                headers.put("Content-Type", "application/json");
//                  headers.put("Authorization", "Bearer " + user.getToken());
//                  System.out.println("payment_acknowledgement_save_app"+user.getToken());
//                  return headers;
//              }
//
//          };
//          //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//          stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//          VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
//          stringRequest.setShouldCache(false);
//          volleySingleton.addToRequestQueue(stringRequest);
          /////
       //   InputStream iStream = null;
         // try {

//              iStream = getContentResolver().openInputStream(pdffile);
//              final byte[] inputData = getBytes(iStream);
//              final ProgressDialog pDialog = ProgressDialog.show(MainActivity.this,"","Loading...",false);

//              VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, BASEURL2+"payment_acknowledgement_save_app",
//                      new Response.Listener<NetworkResponse>() {
//                          @Override
//                          public void onResponse(NetworkResponse response) {
//                              System.out.println("payment_acknowledgement_save_app"+response.data);
//                              try {
////                                  JSONObject obj = new JSONObject(response);
//                                  JSONObject obj = new JSONObject(new String(response.data));
//                                  String status = obj.getString("status");
//                                  String msg = obj.getString("msg");
////                            String count = obj.getString("count");
//                                  if (status.equals("success")){
//                                      Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
//                                      startActivity(intent);
//                                      Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
//                                  }else {
//                                      Toast.makeText(getApplicationContext(),"Empty Admission List",Toast.LENGTH_SHORT).show();
//                                  }
//                              } catch (JSONException e) {
//                                  e.printStackTrace();
//                              }
//                          }
//                      },
//                      new Response.ErrorListener() {
//                          @Override
//                          public void onErrorResponse(VolleyError error) {
//                              Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                          }
//                      }) {
//
//                  /*
//                   * If you want to add more parameters with the image
//                   * you can do it here
//                   * here we have only one parameter with the image
//                   * which is tags
//                   * */
//                  @Override
//                  protected Map<String, String> getParams() throws AuthFailureError {
//                      Map<String, String> params = new HashMap<>();
//                     // params.put("paymentdoc[]", String.valueOf(imageLink));///lead_id
//                      params.put("lead_id_from_payment",lead_id);///lead_id
//                      params.put("student_fees[]",Sstudent_fees_structure_id112);//student_fees_structure_id
//                      if (tcol.equals("temp")){
//                          params.put("installment_type[]",Sinstallment_type112);
//                          params.put("payment_type[]","");//installment_type temp value
//                          params.put("selected_row_temp_fees[]",Stemp_fees_structure_id112); //temp_fees_structure_id
//                          params.put("student_fees_structure_id[]","");//student_amount
//                      }
//                      if (tcol.equals("student")){
//                          params.put("payment_type[]",Sinstallment_type112);//installment_type
//                          params.put("installment_type[]","");
//                          params.put("student_fees_structure_id[]",Sstudent_fees_structure_id112);//student_amount
//                          params.put("selected_row_temp_fees[]",""); //temp_fees_structure_id
//                      }
//                      params.put("paymentmethod[]",Spaymentmethod12);
//                      params.put("sessions",session_id);
//                      params.put("payment_amount[]",Samouts12);
//                      params.put("payment_date[]",Sdates12);
//                      params.put("chequeaccountholdername[]",Saccountholder12);
//                      params.put("chequeddno[]",Sddcheckno12);
//                      params.put("bank_name[]",Sbankname12);
//                      params.put("bank_branch[]",Sbranchname12);
//                      params.put("chequedddate[]",chequedddate13);
//                      params.put("transaction_id[]",StrangactionIdss12);
//                      //                params.put("selected_row_temp_fees[]","");
//                      //                params.put("student_fees[]","");
//                      params.put("account_to[]",SbanksDataId12);
//                      params.put("college_consellor","");
//                      params.put("tcol",tcol);
//                      System.out.println("payment_acknowledgement_save_app"+" "+params.toString());
//                      return params;
//                  }
//
//                  /*
//                   *pass files using below method
//                   * */
//                  @Override
//                  protected Map<String, DataPart> getByteData() {
//                      Map<String, DataPart> params = new HashMap<>();
//                      params.put("paymentdoc[]", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
//                      System.out.println("FileUpload"+params.toString());
//                      return params;
//                  }
//              };
//
//
//              volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
//                      0,
//                      DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//
//
//              );
//
//              volleyMultipartRequest.setRetryPolicy(new RetryPolicy() {
//                  @Override
//                  public int getCurrentTimeout() {
//                      return 50000;
//                  }
//
//                  @Override
//                  public int getCurrentRetryCount() {
//                      return 50000;
//                  }
//
//                  @Override
//                  public void retry(VolleyError error) throws VolleyError {
//
//                  }
//              });
//              rQueue = Volley.newRequestQueue(SendPaymentInfo.this);
//              rQueue.add(volleyMultipartRequest);


//
////          } catch (FileNotFoundException e) {
////              e.printStackTrace();
////          } catch (IOException e) {
////              e.printStackTrace();
////          }
          AndroidNetworking.upload(BASEURL2+"payment_acknowledgement_save_app")
                  .addMultipartFileList("paymentdoc[]",imageLink)
                  .addMultipartParameter("lead_id_from_payment",lead_id)
                  .addMultipartParameter("student_fees[]",Sstudent_fees_structure_id112)
                  .addMultipartParameter("paymentmethod[]",Spaymentmethod12)
                  .addMultipartParameter("sessions",session_id)
                  .addMultipartParameter("payment_amount[]",Samouts12)
                  .addMultipartParameter("payment_date[]",Sdates12)
                  .addMultipartParameter("chequeaccountholdername[]",Saccountholder12)
                  .addMultipartParameter("chequeddno[]",Sddcheckno12)
                  .addMultipartParameter("bank_name[]",Sbankname12)
                  .addMultipartParameter("bank_branch[]",Sbranchname12)
                  .addMultipartParameter("chequedddate[]",chequedddate13)
                  .addMultipartParameter("transaction_id[]",StrangactionIdss12)
                  .addMultipartParameter("account_to[]",SbanksDataId12)
                  .addMultipartParameter("college_consellor","")
                  .addMultipartParameter("tcol",tcol)
                  .addMultipartParameter("installment_type[]",Sinstallment_type112)
                  .addMultipartParameter("payment_type[]",Sinstallment_type112)
                  .addMultipartParameter("selected_row_temp_fees[]",Stemp_fees_structure_id112)
                  .addMultipartParameter("student_fees_structure_id[]",Sstudent_fees_structure_id112)
                  .addHeaders("Authorization", "Bearer " + user.getToken())
                  .setTag("uploadTest")
                  .setPriority(Priority.HIGH)
                  .build()
                  .setUploadProgressListener(new UploadProgressListener() {
                      @Override
                      public void onProgress(long bytesUploaded, long totalBytes) {
                          int percentUploaded = (int) ((bytesUploaded * 100) / totalBytes);
                          Log.e("rex", percentUploaded+"");
//                      progressBarUpload.setProgress(percentUploaded);
                          Log.e("rex", "onProgress: called..." );
                      }
                  }).getAsJSONObject(new JSONObjectRequestListener() {
                      @Override
                      public void onResponse(JSONObject response) {
                          Log.e("rex", "onResponse: "+response );
                          Toast.makeText(SendPaymentInfo.this, "Upload", Toast.LENGTH_SHORT).show();
                          SendPaymentInfo.this.setResult(RESULT_OK);
//                        finish(); {"status":1,"msg":"Form inserted successfully. Wait for admin approval","result":""}
                          String res = String.valueOf(response);
                          Log.e("rex",res);
                          try {
                              JSONObject obj =new JSONObject(res);
                              String status = obj.getString("status");
                              String msg = obj.getString("msg");
//                            String count = obj.getString("count");
                              if (status.equals("success")){
                                  Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                  startActivity(intent);
                                  Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                              }else {
                                  Toast.makeText(getApplicationContext(),"Empty Admission List",Toast.LENGTH_SHORT).show();
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();}}
                      @Override
                      public void onError(ANError error) {
                          Log.e("rex", "onError: "+error.getErrorDetail());
//                          loaderId.setVisibility(View.GONE);
                          Toast.makeText(SendPaymentInfo.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                          Log.e("resp", String.valueOf("error"));
                          if (error.getErrorCode() != 0) {
                              Log.e("onError errorCode : ", String.valueOf(error.getErrorCode()));
                              Log.e("onError errorBody : ", String.valueOf(error.getErrorBody()));
                              Log.e("onError errorDetail : ", String.valueOf(error.getErrorDetail()));
                              // get parsed error object (If ApiError is your class)
                          } else {
                              // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                              Log.e("onError errorDetail : ", String.valueOf(error.getErrorDetail()));
                          }
                      }
                  });
      }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
      }
    private void takePhotoFromCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Image_Capture_Code);
        } else {

        }
    }
    private void showFileChooser() {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission1();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }
    // checking storage permissions
    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    // Requesting  gallery permission
    private void requestStoragePermission1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(storagePermission, STORAGE_REQUEST);
        }
    }
    // checking camera permissions
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    // Requesting camera permission
    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(cameraPermission, CAMERA_REQUEST);
        }
    }
    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().start(SendPaymentInfo.this);
    }
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage = FileUtil.from(getApplicationContext(), data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage));
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                Log.e("file_extn", file_extn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                File finalFile = new File(getRealPathFromURII(tempUri));
                //Log.e("IMGPATH"," "+finalFile);
                url = String.valueOf(finalFile);
                actualImage = finalFile;
                Log.e("path", String.valueOf(actualImage));
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                Log.e("file_extn", file_extn);
                // resizeAndCompressImageBeforeSend(context,fileImage,fileImage);
            } else if (resultCode == RESULT_CANCELED) {

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    System.out.println("Crop_Image"+" "+resultUri);

                }
            }

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURII(Uri uri) {
        String path = "";
        if (getApplicationContext().getContentResolver() != null) {
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    //public String getRealPathFromURI(Uri contentUri) {
//        String[] proj 		= {MediaStore.Images.Media.DATA};
//        Cursor cursor 		= getActivity().managedQuery( contentUri, proj, null, null,null);
//        if (cursor == null) return null;
//        int column_index 	= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
    public String getPath(Uri uri) {
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//      cursor.moveToFirst();
//      String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//      cursor.close();
//      return path;
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        }
        return null;
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(SendPaymentInfo.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(SendPaymentInfo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SendPaymentInfo.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SendPaymentInfo.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }

    }
    public AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_diolog, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ((ImageView)dialogView.findViewById(R.id.openCamera)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(SendPaymentInfo.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(SendPaymentInfo.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
               showFileChooser();

                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //showImagePicDialog();
                dialog.dismiss();
               // isCrop = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }

}


