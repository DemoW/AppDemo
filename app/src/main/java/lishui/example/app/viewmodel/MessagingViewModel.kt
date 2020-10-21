package lishui.example.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lishui.example.app.AppRepository
import lishui.example.app.Dependency
import lishui.example.app.net.NetworkManager
import lishui.example.app.db.entity.ConversationEntity
import lishui.example.app.messaging.MessagingLoader
import lishui.example.common.util.LogUtils
import java.io.PrintWriter

/**
 * Created by lishui.lin on 20-9-24
 */
class MessagingViewModel(val app: Application) : AndroidViewModel(app) {

    companion object {
        const val TAG = "MessagingViewModel"
        const val LAST_QUERY_SMS_CONVERSATION = "last_query_conversation"
        const val LAST_QUERY_DELAY_TIME = 30_000L
    }

    val mSmsConversationLiveData: MutableLiveData<List<ConversationEntity>> = MutableLiveData()

    fun loadSmsConversation(forceUpdate: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            val conversations = AppRepository.get().appDb.conversationDao().listAllConversations()
            val lastQueryTime =
                Dependency.get().sharedPreferences.getLong(LAST_QUERY_SMS_CONVERSATION, 0)
            if (forceUpdate
                || (conversations.isEmpty() && System.currentTimeMillis() - lastQueryTime > LAST_QUERY_DELAY_TIME)
            ) {
                val conversationList = arrayListOf<ConversationEntity>()
                MessagingLoader.get().loadSmsConversations(conversationList)
                conversationList.forEach {
                    MessagingLoader.get().loadSmsDataWithThreadId(it.threadId, it)
                }
                AppRepository.get().appDb.conversationDao().insertConversations(conversationList)
                Dependency.get().sharedPreferences.edit()
                    .putLong(LAST_QUERY_SMS_CONVERSATION, System.currentTimeMillis())
                    .apply()
                mSmsConversationLiveData.postValue(conversationList)
            } else {
                mSmsConversationLiveData.postValue(conversations)
            }

            val result = NetworkManager.get().queryBySearchKey(0, "Android")
            LogUtils.d(MainViewModel.TAG, "result total=" + result.data.total)
        }
    }

    fun dumpInfo(writer: PrintWriter) {
        writer.println("${javaClass.simpleName}: ")
        writer.println("\tmSmsConversationLiveData=${mSmsConversationLiveData.value?.size}")
    }
}