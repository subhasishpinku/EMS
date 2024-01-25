package com.pmit.ems.ui;
import static android.content.Context.MODE_PRIVATE;
import static com.pmit.ems.api.Config.BASEURL2;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pmit.ems.R;
import com.pmit.ems.api.Consts;
import com.pmit.ems.model.CheckOtp;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class OtpBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    String otp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_otp, container, false);
//        TextInputEditText t1 = v.findViewById(R.id.otp);
//        TextInputEditText t2 = v.findViewById(R.id.otp1);
//        TextInputEditText t3 = v.findViewById(R.id.otp2);
//        TextInputEditText t4 = v.findViewById(R.id.otp3);
//        TextInputEditText t5 = v.findViewById(R.id.otp4);
//        TextInputEditText t6 = v.findViewById(R.id.otp5);
          OtpTextView otp_view = v.findViewById(R.id.otp_views);
        MaterialButton button2 = v.findViewById(R.id.submitId);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onButtonClicked("Button 1 clicked");
//                dismiss();
//            }
//        });
        otp_view.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otps) {
                // fired when user has entered the OTP fully.
                otp = otps;
                Toast.makeText(getContext(), "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String otp = t1.getText().toString() + t2.getText().toString() + t3.getText().toString() +
                       // t4.getText().toString() + t5.getText().toString() + t6.getText().toString();
                Otp(otp);
            }
        });
        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    public void Otp(String otp) {
        System.out.println("OTPSEND" + otp);
        SharedPreferences sp = getContext().getSharedPreferences(Consts.LEADID, MODE_PRIVATE);
        SharedPreferences sp1 = getContext().getSharedPreferences(Consts.COURSE, MODE_PRIVATE);
        String  LEADID = sp.getString("LEADID", "");
        String  COURSE = sp.getString("COURSE", "");
        UserLoginData user = SharedPrefManagerLogin.getInstance(getContext()).getUser();
        CheckOtp checkOtp = new CheckOtp();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL2 + "check_otp_manager_app",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("check_otp_manager_app  " + response);
                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);
                            String success = obj.getString("status");
                            String error = obj.getString("error");
                            if (success.equals("1")) {
                                String message = obj.getString("responce");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                mListener.onButtonClicked("Done");
                                dismiss();
                            } else {
                                String message = obj.getString("responce");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                                mListener.onButtonClicked("Done");
//                                dismiss();
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
                params.put("otp", otp);
                params.put("lead",LEADID);
                params.put("course",COURSE);
                params.put("user_id",user.getUser_id());
                System.out.println("check_otp_manager_app"+params.toString());
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
        //   VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}