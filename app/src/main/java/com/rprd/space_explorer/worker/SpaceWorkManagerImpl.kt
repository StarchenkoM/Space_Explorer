package com.rprd.space_explorer.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SpaceWorkManagerImpl @Inject constructor(context: Context) : SpaceWorkManager {

    private var workManager = WorkManager.getInstance(context)

    override fun startNewDailyPhotoWorker() {
        starPeriodicWorker<DailyPhotoLoaderWorker>()
    }

/*    override fun startNewDailyPhotoWorker(date: String) {
        starPeriodicWorker<DailyPhotoLoaderWorker>(
                workDataOf(DailyPhotoLoaderWorker.DATE to date)
        )
    }*/


    private inline fun <reified W : ListenableWorker> starOneTimeWorker(inputData: Data) {
        val networkConstraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request = OneTimeWorkRequestBuilder<W>()
                .setConstraints(networkConstraint)
                .setInputData(inputData)
                .build()

        workManager.enqueue(request)
    }

    private inline fun <reified W : ListenableWorker> starPeriodicWorker() {
        val networkConstraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request = PeriodicWorkRequestBuilder<W>(4, TimeUnit.HOURS)
                .setConstraints(networkConstraint)
                .build()

        workManager.enqueue(request)
    }
}