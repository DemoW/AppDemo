package lishui.example.app.work

import androidx.work.*
import lishui.example.app.DependencyBackup
import lishui.example.common.util.LogUtils
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by lishui.lin on 20-10-12
 */
class WorkScheduler @Inject constructor() {

    companion object {
        const val TAG = "WorkScheduler"
    }

    fun runOneTimeWork() {

        LogUtils.d(TAG, "runOneTimeWork invoked.")

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        //val oneTimeRequest = OneTimeWorkRequest.from(OneTimeWorker::class.java)
        val oneTimeRequest: WorkRequest = OneTimeWorkRequestBuilder<OneTimeWorker>()
            .addTag("test-one-time-work")
            .setInitialDelay(1L, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setBackoffCriteria(
                // 设置重试避让策略，当返回`Result.retry()`时，
                // backoffDelay会根据backoffPolicy进行重试
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            // 设置传入的数据
            .setInputData(workDataOf(OneTimeWorker.INPUT_DATA_TEST_KEY to "one-time-value"))
            .build()

        //WorkManager.getInstance(Factory.get().appContext).enqueue(oneTimeRequest)
        WorkManager.getInstance(DependencyBackup.get().appContext)
            .enqueueUniqueWork(
                "one-time",
                ExistingWorkPolicy.KEEP,
                oneTimeRequest as OneTimeWorkRequest)
    }

    fun runPeriodicWork() {

        // 灵活时间段从 repeatInterval - flexInterval 开始，一直到间隔结束
        val periodicWorker = PeriodicWorkRequestBuilder<PeriodicWorker>(
            1, TimeUnit.HOURS, // repeatInterval (the period cycle)
            15, TimeUnit.MINUTES  // flexInterval
        ).build()

    }
}