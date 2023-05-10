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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tools.practicecompose.MainActivity
import com.tools.practicecompose.R
import com.tools.practicecompose.feature.domain.model.Note
import com.tools.practicecompose.feature.domain.use_case.NoteUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var noteUseCase: NoteUseCases

    private var lastNotificationJob: Job? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_NOTE, Note::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_NOTE)
        }
        setNotify(context, note)
    }

    private fun setNotify(context: Context?, note: Note?) {
        context ?: return
        note ?: return
        lastNotificationJob?.cancel()
        lastNotificationJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                noteUseCase.getLevelMapUseCase.invoke()
                    .cancellable()
                    .collectLatest { map ->
                        val colorInt = map[note.level]?.colorInt ?: 0x000000
                        Log.d(TAG, "colorInt: $colorInt")
                        pushNotifyToSystem(
                            context = context, note = note, colorInt = colorInt
                        )
                    }
            } catch (e: Exception) {
                Log.e(TAG, "cancelled when catch error: ", e)
            } finally {
                cancel()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun pushNotifyToSystem(context: Context, note: Note, colorInt: Int) {
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
            color = colorInt
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
        } else {
            Toast.makeText(context, "Please turn on notification", Toast.LENGTH_LONG).show()
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