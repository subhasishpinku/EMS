package com.pmit.ems.ui.frag

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.pmit.ems.R
import com.pmit.ems.adapter.LeadCountAdapter
import com.pmit.ems.api.ApiToken
import com.pmit.ems.api.Config
import com.pmit.ems.databinding.AdmissionListBinding
import com.pmit.ems.manager.net.ApiService.activity
import com.pmit.ems.manager.net.observer.NetworkObserverFragment
import com.pmit.ems.model.LeadCount
import com.pmit.ems.model.LeadModel
import com.pmit.ems.model.Leadfollowuphistory
import com.pmit.ems.ui.LeadDetails
import com.pmit.ems.ui.MainActivity
import com.pmit.ems.ui.SharedPrefManagerLogin
import com.pmit.ems.ui.VolleySingleton
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class AdmissionListHome : NetworkObserverFragment(), ViewTreeObserver.OnScrollChangedListener,
    View.OnClickListener {
    private lateinit var mBinding: AdmissionListBinding
    private var mScrollY = 0
    private var mCurrentTime = System.currentTimeMillis()
    var lv1: LinearLayout? = null
    private var lock = true
    var search_Id : SearchView?  = null
    var ImgZip : ImageView?  = null
    var led_tv3 : TextView?  = null
    var load_more : TextView?  = null
    var recyclerCount : RecyclerView?  = null
    var recyclerLead : RecyclerView?  = null
    var clickcount = 1
    var searchName = ""
    var listtype = "Admission Projection"
    var leadModels: List<LeadModel>? = null
    var leadCounts: List<LeadCount>? = null
    var leadfollowuphistories: List<Leadfollowuphistory>? = null
    var arrlist2 = ArrayList<Leadfollowuphistory>()

    var arrlist = ArrayList<LeadModel>()
    var arrlist1 = ArrayList<LeadCount>()
    var jsonArrayleadlist: JSONArray? = null
    var progressBarHolder: FrameLayout? = null
    var collegename = ""
    var id: String? = null
    var course_id:String? = null
    var courseName: String? = null
    var sessionname:String? = null
    private var courseModelArrayList: java.util.ArrayList<LeadModel>? = null
    var homeItemViewAdapter: LeadViewAdapter? = null
    var followup: YellowListHome.LeadViewAdapter2? = null
    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 10
        private const val TAG = "GreenListHome"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = AdmissionListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init() {
        mBinding.homeHeaderNavBtn.setOnClickListener(this)
        led_tv3 = mBinding.ledTv3.findViewById(R.id.led_tv3) as TextView
        load_more = mBinding.loadMore.findViewById(R.id.load_more) as TextView
        search_Id = mBinding.searchId.findViewById(R.id.search_Id) as SearchView
        ImgZip =mBinding.ImgZip.findViewById(R.id.ImgZip) as ImageView
        recyclerCount = mBinding.recyclerCount.findViewById(R.id.recyclerCount) as RecyclerView
        recyclerLead = mBinding.recyclerLead.findViewById(R.id.recyclerLead) as RecyclerView
        progressBarHolder = mBinding.progressBarHolder.findViewById<View>(R.id.progressBarHolder) as FrameLayout
        leadModels = java.util.ArrayList()
        leadCounts = java.util.ArrayList()
        arrlist = java.util.ArrayList()
        arrlist1 = java.util.ArrayList()
        leadfollowuphistories = java.util.ArrayList()
        pending_followup()
        load_more!!.setOnClickListener {
            searchName = ""
            clickcount = clickcount + 1
            println("Load_more $clickcount")
            ShowLead(clickcount.toString(), searchName,listtype)
        }

        search_Id!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                filter(newText);
                return false
            }
        })
        ImgZip!!.setOnClickListener {
            searchName = search_Id!!.query.toString()
             (leadModels as java.util.ArrayList<LeadModel>).clear()
            clickcount = 1
            ShowLead(clickcount.toString(), searchName,listtype)
        }
        recyclerLead!!.setHasFixedSize(true)
        recyclerLead!!.layoutManager = LinearLayoutManager(activity)
