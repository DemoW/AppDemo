package lishui.example.app.messaging

import android.provider.Telephony
import lishui.example.app.Dependency
import lishui.example.app.db.entity.ConversationEntity
import lishui.example.app.viewmodel.MainViewModel
import lishui.example.common.util.LogUtils

/**
 * Created by lishui.lin on 20-9-30
 */
class MessagingLoader {

    companion object {
        const val TAG = "MessagingLoader"
        fun get(): MessagingLoader {
            return Dependency.get().messagingLoader
        }
    }

    fun loadSmsConversations(conversationList: ArrayList<ConversationEntity>) {
        val app = Dependency.get().appContext
        app.contentResolver.query(
            Telephony.Sms.Conversations.CONTENT_URI,
            arrayOf(
                Telephony.Sms.THREAD_ID,
                Telephony.Sms.Conversations.SNIPPET,
                Telephony.Sms.Conversations.MESSAGE_COUNT
            ),
            null,
            null,
            Telephony.Sms.Conversations.DEFAULT_SORT_ORDER
        )?.use {
            it.moveToFirst()
            do {
                val threadId = it.getInt(it.getColumnIndex(Telephony.Sms.THREAD_ID))
                val snippet =
                    it.getString(it.getColumnIndex(Telephony.Sms.Conversations.SNIPPET))
                val count =
                    it.getString(it.getColumnIndex(Telephony.Sms.Conversations.MESSAGE_COUNT))

                val entity = ConversationEntity(
                    threadId = threadId,
                    snippet = snippet,
                    count = count.toInt()
                )
                conversationList.add(entity)

                LogUtils.d(
                    MainViewModel.TAG,
                    "position=${it.position}, "
                            + "{threadId=$threadId, "
                            + "snippet=$snippet, "
                            + "count=$count, ",
                    false
                )
            } while (it.moveToNext())
        }
    }

    fun loadSmsDataWithThreadId(id: Int, entity: ConversationEntity) {
        val app = Dependency.get().appContext
        app.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms.THREAD_ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.TYPE,
                Telephony.Sms.DATE,
                Telephony.Sms.DATE_SENT,
                Telephony.Sms.READ,
                Telephony.Sms.PERSON,
                Telephony.Sms.STATUS
            ),
            "${Telephony.Sms.THREAD_ID}=$id",
            null,
            "date ASC"
//            Telephony.Sms.DEFAULT_SORT_ORDER
        )?.use {
            it.moveToFirst()
            do {
                val threadId = it.getInt(it.getColumnIndex(Telephony.Sms.THREAD_ID))
                val address = it.getString(it.getColumnIndex(Telephony.Sms.ADDRESS))
                val type = it.getInt(it.getColumnIndex(Telephony.Sms.TYPE))
                val date = System.currentTimeMillis() + it.getInt(it.getColumnIndex(Telephony.Sms.DATE))
                val sentDate = System.currentTimeMillis() + it.getInt(it.getColumnIndex(Telephony.Sms.DATE_SENT))
                val read = it.getInt(it.getColumnIndex(Telephony.Sms.READ))
                val status = it.getInt(it.getColumnIndex(Telephony.Sms.STATUS))

                if (date > entity.receivedTime) {
                    entity.address = address
                    entity.receivedTime = date
                    entity.sentTime = sentDate
                    entity.status = status

                    LogUtils.d(TAG,
                        "position=${it.position}, "
                                + "{threadId=$threadId, "
                                + "address=$address, "
                                + "type=$type, "
                                + "date=$date, "
                                + "sentDate=$sentDate, "
                                + "read=$read, "
                                + "status=$status}",
                        false
                    )
                }

            } while (it.moveToNext())
        }
    }

}