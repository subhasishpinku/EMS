package com.pmit.ems.ui;


import static com.pmit.ems.api.Config.BASEURL2;
import static com.pmit.ems.api.Config.IMAGEURL;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pmit.ems.R;
import com.pmit.ems.api.Config;
import com.pmit.ems.manager.FileUtil;
import com.pmit.ems.model.AcadamyList;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class ProfileRejection extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner,spinner_cat,spinner3,spinner4,spinner5;
    String[] Category = {"Category","General","OBC","ST","SC"};
    String[] Blood =  {"Select Blood Group","A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String[] gender = {"Gender","Male","Female","Other"};
    String[] Marital = {"Marital Status","Never Married","Married","Widow","Divorced or Separated"};
    String[] language = {"Select Language"};
    String[] Educactionstander = {"Select Exam","10th Standard","12th Standard","(UG) Under Graduate","(PG) Post Graduate"};

    ImageView camera,roundedImageView;
    private static final int Image_Capture_Code = 2;
    private static final int CAMERA = 1888;
    private static final int CAMERA1 = 1889;
    private static final int CAMERA2 = 1890;
    private static final int CAMERA3 = 1891;
    private static final int CAMERA4 = 1892;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE1 = 101;
    private static final int PICK_IMAGE2 = 102;
    private static final int PICK_IMAGE3 = 103;
    private static final int PICK_IMAGE4 = 104;

    private File actualImage;
    private Bitmap bitmap;
    private File actualImage1;
    private File actualImage2;
    private File actualImage3;
    private File actualImage4;
    private File actualImage5;
    private File actualImage6;
    File actualImage7;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;
    private Bitmap bitmap5;
    String file_extn = "pmit";
    String file_extn1 = "pmit";
    String file_extn2 = "pmit";
    String file_extn3 = "pmit";
    String file_extn4 = "pmit";
    String file_extn5 = "pmit";
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int STORAGE_PERMISSION_CODES = 123;
    final int PIC_CROP = 2;
    String url;
    String url1;
    String url2;
    String url3;
    TextView c_tv,c_tv1;
    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;
    MaterialButton back;
    LinearLayout personal_Id,acadamy_Id;
    TextView mobile_tv1,mobile_tv2,email_Id,courseName,select_file;
    TextInputEditText fatherId,mothersId,guardianId,PAddress_Id,stateId,DistrictId,cityId,
            pinId,lastschoolId,lastRgNo,persentaID,userName,nationalityID,dbId,fIncmome,PAddress_Id1,stateId1,DistrictId1,cityId1,pinId1;
    RadioGroup radioG,radioG1;
    RadioButton johnCena, randyOrton,fresherId,deputedId;
    CheckBox checkBox;
    DatePickerDialog picker;
    ImageView choosDoc,roundedImageView1;
    boolean isCrop;
    boolean isCrops;
    TextView select_cast_certificate;
    TextView cast_image_show;
    TextView select_file1;
    ImageView choosDoc1;
    ImageView roundedImageView11;
    CheckBox addressCheck;
    String str,str1;
    JSONArray subjectlLeads;
    ArrayList<String> subject;
    String editLeadId,collagesId;
    String subject_id,subject_name;
    String level = "";
    String application = "";
    TextView name,led_tv3;
    public static final String PHOTO_KEY = "photo";
    public static final String MARRIAGE_CERTIFICATE_KEY = "marriage_certificate";
    public static final String SIGNATUREPRESENT_KEY = "signaturePresent";
    public static final String SIGNATUREPRE_KEY = "signature";
    public static final String SECONDARY_ADMIT_KEY = "secondary_admit";
    public static final String UPLOAD_DOCUMET_KEY = "upload_documents[]";
    public static final String CAST_CERTIFICATE_KEY = "cast_certificate";
    public static final String CAST_CERTIFICATE_PRESENT_KEY = "castCertificatePresent";
    public static final String ULOAD_DOCUMENT_PRESENT_KEY = "uploadDocumentsPresent";
    public static final String SECONDARY_ADMIT_PRESENT_KEY = "secondaryAdmitPresent";

    String isPwd ="";
    String Married="";
    String fname = "NA";
    String mname = "NA";
    String lname = "NA";
    String mobile_no = "NA";
    String whatsapp_no ="NA";
    String email ="NA";
    String coursename = "NA";
    String collegename ="NA";
    String session_id = "NA";
    String source_id ="NA";
    String course_id = "NA";
    String flag;
    TextView select_acdamy,acadamy_image_show;
    Spinner acSp;
    TextInputEditText board,years,fullmarks,marksObtent,persentage;
    ImageView add_row;
    ArrayList<File> imageLink = new ArrayList<File>();
    ArrayList<String> educationStander = new ArrayList<String>();
    ArrayList<String> examBoard = new ArrayList<String>();
    ArrayList<String> examYears = new ArrayList<String>();
    ArrayList<String> examFullMarks = new ArrayList<String>();
    ArrayList<String> examMarksObtained = new ArrayList<String>();
    ArrayList<String> examPersentage = new ArrayList<String>();
    ArrayList<String> examAllDataAdd = new ArrayList<String>();
    ArrayList<String> uploadDocumentsPresent = new ArrayList<String>();
    //
    ArrayList<File> imageLink1 = new ArrayList<File>();
    ArrayList<String> educationStander1 = new ArrayList<String>();
    ArrayList<String> examBoard1 = new ArrayList<String>();
    ArrayList<String> examYears1 = new ArrayList<String>();
    ArrayList<String> examFullMarks1 = new ArrayList<String>();
    ArrayList<String> examMarksObtained1 = new ArrayList<String>();
    ArrayList<String> examPersentage1 = new ArrayList<String>();
    ArrayList<String> examAllDataAdd1 = new ArrayList<String>();
    ArrayList<String> uploadDocumentsPresent1 = new ArrayList<String>();

    //
    RecyclerView recyclerLead1;
    List<AcadamyList> acadamyLists;
    public LeadViewAdapter1 leadViewAdapter1;
    ArrayList<AcadamyList> AcadamyListArray = new ArrayList<AcadamyList>();
    TextView acdamy1,image_show1;
    String is_present_same ="";
    int clickcount= -1;
    String SeducationStander1111,SexamBoard1111,SexamYears1111,SexamFullMarks1111,SexamMarksObtained1111,
            SexamPersentage1111,SexamAllDataAdd1111,SuploadDocumentsPresent1111,SimageLink1111;
    String stueducation_standard;
    TextView sessionId;
    String ocid = "";
    String spdid ="";
    GifImageView loaderId;
    boolean isChek = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner_cat = (Spinner)findViewById(R.id.spinner_cat);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        spinner5 = (Spinner)findViewById(R.id.spinner5);
        camera = (ImageView)findViewById(R.id.camera);
        roundedImageView  = (ImageView)findViewById(R.id.roundedImageView);
        personal_Id = (LinearLayout)findViewById(R.id.personal_Id);
        acadamy_Id = (LinearLayout)findViewById(R.id.acadamy_Id);
        c_tv = (TextView) findViewById(R.id.c_tv);
        c_tv1 = (TextView) findViewById(R.id.c_tv1);
        back = (MaterialButton)findViewById(R.id.back);

        fatherId = (TextInputEditText)findViewById(R.id.fatherId);
        mothersId = (TextInputEditText)findViewById(R.id.mothersId);
        guardianId = (TextInputEditText)findViewById(R.id.guardianId);
        PAddress_Id = (TextInputEditText)findViewById(R.id.PAddress_Id);
        stateId = (TextInputEditText)findViewById(R.id.stateId);
        DistrictId  = (TextInputEditText)findViewById(R.id.DistrictId);
        cityId = (TextInputEditText)findViewById(R.id.cityId);
        pinId = (TextInputEditText)findViewById(R.id.pinId);
        lastschoolId = (TextInputEditText)findViewById(R.id.lastschoolId);
        lastRgNo = (TextInputEditText)findViewById(R.id.lastRgNo);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        persentaID = (TextInputEditText)findViewById(R.id.persentaID);
        userName = (TextInputEditText)findViewById(R.id.userName);
        nationalityID = (TextInputEditText)findViewById(R.id.nationalityID);
        dbId = (TextInputEditText)findViewById(R.id.dbId);
        fIncmome = (TextInputEditText)findViewById(R.id.fIncmome);
        PAddress_Id1 = (TextInputEditText)findViewById(R.id.PAddress_Id1);
        stateId1 = (TextInputEditText)findViewById(R.id.stateId1);
        DistrictId1 = (TextInputEditText)findViewById(R.id.DistrictId1);
        cityId1 = (TextInputEditText)findViewById(R.id.cityId1);
        pinId1 = (TextInputEditText)findViewById(R.id.pinId1);
        select_file = (TextView)findViewById(R.id.select_file);
        choosDoc = (ImageView)findViewById(R.id.choosDoc);
        roundedImageView1 = (ImageView)findViewById(R.id.roundedImageView1);
        select_cast_certificate = (TextView)findViewById(R.id.select_cast_certificate);
        cast_image_show = (TextView)findViewById(R.id.cast_image_show);
        select_file1 = (TextView)findViewById(R.id.select_file1);
        choosDoc1 = (ImageView)findViewById(R.id.choosDoc1);
        roundedImageView11 = (ImageView)findViewById(R.id.roundedImageView11);
        addressCheck = (CheckBox)findViewById(R.id.addressCheck);
        name = (TextView)findViewById(R.id.name);
        mobile_tv1 = (TextView)findViewById(R.id.mobile_tv1);
        mobile_tv2 = (TextView)findViewById(R.id.mobile_tv2);
        email_Id = (TextView)findViewById(R.id.email_Id);
        led_tv3 = (TextView)findViewById(R.id.led_tv3);
        courseName = (TextView)findViewById(R.id.led_tv4);

        radioG = (RadioGroup)findViewById(R.id.radioG);
        johnCena = (RadioButton)findViewById(R.id.johnCena);
        randyOrton = (RadioButton)findViewById(R.id.randyOrton);

        radioG1 = (RadioGroup)findViewById(R.id.radioG1);
        fresherId = (RadioButton)findViewById(R.id.fresherId);
        deputedId = (RadioButton)findViewById(R.id.deputedId);
        persentaID.setEnabled(false);

        select_acdamy = (TextView)findViewById(R.id.select_acdamy);
        acadamy_image_show = (TextView)findViewById(R.id.acadamy_image_show);
        acSp = (Spinner)findViewById(R.id.acSp);
        board = (TextInputEditText)findViewById(R.id.board);
        years = (TextInputEditText)findViewById(R.id.years);
        fullmarks = (TextInputEditText)findViewById(R.id.fullmarks);
        marksObtent = (TextInputEditText)findViewById(R.id.marksObtent);
        persentage = (TextInputEditText)findViewById(R.id.persentage);
        add_row = (ImageView)findViewById(R.id.add_row);
        recyclerLead1 = (RecyclerView)findViewById(R.id.recyclerLead1);
        acdamy1 = (TextView)findViewById(R.id.acdamy1);
        image_show1 = (TextView)findViewById(R.id.image_show1);
        sessionId = (TextView)findViewById(R.id.sessionId);
        loaderId = (GifImageView)findViewById(R.id.loaderId);
        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.johnCena){
                    level = "Class 12";
                }
                else if (checkedId== R.id.randyOrton){
                    level = "UG / PG";
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Selected",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        System.out.println("level"+" "+level);

        radioG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.fresherId){
                    application = "Freshers";
                    System.out.println("Check_Level"+" "+application);
                }
                else if (checkedId== R.id.deputedId){
                    application = "Deputed";
                    System.out.println("Check_Level"+" "+application);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Selected",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            editLeadId = extras.getString("editLeadId");
            collagesId  = extras.getString("collagesId");
            session_id  = extras.getString("session_id");
            source_id  = extras.getString("source_id");
            course_id  = extras.getString("course_id");
            flag  = extras.getString("flag");
        }
        back.setVisibility(View.GONE);
        System.out.println("editLeadId"+"  "+editLeadId);
        if (flag.equals("0")){
            spinner.setEnabled(true);
            back.setVisibility(View.VISIBLE);
            camera.setEnabled(true);
            roundedImageView.setEnabled(true);
            fatherId.setEnabled(true);
            mothersId.setEnabled(true);
            guardianId.setEnabled(true);
            fIncmome.setEnabled(true);
            PAddress_Id.setEnabled(true);
            stateId.setEnabled(true);
            DistrictId.setEnabled(true);
            cityId.setEnabled(true);
            pinId.setEnabled(true);
            addressCheck.setEnabled(true);
            PAddress_Id1.setEnabled(true);
            stateId1.setEnabled(true);
            DistrictId1.setEnabled(true);
            cityId1.setEnabled(true);
            pinId1.setEnabled(true);
            spinner_cat.setEnabled(true);
            select_cast_certificate.setEnabled(true);
            radioG.setEnabled(true);
            johnCena.setEnabled(true);
            randyOrton.setEnabled(true);
            lastschoolId.setEnabled(true);
            lastRgNo.setEnabled(true);
            radioG1.setEnabled(true);
            fresherId.setEnabled(true);
            deputedId.setEnabled(true);
            checkBox.setEnabled(true);
            persentaID.setEnabled(true);
            spinner3.setEnabled(true);
            userName.setEnabled(true);
            nationalityID.setEnabled(true);
            spinner4.setEnabled(true);
            dbId.setEnabled(true);
            spinner5.setEnabled(true);
            select_file.setEnabled(true);
            select_file.setVisibility(View.VISIBLE);
            select_file1.setEnabled(true);
            select_file1.setVisibility(View.VISIBLE);
            select_acdamy.setEnabled(true);
            select_acdamy.setVisibility(View.VISIBLE);
            acSp.setEnabled(true);
            board.setEnabled(true);
            years.setEnabled(true);
            fullmarks.setEnabled(true);
            marksObtent.setEnabled(true);
            persentage.setEnabled(true);
            acdamy1.setEnabled(true);
            acdamy1.setVisibility(View.VISIBLE);
            add_row.setEnabled(true);
            add_row.setVisibility(View.VISIBLE);
        } else{
            spinner.setEnabled(false);
            back.setVisibility(View.GONE);
            camera.setEnabled(false);
            roundedImageView.setEnabled(false);
            fatherId.setEnabled(false);
            mothersId.setEnabled(false);
            guardianId.setEnabled(false);
            fIncmome.setEnabled(false);
            PAddress_Id.setEnabled(false);
            stateId.setEnabled(false);
            DistrictId.setEnabled(false);
            cityId.setEnabled(false);
            pinId.setEnabled(false);
            addressCheck.setEnabled(false);
            PAddress_Id1.setEnabled(false);
            stateId1.setEnabled(false);
            DistrictId1.setEnabled(false);
            cityId1.setEnabled(false);
            pinId1.setEnabled(false);
            spinner_cat.setEnabled(false);
            select_cast_certificate.setEnabled(false);
            radioG.setEnabled(false);
            johnCena.setEnabled(false);
            randyOrton.setEnabled(false);
            lastschoolId.setEnabled(false);
            lastRgNo.setEnabled(false);
            radioG1.setEnabled(false);
            fresherId.setEnabled(false);
            deputedId.setEnabled(false);
            checkBox.setEnabled(false);
            persentaID.setEnabled(false);
            spinner3.setEnabled(false);
            userName.setEnabled(false);
            nationalityID.setEnabled(false);
            spinner4.setEnabled(false);
            dbId.setEnabled(false);
            spinner5.setEnabled(false);
            select_file.setEnabled(false);
            select_file.setVisibility(View.GONE);
            select_file1.setEnabled(false);
            select_file1.setVisibility(View.GONE);
            select_acdamy.setEnabled(false);
            select_acdamy.setVisibility(View.GONE);
            acSp.setEnabled(false);
            board.setEnabled(false);
            years.setEnabled(false);
            fullmarks.setEnabled(false);
            marksObtent.setEnabled(false);
            persentage.setEnabled(false);
            acdamy1.setEnabled(false);
            acdamy1.setVisibility(View.GONE);
            add_row.setEnabled(false);
            add_row.setVisibility(View.GONE);
        }
        System.out.println("Bundel_Data"+editLeadId+" "+collagesId+" "+session_id+" "+source_id+" "+course_id);
        select_cast_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog2();
            }
        });
        select_file1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog3();
            }
        });
        select_acdamy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog4();
            }
        });
        acdamy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog5();
            }
        });
        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog1();
            }
        });
        dbId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ProfileRejection.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dbId.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

            }
        });

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
        } else {
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
//                showImagePicDialog();
            }
        });
        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
