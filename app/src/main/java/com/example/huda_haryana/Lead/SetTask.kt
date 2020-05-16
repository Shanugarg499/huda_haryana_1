package com.example.huda_haryana.Lead

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.allyants.notifyme.NotifyMe
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivitySetTaskBinding
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
        datetime.simpleDateMonthAndDayFormat = SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
        binding.selectTimeSetTask.setOnClickListener {
            datetime.show(supportFragmentManager, "Set")
        }
        binding.settaskToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        var d2: Date? = null
        val mref = FirebaseDatabase.getInstance().getReference("leads").child(id!!).child("Alarm")
        val mref2 = FirebaseDatabase.getInstance().getReference("Tasks")
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
            val data = AlarmData(desctxt, d.toString(), name!!)
            val key2 = mref2.push().key
            val key = mref.push().key
            mref2.child(key2!!).setValue(data)
            mref.child(key!!).setValue(data)
            if (d2 != null) {
                Toast.makeText(this, "SET", Toast.LENGTH_SHORT).show()
                val notifyMe = NotifyMe.Builder(applicationContext)
                        .title(name)
                        .content(desctxt)
                        .color(255, 0, 0, 255)
                        .led_color(255, 255, 255, 255)
                        .time(d2)
                        .addAction(Intent(this, AddTask::class.java), "DONE")
                        .large_icon(R.drawable.small_logo)
                        .small_icon(R.drawable.small_logo)  
                        .build()
            }
        }
    }
}
