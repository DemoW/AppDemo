package lishui.example.app.viewmodel

import android.app.Application
import android.provider.Telephony
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lishui.example.common.util.LogUtils

/**
 * Created by lishui.lin on 20-9-24
 */
class MainViewModel(val app: Application) : AndroidViewModel(app) {

    companion object {
        const val TAG = "MainViewModel"
    }

    fun testCoroutine() {
        viewModelScope.launch {
            val result = async { doWorkThread() }
            LogUtils.d(
                TAG, "test thread: "
                        + Thread.currentThread().name
                        + ", work result=" + result.await()
            )
            doMainThread()
        }
    }

    private suspend fun doWorkThread(): String = withContext(Dispatchers.Default) {
        LogUtils.d(TAG, "doWorkThread start: " + Thread.currentThread().name)
        Thread.sleep(5_000)
        LogUtils.d(TAG, "doWorkThread end: " + Thread.currentThread().name)
        return@withContext "Hello world"
    }

    private suspend fun doMainThread() = withContext(Dispatchers.Main) {
        LogUtils.d(TAG, "doMainThread: " + Thread.currentThread().name)
    }
}