package com.tools.practicecompose.feature.repository.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.tools.practicecompose.BuildConfig
import com.tools.practicecompose.feature.domain.model.Note
import java.time.LocalDateTime
import java.time.ZoneId

class MyAlarmScheduler(
    private val context: Context
): AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    override fun schedule(item: Note) {
        val triggerAtMillis = if (BuildConfig.DEBUG) {
            LocalDateTime.now().plusSeconds(10)
                .atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        } else {
            item.remindTime!!
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_NOTE, item)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: Note) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}

const val EXTRA_NOTE = "EXTRA_NOTE"