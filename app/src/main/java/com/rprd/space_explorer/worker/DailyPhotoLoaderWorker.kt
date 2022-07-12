package com.rprd.space_explorer.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rprd.space_explorer.R
import com.rprd.space_explorer.domain.dailyphotousecases.CheckNewDailyPhotoUseCase
import com.rprd.space_explorer.presentation.activities.MainActivity
import com.rprd.space_explorer.utils.DAILY_PHOTO_NOTIFICATION_KEY
import com.rprd.space_explorer.utils.TimeUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class DailyPhotoLoaderWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val workerParameters: WorkerParameters,
        private val checkNewDailyPhotoUseCase: CheckNewDailyPhotoUseCase,
) : CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var timeUtil: TimeUtil

    override suspend fun doWork(): Result {
        return checkNewPhoto()
    }

    private suspend fun checkNewPhoto(): Result {
        val date = timeUtil.getTodayDate()
        val newPhotoExists = checkNewDailyPhotoUseCase.checkNewDailyPhoto(date)
        return if (newPhotoExists) {
            showNotification()
            Result.success()
        } else {
            Result.retry()
        }
    }

    private fun showNotification() {
        createNotificationChannel()
        sendNotification()
    }

    private fun sendNotification() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.putExtra(DAILY_PHOTO_NOTIFICATION_KEY, "DAILY_PHOTO_NOTIFICATION_KEY")
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = provideNotificationBuilder(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    private fun provideNotificationBuilder(pendingIntent: PendingIntent) =
            NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.header_icon_to_left_64_px)
                    .setContentTitle(context.getString(R.string.notification_title))
                    .setContentText(context.getString(R.string.notification_text))
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(context.getString(R.string.notification_text)))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val TAG = "Worker"
        const val DATE = "DATE"
        private var CHANNEL_ID = "Daily_channel"
        private var notificationId = 100
    }
}