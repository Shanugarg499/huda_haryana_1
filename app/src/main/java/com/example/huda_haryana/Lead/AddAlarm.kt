package com.example.huda_haryana.Lead

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings

class AddAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val mediaPlayer=MediaPlayer.create(context,Settings.System.DEFAULT_NOTIFICATION_URI)
        mediaPlayer.start()
    }

}