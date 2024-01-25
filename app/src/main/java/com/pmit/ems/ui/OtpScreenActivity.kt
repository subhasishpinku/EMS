package com.pmit.ems.ui

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.pmit.ems.R
import com.pmit.ems.api.Config.BASEURL2
import com.pmit.ems.databinding.OtpScreenActivityBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class OtpScreenActivity : AppCompatActivity() {
    var verificationCodeFirstEdtx: TextInputEditText? = null
    var verificationCodeSecondEdtx: TextInputEditText? = null
    var verificationCodeThirdEdtx: TextInputEditText?  = null
    var verificationCodeFourthEdtx: TextInputEditText? = null
    var verificationCodeFifthEdtx: TextInputEditText?  = null
    var verificationCodeSixthEdtx: TextInputEditText?  = null
    var verifyAccountDescTv1: TextView? = null
    var verifyAccountResendBtn:MaterialButton? = null
    var token: PhoneAuthProvider.ForceResendingToken? = null
    var fAuth: FirebaseAuth? = null
    var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    var phone: String? = null
    var otpWeb: String? = null
    var ph: String? = null
    var message: String? = null
    var verify:kotlin.String? = null
    var tokens:kotlin.String? = null
    var tv_id1:  TextView?  = null
    var tv_id2 : TextView?  = null
    var resent_Otp : TextView?  = null
    var otpTextView: OtpTextView? = null

    var name: String? = null
    var token1: String? = null
    var user_id: String? = null
    var email: String? = null
    var dept_name: String? = null
    var user_code: String? = null
    var user_image: String? = null
    var phone1: String? = null
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    var otpV :kotlin.String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = OtpScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var send_otp = findViewById<MaterialButton>(R.id.send_otp)
//        verificationCodeFirstEdtx =
//            findViewById<View>(R.id.otp) as TextInputEditText
//        verificationCodeSecondEdtx =
//            findViewById<View>(R.id.otp1) as TextInputEditText
//        verificationCodeThirdEdtx =
//            findViewById<View>(R.id.otp2) as TextInputEditText
//        verificationCodeFourthEdtx =
//            findViewById<View>(R.id.otp3) as TextInputEditText
//        verificationCodeFifthEdtx =
//            findViewById<View>(R.id.otp4) as TextInputEditText
//        verificationCodeSixthEdtx =
//            findViewById<View>(R.id.otp5) as TextInputEditText

        verifyAccountResendBtn = findViewById<View>(R.id.send_otp) as MaterialButton
        tv_id1 = findViewById<View>(R.id.tv_id1) as TextView
        tv_id2 = findViewById<View>(R.id.tv_id2) as TextView
        resent_Otp = findViewById<View>(R.id.resent_Otp) as TextView
        fAuth = FirebaseAuth.getInstance()
        val extras = intent.extras
        if (extras != null) {
            phone =  extras.getString("phone")
            verify = extras.getString("verify")
            tokens = extras.getString("token")
            otpWeb = extras.getString("otp")
            ph = extras.getString("ph")

            name =  extras.getString("name")
            token1 = extras.getString("token1")
            user_id = extras.getString("user_id")
            email = extras.getString("email")
            dept_name = extras.getString("dept_name")
            user_code = extras.getString("user_code")
            user_image = extras.getString("user_image")
            phone1 = extras.getString("phone")


        }
        System.out.println("verify"+verify+" "+tokens)
        otpTextView = findViewById(R.id.otp_view)
        otpTextView?.requestFocusOTP()
        otpTextView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }
            override fun onOTPComplete(otp: String) {
                otpV = otp;
            }
        }
        tv_id2!!.text = "registered mobile "+phone
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                authenticateUser(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@OtpScreenActivity, e.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verify = s
                token = forceResendingToken
                println("otp_Is_Resent $verify $token")
            }

            override fun onCodeAutoRetrievalTimeOut(s: String) {
                super.onCodeAutoRetrievalTimeOut(s)
            }
        }
        resent_Otp!!.setOnClickListener(View.OnClickListener {
//            verifyPhoneNumber(phone)
//            System.out.println("Account_verify"+phone)
            val intent = Intent(this, MobileNumberActivity::class.java)
            startActivity(intent)
        })