//                showImagePicDialog();
            }
        });
        requestStoragePermission();
//        spinner.setOnItemSelectedListener(this);
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,language);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject_id = getsubjectId(position);
                subject_name = spinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cat.setOnItemSelectedListener(this);
        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Category);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(aa1);

        spinner3.setOnItemSelectedListener(this);
        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Blood);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(aa2);

        spinner4.setOnItemSelectedListener(this);
        ArrayAdapter aa3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,gender);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(aa3);

        spinner5.setOnItemSelectedListener(this);
        ArrayAdapter aa4 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Marital);
        aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(aa4);

        acSp.setOnItemSelectedListener(this);
        ArrayAdapter aa5 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Educactionstander);
        aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acSp.setAdapter(aa5);
        subject = new ArrayList<>();
        imageLink = new ArrayList<File>();
        imageLink1 = new ArrayList<>();
        educationStander1 = new ArrayList<>();
        educationStander = new ArrayList<>();
        examBoard = new ArrayList<>();
        examBoard1 = new ArrayList<>();
        examYears = new ArrayList<>();
        examYears1 = new ArrayList<>();
        examFullMarks = new ArrayList<>();
        examFullMarks1 = new ArrayList<>();
        examMarksObtained1 = new ArrayList<>();
        examMarksObtained = new ArrayList<>();
        examPersentage = new ArrayList<>();
        examPersentage1 = new ArrayList<>();
        examAllDataAdd = new ArrayList<>();
        examAllDataAdd1 = new ArrayList<>();
        acadamyLists = new ArrayList<>();
        AcadamyListArray = new ArrayList<>();
        uploadDocumentsPresent = new ArrayList<>();
        uploadDocumentsPresent1 = new ArrayList<>();
        add_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String boardTv = board.getText().toString();
                String yearsTv = years.getText().toString();
                String fullmarksTv = fullmarks.getText().toString();
                String marksObtentTv = marksObtent.getText().toString();
                String persentageTv =persentage.getText().toString();
                if (image_show1.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Upload Education Details",Toast.LENGTH_SHORT).show();
                    return;
                } else {}
                if (TextUtils.isEmpty(boardTv)) {
                    board.setError("Enter Board Name");
                    board.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(yearsTv)) {
                    years.setError("Enter Years Name");
                    years.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(fullmarksTv)) {
                    fullmarks.setError("Enter FullMarks");
                    fullmarks.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(marksObtentTv)) {
                    marksObtent.setError("Enter Marks Obtent");
                    marksObtent.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(persentageTv)) {
                    persentage.setError("Enter Percentage");
                    persentage.requestFocus();
                    return;
                }
                if (yearsTv.equals("0")){
                    years.setError("Enter not allow 0 value");
                    years.requestFocus();
                    return;
                }
                if (fullmarksTv.equals("0")){
                    fullmarks.setError("Enter not allow 0 value");
                    fullmarks.requestFocus();
                    return;
                }
                if (marksObtentTv.equals("0")){
                    marksObtent.setError("Enter not allow 0 value");
                    marksObtent.requestFocus();
                    return;
                }
                if (persentageTv.equals("0")){
                    persentage.setError("Enter not allow 0 value");
                    persentage.requestFocus();
                    return;
                }

                String madhyamik = String.valueOf(actualImage4);
                if (madhyamik.equals("null")){
                    Toast.makeText(getApplicationContext(),"Upload Madhyamik Admit",Toast.LENGTH_SHORT).show();
                    return;
                }
                String certificated = String.valueOf(actualImage5);
                if (certificated.equals("null")){
                    Toast.makeText(getApplicationContext(),"Upload Certificated",Toast.LENGTH_SHORT).show();
                    return;
                }
                isChek =true;
                //
                final int random = new Random().nextInt(61) + 20;
                String id = String.valueOf(random);
                acadamyLists.add(
                        new AcadamyList(
                                id,
                                String.valueOf(actualImage5),
//                                file_extn5,
                                acSp.getSelectedItem().toString(),
                                boardTv,
                                yearsTv,
                                fullmarksTv,
                                marksObtentTv,
                                persentageTv
                        ));
                AcadamyListArray.addAll(acadamyLists);
                System.out.println("List_list_list"+" "+id+" "
                        +String.valueOf(actualImage5)+" "
                        +acSp.getSelectedItem().toString()+" "+boardTv+" "+yearsTv+" "+fullmarksTv+" "+marksObtentTv+" "+persentageTv);
                clickcount=clickcount+1;
                System.out.println("Count++"+" "+clickcount);
                String upLoads = String.valueOf(uploadDocumentsPresent);
                //
                imageLink.add(actualImage5);
//                imageLink.add(new File(file_extn5));

                educationStander.add(acSp.getSelectedItem().toString());
                examBoard.add(boardTv);
                examYears.add(yearsTv);
                examFullMarks.add(fullmarksTv);
                examMarksObtained.add(marksObtentTv);
                examPersentage.add(persentageTv);
                uploadDocumentsPresent.add("0");
                //
                examAllDataAdd.add(String.valueOf(actualImage5));
