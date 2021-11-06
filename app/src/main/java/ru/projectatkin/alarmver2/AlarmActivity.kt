package ru.projectatkin.alarmver2

import android.media.Ringtone
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.w3c.dom.Text

class AlarmActivity : AppCompatActivity() {
    lateinit var ringtone: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        var notificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        var ringtone = RingtoneManager.getRingtone(this, notificationRingtone)
        if (ringtone == null) {
            notificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            this.ringtone = RingtoneManager.getRingtone(this, notificationRingtone)
        }
        if (ringtone != null) {
            ringtone.play()
        }
    }

    override fun onDestroy() {
        if(ringtone != null && ringtone.isPlaying) {
            ringtone.stop()
        }
        super.onDestroy()
    }
}