//        verificationCodeFirstEdtx!!.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                // If the event is a key-down event on the "enter" button
//                if (event.getAction() === KeyEvent.ACTION_DOWN &&
//                    keyCode == KeyEvent.KEYCODE_ENTER
//                ) {
//                    // Perform action on Enter key press
//                    verificationCodeSecondEdtx!!.requestFocus()
//                    return true
//                }
//                return false
//            }
//        })
//        verificationCodeSecondEdtx!!.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                // If the event is a key-down event on the "enter" button
//                if (event.getAction() === KeyEvent.ACTION_DOWN &&
//                    keyCode == KeyEvent.KEYCODE_ENTER
//                ) {
//                    // Perform action on Enter key press
//                    verificationCodeThirdEdtx!!.requestFocus()
//                    return true
//                }
//                return false
//            }
//        })
//        verificationCodeThirdEdtx!!.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                // If the event is a key-down event on the "enter" button
//                if (event.getAction() === KeyEvent.ACTION_DOWN &&
//                    keyCode == KeyEvent.KEYCODE_ENTER
//                ) {
//
//                    return true
//                }
//                return false
//            }
//        })
        verifyAccountResendBtn!!.setOnClickListener(View.OnClickListener {
//            if (verificationCodeFirstEdtx!!.text.toString().isEmpty()) {
//                verificationCodeFirstEdtx!!.error = "Empty"
//                return@OnClickListener
//            }
//            if (verificationCodeSecondEdtx!!.text.toString().isEmpty()) {
//                verificationCodeSecondEdtx!!.error = "Empty"
//                return@OnClickListener
//            }
//            if (verificationCodeThirdEdtx!!.text.toString().isEmpty()) {
//                verificationCodeThirdEdtx!!.error = "Empty"
//                return@OnClickListener
//            }
//            if (verificationCodeFourthEdtx!!.text.toString().isEmpty()) {
//                verificationCodeFourthEdtx!!.error = "Empty"
//                return@OnClickListener
//            }
//            if (verificationCodeFifthEdtx!!.text.toString().isEmpty()) {
//                verificationCodeFifthEdtx!!.error = "Empty"
//                return@OnClickListener
//            }
//            if (verificationCodeSixthEdtx!!.text.toString().isEmpty()) {
//                verificationCodeSixthEdtx!!.error = "Empty"
//                return@OnClickListener
//            }
//            val otp = (verificationCodeFirstEdtx!!.text.toString()
//                    + verificationCodeSecondEdtx!!.text.toString()
//                    + verificationCodeThirdEdtx!!.text.toString()
//                    + verificationCodeFourthEdtx!!.text.toString()
//                    + verificationCodeFifthEdtx!!.text.toString()
//                    + verificationCodeSixthEdtx!!.text.toString())
//          val otp = signUpEmailOtpEdtx!!.text.toString()

            val credential = PhoneAuthProvider.getCredential(verify!!, otpV.toString())
            authenticateUser(credential)
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
        })