//                examAllDataAdd.add(file_extn5);
                examAllDataAdd.add(acSp.getSelectedItem().toString());
                examAllDataAdd.add(boardTv);
                examAllDataAdd.add(yearsTv);
                examAllDataAdd.add(fullmarksTv);
                examAllDataAdd.add(marksObtentTv);
                examAllDataAdd.add(persentageTv);
                //
                recyclerLead1.setHasFixedSize(true);
                recyclerLead1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                leadViewAdapter1 = new LeadViewAdapter1(getApplicationContext(), acadamyLists);
                recyclerLead1.setAdapter(leadViewAdapter1);
                //
                image_show1.setText("");
                board.setText("");
                years.setText("");
                fullmarks.setText("");
                marksObtent.setText("");
                persentage.setText("");
            }
        });
        collageList(collagesId);
        share_lead_info(editLeadId);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChek == true) {
                    saveNext(course_id, application);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Click Plus for add Doucument",Toast.LENGTH_SHORT).show();

                }
            }
        });
        non_edit_view_form();
    }
    public class LeadViewAdapter1 extends RecyclerView.Adapter<LeadViewAdapter1.ViewHolder> {
        private Context mCtx;
        List<AcadamyList> acadamyLists;
        int listview;
        String[] Educactionstander1 = {"Select Exam","10th Standard","12th Standard","(UG) Under Graduate","(PG) Post Graduate"};
        public LeadViewAdapter1(Context mCtx, List<AcadamyList> acadamyLists) {
            this.mCtx = mCtx;
            this.acadamyLists = acadamyLists;
        }

        @Override
        public LeadViewAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acadamy_table, parent, false);
            return new LeadViewAdapter1.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final LeadViewAdapter1.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            AcadamyList acadamyList = acadamyLists.get(position);
            listview = position;
            System.out.println("LodingData"+" "+acadamyList.getExamBoard());
            holder.acadamy_image_show1.setText(acadamyList.getImageLink());
            holder.board1.setText(acadamyList.getExamBoard());
            holder.years1.setText(acadamyList.getExamYears());
            holder.fullmarks1.setText(acadamyList.getExamFullMarks());
            holder.marksObtent1.setText(acadamyList.getExamMarksObtained());
            holder.persentage1.setText(acadamyList.getExamPersentage());
            holder.board1.setEnabled(false);
            holder.years1.setEnabled(false);
            holder.fullmarks1.setEnabled(false);
            holder.marksObtent1.setEnabled(false);
            holder.persentage1.setEnabled(false);
            holder.minus_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageLink.clear();
                    educationStander.clear();
                    examBoard.clear();
                    examYears.clear();
                    examFullMarks.clear();
                    examMarksObtained.clear();
                    examPersentage.clear();
                    examAllDataAdd.clear();
                    removeAt(position);
                }
            });
            if (flag.equals("0")){
                holder.minus_row.setVisibility(View.VISIBLE);
                holder.acSp1.setEnabled(true);
            }else{
                holder.minus_row.setVisibility(View.GONE);
                holder.acSp1.setEnabled(false);

            }
            ArrayAdapter spinnerCollage = new ArrayAdapter(mCtx,android.R.layout.simple_spinner_dropdown_item,Educactionstander1);
            spinnerCollage.setDropDownViewResource(R.layout.spinner_text_view);
            holder.acSp1.setAdapter(spinnerCollage);
//        spinner_collage.setSelection(1);
//        int spinnerPosition = spinnerCollage.getPosition("SAHAJPATH");
//        spinner_collage.setSelection(spinnerPosition);
            String compareValue = acadamyList.getEducationStander();
            if (compareValue != null) {
                int spinnerPosition = spinnerCollage.getPosition(compareValue);
                holder.acSp1.setSelection(spinnerPosition);
            }
            Glide.with(getApplicationContext())
                    .load(acadamyList.getImageLink())
                    .into(holder.cast_images1);
            holder.cast_images1.setBackgroundResource(0);
        }

        @Override
        public int getItemCount() {
            return acadamyLists.size();
        }
        public void removeAt(int position) {
            acadamyLists.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, acadamyLists.size());
            for (int i = 0; i<acadamyLists.size(); i++){
                System.out.println("LoopS"+i);
                String ids = acadamyLists.get(i).getId();
                String imageLink1 = acadamyLists.get(i).getImageLink();
                String educationStander1 = acadamyLists.get(i).getEducationStander();
                String examBoard1 = acadamyLists.get(i).getExamBoard();
                String examYears1 = acadamyLists.get(i).getExamYears();
                String examFullMarks1 = acadamyLists.get(i).getExamFullMarks();
                String examMarksObtained1 = acadamyLists.get(i).getExamMarksObtained();
                String examPersentage1 = acadamyLists.get(i).getExamPersentage();
                imageLink.add(new File(imageLink1));
                educationStander.add(educationStander1);
                examBoard.add(examBoard1);
                examYears.add(examYears1);
                examFullMarks.add(examFullMarks1);
                examMarksObtained.add(examMarksObtained1);
                examPersentage.add(examPersentage1);
                //
                examAllDataAdd.add(imageLink1);
                examAllDataAdd.add(educationStander1);
                examAllDataAdd.add(examBoard1);
                examAllDataAdd.add(examYears1);
                examAllDataAdd.add(examFullMarks1);
                examAllDataAdd.add(examMarksObtained1);
                examAllDataAdd.add(examPersentage1);
            }

        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView select_acdamy1,acadamy_image_show1;
            Spinner acSp1;
            TextInputEditText board1,years1,fullmarks1,marksObtent1,persentage1;
            ImageView minus_row,cast_images1;
            public ViewHolder(View view) {
                super(view);
                select_acdamy1 = (TextView) view.findViewById(R.id.select_acdamy1);
                acadamy_image_show1 = (TextView) view.findViewById(R.id.acadamy_image_show1);
                acSp1 = (Spinner)view.findViewById(R.id.acSp1);
                board1 = (TextInputEditText)view.findViewById(R.id.board1);
                years1 = (TextInputEditText)view.findViewById(R.id.years1);
                fullmarks1 = (TextInputEditText)view.findViewById(R.id.fullmarks1);
                marksObtent1 = (TextInputEditText)view.findViewById(R.id.marksObtent1);
                persentage1 = (TextInputEditText)view.findViewById(R.id.persentage1);
                minus_row = (ImageView)view.findViewById(R.id.minus_row);
                cast_images1 = (ImageView)view.findViewById(R.id.cast_images1);

            }
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.addressCheck:
                str = checked?"Same Address":"Not Same Address";
                if (str.equals("Same Address")){
                    is_present_same = "Yes";
                    String aAddress =PAddress_Id.getText().toString();
                    String sState = stateId.getText().toString();
                    String dDistric = DistrictId.getText().toString();
                    String cCity = cityId.getText().toString();
                    String pPin = pinId.getText().toString();
                    if (TextUtils.isEmpty(aAddress)) {
                        PAddress_Id.setError("Enter Address");
                        PAddress_Id.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(sState)) {
                        stateId.setError("Enter State");
                        stateId.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(dDistric)) {
                        DistrictId.setError("Enter District");
                        DistrictId.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(cCity)) {
                        cityId.setError("Enter City");
                        cityId.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(pPin)) {
                        pinId.setError("Enter Pin Code");
                        pinId.requestFocus();
                        return;
                    }
                    PAddress_Id1.setText(PAddress_Id.getText().toString());
                    stateId1.setText(stateId.getText().toString());
                    DistrictId1.setText(DistrictId.getText().toString());
                    cityId1.setText(cityId.getText().toString());
                    pinId1.setText(pinId.getText().toString());
                }else if(str.equals("Not Same Address")){
                    PAddress_Id1.setText("");
                    stateId1.setText("");
                    DistrictId1.setText("");
                    cityId1.setText("");
                    pinId1.setText("");
                }
                break;
            case R.id.checkBox:
                str1 = checked?"yes":"no";
                if (str1.equals("yes")){
                    persentaID.setEnabled(true);

                }
                else  if (str1.equals("no")){
                    persentaID.setEnabled(false);

                }
                break;
            default:

        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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

    private void takePhotoFromCamera1() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } else {

        }
    }

    private void showFileChooser1() {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTS);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    private void takePhotoFromCamera2() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA1);
        } else {

        }
    }
    private void takePhotoFromCamera3() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA2);
        } else {

        }
    }
    private void takePhotoFromCamera4() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA3);
        } else {

        }
    }
    private void takePhotoFromCamera5() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA4);
        } else {

        }
    }
    private void showFileChooser2() {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTS);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE1);
    }
    private void showFileChooser3() {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTS);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE2);
    }

    private void showFileChooser4() {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTS);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE3);
    }

    private void showFileChooser5() {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUESTS);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE4);
    }
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage = FileUtil.from(getApplicationContext(), data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage));
                camera.setImageBitmap(bitmap);
                camera.setVisibility(View.GONE);
                roundedImageView.setVisibility(View.VISIBLE);
                c_tv.setVisibility(View.GONE);
                c_tv1.setVisibility(View.VISIBLE);
                roundedImageView.setImageBitmap(bitmap);
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage1 = FileUtil.from(getApplicationContext(), data.getData());
                bitmap1 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage1));
                choosDoc.setImageBitmap(bitmap1);
                choosDoc.setVisibility(View.GONE);
                roundedImageView1.setVisibility(View.VISIBLE);
                roundedImageView1.setImageBitmap(bitmap1);
                String fileImage = String.valueOf(actualImage1);
                file_extn1 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
        ////////////////////////////////////
        if (requestCode == PICK_IMAGE1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage2 = FileUtil.from(getApplicationContext(), data.getData());
                bitmap2 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage2));
                String fileImage = String.valueOf(actualImage2);
                file_extn2 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                cast_image_show.setText(file_extn2);
                System.out.println("gjhvjhvjhbhj"+file_extn2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
        if (requestCode == CAMERA1 && resultCode == Activity.RESULT_OK)
        {
            bitmap2 = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), bitmap2);
            File finalFile = new File(getRealPathFromURII(tempUri));
            url2 = String.valueOf(finalFile);
            actualImage2 = finalFile;
            Log.e("path", String.valueOf(actualImage2));
            String fileImage = String.valueOf(actualImage2);
            file_extn2 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            cast_image_show.setText(file_extn2);
            System.out.println("gjhvjhvjhbhj"+file_extn2);
        }

        if (requestCode == PICK_IMAGE2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage3 = FileUtil.from(getApplicationContext(), data.getData());
                bitmap3 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage3));
                choosDoc1.setVisibility(View.GONE);
                roundedImageView11.setVisibility(View.VISIBLE);
                roundedImageView11.setImageBitmap(bitmap3);
                String fileImage = String.valueOf(actualImage3);
                file_extn3 = fileImage.substring(fileImage.lastIndexOf("/") + 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }

        if (requestCode == CAMERA2 && resultCode == Activity.RESULT_OK)
        {
            bitmap3 = (Bitmap) data.getExtras().get("data");
            roundedImageView11.setImageBitmap(bitmap3);
            choosDoc1.setVisibility(View.GONE);
            roundedImageView11.setVisibility(View.VISIBLE);
            Uri tempUri = getImageUri(getApplicationContext(), bitmap3);
            File finalFile = new File(getRealPathFromURII(tempUri));
            url3 = String.valueOf(finalFile);
            actualImage3 = finalFile;
            Log.e("path", String.valueOf(actualImage3));
            String fileImage = String.valueOf(actualImage3);
            file_extn3 = fileImage.substring(fileImage.lastIndexOf("/") + 1);

        }


        if (requestCode == PICK_IMAGE3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage4 = FileUtil.from(getApplicationContext(), data.getData());
                bitmap4 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage4));
                String fileImage = String.valueOf(actualImage4);
                file_extn4 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                acadamy_image_show.setText(file_extn4);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }

        if (requestCode == CAMERA3 && resultCode == Activity.RESULT_OK)
        {
            bitmap4 = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), bitmap4);
            File finalFile = new File(getRealPathFromURII(tempUri));
            url3 = String.valueOf(finalFile);
            actualImage4 = finalFile;
            Log.e("path", String.valueOf(actualImage4));
            String fileImage = String.valueOf(actualImage4);
            file_extn4 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            acadamy_image_show.setText(file_extn4);
        }

        if (requestCode == PICK_IMAGE4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage5 = FileUtil.from(getApplicationContext(), data.getData());
                bitmap5 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(actualImage5));
                String fileImage = String.valueOf(actualImage5);
                file_extn5 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                image_show1.setText(file_extn5);

                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes=stream.toByteArray();
                String sImage= Base64.encodeToString(bytes, Base64.DEFAULT);
