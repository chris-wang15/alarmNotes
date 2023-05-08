package com.tools.practicecompose.feature.repository.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tools.practicecompose.MainActivity
import com.tools.practicecompose.R
import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.model.defaultLevelColorMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_NOTE, Note::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_NOTE)
        }
        setNotify(context, note)
    }

    @SuppressLint("MissingPermission")
    private fun setNotify(context: Context?, note: Note?) {
        context ?: return
        note ?: return
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_ID,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(
            context, context.packageName
        ).apply {
            color = defaultLevelColorMap[note.level]?.colorInt ?: Color.White.toArgb()
            setSmallIcon(R.drawable.baseline_note_24)
            setContentTitle(note.title)
            setContentText(note.content)
            priority = NotificationCompat.PRIORITY_HIGH
            setContentIntent(pendingIntent)
        }
        // Some device wont notify if there is no channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                TAG,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManagerCompat.createNotificationChannel(channel)
        }
        builder.setChannelId(context.packageName)
        if (permissionGranted(context)) {
            Log.i(TAG, "notify $note")
            notificationManagerCompat.notify(TAG, NOTIFICATION_ID, builder.build())
        }
    }

    private fun permissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }
}

private const val TAG = "AlarmReceiver"
private const val REQUEST_ID = 1
private const val NOTIFICATION_ID = 1