//        startPhoneAuthFirebase()
//        startSmsUserConsent()
//        val mSmsBroadcastReceiver = MySMSBroadcastReceiver()
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
//        applicationContext.registerReceiver(mSmsBroadcastReceiver, intentFilter)
//        startSMSRetrievingProcess()
    }
    fun authenticateUser(credential: PhoneAuthCredential?) {
        fAuth!!.signInWithCredential(credential!!).addOnSuccessListener {
            val userdata = UserLoginData(
                name,token1,user_id,email,dept_name,user_code,user_image,phone
            )
            SharedPrefManagerLogin.getInstance(applicationContext).CustomerID(userdata)
            Toast.makeText(this@OtpScreenActivity, "Account Verify successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener { e ->
            Toast.makeText(
                this@OtpScreenActivity,
                e.message,
                Toast.LENGTH_SHORT
            ).show()
            Otp(otpWeb.toString(),ph.toString())
        }
    }
    fun verifyPhoneNumber(phone: String?) {
        val options = PhoneAuthOptions.newBuilder(fAuth!!)
            .setActivity(this)
            .setPhoneNumber(phone!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun startSmsUserConsent() {
        SmsRetriever.getClient(applicationContext).also {
            //We can add user phone number or leave it blank
            it.startSmsUserConsent(null)
                .addOnSuccessListener {
                    Log.d(TAG, "LISTENING_SUCCESS")
                }
                .addOnFailureListener {
                    Log.d(TAG, "LISTENING_FAILURE")
                }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_USER_CONSENT -> {
                if ((resultCode == Activity.RESULT_OK) && (data != null)) {
                    //That gives all message to us. We need to get the code from inside with regex
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    val code = message?.let { fetchVerificationCode(it) }
                    System.out.println("CODDDD"+code)
//                  signUpEmailOtpEdtx!!.setText(code)
//                  etVerificationCode.setText(code)
                    val numbers = code!!.split("").map { it.toInt() }
                    verificationCodeFirstEdtx!!.setText(numbers.get(1))
                    verificationCodeSecondEdtx!!.setText(numbers.get(2))
                    verificationCodeThirdEdtx!!.setText(numbers.get(3))
                    verificationCodeFourthEdtx!!.setText(numbers.get(4))
                    verificationCodeFifthEdtx!!.setText(numbers.get(5))
                    verificationCodeSixthEdtx!!.setText(numbers.get(6))
                }
            }
        }
    }
    private fun registerToSmsBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver().also {
            it.smsBroadcastReceiverListener = object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent?) {
                    intent?.let { context -> startActivityForResult(context, REQ_USER_CONSENT)

                    }
                }
                override fun onFailure() {
                }
            }
        }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    private fun fetchVerificationCode(message: String): String {
        return Regex("(\\d{6})").find(message)?.value ?: ""
    }
    companion object {
        const val TAG = "SMS_USER_CONSENT"
        const val REQ_USER_CONSENT = 100
    }

    override fun onStart() {
        super.onStart()
//        registerToSmsBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
//        unregisterReceiver(smsBroadcastReceiver)
    }

    private fun startPhoneAuthFirebase() {
        FirebaseAuth.getInstance().also {
            val options = PhoneAuthOptions.newBuilder(it)
                .setPhoneNumber(phone.toString())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callBackFirebase)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        startReceiver()
    }
    private fun startReceiver() {
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsVerificationReceiver, intentFilter)
        SmsRetriever.getClient(this).startSmsUserConsent(null)
    }
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {

                if (result.data != null) {
                    // Get SMS message content
                    val message = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    // Extract one-time code from the message and complete verification
                    // `message` contains the entire text of the SMS message, so you will need
                    // to parse the string.
                    try {
                        message?.substring(0, 6)?.toInt()?.let {
                            System.out.println("CODDDD"+it.toString())
                        }
                    } catch (e: Exception) {

                    }
                } else {
                    // Consent denied. User can type OTC manually.
                }

            }
        }
    private val smsVerificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status?

                when (smsRetrieverStatus?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {

                        try {
                            val consentIntent =
                                extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                            startForResult.launch(consentIntent)
                        } catch (e: Exception) {

                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {

                    }
                }
            }
        }
    }
    private val callBackFirebase = object :
        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Toast.makeText(applicationContext, credential.toString(), Toast.LENGTH_SHORT).show()
        }
        override fun onVerificationFailed(ex: FirebaseException) {
        }
    }
    private fun startSMSRetrievingProcess(){
        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Log.e(TAG,"SMS received")

        }
        task.addOnFailureListener {
            Log.e(TAG,"SMS received failure")

        }
    }
    fun Otp(otp: String, phone: String) {
        println("OTPSEND$otp")
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, BASEURL2+"app_login_otp_verify",
            com.android.volley.Response.Listener { response ->
                Log.e("ResponseLogin", response!!)
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                     message = obj.getString("message")
                    if(status.equals("true")){
                        Toast.makeText(this@OtpScreenActivity, message, Toast.LENGTH_SHORT).show()
                        val userdata = UserLoginData(
                            name,token1,user_id,email,dept_name,user_code,user_image,phone
                        )
                        SharedPrefManagerLogin.getInstance(applicationContext).CustomerID(userdata)
                        Toast.makeText(this@OtpScreenActivity, "Account Verify successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(applicationContext, SplashScreenActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@OtpScreenActivity, message, Toast.LENGTH_SHORT).show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            com.android.volley.Response.ErrorListener {
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["otp"] = otp
                params["mobileno"] = phone
                System.out.println("OTPP"+params.toString())
                return params
            }
        }
        stringRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        val volleySingleton = VolleySingleton.getInstance(applicationContext)
        stringRequest.setShouldCache(false)
        volleySingleton.addToRequestQueue(stringRequest)
    }
}