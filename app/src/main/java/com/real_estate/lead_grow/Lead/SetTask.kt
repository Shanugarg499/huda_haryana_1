package com.real_estate.lead_grow.Lead

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.allyants.notifyme.NotifyMe
import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.databinding.ActivitySetTaskBinding
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import java.text.SimpleDateFormat
import java.util.*

lateinit var time: String

lateinit var desctxt: String

class SetTask : AppCompatActivity() {
    lateinit var binding: ActivitySetTaskBinding
    var d: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_task)
        val datetime = SwitchDateTimeDialogFragment.newInstance("Set Reminder", "SET", "CANCEL")
        datetime.startAtCalendarView()
        datetime.setHighlightAMPMSelection(false)
        datetime.set24HoursMode(false)
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        var mainkey = intent.getStringExtra("key")
        val initialdesc = intent.getStringExtra("desc")
        val initialdate = intent.getStringExtra("date")
        if (initialdate != null && initialdesc != null) {
            val dateFormat = SimpleDateFormat("hh:mm a dd-MMM")
            val initialtime = dateFormat.format(Date(initialdate.toLong()))
            binding.dateTime.setText(initialtime)
            binding.descriptionSettask.setText(initialdesc)
        }
        datetime.simpleDateMonthAndDayFormat = SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
        binding.selectTimeSetTask.setOnClickListener {
            datetime.show(supportFragmentManager, "Set")
        }
        binding.settaskToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        var d2: Date? = null
        val acct = GoogleSignIn.getLastSignedInAccount(FacebookSdk.getApplicationContext())
        val mref = FirebaseDatabase.getInstance().getReference("User").child(acct?.id!!).child("Leads").child(id!!).child("Alarm")
        val mref2 = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Tasks")

        datetime.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonClickListener {
            override fun onPositiveButtonClick(date: Date?) {
                Toast.makeText(this@SetTask, date.toString(), Toast.LENGTH_SHORT).show()
                d = date?.time
                d2 = date!!
                val dateFormat = SimpleDateFormat("hh:mm a dd-MMM")
                time = dateFormat.format(Date(d!!))
                binding.dateTime.setText(time)


            }

            override fun onNegativeButtonClick(date: Date?) {

            }

        })
        binding.setalarmSettask.setOnClickListener {
            desctxt = binding.descriptionSettask.text.toString()
            if (desctxt.isEmpty()) {
                binding.descriptionSettask.error = "Enter description"
                binding.descriptionSettask.requestFocus()
                return@setOnClickListener
            }
            if (d2 == null) {
                Toast.makeText(this, "Select Time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (mainkey == null) {
                mainkey = mref.push().key
            }
            NotifyMe.cancel(applicationContext, mainkey)
            val data = AlarmData(desctxt, d.toString(), name!!, mainkey!!, id)
            mref2.child(mainkey!!).setValue(data)
            mref.child(mainkey!!).setValue(data)
            if (d2 != null) {
                val notifyMe = NotifyMe.Builder(applicationContext)
                        .title(name)
                        .content(desctxt)
                        .color(255, 0, 0, 255)
                        .led_color(255, 255, 255, 255)
                        .time(d2)
                        .key(mainkey)
                        .addAction(Intent(this, AddTask::class.java), "DONE")
                        .large_icon(R.drawable.small_logo)
                        .small_icon(R.drawable.small_logo)
                        .build()
                onBackPressed()
            }

        }
    }
}
