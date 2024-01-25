package com.pmit.ems.ui
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.auth.api.credentials.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.makeramen.roundedimageview.RoundedImageView
import com.pmit.ems.R
import com.pmit.ems.api.ApiClientToken
import com.pmit.ems.api.Config.BASEURL2
import com.pmit.ems.databinding.ActivityNumbersBinding
import com.pmit.ems.manager.App
import com.pmit.ems.model.Country
import com.pmit.ems.model.SelectionDialog
import com.pmit.ems.model.UserLoginMach
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
class MobileNumberActivity : AppCompatActivity(), SelectionDialog.ItemSelection<Country> {
    private var mCountry: Country? = null
    var round_Image: RoundedImageView? = null
    var countyCode: String? = "91"
    var verificationId:kotlin.String? = null
    var plus:kotlin.String? = null
    var token: PhoneAuthProvider.ForceResendingToken? = null
    var fAuth: FirebaseAuth? = null
    var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    var signUpEmailPhoneEdtx: TextInputEditText? = null
    var otp: String? = null
    var name: String? = null
    var token1: String? = null
    var user_id: String? = null
    var email: String? = null
    var dept_name: String? = null
    var user_code:String? = null
    var user_image:String? = null
    companion object {
        var CREDENTIAL_PICKER_REQUEST = 1
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        round_Image = findViewById<View>(R.id.round_Image) as RoundedImageView
        signUpEmailPhoneEdtx = findViewById<View>(R.id.pnone_ID) as TextInputEditText
        val btn_otp = findViewById<MaterialButton>(R.id.send_otp)
        val backId = findViewById<TextView>(R.id.backId) as TextView
        backId?.setOnClickListener{
            val i = Intent(applicationContext, SplashScreenActivity::class.java)
            startActivity(i)
        }
        phoneSelection()
        round_Image!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(App.SELECTION_TYPE, SelectionDialog.Selection.Country)
            val instance = SelectionDialog.getInstance<Country>()
            instance.setOnItemSelected(this)
            instance.arguments = bundle
            instance.show(supportFragmentManager, null)
        }
        fAuth = FirebaseAuth.getInstance()
        round_Image!!.setImageResource(R.drawable.flag_india)
        btn_otp!!.setOnClickListener(View.OnClickListener {
//            if (signUpEmailPhoneEdtx!!.text.toString().isEmpty()) {
//                signUpEmailPhoneEdtx!!.error = "Enter Phone Number"
//                return@OnClickListener
//            }
//            plus = "+"+countyCode +signUpEmailPhoneEdtx!!.text.toString()
//            Toast.makeText(this@MobileNumberActivity, plus, Toast.LENGTH_SHORT).show()
//            verifyPhoneNumber(plus)

         //  val i = Intent(applicationContext, OtpScreenActivity::class.java)
//            i.putExtra("phone", plus)
//            i.putExtra("verify", verificationId)
//            i.putExtra("token", token)
            // startActivity(i)

            login()
        })
        val user: UserLoginData = SharedPrefManagerLogin.getInstance(applicationContext).user
        val userMach: UserLoginMach = SharedPrefManagerLoginMach.getInstance(applicationContext).userMach
        System.out.println("NoMach"+"1"+" "+user.phoneNumber+" "+userMach.userPhone)
        if (user.phoneNumber != null && userMach.userPhone != null ){
            System.out.println("NoMach"+"2")

            if(user.phoneNumber.equals(userMach.userPhone)){
                val nextMach = Intent(applicationContext, MainActivity::class.java)
                startActivity(nextMach)
                System.out.println("NoMach"+"3")
            }
            else {
                System.out.println("NoMach"+"4")
            }
        }
        else{
            System.out.println("NoMach"+"5")
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                authenticateUser(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@MobileNumberActivity, e.message, Toast.LENGTH_SHORT).show()
                val phone: String = signUpEmailPhoneEdtx!!.getText().toString()
//                val userdata = UserLoginData(
//                    name,token1,user_id,email,dept_name,user_code,user_image,phone
//                )
//                SharedPrefManagerLogin.getInstance(applicationContext).CustomerID(userdata)
                val intent = Intent(applicationContext, OtpScreenActivity::class.java)
                intent.putExtra("phone", "NA")
                intent.putExtra("verify", "NA")
                intent.putExtra("token", "NA")
                intent.putExtra("otp", otp)
                intent.putExtra("ph",signUpEmailPhoneEdtx!!.text.toString())

                intent.putExtra("name", name)
                intent.putExtra("token1", token1)
                intent.putExtra("user_id", user_id)
                intent.putExtra("email", email)
                intent.putExtra("dept_name", dept_name)
                intent.putExtra("user_code", user_code)
                intent.putExtra("user_image", user_image)
                intent.putExtra("phone", phone)

                startActivity(intent)
            }

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
                token = forceResendingToken
                println("otp_Is $verificationId $token")
                val phone: String = signUpEmailPhoneEdtx!!.getText().toString()
//                val userdata = UserLoginData(
//                    name,token1,user_id,email,dept_name,user_code,user_image,phone
//                )
//                SharedPrefManagerLogin.getInstance(applicationContext).CustomerID(userdata)
                val intent = Intent(applicationContext, OtpScreenActivity::class.java)
                intent.putExtra("phone", plus)
                intent.putExtra("verify", verificationId)
                intent.putExtra("token", token)
                intent.putExtra("otp", otp)
                intent.putExtra("ph",signUpEmailPhoneEdtx!!.text.toString())

