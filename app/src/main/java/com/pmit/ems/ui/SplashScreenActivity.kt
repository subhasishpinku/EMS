package com.pmit.ems.ui
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.pmit.ems.R
import com.pmit.ems.api.Config
import com.pmit.ems.utility.Utility
import org.json.JSONException
import org.json.JSONObject


class SplashScreenActivity : AppCompatActivity() {
    var btnImg: ImageView? = null
    var btn_tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.splashscreen_main)
        //       val binding = SplashscreenMainBinding.inflate(layoutInflater)
        btnImg = findViewById<ImageView>(R.id.btn_Img)
        val imageSlider = findViewById<ImageSlider>(R.id.slider) // init imageSlider
        btn_tv = findViewById<TextView>(R.id.btn_tv)
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.ch1))
        imageList.add(SlideModel(R.drawable.ch2))
        imageList.add(SlideModel(R.drawable.ch3))
//        imageList.add(SlideModel(R.drawable.ch4))
        imageSlider.setImageList(imageList)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
            }
        })
        btnImg!!.setOnTouchListener { v, event ->
            val action = event.action
            when(action){

                MotionEvent.ACTION_DOWN -> {

                }

                MotionEvent.ACTION_MOVE -> {
                    if (Utility.checkConnectivity(this@SplashScreenActivity)) {
                        appversion_check();
                    } else{
                        Toast.makeText(applicationContext, "No Internet connection", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                MotionEvent.ACTION_UP -> {
                    btn_tv!!.visibility = View.VISIBLE
                }

                MotionEvent.ACTION_CANCEL -> {

                }

                else ->{
                }
            }
            true
        }
//        imageSlider.setItemChangeListener(object : ItemChangeListener {
//            override fun onItemChanged(position: Int) {
//                //println("Pos: " + position)
//            }
//        })
//
//        imageSlider.setTouchListener(object : TouchListener {
//            override fun onTouched(touched: ActionTypes) {
//                if (touched == ActionTypes.DOWN){
//                    imageSlider.stopSliding()
//                } else if (touched == ActionTypes.UP ) {
//                    imageSlider.startSliding(1000)
//                }
//            }
//        })
    }
    fun appversion_check() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, Config.BASEURL2 + "appversion_check",
            Response.Listener { response ->
                println("appversion_check$response")
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    if (status == "true") {
                        val appversion = obj.getString("appversion")
                        if (appversion.equals("1.001")){
                            val animation1: Animation =
                                AnimationUtils.loadAnimation(applicationContext, android.R.anim.slide_out_right)
                            btnImg!!.startAnimation(animation1)
                            btn_tv!!.visibility = View.GONE
                            val intent = Intent(this, MobileNumberActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(applicationContext, "You are not in right version contract administration", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                return params
            } }
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        val volleySingleton = VolleySingleton.getInstance(this)
        stringRequest.setShouldCache(false)
        volleySingleton.addToRequestQueue(stringRequest)
    }
    override fun onBackPressed() {
//        onBackPressedDispatcher.onBackPressed() //with this line
//        super.onBackPressed()
//        finishAffinity();
//        finish()
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setMessage("DO YOU REALLY WANT TO EXIT ??")
//            .setCancelable(false)
//            .setPositiveButton("YES",
//                DialogInterface.OnClickListener { dialog, i -> super@SplashScreenActivity.onBackPressed() })
//            .setNegativeButton("NO",
//                DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
//        val alertDialog: AlertDialog = builder.create()
//        alertDialog.show()
//        System.out.println("Back_Space"+"0")
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finishAffinity();
        finish()
    }


}