//                String decoded = new String(Base64.decode(bytes,Base64.DEFAULT));
                System.out.println("Base64"+" "+sImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
        if (requestCode == CAMERA4 && resultCode == Activity.RESULT_OK)
        {
            bitmap5 = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), bitmap5);
            File finalFile = new File(getRealPathFromURII(tempUri));
            url3 = String.valueOf(finalFile);
            actualImage5 = finalFile;
            Log.e("path", String.valueOf(actualImage5));
            String fileImage = String.valueOf(actualImage5);
            file_extn5 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            image_show1.setText(file_extn5);

//          Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap5.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] bytes=stream.toByteArray();
            String sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
//                String decoded = new String(Base64.decode(bytes,Base64.DEFAULT));
            System.out.println("Base64"+" "+sImage);
        }
        ////////////////////
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                camera.setImageBitmap(bitmap);
                camera.setVisibility(View.GONE);
                roundedImageView.setVisibility(View.VISIBLE);
                c_tv.setVisibility(View.GONE);
                c_tv1.setVisibility(View.VISIBLE);
                roundedImageView.setImageBitmap(bitmap);
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
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK)
        {
            bitmap1 = (Bitmap) data.getExtras().get("data");
            System.out.println("CAMERACODE"+" "+bitmap1);
            choosDoc.setImageBitmap(bitmap1);
            choosDoc.setVisibility(View.GONE);
            roundedImageView1.setVisibility(View.VISIBLE);
            roundedImageView1.setImageBitmap(bitmap1);
            Uri tempUri = getImageUri(getApplicationContext(), bitmap1);
            File finalFile = new File(getRealPathFromURII(tempUri));
            //Log.e("IMGPATH"," "+finalFile);
            url1 = String.valueOf(finalFile);
            actualImage1 = finalFile;
            Log.e("path", String.valueOf(actualImage1));
            String fileImage = String.valueOf(actualImage1);
            file_extn1 = fileImage.substring(fileImage.lastIndexOf("/") + 1);
            //Log.e("file_extn", file_extn);
            // resizeAndCompressImageBeforeSend(context,fileImage,fileImage);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
//              Picasso.with(this).load(resultUri).into(userpic);
                if (isCrop == true) {
                    camera.setVisibility(View.GONE);
                    roundedImageView.setVisibility(View.VISIBLE);
                    c_tv.setVisibility(View.GONE);
                    c_tv1.setVisibility(View.VISIBLE);
                    Picasso.get().load(resultUri).into(roundedImageView);
                    isCrop = false;
                }
                if (isCrops==true){
                    choosDoc.setVisibility(View.GONE);
                    roundedImageView1.setVisibility(View.VISIBLE);
                    c_tv.setVisibility(View.GONE);
                    c_tv1.setVisibility(View.VISIBLE);
                    Picasso.get().load(resultUri).into(roundedImageView1);
                    isCrops = false;
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

        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileRejection.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileRejection.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ProfileRejection.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinner4.getSelectedItem().toString().equals("Female")){
            select_file.setEnabled(true);
            System.out.println("Gender_Spinner"+"true");
        }else{
            select_file.setEnabled(false);
            System.out.println("Gender_Spinner"+"false");

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
                showFileChooser();

                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showImagePicDialog();
                dialog.dismiss();
                isCrop = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    public AlertDialog createDialog1(){
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
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera1();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
                showFileChooser1();
                dialog.dismiss();
            }
        });
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showImagePicDialog();
                dialog.dismiss();
                isCrops = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    public AlertDialog createDialog2(){
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
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera2();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
                showFileChooser2();
                dialog.dismiss();
            }
        });
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//                showImagePicDialog();
                dialog.dismiss();
                isCrops = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    public AlertDialog createDialog3(){
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
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera3();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
                showFileChooser3();
                dialog.dismiss();
            }
        });
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//              showImagePicDialog();
                dialog.dismiss();
                isCrops = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    public AlertDialog createDialog4(){
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
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera4();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
                showFileChooser4();
                dialog.dismiss();
            }
        });
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//              showImagePicDialog();
                dialog.dismiss();
                isCrops = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
    }
    public AlertDialog createDialog5(){
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
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                }
                takePhotoFromCamera5();
                dialog.dismiss();
            }
        });
        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProfileRejection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {

                }
                showFileChooser5();
                dialog.dismiss();
            }
        });
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//              showImagePicDialog();
                dialog.dismiss();
                isCrops = true;
            }
        });

        builder.setView(dialogView);
        dialog.show();
//        dialog.dismiss();
        return dialog;
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
        CropImage.activity().start(ProfileRejection.this);
    }

    public void saveNext(String course_id,String application){
        for (int counter = 0; counter <imageLink.size(); counter++) {
//            Bitmap bm = BitmapFactory.decodeFile(String.valueOf(imageLink.get(counter)));
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
//            byte[] b = baos.toByteArray();
//            System.out.println("Base64"+" "+b);
            String a = "["+imageLink.get(counter)+"]";
            imageLink1.add(new File(a));
            System.out.println("aaaaaaaaa"+imageLink1);
            String SimageLink = String.valueOf(imageLink1);
            String SimageLinks1 = SimageLink.replace(", ", ",");
            String SimageLink1 = SimageLinks1.replace("[[", "");
            String SimageLink11 = SimageLink1.replace("]]", "");
            String SimageLink111 = SimageLink11.replace("]", "");
            SimageLink1111 = SimageLink111.replace("[", "");

            actualImage6 = new File(String.valueOf(imageLink));
        }
        //
//        List<String> SeducationStander11111 = educationStander;
//        System.out.println("sampleList"+SeducationStander11111);

        for (int counter1 = 0; counter1 <educationStander.size(); counter1++) {
            String b = "[" + educationStander.get(counter1) + "]";
            educationStander1.add(b);
            String SeducationStander = String.valueOf(educationStander1);
            String SeducationStanders1 = SeducationStander.replace(", ", ",");
            String SeducationStander1 = SeducationStanders1.replace("[[", "[");
            String SeducationStander11 = SeducationStander1.replace("]]", "]");

            String SeducationStander111 = SeducationStander11.replace("]", "");
            SeducationStander1111 = SeducationStander111.replace("[", "");

        }
        //
        for (int counter2 = 0; counter2 <examBoard.size(); counter2++) {
            String c = "[" + examBoard.get(counter2) + "]";
            examBoard1.add(c);
            String SexamBoard = String.valueOf(examBoard1);
            String SexamBoards1 = SexamBoard.replace(", ", ",");
            String SexamBoard1 = SexamBoards1.replace("[[", "[");
            String SexamBoard11 = SexamBoard1.replace("]]", "]");

            String SexamBoard111 = SexamBoard11.replace("]", "");
            SexamBoard1111 = SexamBoard111.replace("[", "");
        }
        //
        for (int counter3 = 0; counter3 <examYears.size(); counter3++) {
            String d = "[" + examYears.get(counter3) + "]";
            examYears1.add(d);
            String SexamYears = String.valueOf(examYears1);
            String SexamYearss1 = SexamYears.replace(", ", ",");
            String SexamYears1 = SexamYearss1.replace("[[", "[");
            String SexamYears11 = SexamYears1.replace("]]", "]");

            String SexamYears111 = SexamYears11.replace("]", "");
            SexamYears1111 = SexamYears111.replace("[", "");
        }
        //
        for (int counter4 = 0; counter4 <examFullMarks.size(); counter4++) {
            String e = "[" + examFullMarks.get(counter4) + "]";
            examFullMarks1.add(e);
            String SexamFullMarks = String.valueOf(examFullMarks1);
            String SexamFullMarkss1 = SexamFullMarks.replace(", ", ",");
            String SexamFullMarks1 = SexamFullMarkss1.replace("[[", "[");
            String SexamFullMarks11 = SexamFullMarks1.replace("]]", "]");

            String SexamFullMarks111 = SexamFullMarks11.replace("]", "");
            SexamFullMarks1111 = SexamFullMarks111.replace("[", "");
        }
        //
        for (int counter5 = 0; counter5 <examMarksObtained.size(); counter5++) {
            String f = "[" + examMarksObtained.get(counter5) + "]";
            examMarksObtained1.add(f);
            String SexamMarksObtained = String.valueOf(examMarksObtained1);
            String SexamMarksObtaineds1 = SexamMarksObtained.replace(", ", ",");
            String SexamMarksObtained1 = SexamMarksObtaineds1.replace("[[", "[");
            String SexamMarksObtained11 = SexamMarksObtained1.replace("]]", "]");

            String SexamMarksObtained111 = SexamMarksObtained11.replace("]", "");
            SexamMarksObtained1111 = SexamMarksObtained111.replace("[", "");
        }
        //
        for (int counter6 = 0; counter6 <examPersentage.size(); counter6++) {
            String g = "[" + examPersentage.get(counter6) + "]";
            examPersentage1.add(g);
            String SexamPersentage = String.valueOf(examPersentage1);
            String SexamPersentages1 = SexamPersentage.replace(", ", ",");
            String SexamPersentage1 = SexamPersentages1.replace("[[", "[");
            String SexamPersentage11 = SexamPersentage1.replace("]]", "]");

            String SexamPersentage111 = SexamPersentage11.replace("]", "");
            SexamPersentage1111 = SexamPersentage111.replace("[", "");
        }
        //
        for (int counter7 = 0; counter7 <examAllDataAdd.size(); counter7++) {
            String f = "[" + examAllDataAdd.get(counter7) + "]";
            examAllDataAdd1.add(f);
            String SexamAllDataAdd = String.valueOf(examAllDataAdd1);
            String SexamAllDataAdds1 = SexamAllDataAdd.replace(", ", ",");
            String SexamAllDataAdd1 = SexamAllDataAdds1.replace("[[", "[");
            String  SexamAllDataAdd11 = SexamAllDataAdd1.replace("]]", "]");

            String SexamAllDataAdd111 = SexamAllDataAdd11.replace("]", "");
            SexamAllDataAdd1111 = SexamAllDataAdd111.replace("[", "");
        }
        //
        for (int counter8 = 0; counter8 <uploadDocumentsPresent.size(); counter8++) {
            String h = "[" + uploadDocumentsPresent.get(counter8) + "]";
            uploadDocumentsPresent1.add(h);
            String SuploadDocumentsPresent = String.valueOf(uploadDocumentsPresent1);
            String SuploadDocumentsPresents1 = SuploadDocumentsPresent.replace(", ", ",");
            String SuploadDocumentsPresent1 = SuploadDocumentsPresents1.replace("[[", "[");
            String SuploadDocumentsPresent11 = SuploadDocumentsPresent1.replace("]]", "]");

            String SuploadDocumentsPresent111 = SuploadDocumentsPresent11.replace("]", "");
            SuploadDocumentsPresent1111 = SuploadDocumentsPresent111.replace("[", "");
        }
        //
        System.out.println("UploaddocuMents"+" "+SuploadDocumentsPresent1111);
        System.out.println("PintVal"+actualImage6);
        System.out.println("PintVal1"+SeducationStander1111);
        System.out.println("PintVal2"+SexamBoard1111);
        System.out.println("PintVal3"+SexamYears1111);
        System.out.println("PintVal4"+SexamFullMarks1111);
        System.out.println("PintVal5"+SexamMarksObtained1111);
        System.out.println("PintVal6"+SexamPersentage1111);
        System.out.println("PintVal7"+SexamAllDataAdd1111);

        String fName = fatherId.getText().toString();
        String mName = mothersId.getText().toString();
        String gName = guardianId.getText().toString();
        String aAddress =PAddress_Id.getText().toString();
        String sState = stateId.getText().toString();
        String dDistric = DistrictId.getText().toString();
        String cCity = cityId.getText().toString();
        String pPin = pinId.getText().toString();
        String lSchoolName = lastschoolId.getText().toString();
        String lastSchoolRgNo = lastRgNo.getText().toString();
        String perSent = persentaID.getText().toString();
        String adherNo = userName.getText().toString();
        String nationa = nationalityID.getText().toString();
        String dob = dbId.getText().toString();
        String fIncome = fIncmome.getText().toString();
        String PAddressId1 = PAddress_Id1.getText().toString();
        String stateIds1=stateId1.getText().toString();
        String DistrictIds1 = DistrictId1.getText().toString();
        String cityIds1 = cityId1.getText().toString();
        String pinIds1 = pinId1.getText().toString();
        String profilePic = String.valueOf(actualImage);
        if (profilePic.equals("null")){
            Toast.makeText(getApplicationContext(),"Upload Profile Picture",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fName)) {
            fatherId.setError("Enter Father Name");
            fatherId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mName)) {
            mothersId.setError("Enter Mother Name");
            mothersId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(gName)) {
            mothersId.setError("Enter Name");
            mothersId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(aAddress)) {
            PAddress_Id.setError("Enter Address");
            PAddress_Id.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sState)) {
            stateId.setError("Enter State");
            stateId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dDistric)) {
            DistrictId.setError("Enter District");
            DistrictId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cCity)) {
            cityId.setError("Enter City");
            cityId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pPin)) {
            pinId.setError("Enter Pin Code");
            pinId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lSchoolName)) {
            lastschoolId.setError("Enter School Name");
            lastschoolId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastSchoolRgNo)) {
            lastRgNo.setError("Enter Registration No");
            lastRgNo.requestFocus();
            return;
        }
