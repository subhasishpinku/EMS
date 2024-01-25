package com.pmit.ems.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pmit.ems.R;

public class LeadDetailsFromActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner collageList,mod_of_payment,paid_to_account;
    String[] collagelist = { "Select Collage from the list", "Surendranath College", "Prafulla Chandra College", "Acharya Girish Chandra Bose College", "Calcutta Girls' College"};
    String[] modePayment = { "Mode of Payment", "Online", "ATM"};

    String[] PaidAccount = { "Paid to Account", "10,000", "20,000","30,000" ,"40,000", "50,000"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_details_from_activity);
        collageList = (Spinner)findViewById(R.id.collageList);
        mod_of_payment = (Spinner) findViewById(R.id.mod_of_payment);
        paid_to_account = (Spinner) findViewById(R.id.paid_to_account);
        collageList.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,collagelist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collageList.setAdapter(aa);

        mod_of_payment.setOnItemSelectedListener(this);
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,modePayment);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mod_of_payment.setAdapter(aaa);

        paid_to_account.setOnItemSelectedListener(this);
        ArrayAdapter aaa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,PaidAccount);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paid_to_account.setAdapter(aaa1);
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
