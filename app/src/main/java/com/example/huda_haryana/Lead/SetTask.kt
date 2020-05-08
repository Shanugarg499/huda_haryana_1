package com.example.huda_haryana.Lead

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivitySetTaskBinding
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class SetTask : AppCompatActivity() {
    lateinit var binding: ActivitySetTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_set_task)
        val datetime=SwitchDateTimeDialogFragment.newInstance("Set Reminder","SET","CANCEL")
        datetime.startAtCalendarView()
        datetime.setHighlightAMPMSelection(false)
        datetime.set24HoursMode(false)
        datetime.simpleDateMonthAndDayFormat= SimpleDateFormat("MMM dd",java.util.Locale.getDefault())
        binding.selectTimeSetTask.setOnClickListener {
            datetime.show(supportFragmentManager,"Set")
        }
        var d:Long?
        datetime.setOnButtonClickListener(object :SwitchDateTimeDialogFragment.OnButtonClickListener{
            override fun onPositiveButtonClick(date: Date?) {
                Toast.makeText(this@SetTask,date.toString(),Toast.LENGTH_SHORT).show()
                d=date?.time
                setalarm(d)
               // binding.dateTime.setText(date?.date.toString()+date?.month.toString()+date?.year.toString())
            }

            override fun onNegativeButtonClick(date: Date?) {

            }

        })

    }

    private fun setalarm(d: Long?) {
        val alarmanager=  getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent= Intent(this,AddAlarm::class.java)
        val pendingIntent=PendingIntent.getBroadcast(this,0,intent,0)
        alarmanager.setExact(AlarmManager.RTC_WAKEUP,d!!,pendingIntent)
        Toast.makeText(this,"Alarm Set",Toast.LENGTH_SHORT).show()
    }
}