//        if (TextUtils.isEmpty(perSent)) {
//            persentaID.setError("Enter Percentage");
//            persentaID.requestFocus();
//            return;
//        }
        if (TextUtils.isEmpty(adherNo)) {
            userName.setError("Enter AdherNo");
            userName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nationa)) {
            nationalityID.setError("Enter nationality");
            nationalityID.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dob)) {
            dbId.setError("Enter DOB");
            dbId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fIncome)) {
            fIncmome.setError("Enter Family Income");
            fIncmome.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PAddressId1)) {
            PAddress_Id1.setError("Enter present Address");
            PAddress_Id1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(stateIds1)) {
            stateId1.setError("Enter present State");
            stateId1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DistrictIds1)) {
            DistrictId1.setError("Enter present District");
            DistrictId1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cityIds1)) {
            cityId1.setError("Enter present City");
            cityId1.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pinIds1)) {
            pinId1.setError("Enter present Pin");
            pinId1.requestFocus();
            return;
        }
        if (spinner_cat.getSelectedItem().toString().equals("Category")){
            Toast.makeText(getApplicationContext(),"Select Category",Toast.LENGTH_SHORT).show();
            return;
        }else {}
        if(level.equals("")){
            Toast.makeText(getApplicationContext(),"Select Level",Toast.LENGTH_SHORT).show();
            return;
        }else{}
        if(application.equals("")){
            Toast.makeText(getApplicationContext(),"Select Application Type",Toast.LENGTH_SHORT).show();
            return;
        }else{}
//        str1 = checked?"yes":"no";
        if (checkBox.isChecked()== true){
            isPwd = persentaID.getText().toString();
            if (TextUtils.isEmpty(isPwd)) {
                persentaID.setError("Enter PWD");
                persentaID.requestFocus();
                return;
            }
        }
        else {
            isPwd = "0";
        }
        if (spinner3.getSelectedItem().toString().equals("Select Blood Group")){
            Toast.makeText(getApplicationContext(),"Select Blood Group",Toast.LENGTH_SHORT).show();
            return;
        }else{}

        if (spinner4.getSelectedItem().toString().equals("Gender")){
            Toast.makeText(getApplicationContext(),"Select Gender",Toast.LENGTH_SHORT).show();
            return;
        }else{


        }
        if (spinner5.getSelectedItem().toString().equals("Marital Status")){
            Married = "";
            Toast.makeText(getApplicationContext(),"Select Marital Status",Toast.LENGTH_SHORT).show();
            return;
        }else {Married =  spinner5.getSelectedItem().toString();}
        if (spinner_cat.getSelectedItem().toString().equals("General")){
            actualImage2 =actualImage;
        }else{
            if (cast_image_show.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Select Cast Certificated",Toast.LENGTH_SHORT).show();
                return;
            }}
