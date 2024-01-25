package com.pmit.ems.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract



class ResultContracts {

    class SelectFile : ActivityResultContract<String, Uri?>() {

        override fun createIntent(context: Context, input: String): Intent =
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type = input
            }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? = when (resultCode) {
            Activity.RESULT_OK -> intent?.data
            else -> null
        }
    }



    class ActivityResultResponse : ActivityResultContract<Intent, Intent?>() {

        override fun createIntent(context: Context, intent: Intent): Intent = intent

        override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
            if (resultCode == Activity.RESULT_OK) {
                return intent
            }

            return null
        }
    }


//    class CropImage : ActivityResultContract<Uri, Uri?>() {
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Uri? = when (resultCode) {
//            Activity.RESULT_OK -> {
//                val result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(intent)
//                result.uri
//            }
//            else -> null
//        }
//
//        override fun createIntent(context: Context, input: Uri): Intent {
//            var activity = com.theartofdev.edmodo.cropper.CropImage.activity(input)
//            activity =
//                activity.setActivityMenuIconColor(ContextCompat.getColor(context, R.color.black))
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setBackgroundColor(ContextCompat.getColor(context, R.color.bgGray))
//                    .setAllowRotation(true)
//                    .setAllowFlipping(true)
//            return activity.getIntent(context)
//        }
//    }


}