//        recyclerCount.setHasFixedSize(true);
//        recyclerCount.setLayoutManager(new LinearLayoutManager(this));
        courseModelArrayList = java.util.ArrayList()
        ShowLead(clickcount.toString(), searchName, listtype)
        pending_followup()
    }
    fun pending_followup() {
        val user = SharedPrefManagerLogin.getInstance(activity).user
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Config.BASEURL2 + "pending_followup",
            Response.Listener { response ->
                println("pending_followup$response")
                try {
                    //converting the string to json array object
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    if (status == "true") {
                        val followup = obj.getString("followup")
                        led_tv3!!.text = followup
                    } else {
                        Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show()
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
                return HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                //              headers.put("Content-Type", "application/json");
                headers["Authorization"] = "Bearer " + user.token
                println("pending_followup" + user.token)
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
    fun leadCourseCount() {
        progressBarHolder!!.visibility = View.VISIBLE
        val user = SharedPrefManagerLogin.getInstance(activity).user
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, Config.BASEURL2 + "get_lead_course_count",
            Response.Listener { response ->
                println("course_count$response")
                try {
                    //converting the string to json array object
                    val obj = JSONObject(response)
                    val status = obj.getString("status")
                    progressBarHolder!!.visibility = View.GONE
                    if (status == "true") {
                        jsonArrayleadlist = obj.getJSONArray("leadlist")
                        for (i in 0 until jsonArrayleadlist!!.length()) {
                            val jsonObject = jsonArrayleadlist!!.getJSONObject(i)
                            val total_leads = jsonObject.getString("total_leads")
                            val course_name = jsonObject.getString("course_name")
                            println("course_count $total_leads $course_name")
                            (leadCounts!! as ArrayList).add(
                                LeadCount(
                                    total_leads, course_name
                                )
                            )
                            arrlist1.addAll(leadCounts!!)
                            val adapter = LeadCountAdapter(activity, leadCounts)
                            recyclerCount!!.adapter = adapter
                            recyclerCount!!.layoutManager = LinearLayoutManager(
                                activity,
                                LinearLayoutManager.HORIZONTAL, true
                            )
                            //                                    PagerSnapHelper snapHelper = new PagerSnapHelper();
                            //                                    snapHelper.attachToRecyclerView(recyclerCount);
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Empty Count List",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { progressBarHolder!!.visibility = View.GONE }
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
    fun ShowLead(clickcount: String, searchName: String?, listtype: String?) {
        println("searchName$searchName")
        progressBarHolder!!.visibility = View.VISIBLE
        val user = SharedPrefManagerLogin.getInstance(activity).user
        val call = ApiToken.getInstance().getLead(clickcount, searchName, listtype)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                try {
                    val res = response.body()!!.string()
                    println("get_lead$res")
                    val obj = JSONObject(res)
                    val status = obj.getString("status")
                    // JSONObject Object = obj.getJSONObject("leadlist");
//                    System.out.println("get_lead_status"+Object);

//                            String currentpage = Object.getString("current_page");
                    progressBarHolder!!.visibility = View.GONE
                    if (status == "true") {
                        leadCourseCount()
                        jsonArrayleadlist = obj.getJSONArray("leadlist")
                        for (i in 0 until jsonArrayleadlist!!.length()) {
                            val jsonObject = jsonArrayleadlist!!.getJSONObject(i)
                            println("leadlist1$jsonObject")
                            id = jsonObject.getString("id")
                            val fname = jsonObject.getString("fname")
                            val mobile_no = jsonObject.getString("mobile_no")
                            val whatsapp_no = jsonObject.getString("whatsapp_no")
                            val created_at = jsonObject.getString("created_at")
                            val lead_followups = jsonObject.getString("lead_followups")
                            val email = jsonObject.getString("email")
                            val college_id = jsonObject.getString("college_id")
                            val status1 = jsonObject.getString("status")
                            val session_id = jsonObject.getString("session_id")
                            val source_id = jsonObject.getString("source_id")
                            val course_id = jsonObject.getString("course_id")
                            val counsellorname = jsonObject.getString("counsellorname")
                            val check_payment_approval_status =
                                jsonObject.getString("check_payment_approval_status")
                            courseName = jsonObject.getString("coursename")
                            sessionname = jsonObject.getString("sessionname")
                            val check_srn = jsonObject.getString("check_srn")
                            val srn = jsonObject.getString("srn")
                            val check_booking = jsonObject.getString("check_booking")
                            val srn_flag = jsonObject.getString("srn_flag")
                            val rejectform = jsonObject.getString("rejectform")
                            println("searchName$rejectform")
                            courseName = if (courseName == "null") {
                                "NA"
                            } else {
                                jsonObject.getString("coursename")
                            }
                            sessionname = if (sessionname == "null") {
                                "NA"
                            } else {
                                jsonObject.getString("sessionname")
                            }
                            (leadModels!! as ArrayList).add(
                                LeadModel(
                                    id,
                                    fname,
                                    mobile_no,
                                    whatsapp_no,
                                    email,
                                    course_id,
                                    source_id,
                                    session_id,
                                    lead_followups,
                                    status1,
                                    created_at,
                                    college_id,
                                    courseName,
                                    sessionname,
                                    counsellorname,
                                    check_payment_approval_status,
                                    check_srn,
                                    srn,
                                    srn_flag,
                                    rejectform,
                                    check_booking
                                )
                            )
                            arrlist.addAll(leadModels!!)
                            courseModelArrayList!!.addAll(leadModels!!)
                            homeItemViewAdapter = LeadViewAdapter(activity!!, leadModels!!)
                            recyclerLead!!.adapter = homeItemViewAdapter

                        }
                    } else {
                        Toast.makeText(activity, "Empty source", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBarHolder!!.visibility = View.GONE
            }
        })
    }
    class LeadViewAdapter(private val mCtx: Context, private val leadModels: List<LeadModel>) :
        RecyclerView.Adapter<LeadViewAdapter.ViewHolder>() {

        var listview = 0
        var leadfollowuphistories: List<Leadfollowuphistory>? = null
        var arrlist2 = ArrayList<Leadfollowuphistory>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.leadmodel_activity, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            @SuppressLint("RecyclerView") position: Int
        ) {
            val leadModel = leadModels[position]
            listview = position
            leadfollowuphistories = java.util.ArrayList()
            holder.led_tv.text = leadModel.fname
            holder.led_tv2.text = leadModel.mobile_no
            holder.led_tv4.text = leadModel.whatsapp_no
            holder.led_tv5.text = leadModel.course_name
            holder.councilername.text = leadModel.counsellorname
            //            holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(leadModel.getFlagImg()));
            holder.date_tv1.text = leadModel.created_at
            //            holder.led_tv5.setBackgroundColor(Color.parseColor(leadModel.getColorCode()));
            println("getLead_followups" + "  " + leadModel.lead_followups)
            holder.color_image.visibility = View.VISIBLE
            holder.color_image.setImageDrawable(mCtx.resources.getDrawable(R.drawable.admisionlist))
            var status = leadModel.status1
            if (status == "active") {
                if (leadModel.lead_followups == "1") {
                    holder.Img_Id.setImageDrawable(mCtx.resources.getDrawable(R.drawable.tag3))
                }
                if (leadModel.lead_followups == "0") {
//                    holder.Img_Id.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.tag4));
                    holder.Img_Id.setImageDrawable(mCtx.resources.getDrawable(R.drawable.tag_gray))
                }
            } else {
                holder.Img_Id.setImageDrawable(mCtx.resources.getDrawable(R.drawable.tag4))
            }
            holder.Img_Id.setOnClickListener {
                val followups = leadModel.lead_followups
                println("followups$followups")
                if (followups == "1" && leadModel.status1 == "active") {
                    var editLeadId = leadModel.id
                    (leadfollowuphistories as java.util.ArrayList<Leadfollowuphistory>).clear()
                    lead_followup_history(editLeadId)
                }
            }
            holder.rv.setOnClickListener {
                var name = holder.led_tv.text.toString()
                var mPhone = holder.led_tv2.text.toString()
                var  wPhone = holder.led_tv4.text.toString()
                var  course = holder.led_tv5.text.toString()
                var date = holder.date_tv1.text.toString()
                var email = leadModel.email
                var editLeadId = leadModel.id
                var courseIds = leadModel.course_id
                var  collageId = leadModel.college_id
                var status = leadModel.status1
                var  session_id = leadModel.session_id
                var source_id = leadModel.source_id
                var  course_id = leadModel.course_id
                var sessionname_id = leadModel.sessionname
                var check_payment_approval_status = leadModel.check_payment_approval_status
                val check_srn = leadModel.check_srn
                val srn = leadModel.srn
                val srn_flag = leadModel.srn_flag
                val rejectform = leadModel.rejectform
                val check_booking = leadModel.check_booking
                share_lead_info(
                    name,
                    mPhone,
                    wPhone,
                    course,
                    date,
                    email,
                    editLeadId,
                    courseIds,
                    collageId,
                    status,
                    session_id,
                    source_id,
                    course_id,
                    sessionname_id,
                    check_payment_approval_status,
                    check_srn,
                    srn,
                    srn_flag,
                    rejectform,
                    check_booking
                )
            }
        }


        override fun getItemCount(): Int {
            return leadModels.size
        }
        fun share_lead_info(
            name: String?,
            mPhone: String?,
            wPhone: String?,
            course: String?,
            date: String?,
            email: String?,
            editLeadId: String,
            courseIds: String?,
            collageId: String?,
            status: String,
            session_id: String?,
            source_id: String?,
            course_id: String?,
            sessionname_id: String?,
            check_payment_approval_status: String?,
            check_srn: String?,
            srn: String?,
            srn_flag: String?,
            rejectform: String?,
            check_booking: String?
        ) {

            var collegename:String? = null
            val user = SharedPrefManagerLogin.getInstance(mCtx).user
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, Config.BASEURL2 + "share_lead_info",
                Response.Listener { response ->
                    println("share_lead_info$response")
                    try {
                        val obj = JSONObject(response)
                        val status1 = obj.getString("status")
                        val error = obj.getString("error")
                        if (status1 == "true") {
                            val personaldetail = obj.getJSONArray("personal_detail")
                            for (i in 0 until personaldetail.length()) {
                                val jsonObject = personaldetail.getJSONObject(i)
                                val id = jsonObject.getString("id")
                                val fname = jsonObject.getString("fname")
                                val mname = jsonObject.getString("mname")
                                val lname = jsonObject.getString("lname")
                                val mobile_no = jsonObject.getString("mobile_no")
                                val whatsapp_no = jsonObject.getString("whatsapp_no")
                                val email = jsonObject.getString("email")
                                val coursename = jsonObject.getString("course_name")
                                collegename = jsonObject.getString("collegename")
                            }
                            //                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                            if (status == "active") {
                                val intent = Intent(mCtx, LeadDetails::class.java)
                                intent.putExtra("name", name)
                                intent.putExtra("mPhone", mPhone)
                                intent.putExtra("wPhone", wPhone)
                                intent.putExtra("course", course)
                                intent.putExtra("date", date)
                                intent.putExtra("email", email)
                                intent.putExtra("editLeadId", editLeadId)
                                intent.putExtra("courseIds", courseIds)
                                intent.putExtra("collageId", collageId)
                                intent.putExtra("collegename", collegename)
                                intent.putExtra("session_id", session_id)
                                intent.putExtra("source_id", source_id)
                                intent.putExtra("course_id", course_id)
                                intent.putExtra("sessionname_id", sessionname_id)
                                intent.putExtra(
                                    "check_payment_approval_status",
                                    check_payment_approval_status
                                )
                                intent.putExtra("check_srn", check_srn)
                                intent.putExtra("srn", srn)
                                intent.putExtra("srn_flag", srn_flag)
                                intent.putExtra("rejectform", rejectform)
                                intent.putExtra("check_booking", check_booking)
                                intent.putExtra("ListFlag", "admission-projection")
                                mCtx.startActivity(intent)
                                activity!!.overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                            } else {
                                Toast.makeText(
                                    mCtx,
                                    "This is a Inactive Lead",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(mCtx, error, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = java.util.HashMap()
                    params["lead_id"] = editLeadId
                    println("share_lead_info$editLeadId")
                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = java.util.HashMap<String, String>()
                    //                   headers.put("Content-Type", "application/json");
                    headers["Authorization"] = "Bearer " + user.token
                    println("share_lead_info" + user.token)
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
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val led_tv: TextView
            val led_tv2: TextView
            val led_tv4: TextView
            val led_tv5: TextView
            val date_tv1: TextView
            val councilername: TextView
            val Img_Id: ImageView
            var rv: RelativeLayout
            val color_image: ImageView

            init {
                led_tv = view.findViewById<View>(R.id.led_tv) as TextView
                led_tv2 = view.findViewById<View>(R.id.led_tv2) as TextView
                led_tv4 = view.findViewById<View>(R.id.led_tv4) as TextView
                led_tv5 = view.findViewById<View>(R.id.led_tv5) as TextView
                date_tv1 = view.findViewById<View>(R.id.date_tv1) as TextView
                Img_Id = view.findViewById<View>(R.id.Img_Id) as ImageView
                color_image = view.findViewById<View>(R.id.color_image) as ImageView
                rv = view.findViewById<View>(R.id.rv) as RelativeLayout
                councilername = view.findViewById<View>(R.id.councilername) as TextView
            }
        }
        private fun lead_followup_history(editLeadId: String?) {
            val user = SharedPrefManagerLogin.getInstance(mCtx).user
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, Config.BASEURL2 + "lead_followup_history",
                Response.Listener { response ->
                    println("lead_followup_history$response")
                    try {
                        //converting the string to json array object
                        val obj = JSONObject(response)
                        val status = obj.getString("status")
                        if (status == "true") {
                            val jsonArray = obj.getJSONArray("followupDetails")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val lead_followups = jsonObject.getString("lead_followups")
                                val comment = jsonObject.getString("comment")
                                val comment_date = jsonObject.getString("comment_date")
                                val lead_stage = jsonObject.getString("lead_stage")
                                (leadfollowuphistories!! as ArrayList).add(
                                    Leadfollowuphistory(
                                        lead_followups,
                                        comment,
                                        comment_date,
                                        lead_stage
                                    )
                                )
                                arrlist2.addAll(leadfollowuphistories!!)
                                createDialog5()
                            }
                        } else {
                            Toast.makeText(
                                mCtx,
                                "No Follow-up to Show",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = java.util.HashMap()
                    params["lead_id"] = editLeadId!!
                    println("lead_followup_history$params")
                    return params
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = java.util.HashMap<String, String>()
                    //              headers.put("Content-Type", "application/json");
                    headers["Authorization"] = "Bearer " + user.token
                    println("lead_followup_history" + user.token)
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
            val volleySingleton = VolleySingleton.getInstance(mCtx)
            stringRequest.setShouldCache(false)
            volleySingleton.addToRequestQueue(stringRequest)
        }
        fun createDialog5(): AlertDialog? {
            val builder = AlertDialog.Builder(mCtx)
            val inflater =
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.flowup_alert, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            //        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            val recyclerflowups = dialogView.findViewById<View>(R.id.recyclerflowups) as RecyclerView
            recyclerflowups.setHasFixedSize(true)
            recyclerflowups.layoutManager = LinearLayoutManager(mCtx)
            var followup = leadfollowuphistories?.let { LeadViewAdapter2(mCtx, it) }
            recyclerflowups.adapter = followup
            //        ((ImageView)dialogView.findViewById(R.id.openCamera)).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//                    ActivityCompat.requestPermissions(LeadTableActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
//                } else {
//                }
//                dialog.dismiss();
//            }
//        });
//        ((ImageView)dialogView.findViewById(R.id.openGallery)).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
//                    ActivityCompat.requestPermissions(LeadTableActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
//                } else {
//
//                }
//
//                dialog.dismiss();
//            }
//        });
////        ((ImageView)dialogView.findViewById(R.id.cropimage)).setVisibility(View.GONE);
//        ((ImageView)dialogView.findViewById(R.id.cropimage)).setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                dialog.dismiss();
//
//            }
//        });
            builder.setView(dialogView)
            dialog.show()
            //        dialog.dismiss();
            return dialog
        }
    }





    class LeadViewAdapter2(
        private val mCtx: Context,
        private val leadfollowuphistories: List<Leadfollowuphistory>
    ) :
        RecyclerView.Adapter<LeadViewAdapter2.ViewHolder>() {
        var listview = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.lead_followup_history_layout, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            @SuppressLint("RecyclerView") position: Int
        ) {
            val leadfollowuphistoriess = leadfollowuphistories[position]
            listview = position
            holder.follwup.text = leadfollowuphistoriess.lead_followups
            holder.follwupdate.text = leadfollowuphistoriess.comment_date
            holder.comment.text = leadfollowuphistoriess.comment
            //            holder.lead_stage.setText(leadfollowuphistoriess.getLead_stage());
        }

        override fun getItemCount(): Int {
            return leadfollowuphistories.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val follwup: TextView
            val follwupdate: TextView
            val comment: TextView

            init {
                follwup = view.findViewById<View>(R.id.follwup) as TextView
                follwupdate = view.findViewById<View>(R.id.follwupdate) as TextView
                comment = view.findViewById<View>(R.id.comment) as TextView
                //                lead_stage = (TextView) view.findViewById(R.id.lead_stage);
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
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

    override fun onScrollChanged() {
        TODO("Not yet implemented")
    }
}