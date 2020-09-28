package lishui.example.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lishui.example.common.util.LogUtils

/**
 * Created by lishui.lin for AppDemo on 20-9-24
 */
class MainViewModel(val app: Application) : AndroidViewModel(app) {

    fun testCoroutine() {
        viewModelScope.launch {
            val result = async { doWorkThread() }
            LogUtils.d(
                content = "test thread: "
                        + Thread.currentThread().name
                        + ", work result=" + result.await()
            )
            doMainThread()
        }
    }

    private suspend fun doWorkThread(): String = withContext(Dispatchers.Default) {
        LogUtils.d(content = "doWorkThread start: " + Thread.currentThread().name)
        Thread.sleep(5_000)
        LogUtils.d(content = "doWorkThread end: " + Thread.currentThread().name)
        return@withContext "Hello world"
    }

    private suspend fun doMainThread() = withContext(Dispatchers.Main) {
        LogUtils.d(content = "doMainThread: " + Thread.currentThread().name)
    }
}