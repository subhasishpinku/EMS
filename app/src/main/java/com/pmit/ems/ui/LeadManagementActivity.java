package com.pmit.ems.ui;
import static com.pmit.ems.api.Config.BASEURL2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.pmit.ems.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class LeadManagementActivity extends AppCompatActivity {
   RelativeLayout home_corner2;
   RelativeLayout leadmange_corner1,leadmange_corner2,leadmange_corner13,leadmange_corner4;
   TextView tv_1,tv_11,tv_13,tv_4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leadmanagement_activity);
        home_corner2 = (RelativeLayout) findViewById(R.id.home_corner2);
        leadmange_corner1 = (RelativeLayout)findViewById(R.id.leadmange_corner1);
        leadmange_corner2 = (RelativeLayout)findViewById(R.id.leadmange_corner2);
        leadmange_corner13 = (RelativeLayout)findViewById(R.id.leadmange_corner13);
        leadmange_corner4 = (RelativeLayout)findViewById(R.id.leadmange_corner4);
        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_11 = (TextView)findViewById(R.id.tv_11);
        tv_13 = (TextView)findViewById(R.id.tv_13);
        tv_4 = (TextView)findViewById(R.id.tv_4);
        home_corner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeadEntryScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        leadmange_corner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LeadTableActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        leadmange_corner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyStudentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        leadmange_corner13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RejectionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        leadmange_corner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PendingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        LeadCount();
    }
    public void LeadCount(){
        UserLoginData user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2+"dashboard_count",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("get_lead_course_count"+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("true")){
                                String total_leads = obj.getString("total_leads");
                                String admission_count = obj.getString("admission_count");
                                String reject_count = obj.getString("reject_count");
                                String pending_count = obj.getString("pending_count");
                                String error = obj.getString("error");
                                tv_1.setText(total_leads);
                                tv_11.setText(admission_count);
                                tv_13.setText(reject_count);
                                tv_4.setText(pending_count);

                            }else {
                                Toast.makeText(getApplicationContext(),"Empty Count",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
