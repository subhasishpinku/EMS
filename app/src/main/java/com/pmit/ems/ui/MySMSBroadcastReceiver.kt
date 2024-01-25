package com.pmit.ems.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class MySMSBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "MySMSBroadcastReceiver"

//    private var otpReceiver: OTPReceiveListener? = null
//
//    fun initOTPListener(receiver: OTPReceiveListener) {
//        this.otpReceiver = receiver
//    }

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            Log.e("OTP_Message","hdhjvcj")

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    // Get SMS message contents
                    var otp: String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                    Log.e("OTP_Message", otp)
//                    if (otpReceiver != null) {
//                        otp = otp.replace("<#> Your ExampleApp code is: ", "").split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
//                        otpReceiver!!.onOTPReceived(otp)
//                    }
                }

                CommonStatusCodes.TIMEOUT ->{
                    Log.e(TAG,"TimedOut")
                }
                // Waiting for SMS timed out (5 minutes)
                // Handle the error ...
//                    otpReceiver!!.onOTPTimeOut()
            }
        }
    }
}