                intent.putExtra("name", name)
                intent.putExtra("token1", token1)
                intent.putExtra("user_id", user_id)
                intent.putExtra("email", email)
                intent.putExtra("dept_name", dept_name)
                intent.putExtra("user_code", user_code)
                intent.putExtra("user_image", user_image)
                intent.putExtra("phone", phone)
                startActivity(intent)
            }

            override fun onCodeAutoRetrievalTimeOut(s: String) {
                super.onCodeAutoRetrievalTimeOut(s)
            }
        }
    }
    override fun onItemSelected(country: Country) {
        mCountry = country
        country.img?.let { round_Image!!.setImageResource(it) }
        var string = country.title
        countyCode = string!!.replace("[^0-9]".toRegex(), "")
    }
    fun authenticateUser(credential: PhoneAuthCredential?) {
        fAuth!!.signInWithCredential(credential!!).addOnSuccessListener {
            Toast.makeText(this@MobileNumberActivity, "Success", Toast.LENGTH_SHORT).show()
            //startActivity(new Intent(getApplicationContext(),VeriftOtpScreenActivity.class));
//                finish();
        }.addOnFailureListener { e ->
            Toast.makeText(
                this@MobileNumberActivity,
                e.message,
                Toast.LENGTH_SHORT
            ).show()
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
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, SplashScreenActivity::class.java)
        startActivity(intent)
    }
    private fun phoneSelection() {
        // To retrieve the Phone Number hints, first, configure
        // the hint selector dialog by creating a HintRequest object.
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()

        val options = CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build()
        val credentialsClient = Credentials.getClient(applicationContext, options)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(
                intent.intentSender,
                CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0, Bundle()
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK) {
            // get data from the dialog which is of type Credential
            val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)
            // set the received data t the text view
            credential?.apply {
                var number:String = credential.id
                val str1 = number
                val phoneNumber = 3
                val result = str1.drop(phoneNumber)
                signUpEmailPhoneEdtx!!.setText(result)
            }
        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }

    override fun onStart() {
        super.onStart()
//        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }
    }

        private fun service(){
        val call: Call<ResponseBody> = ApiClientToken.getInstance().courses()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val res = response.body()!!.string().trim { it <= ' ' }
                        println("coursesssssssssss$res")
                        val jsonObject = JSONObject(res)
//                        val data = jsonObject.getJSONObject("data")
//                        val array = data.getJSONArray("list")
//                        for (i in 0 until array.length()) {
//                            val obj = array.getJSONObject(i)
//                            val userID = obj.getString("userID")
//                            val name = obj.getString("name")
//                            val email = obj.getString("email")
//                            val agencyID = obj.getString("agency_ID")
//                            val emailID = obj.getString("emailID")
//                            val mobile = obj.getString("mobile")
//
//                        }
                    } catch (e: Exception) {
                    }
                } else {
                    Log.e("DATA", " " + response.message())
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
        })
    }
    private fun login(){
        val phone: String = signUpEmailPhoneEdtx!!.getText().toString()
        if (TextUtils.isEmpty(phone)) {
            signUpEmailPhoneEdtx!!.setError("Enter your Phone Number")
            signUpEmailPhoneEdtx!!.requestFocus()
            return
        }
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, BASEURL2+"app_login",
            com.android.volley.Response.Listener { response ->
                Log.e("ResponseLogin", response!!)
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    if(status.equals("true")){
                        val user: JSONObject = obj.getJSONObject("user_data")
                         name = user.getString("u_name")
                         token1 = user.getString("token")
                         user_id = user.getString("id")
                         email = user.getString("email")
                         dept_name = user.getString("dept_name")
                         user_code = user.getString("user_code")
                         user_image = user.getString("user_image")
                         otp = user.getString("otp")
                         System.out.println("UserData"+" "+name+" "+token+" "+user_id)
//                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
//                        val userdata = UserLoginData(
//                            name,token1,user_id,email,dept_name,user_code,user_image,phone
//                        )
//                        SharedPrefManagerLogin.getInstance(applicationContext).CustomerID(userdata)
                         plus = "+"+countyCode +signUpEmailPhoneEdtx!!.text.toString()
                         System.out.println("Account_verify"+plus)
                         Toast.makeText(this@MobileNumberActivity, "Verification code sent successfully", Toast.LENGTH_SHORT).show()
                         verifyPhoneNumber(plus)
//                       val next = Intent(applicationContext, OtpScreenActivity::class.java)
//                       next.putExtra("phone", plus)
//                       next.putExtra("verify", verificationId)
//                       next.putExtra("token", token)
//                       startActivity(next)
                    } else {
                        Toast.makeText(this@MobileNumberActivity, "You are Not a valid User", Toast.LENGTH_SHORT).show()
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
                params["phone"] = phone
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