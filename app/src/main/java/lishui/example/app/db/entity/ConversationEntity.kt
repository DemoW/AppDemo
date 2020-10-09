package lishui.example.app.db.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by lishui.lin on 20-9-30
 */
@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val id: Int = 0,
    @ColumnInfo(name = CONVERSATION_THREAD_ID, index = true)
    val threadId: Int,
//    @ColumnInfo(name = CONVERSATION_LATEST_MESSAGE_ID)
//    val latestMessageId: Int,
    @ColumnInfo(name = CONVERSATION_NAME)
    var name: String = "",
    @ColumnInfo(name = CONVERSATION_PREVIEW_URI)
    var previewUri: String = "",
    @ColumnInfo(name = CONVERSATION_SNIPPET)
    var snippet: String = "",
    @ColumnInfo(name = CONVERSATION_ADDRESS)
    var address: String = "",
    @ColumnInfo(name = CONVERSATION_RECEIVED_TIME)
    var receivedTime: Long = 0,
    @ColumnInfo(name = CONVERSATION_SENT_TIME)
    var sentTime: Long = 0,
    @ColumnInfo(name = CONVERSATION_STATUS)
    var status: Int = 0,
    @ColumnInfo(name = CONVERSATION_COUNT)
    var count: Int = 0
) {
    // ignore type should not in constructor
    @Ignore val ignoreStr: String = ""
}