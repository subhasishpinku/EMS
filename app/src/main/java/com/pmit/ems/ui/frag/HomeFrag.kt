package com.pmit.ems.ui.frag
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.pmit.ems.R
import com.pmit.ems.api.Config
import com.pmit.ems.databinding.FragHomeBinding
import com.pmit.ems.manager.App
import com.pmit.ems.manager.net.observer.NetworkObserverFragment
import com.pmit.ems.ui.*
import com.pmit.ems.ui.frag.widget.CustomScrollView
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.abs

class HomeFrag : NetworkObserverFragment(), ViewTreeObserver.OnScrollChangedListener,
    View.OnClickListener {
    private lateinit var mBinding: FragHomeBinding
    private var mScrollY = 0
    private var mCurrentTime = System.currentTimeMillis()
    var lv1: LinearLayout? = null
    private var lock = true
    var tv : TextView?  = null
    var admins : TextView?  = null
    var tv22 : TextView?= null
    var tv33 :  TextView?= null
    var user_profile_photo1 : RoundedImageView?= null
    var leadmange_corner1 : RelativeLayout?= null
    var leadmange_corner2 : RelativeLayout?= null
    var leadmange_corner13 : RelativeLayout?= null
    var leadmange_corner4 : RelativeLayout?= null
    var tv_1 : TextView?  = null
    var tv_13 : TextView?  = null
    var tv_4 : TextView?  = null

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 10
        private const val TAG = "HomeFrag"
    }
    private val mViewAllBtnListener = View.OnClickListener { v ->
        var map: HashMap<String, String>? = null
//      var bundles: ArrayList<Course>? = null
        val title: String
        when (v?.id) {
            else -> {
                return@OnClickListener
            }
        }
        val bundle = Bundle()
        bundle.putString(App.TITLE, title)
//        if (bundles != null) {
//            bundle.putParcelableArrayList(App.COURSES, bundles)
//        }else{
//            bundle.putSerializable(App.FILTERS, map)
//        }
//        val frag = ClassesFrag()
//        frag.arguments = bundle
//        (activity as MainActivity).transact(frag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init() {
        (activity as MainActivity).hideToolbar(
            App.getShapeFromColor(
                mBinding.homeHeaderContainer,
                R.color.blue_naviBar,
                20f
            )
        )
        (activity as MainActivity).setContainer(false)
        (activity as MainActivity).showHome()
        (activity as MainActivity).uncheckAllItems()
        (activity as MainActivity).setDrawerLock(false)
//      App.initHeader(mBinding.homeStatusBar)
        mBinding.homeHeaderNavBtn.setOnClickListener(this)
        setScrollListener()
        lv1 = (activity as MainActivity).findViewById<View>(R.id.lv1) as LinearLayout
        lv1!!.setOnClickListener {
            val i = Intent(context, LeadManagementActivity::class.java)
            startActivity(i)
                (activity as MainActivity).overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
        val user: UserLoginData = SharedPrefManagerLogin.getInstance(context).getUser()
        tv = (activity as MainActivity).findViewById<View>(R.id.tv) as TextView
        admins = (activity as MainActivity).findViewById<View>(R.id.admins) as TextView
        tv22 = (activity as MainActivity).findViewById<View>(R.id.tv22) as TextView
        tv33 = (activity as MainActivity).findViewById<View>(R.id.tv33) as TextView
        user_profile_photo1 = (activity as MainActivity).findViewById<View>(R.id.user_profile_photo1) as RoundedImageView
        leadmange_corner1 = (activity as MainActivity).findViewById<View>(R.id.leadmange_corner1) as RelativeLayout
        leadmange_corner2 = (activity as MainActivity).findViewById<View>(R.id.leadmange_corner2) as RelativeLayout
        leadmange_corner13 = (activity as MainActivity).findViewById<View>(R.id.leadmange_corner13) as RelativeLayout
        leadmange_corner4 = (activity as MainActivity).findViewById<View>(R.id.leadmange_corner4) as RelativeLayout

        tv_1 = (activity as MainActivity).findViewById<View>(R.id.tv_1) as TextView
        tv_13 = (activity as MainActivity).findViewById<View>(R.id.tv_13) as TextView
        tv_4 = (activity as MainActivity).findViewById<View>(R.id.tv_4) as TextView
        tv!!.text = user.name
        admins!!.text = user.dept_name
        tv22!!.text = user.user_code+"| Dept :"+user.dept_name
        tv33!!.text = user.email
        if (user.user_image.equals("null")){
            Glide.with(requireContext())
                .load(R.drawable.user_image_empty)
                .into(user_profile_photo1!!)
        }
        else{
            var u_profile : String = "https://pmitgroupofcolleges.org/public/profile/"+user.user_image
            Glide.with(requireContext())
                .load(u_profile)
                .into(user_profile_photo1!!)
        }
        if (user.dept_name.equals("Admission")){

            lv1!!.visibility = View.VISIBLE
            leadmange_corner1!!.visibility = View.VISIBLE
            leadmange_corner2!!.visibility = View.VISIBLE
            leadmange_corner13!!.visibility = View.VISIBLE
            leadmange_corner4!!.visibility = View.VISIBLE

        }
        else{
            lv1!!.visibility = View.GONE
            leadmange_corner1!!.visibility = View.GONE
            leadmange_corner2!!.visibility = View.VISIBLE
            leadmange_corner13!!.visibility = View.GONE
            leadmange_corner4!!.visibility = View.GONE
        }
        leadmange_corner1!!.setOnClickListener {
            val i = Intent(context, YellowListActivity::class.java)
            startActivity(i)
            (activity as MainActivity).overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
        leadmange_corner13!!.setOnClickListener {
            val i = Intent(context, GreenListActivity::class.java)
            startActivity(i)
            (activity as MainActivity).overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
        leadmange_corner4!!.setOnClickListener {
            val i = Intent(context, AdmissionListActivity::class.java)
            startActivity(i)
            (activity as MainActivity).overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
        LeadCount()
    }
    fun LeadCount() {
        val user = SharedPrefManagerLogin.getInstance(activity).user
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Config.BASEURL2 + "lead_count",
            Response.Listener { response ->
                println("get_lead_course_count$response")
                try {
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    if (status == "true") {
                        val total_leads = obj.getString("total_leads")
                        val yellow_list = obj.getString("yellow_list")
                        val greenlist = obj.getString("greenlist")
                        val admissionprojection = obj.getString("admissionprojection")
                        val error = obj.getString("error")
                        tv_1!!.text = yellow_list
                        tv_13!!.text = greenlist
                        tv_4!!.text = admissionprojection
                    } else {
                        Toast.makeText(activity, "Empty Count", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                //                params.put("", "");
                return java.util.HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = java.util.HashMap<String, String>()
                //              headers.put("Content-Type", "application/json");
                headers["Authorization"] = "Bearer " + user.token
                println("leadCourseCount" + user.token)
                return headers
            }
        }
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        val volleySingleton = VolleySingleton.getInstance(activity)
        stringRequest.setShouldCache(false)
        volleySingleton.addToRequestQueue(stringRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeScrollListener()
    }

    private fun setScrollListener() {
        mBinding.homeScrollView.setOnScrollChangeListener(object :
            CustomScrollView.ScrollStatusChangeListener {
            override fun onScrollChanged(type: CustomScrollView.ScrollType) {
                if (type == CustomScrollView.ScrollType.DOWN) {

                } else {

                }
            }
        })
    }

    private fun removeScrollListener() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mBinding.homeScrollView.viewTreeObserver.removeOnScrollChangedListener(this)
        }
    }

    override fun onScrollChanged() {
        val scrollY = mBinding.homeScrollView.scrollY

        var difference = 100
        if (lock) {
            difference = 300
        }

        if (System.currentTimeMillis() - mCurrentTime >= difference && abs(mScrollY - scrollY) > 10) {
            mCurrentTime = System.currentTimeMillis()
            lock = false

            if (scrollY > 0) {
                if (mScrollY < scrollY) {



                }

                mScrollY = scrollY
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.homeHeaderNavBtn -> {
                (activity as MainActivity).openDrawer()
            }

        }
    }
    override fun onBackPressed() {
        if (backpressedlistener != null) {
            backpressedlistener.onBackPressed()

        }
    }
}