//        if (spinner4.getSelectedItem().toString().equals("Female")){
//            Toast.makeText(getApplicationContext(),"Upload Marriage Certificated",Toast.LENGTH_SHORT).show();
//            return;
//        }else{}
        if (spinner4.getSelectedItem().toString().equals("Female")){
            if (spinner5.getSelectedItem().toString().equals("Married")){
                String marragecirtificate = String.valueOf(actualImage1);
                if (marragecirtificate.equals("null")){
                    Toast.makeText(getApplicationContext(),"Upload Marriage Certificated",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }else {
            actualImage1 = actualImage;
        }
        String signature = String.valueOf(actualImage3);
        System.out.println("Image"+signature);
        if (signature.equals("null")){
            Toast.makeText(getApplicationContext(),"Upload Signature",Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("addmission_form_app"+" "
                +"source_id :"+source_id+" "
                +"fname :"+fname+" "
                +"mname :"+mname+" "
                +"lname :"+lname+" "
                +"mobile_no :"+mobile_no+" "
                +"whatsapp_no :"+whatsapp_no+" "
                +"email :"+email+" "
                +"lead :"+editLeadId+" "
                +"photo :"+actualImage+" "
                +"session_id :"+session_id+" "
                +"label :"+level+" "
                +"school_last_attended :"+lSchoolName+" "
                +"school_registration_number :"+lastSchoolRgNo+" "
                +"application_type :"+application+" "
                +"category :"+ spinner_cat.getSelectedItem().toString()+" "
                +"cast_certificate :"+actualImage2+" "
                +"pwd: "+str1+" "
                +"percentage_of_disability :"+isPwd+" "
                +"blood_group :"+spinner3.getSelectedItem().toString()+" "
                +"course_id :"+course_id+" "
                +"college_id :"+collagesId+" "
                +"subject_id :"+subject_id+" "
                +"father_name :"+fName+" "
                +"mother_name :" +mName+" "
                +"guardian_name :"+gName+" "
                +"family_income :"+fIncome+" "
                +"permanent_address :"+aAddress+" "
                +"state :"+sState+" "
                +"district :"+dDistric+" "
                +"city :"+cCity+" "
                +"pin_code :"+pPin+" "
                +"is_present_same :"+is_present_same+" "
                +"present_address :"+PAddressId1+" "
                +"present_state :"+stateIds1+" "
                +"present_district :"+DistrictIds1+" "
                +"present_city :"+cityIds1+" "
                +"present_pin_code :"+pinIds1+" "
                +"aadhar_no :"+adherNo+" "
                +"nationality :"+nationa+" "
                +"gender :"+spinner4.getSelectedItem().toString()+" "
                +"dob :"+dob+" "
                +"marital_status :"+Married+" "
                +"marriage_certificate :"+actualImage1+" "
                +"signature :"+actualImage3+" "
                +"secondary_admit :"+actualImage4+" "
                ////////
                +"education_standard[] :"+SeducationStander1111+" "
                +"board[] :"+SexamBoard1111+" "
                +"passing_year[] :"+SexamYears1111+" "
                +"full_marks[] :"+SexamFullMarks1111+" "
                +"marks_obtained[] :"+SexamMarksObtained1111+" "
                +"percentage[] :"+SexamPersentage1111+" "
                +"uploadDocumentsPresent[] :" +SuploadDocumentsPresent1111
                //
//                +"education_standard[] :"+educationStander+" "
//                +"board[] :"+examBoard+" "
//                +"passing_year[] :"+examYears+" "
//                +"full_marks[] :"+examFullMarks+" "
//                +"marks_obtained[] :"+examMarksObtained+" "
                //               +"uploadDocumentsPresent[] :" +uploadDocumentsPresent+" "
//                +"percentage[] :"+examPersentage+" "
//              +"upload_documents[] :"+actualImage6+" "
                +"upload_documents[] :"+imageLink+" "
                +"ocid :" +""
                +"spdid:" +""
                +"studentAcademicid[] :" +SuploadDocumentsPresent1111
                +"photoPresent :" +"0"
                +"profileImgb64 :" +""
                +"castCertificatePresent :" +"0"
                +"marriageCertificatePresent :" +"0"
                +"signaturePresent :" +"0"
                +"secondaryAdmitPresent :" +"0");
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        for (int counter = 0; counter <imageLink.size(); counter++) {
            actualImage7 = imageLink.get(counter);
            System.out.println("rex"+" "+actualImage7);
        }
//                String a = "{"+"education_standard[]:" +String.valueOf(educationStander)+"}";

        loaderId.setVisibility(View.VISIBLE);
        AndroidNetworking.upload(BASEURL2+"addmission_form_app")
                .addMultipartFileList(UPLOAD_DOCUMET_KEY,imageLink)
                .addMultipartParameter("ocid",ocid)
                .addMultipartParameter("spdid",spdid)
                .addMultipartParameter("studentAcademicid[]",SuploadDocumentsPresent1111)
                .addMultipartParameter("photoPresent","0")
                .addMultipartParameter("profileImgb64","")
                .addMultipartParameter("castCertificatePresent","0")
                .addMultipartParameter("marriageCertificatePresent","0")
                .addMultipartParameter("signaturePresent","0")
                .addMultipartParameter("secondaryAdmitPresent","0")
                // .addMultipartFile(UPLOAD_DOCUMET_KEY,actualImage6)
//                    .addMultipartFile(UPLOAD_DOCUMET_KEY,actualImage4)
                .addMultipartParameter("source_id",source_id)
                .addMultipartParameter("fname",fname)
                .addMultipartParameter("mname",mname)
                .addMultipartParameter("lname",lname)
                .addMultipartParameter("mobile_no",mobile_no)
                .addMultipartParameter("whatsapp_no",whatsapp_no)
                .addMultipartParameter("email",email)
                .addMultipartParameter("lead",editLeadId)
                .addMultipartFile(PHOTO_KEY,actualImage)
                .addMultipartParameter("session_id",session_id)
                .addMultipartParameter("label",level)
                .addMultipartParameter("school_last_attended",lSchoolName)
                .addMultipartParameter("school_registration_number",lastSchoolRgNo)
                .addMultipartParameter("application_type",application)
                .addMultipartParameter("category",spinner_cat.getSelectedItem().toString())
                .addMultipartFile(CAST_CERTIFICATE_KEY,actualImage2)
//                  .addMultipartFile(CAST_CERTIFICATE_PRESENT_KEY,actualImage2)
//                  .addMultipartParameter("cast_certificate","")
                .addMultipartParameter("pwd",str1)
                .addMultipartParameter("percentage_of_disability",isPwd)
                .addMultipartParameter("blood_group",spinner3.getSelectedItem().toString())
                .addMultipartParameter("course_id",course_id)
                .addMultipartParameter("college_id",collagesId)
                .addMultipartParameter("subject_id",subject_id)
                .addMultipartParameter("father_name",fName)
                .addMultipartParameter("mother_name",mName)
                .addMultipartParameter("guardian_name",gName)
                .addMultipartParameter("family_income",fIncome)
                .addMultipartParameter("permanent_address",aAddress)
                .addMultipartParameter("state",sState)
                .addMultipartParameter("district",dDistric)
                .addMultipartParameter("city",cCity)
                .addMultipartParameter("pin_code",pPin)
                .addMultipartParameter("is_present_same",is_present_same)
                .addMultipartParameter("present_address",PAddressId1)
                .addMultipartParameter("present_state",stateIds1)
                .addMultipartParameter("present_district",DistrictIds1)
                .addMultipartParameter("present_city",cityIds1)
                .addMultipartParameter("present_pin_code",pinIds1)
                .addMultipartParameter("aadhar_no",adherNo)
                .addMultipartParameter("nationality",nationa)
                .addMultipartParameter("gender",spinner4.getSelectedItem().toString())
                .addMultipartParameter("dob",dob)
                .addMultipartParameter("marital_status",Married)
                .addMultipartFile(MARRIAGE_CERTIFICATE_KEY,actualImage1)
                .addMultipartFile(SIGNATUREPRE_KEY,actualImage3)
                .addMultipartFile(SECONDARY_ADMIT_KEY,actualImage4)
//                  .addMultipartFile(SIGNATUREPRESENT_KEY,actualImage3)
                //              .addMultipartParameter("secondaryAdmitPresent","")
                //              .addMultipartFile(SECONDARY_ADMIT_PRESENT_KEY,actualImage4)
                //
                .addMultipartParameter("education_standard[]",SeducationStander1111)
                .addMultipartParameter("board[]",SexamBoard1111)
                .addMultipartParameter("passing_year[]",SexamYears1111)
                .addMultipartParameter("full_marks[]",SexamFullMarks1111)
                .addMultipartParameter("marks_obtained[]",SexamMarksObtained1111)
                .addMultipartParameter("percentage[]",SexamPersentage1111)
                .addMultipartParameter("uploadDocumentsPresent[]",SuploadDocumentsPresent1111)
//                       .addMultipartParameter("education_standard[]", String.valueOf(educationStander))
//                       .addMultipartParameter("board[]", String.valueOf(examBoard))
//                       .addMultipartParameter("passing_year[]", String.valueOf(examYears))
//                       .addMultipartParameter("full_marks[]", String.valueOf(examFullMarks))
//                       .addMultipartParameter("marks_obtained[]", String.valueOf(examMarksObtained))
//                       .addMultipartParameter("percentage[]", String.valueOf(examPersentage))
//                       .addMultipartParameter("uploadDocumentsPresent[]", String.valueOf(uploadDocumentsPresent))
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
                        // do anything with response
                        loaderId.setVisibility(View.GONE);
                        Log.e("rex", "onResponse: "+response );
                        Toast.makeText(ProfileRejection.this, "Upload", Toast.LENGTH_SHORT).show();
                        ProfileRejection.this.setResult(RESULT_OK);
//                        finish(); {"status":1,"msg":"Form inserted successfully. Wait for admin approval","result":""}
                        String res = String.valueOf(response);
                        Log.e("rex",res);
                        try {
                            JSONObject obj =new JSONObject(res);
                            String status = obj.getString("status");
                            String msg = obj.getString("msg");
                            String result = obj.getString("result");
                            if (status.equals("1")){
                                Toast.makeText(ProfileRejection.this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LeadManagementActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                            }else {}
                        } catch (JSONException e) {
                            e.printStackTrace();}}
                    @Override
                    public void onError(ANError error) {
                        Log.e("rex", "onError: "+error.getErrorDetail());
                        loaderId.setVisibility(View.GONE);
                        Toast.makeText(ProfileRejection.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Log.e("resp", String.valueOf("error"));
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
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

    public void collageList(String collagesId){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"subject_select",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("subject_select"+response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            if (status.equals("true")){
                                subjectlLeads = obj.getJSONArray(Config.SUBJECT_LEADS);
                                getsubject(subjectlLeads);
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
                params.put("college_id", collagesId);
                System.out.println("subject_select"+collagesId);
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

    private void getsubject(JSONArray i){
        for(int I=0;I<i.length();I++){
            try {
                JSONObject json = i.getJSONObject(I);
                subject.add(json.getString(Config.SUBJECT_SUBJECT));
                Log.e("subject"," "+subject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(ProfileRejection.this, android.R.layout.simple_spinner_dropdown_item, subject));
        ArrayAdapter leadStage = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,subject);
        leadStage.setDropDownViewResource(R.layout.spinner_text_view);
        spinner.setAdapter(leadStage);
    }

    private String getsubjectId(int position){
        try {
            JSONObject json = subjectlLeads.getJSONObject(position);
            subject_id = json.getString(Config.SUBJECT_ID);
//         Toast.makeText(getApplicationContext(),followUpSlow, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subject_id;
    }
    public void share_lead_info(String editLeadId){
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
                                    fname = jsonObject.getString("fname");
                                    mname = jsonObject.getString("mname");
                                    lname = jsonObject.getString("lname");
                                    mobile_no = jsonObject.getString("mobile_no");
                                    whatsapp_no = jsonObject.getString("whatsapp_no");
                                    email = jsonObject.getString("email");
                                    coursename = jsonObject.getString("course_name");
                                    collegename = jsonObject.getString("collegename");
                                    name.setText(fname+" "+mname+" "+lname);
                                    mobile_tv1.setText(mobile_no);
                                    mobile_tv2.setText(whatsapp_no);
                                    email_Id.setText(email);
                                    led_tv3.setText(collegename);
                                    courseName.setText(coursename);

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
    public void non_edit_view_form(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"admission_form_edit",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("admission_form_edit"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String error = obj.getString("error");
                            String isAlreadyFillup = obj.getString("isAlreadyFillup");
                            String photoPresent = obj.getString("photoPresent");
                            String castCertificatePresent = obj.getString("castCertificatePresent");
                            String marriageCertificatePresent = obj.getString("marriageCertificatePresent");
                            String secondaryAdmitPresent = obj.getString("secondaryAdmitPresent");
                            if (status.equals("true")){
//                                JSONObject jsonObject = obj.getJSONObject("lead");
//                                String id = jsonObject.getString("id");
//                                String fname = jsonObject.getString("fname");
//                                String mname = jsonObject.getString("mname");
//                                String lname = jsonObject.getString("lname");
//                                String mobile_no = jsonObject.getString("mobile_no");
//                                String whatsapp_no = jsonObject.getString("whatsapp_no");
//                                String email = jsonObject.getString("email");
//                                String city_id = jsonObject.getString("city_id");
//                                String state_id = jsonObject.getString("state_id");
//                                String user_id = jsonObject.getString("user_id");
//                                String course_id = jsonObject.getString("course_id");
//                                String source_id = jsonObject.getString("source_id");
//                                String campaign_id = jsonObject.getString("campaign_id");
//                                String session_id = jsonObject.getString("session_id");
//                                String lead_followups = jsonObject.getString("lead_followups");
//                                String lead_stage = jsonObject.getString("lead_stage");
//                                String assign_type = jsonObject.getString("assign_type");
//                                String assign_to = jsonObject.getString("assign_to");
//                                String status1 = jsonObject.getString("status");
//                                String lead_view_status = jsonObject.getString("lead_view_status");
//                                String comment = jsonObject.getString("comment");
//                                String comment_date = jsonObject.getString("comment_date");
//                                String counselor_id = jsonObject.getString("counselor_id");
//                                String created_at = jsonObject.getString("created_at");
//                                String updated_at = jsonObject.getString("updated_at");
//                                String lead_update_date = jsonObject.getString("lead_update_date");
//                                String reason_for_edit = jsonObject.getString("reason_for_edit");
//                                String lead_details_edit_date = jsonObject.getString("lead_details_edit_date");
//                                String lead_details_edited_by_type = jsonObject.getString("lead_details_edited_by_type");
//                                String lead_details_edited_by = jsonObject.getString("lead_details_edited_by");
//                                String is_followup = jsonObject.getString("is_followup");
//                                String otp = jsonObject.getString("otp");
//                                String is_revoked = jsonObject.getString("is_revoked");
//                                String revoked_date_time = jsonObject.getString("revoked_date_time");
//                                String lead_form_name = jsonObject.getString("lead_form_name");
//                                String lead_form_location = jsonObject.getString("lead_form_location");
//                                String qualification_id = jsonObject.getString("qualification_id");
//                                String revoke_reason_type = jsonObject.getString("revoke_reason_type");
//                                String revoke_value = jsonObject.getString("revoke_value");
//                                String consultant_lead_status = jsonObject.getString("consultant_lead_status");
//                                String admission_status = jsonObject.getString("admission_status");
//                                String consultant_id = jsonObject.getString("consultant_id");
//                                String is_duplicate_from_consultant = jsonObject.getString("is_duplicate_from_consultant");
//                                String reason_fr_suspend = jsonObject.getString("reason_fr_suspend");
//                                String is_refund = jsonObject.getString("is_refund");
//                                String secondary_admit = jsonObject.getString("secondary_admit");
//                                String councellor_end_admission_status = jsonObject.getString("councellor_end_admission_status");
//                                String sorting_date = jsonObject.getString("sorting_date");
//                                String college_id = jsonObject.getString("college_id");
//                                String student_phoneno = jsonObject.getString("student_phoneno");
//                                String alternative_phoneno = jsonObject.getString("alternative_phoneno");
//                                String list_property = jsonObject.getString("list_property");
//                                String duplicate_flag = jsonObject.getString("duplicate_flag");
//                                System.out.println("leadViews"+duplicate_flag);
//                                //
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("state");
//                                String states_id = jsonObject1.getString("id");
//                                String state_name = jsonObject1.getString("state_name");
//                                String country_id = jsonObject1.getString("country_id");
//                                String state_status = jsonObject1.getString("status");
//                                String state_created_at = jsonObject1.getString("created_at");
//                                String state_updated_at = jsonObject1.getString("updated_at");
//                                System.out.println("leadViews_state "+state_updated_at);
//
////
//                                JSONObject jsonObject2 = jsonObject.getJSONObject("city");
//                                String citys_id = jsonObject2.getString("id");
//                                String city_name = jsonObject2.getString("city_name");
//                                String city_state_id = jsonObject2.getString("state_id");
//                                String city_created_at = jsonObject2.getString("created_at");
//                                String city_updated_at = jsonObject2.getString("updated_at");
//                                System.out.println("leadViews_city "+city_updated_at);
//
////
//                                JSONObject jsonObject3 = jsonObject.getJSONObject("course");
//                                System.out.println("leadViews_course "+jsonObject3);
//                                String couserid = jsonObject3.getString("id");
//                                String course_name = jsonObject3.getString("course_name");
//                                String course_slug = jsonObject3.getString("course_slug");
//                                String counsellor_id = jsonObject3.getString("counsellor_id");
//                                String course_stream_id = jsonObject3.getString("course_stream_id");
//                                String course_status = jsonObject3.getString("status");
//                                String course_created_at = jsonObject3.getString("created_at");
//                                String courseupdated_at = jsonObject3.getString("updated_at");
//                                String is_show_in_widge = jsonObject3.getString("is_show_in_widge");
//                                String duration = jsonObject3.getString("duration");
////                                String termsandconditions = jsonObject3.getString("termsandcondition");
//                                System.out.println("leadViews_course "+couserid+ " "+course_name+" "+course_slug+
//                                        " "+counsellor_id+" "+course_stream_id+" "+course_status
//                                        +" "+course_created_at+" "+courseupdated_at+" "+is_show_in_widge+" "+duration+" ");
//
////
//                                JSONObject jsonObject4 = jsonObject.getJSONObject("college");
//                                String coll_id = jsonObject4.getString("id");
//                                String collegename = jsonObject4.getString("collegename");
//                                String tagline = jsonObject4.getString("tagline");
//                                String description = jsonObject4.getString("description");
//                                String logo = jsonObject4.getString("logo");
//                                String college_banner = jsonObject4.getString("college_banner");
//                                String collegecode = jsonObject4.getString("collegecode");
//                                String collegeaddress = jsonObject4.getString("collegeaddress");
//                                String contactperson = jsonObject4.getString("contactperson");
//                                String contactno = jsonObject4.getString("contactno");
//                                String coll_email = jsonObject4.getString("email");
//                                String website = jsonObject4.getString("website");
//                                String courses = jsonObject4.getString("courses");
//                                String cr_by = jsonObject4.getString("cr_by");
//                                String coll_created_at = jsonObject4.getString("created_at");
//                                String udt_by = jsonObject4.getString("udt_by");
//                                String coll_updated_at = jsonObject4.getString("updated_at");
//                                System.out.println("leadViews_college"+coll_updated_at);
//
//                                //
//                                JSONObject jsonObject5 = jsonObject.getJSONObject("user");
//                                String users_id = jsonObject5.getString("id");
//                                String users_name = jsonObject5.getString("name");
//                                String first_name = jsonObject5.getString("first_name");
//                                String middle_name = jsonObject5.getString("middle_name");
//                                String last_name = jsonObject5.getString("last_name");
//                                String genders = jsonObject5.getString("gender");
//                                String user_image = jsonObject5.getString("user_image");
//                                String user_email = jsonObject5.getString("email");
//                                String user_mobile = jsonObject5.getString("mobile");
//                                String user_type = jsonObject5.getString("user_type");
//                                String email_verified_at = jsonObject5.getString("email_verified_at");
//                                String users_status = jsonObject5.getString("status");
//                                String joining_date = jsonObject5.getString("joining_date");
//                                String user_created_at = jsonObject5.getString("created_at");
//                                String user_updated_at = jsonObject5.getString("updated_at");
//                                String user_consultant_status = jsonObject5.getString("consultant_status");
//                                String user_email_otp = jsonObject5.getString("email_otp");
//                                String user_mobile_otp = jsonObject5.getString("mobile_otp");
//                                String counsellor_college = jsonObject5.getString("counsellor_college");
//                                String consultant_managers = jsonObject5.getString("consultant_managers");
//                                System.out.println("leadViews_user "+consultant_managers);
//
//                                //
//                                JSONObject jsonObject9 = jsonObject.getJSONObject("session");
//                                String sessions_id = jsonObject9.getString("id");
//                                String from_date = jsonObject9.getString("from_date");
//                                String todate = jsonObject9.getString("todate");
//                                String session_value = jsonObject9.getString("session_value");
//                                String session_status = jsonObject9.getString("status");
//                                String session_created_at = jsonObject9.getString("created_at");
//                                String session_updated_at = jsonObject9.getString("updated_at");
//                                System.out.println("leadViews_session "+session_updated_at);

//
//                                JSONObject jsonObject6 = obj.getJSONObject("Confirmadmission");
//                                System.out.println("leadViews_Confirmadmission "+jsonObject6);
//                                if (jsonObject6 != null) {
//                                    String Confir_id = jsonObject6.getString("id");
//                                    String Confir_lead_id = jsonObject6.getString("lead_id");
//                                    String booking_amount = jsonObject6.getString("booking_amount");
//                                    String mode_of_transaction = jsonObject6.getString("mode_of_transaction");
//                                    String transaction_id = jsonObject6.getString("transaction_id");
//                                    String paid_to_account = jsonObject6.getString("paid_to_account");
//                                    String Confi_college_id = jsonObject6.getString("college_id");
//                                    String Confi_sub_cat_id = jsonObject6.getString("sub_cat_id");
//                                    String Confi_subject_id = jsonObject6.getString("subject_id");
//                                    String Confi_image = jsonObject6.getString("image");
//                                    String Confi_srn_no = jsonObject6.getString("srn_no");
//                                    String Confi_is_verify = jsonObject6.getString("is_verify");
//                                    String Confi_father_name = jsonObject6.getString("father_name");
//                                    String Confi_dob = jsonObject6.getString("dob");
//                                    String Confi_session_id = jsonObject6.getString("session_id");
//                                    String Confi_created_at = jsonObject6.getString("created_at");
//                                    String Confi_updated_at = jsonObject6.getString("updated_at");
//                                    String Confi_course_fee_payout_readio = jsonObject6.getString("course_fee_payout_readio");
//                                    String Confi_total_course_fee = jsonObject6.getString("total_course_fee");
//                                    String Confi_total_payout = jsonObject6.getString("total_payout");
//                                    String Confi_fees_shared = jsonObject6.getString("fees_shared");
//                                    String Confi_fees_liable = jsonObject6.getString("fees_liable");
//                                    String Confi_term_condition = jsonObject6.getString("term_condition");
//                                    String Confi_consultant_status = jsonObject6.getString("consultant_status");
//                                    String Confi_student_type = jsonObject6.getString("student_type");
//                                    System.out.println("leadViews_Confirmadmission "+Confi_student_type);
//
//                                }
////
//                                JSONObject jsonObject7 = obj.getJSONObject("college_form");
//                                System.out.println("leadViews_college_form "+jsonObject7);
//                                if (jsonObject7 != null) {
//                                        String collageid = jsonObject7.getString("id");
//                                        String collagelead_id = jsonObject7.getString("lead_id");
//                                        String collegesid = jsonObject7.getString("college_id");
//                                        String coll_course_id = jsonObject7.getString("course_id");
//                                        String coll_fname = jsonObject7.getString("fname");
//                                        String coll_name = jsonObject7.getString("mname");
//                                        String coll_lname = jsonObject7.getString("lname");
//                                        String coll_father_name = jsonObject7.getString("father_name");
//                                        String coll_mother_name = jsonObject7.getString("mother_name");
//                                        String coll_dob = jsonObject7.getString("dob");
//                                        String coll_mobile_no = jsonObject7.getString("mobile_no");
//                                        String coll_present_address = jsonObject7.getString("present_address");
//                                        String coll_present_state = jsonObject7.getString("present_state");
//                                        String coll_present_district = jsonObject7.getString("present_district");
//                                        String coll_present_city = jsonObject7.getString("present_city");
//                                        String coll_present_pin = jsonObject7.getString("present_pin");
//                                        String coll_same_address = jsonObject7.getString("same_address");
//                                        String coll_permanent_address = jsonObject7.getString("permanent_address");
//                                        String coll_permanent_state = jsonObject7.getString("permanent_state");
//                                        String coll_permanent_district = jsonObject7.getString("permanent_district");
//                                        String coll_permanent_city = jsonObject7.getString("permanent_city");
//                                        String coll_permanent_pin = jsonObject7.getString("permanent_pin");
//                                        String coll_adhar = jsonObject7.getString("adhar");
//                                        String coll_gender = jsonObject7.getString("session_id");
//                                        String colls_created_at = jsonObject7.getString("created_at");
//                                        String colls_updated_at = jsonObject7.getString("updated_at");
//                                    System.out.println("leadViews_college_form "+colls_updated_at);
//
//                                }
                                //
                                JSONObject OLD_Confirmadmission = obj.getJSONObject("OLD_Confirmadmission");
                                ocid = OLD_Confirmadmission.getString("id");
//                                String old_lead_id = OLD_Confirmadmission.getString("lead_id");
//                                String old_booking_amount = OLD_Confirmadmission.getString("booking_amount");
//                                String old_mode_of_transaction = OLD_Confirmadmission.getString("mode_of_transaction");
//                                String old_transaction_id = OLD_Confirmadmission.getString("transaction_id");
//                                String old_paid_to_account= OLD_Confirmadmission.getString("paid_to_account");
//                                String old_college_id= OLD_Confirmadmission.getString("college_id");
//                                String old_sub_cat_id= OLD_Confirmadmission.getString("sub_cat_id");
//                                String old_subject_id = OLD_Confirmadmission.getString("subject_id");
//                                String old_image = OLD_Confirmadmission.getString("image");
//                                String old_srn_no = OLD_Confirmadmission.getString("srn_no");
//                                String old_is_verify = OLD_Confirmadmission.getString("is_verify");
//                                String old_father_name = OLD_Confirmadmission.getString("father_name");
//                                String old_dob = OLD_Confirmadmission.getString("dob");
//                                String old_session_id = OLD_Confirmadmission.getString("session_id");
//                                String old_created_at = OLD_Confirmadmission.getString("created_at");
//                                String old_updated_at = OLD_Confirmadmission.getString("updated_at");
//                                String old_course_fee_payout_readio = OLD_Confirmadmission.getString("course_fee_payout_readio");
//                                String old_total_course_fee = OLD_Confirmadmission.getString("total_course_fee");
//                                String old_total_payout = OLD_Confirmadmission.getString("total_payout");
//                                String old_fees_shared = OLD_Confirmadmission.getString("fees_shared");
//                                String old_fees_liable = OLD_Confirmadmission.getString("fees_liable");
//                                String old_term_condition = OLD_Confirmadmission.getString("term_condition");
//                                String old_consultant_status = OLD_Confirmadmission.getString("consultant_status");
//                                String old_student_type = OLD_Confirmadmission.getString("student_type");
                                System.out.println("leadViews_OLD_Confirmadmission "+ocid);


                                //
                                JSONObject jsonObject8 = obj.getJSONObject("studentPersonalDetails");
                                spdid = jsonObject8.getString("id");
                                String lead_id = jsonObject8.getString("lead_id");
                                String session = jsonObject8.getString("session");
                                String label = jsonObject8.getString("label");
                                String application_type = jsonObject8.getString("application_type");
                                String photo = jsonObject8.getString("photo");
                                String category = jsonObject8.getString("category");
                                String blood_group = jsonObject8.getString("blood_group");
                                String pwd = jsonObject8.getString("pwd");
                                String cast_certificate = jsonObject8.getString("cast_certificate");
                                String father_name = jsonObject8.getString("father_name");
                                String guardian_name = jsonObject8.getString("guardian_name");
                                String family_income = jsonObject8.getString("family_income");
                                String mother_name = jsonObject8.getString("mother_name");
                                String permanent_address = jsonObject8.getString("permanent_address");
                                String Admi_city = jsonObject8.getString("city");
                                String district = jsonObject8.getString("district");
                                String admin_state = jsonObject8.getString("state");
                                String pin_code = jsonObject8.getString("pin_code");
                                String states = jsonObject8.getString("state");
                                String is_present_same = jsonObject8.getString("is_present_same");
                                String present_address = jsonObject8.getString("present_address");
                                String present_city = jsonObject8.getString("present_city");
                                String present_district = jsonObject8.getString("present_district");
                                String present_state = jsonObject8.getString("present_state");
                                String present_pin_code = jsonObject8.getString("present_pin_code");
                                String aadhar_no = jsonObject8.getString("aadhar_no");
                                String nationality = jsonObject8.getString("nationality");
                                String gender1 = jsonObject8.getString("gender");
                                String dob = jsonObject8.getString("dob");
                                String marital_status = jsonObject8.getString("marital_status");
                                String marriage_certificate = jsonObject8.getString("marriage_certificate");
                                String school_last_attended = jsonObject8.getString("school_last_attended");
                                String school_registration_number = jsonObject8.getString("school_registration_number");
                                String is_deputed_by_any_org = jsonObject8.getString("is_deputed_by_any_org");
                                String deputed_student_institute_name = jsonObject8.getString("deputed_student_institute_name");
                                String deputed_student_joining_date = jsonObject8.getString("deputed_student_joining_date");
                                String percentage_of_disability = jsonObject8.getString("percentage_of_disability");
                                String signature = jsonObject8.getString("signature");
                                String admin_created_at = jsonObject8.getString("created_at");
                                String admins_updated_at = jsonObject8.getString("updated_at");
                                System.out.println("leadViews_studentPersonalDetails "+spdid);

                                actualImage = new File(IMAGEURL+photo);
                                actualImage3 = new File(IMAGEURL+marriage_certificate);
                                actualImage3 = new File(IMAGEURL+signature);
                                actualImage2 = new File(IMAGEURL+cast_certificate);
                                Glide.with(getApplicationContext())
                                        .load(IMAGEURL+photo)
                                        .into(camera);
                                camera.setBackgroundResource(0);
                                // choosDoc
                                Glide.with(getApplicationContext())
                                        .load(IMAGEURL+marriage_certificate)
                                        .into(choosDoc);
                                choosDoc.setBackgroundResource(0);
                                //
                                Glide.with(getApplicationContext())
                                        .load(IMAGEURL+signature)
                                        .into(choosDoc1);
                                choosDoc1.setBackgroundResource(0);
                                //
                                cast_image_show.setText(IMAGEURL+signature);
                                //
                                System.out.println("Url_image"+" "+IMAGEURL+photo);
                                sessionId.setText("");
                                fatherId.setText(father_name);
                                mothersId.setText(mother_name);
                                guardianId.setText(guardian_name);
                                fIncmome.setText(family_income);
                                PAddress_Id.setText(permanent_address);
                                stateId.setText(states);
                                DistrictId.setText(district);
                                cityId.setText(Admi_city);
                                pinId.setText(pin_code);
                                if(is_present_same.equals("yes")) {
                                    addressCheck.setChecked(true);
                                    PAddress_Id1.setText(permanent_address);
                                    stateId1.setText(states);
                                    DistrictId1.setText(district);
                                    cityId1.setText(Admi_city);
                                    pinId1.setText(pin_code);
                                    //spinner_cat
                                    ArrayAdapter aa1 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,Category);
                                    aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_cat.setAdapter(aa1);
                                    String compareValue = category;
                                    if (compareValue != null) {
                                        int spinnerPosition = aa1.getPosition(compareValue);
                                        spinner_cat.setSelection(spinnerPosition);
                                    }
                                    //select_cast_certificate
                                    actualImage2 = new File(cast_certificate);
                                    cast_image_show.setText(cast_certificate);
                                    // radioG johnCena randyOrton
                                    lastschoolId.setText(school_last_attended);
                                    lastRgNo.setText(school_registration_number);
                                    // radioG1 fresherId deputedId
                                    if (application_type.equals("freshers")){
                                        fresherId.setChecked(true);
                                        application = "Freshers";
                                    }else if (application_type.equals("deputed")){
                                        deputedId.setChecked(true);
                                        application = "Deputed";
                                    }
                                    if(label.equals("xii")){
                                        johnCena.setChecked(true);
                                        level = "Class 12";
                                    }
                                    else if(label.equals("xii")){
                                        randyOrton.setChecked(true);
                                        level = "UG / PG";
                                    }
                                    // checkBox

                                    //spinner3
                                    ArrayAdapter aa3 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,gender);
                                    aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner4.setAdapter(aa3);
                                    if (gender1.equals("male")){
                                        gender1 = "Male";
                                    }
                                    else if (gender1.equals("female")){
                                        gender1 = "Female";
                                    }
                                    else if (gender1.equals("other")){
                                        gender1 = "Other";

                                    }
                                    String compareValue1 = gender1;
                                    if (compareValue1 != null) {
                                        int spinnerPosition = aa3.getPosition(compareValue1);
                                        spinner4.setSelection(spinnerPosition);
                                    }
                                    dbId.setText(dob);
                                    //spinner5
                                    ArrayAdapter aa4 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,Marital);
                                    aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner5.setAdapter(aa4);
                                    if (marital_status.equals("never_married")){
                                        marital_status = "Never Married";
                                    }
                                    else if (marital_status.equals("married")){
                                        marital_status = "Married";
                                    }
                                    else if (marital_status.equals("widow")){
                                        marital_status = "Widow";
                                    }
                                    else if (marital_status.equals("divorced")){
                                        marital_status = "Divorced or Separated";

                                    }

                                    String compareValue2 = marital_status;
                                    if (compareValue2 != null) {
                                        int spinnerPosition = aa4.getPosition(compareValue2);
                                        spinner5.setSelection(spinnerPosition);
                                    }
                                    //
                                    userName.setText(aadhar_no);
                                    nationalityID.setText(nationality);
                                    if (pwd.equals("null")){
                                        checkBox.setChecked(false);
                                        str1 = "no";
                                        persentaID.setText(percentage_of_disability);
                                    }
                                    else {
                                        str1 = "yes";
                                        checkBox.setChecked(true);
                                        persentaID.setText(percentage_of_disability);
                                    }
                                    //
                                    ArrayAdapter aa2 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,Blood);
                                    aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner3.setAdapter(aa2);
                                    String compareValue3 = blood_group;
                                    if (compareValue3 != null) {
                                        int spinnerPosition = aa2.getPosition(compareValue3);
                                        spinner3.setSelection(spinnerPosition);
                                    }
                                    //
                                }
//
                                JSONArray jsonArray1 = obj.getJSONArray("studentAcademicDetail");
                                for (int j = 0; j<jsonArray1.length(); j++){
                                    JSONObject jsonObject10 = jsonArray1.getJSONObject(j);
                                    String stuid = jsonObject10.getString("id");
                                    String stulead_id= jsonObject10.getString("lead_id");
                                    stueducation_standard = jsonObject10.getString("education_standard");
                                    String stusecondary_admit = jsonObject10.getString("secondary_admit");
                                    String board = jsonObject10.getString("board");
                                    String passing_year = jsonObject10.getString("passing_year");
                                    String full_marks = jsonObject10.getString("full_marks");
                                    String marks_obtained = jsonObject10.getString("marks_obtained");
                                    String percentage = jsonObject10.getString("percentage");
                                    String upload_document = jsonObject10.getString("upload_document");
                                    String stucreated_at = jsonObject10.getString("created_at");
                                    String stuupdated_at= jsonObject10.getString("updated_at");
                                    System.out.println("leadViews_studentAcademicDetail "+stuupdated_at);
                                    if (stueducation_standard.equals("10th")){
                                        stueducation_standard = "10th Standard";
                                    }
                                    else if (stueducation_standard.equals("12th")){
                                        stueducation_standard = "12th Standard";
                                    }
                                    else if (stueducation_standard.equals("UG")){
                                        stueducation_standard = "(UG) Under Graduate";
                                    } else if (stueducation_standard.equals("PG")){
                                        stueducation_standard = "(PG) Post Graduate";

                                    }
                                    final int random = new Random().nextInt(61) + 20;
                                    String ids = String.valueOf(random);
                                    actualImage5 = new File(IMAGEURL+upload_document);
                                    acadamyLists.add(
                                            new AcadamyList(
                                                    ids,
                                                    String.valueOf(actualImage5),
//                                                  file_extn5,
                                                    stueducation_standard,
                                                    board,
                                                    passing_year,
                                                    full_marks,
                                                    marks_obtained,
                                                    percentage
                                            ));
                                    AcadamyListArray.addAll(acadamyLists);
                                    imageLink.add(actualImage5);
//                imageLink.add(new File(file_extn5));

                                    educationStander.add(stueducation_standard);
                                    examBoard.add(board);
                                    examYears.add(passing_year);
                                    examFullMarks.add(full_marks);
                                    examMarksObtained.add(marks_obtained);
                                    examPersentage.add(percentage);
                                    uploadDocumentsPresent.add("0");
                                    //
                                    examAllDataAdd.add(String.valueOf(actualImage5));
//                examAllDataAdd.add(file_extn5);
                                    examAllDataAdd.add(acSp.getSelectedItem().toString());
                                    examAllDataAdd.add(board);
                                    examAllDataAdd.add(passing_year);
                                    examAllDataAdd.add(full_marks);
                                    examAllDataAdd.add(marks_obtained);
                                    examAllDataAdd.add(percentage);
                                    //

                                    //
                                    recyclerLead1.setHasFixedSize(true);
                                    recyclerLead1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    leadViewAdapter1 = new LeadViewAdapter1(getApplicationContext(), acadamyLists);
                                    recyclerLead1.setAdapter(leadViewAdapter1);
                                    //
                                }
//
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
                System.out.println("admission_form_edit"+editLeadId);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                   headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + user.getToken());
                System.out.println("admission_form_edit"+user.getToken());
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}
