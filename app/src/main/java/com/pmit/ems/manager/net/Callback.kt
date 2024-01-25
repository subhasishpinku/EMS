package com.pmit.ems.manager.net

import android.util.Log
import com.pmit.ems.R
import com.pmit.ems.manager.App
import com.pmit.ems.ui.frag.ErrorFrag
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException

interface CustomCallback<T> : Callback<T> {
    companion object {

        private var mErrorFrag: ErrorFrag? = null
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is IOException && !call.isCanceled) {
            if (showNoNetworkPage()) {
                showNoNetwork(onStateChanged())
            }
        }

        Log.e(CustomCallback::class.java.name, t.toString())
    }

    fun onStateChanged(): RetryListener?

    fun showNoNetworkPage(): Boolean {
        return true
    }

    private fun showNoNetwork(retryListener: RetryListener?) {
        if (mErrorFrag == null || !ErrorFrag.isFragVisible) {
            ErrorFrag.isFragVisible = true
            mErrorFrag = ErrorFrag.getInstance()
            mErrorFrag!!.addOnRetryListener(retryListener)
            App.currentActivity.supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, mErrorFrag!!)
                .addToBackStack(null)
                .commit()
        } else {
            mErrorFrag!!.addOnRetryListener(retryListener)
        }
    }
}