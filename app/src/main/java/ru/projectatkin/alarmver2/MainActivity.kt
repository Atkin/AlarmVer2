package ru.projectatkin.alarmver2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var alarmBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        alarmBtn = findViewById(R.id.alarm_button)
        alarmBtn.setOnClickListener { v ->
            val materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Задайте время для будильника")
                .build()

            materialTimePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.MINUTE, materialTimePicker.minute)
                calendar.set(Calendar.HOUR, materialTimePicker.hour)

                val alarmManager: AlarmManager =
                    getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val alarmClockInfo = AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    getAlarmInfoPendingIntent()
                )
                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
                Toast.makeText(this, "Будильник установлен на "+sdf.format(calendar.time), Toast.LENGTH_LONG).show()
            }
            materialTimePicker.show(supportFragmentManager, "tag_picker")
        }
    }

    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val alarmInfoIntent = Intent(this, MainActivity::class.java)
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        return PendingIntent.getActivity(
            this,
            0,
            alarmInfoIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getAlarmActionPendingIntent(): PendingIntent {
        intent = Intent(this, AlarmActivity()::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}