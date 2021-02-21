package lishui.example.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lishui.example.app.AppRepository
import lishui.example.app.Dependency
import lishui.example.app.DependencyBackup
import lishui.example.app.db.entity.ConversationEntity
import lishui.example.app.messaging.MessagingLoader
import lishui.example.common.util.LogUtils
import java.io.PrintWriter
import javax.inject.Inject

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
            LogUtils.d("loadSmsConversation viewModelScope")
            val repository = Dependency.get(AppRepository::class.java)
            val conversations = repository.appDb.conversationDao().listAllConversations()
            val lastQueryTime =
                DependencyBackup.get().sharedPreferences.getLong(LAST_QUERY_SMS_CONVERSATION, 0)
            if (forceUpdate
                || (conversations.isEmpty() && System.currentTimeMillis() - lastQueryTime > LAST_QUERY_DELAY_TIME)
            ) {
                val conversationList = arrayListOf<ConversationEntity>()
                repository.loadSmsConversations(conversationList)
                conversationList.forEach {
                    repository.loadSmsDataWithThreadId(it.threadId, it)
                }
                repository.appDb.conversationDao().insertConversations(conversationList)
                DependencyBackup.get().sharedPreferences.edit()
                    .putLong(LAST_QUERY_SMS_CONVERSATION, System.currentTimeMillis())
                    .apply()
                mSmsConversationLiveData.postValue(conversationList)
            } else {
                mSmsConversationLiveData.postValue(conversations)
            }
        }
    }

    fun dumpInfo(writer: PrintWriter) {
        writer.println("${javaClass.simpleName}: ")
        writer.println("\tmSmsConversationLiveData=${mSmsConversationLiveData.value?.size}")
    }
}