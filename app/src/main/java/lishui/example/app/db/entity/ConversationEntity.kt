package lishui.example.app.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by lishui.lin on 20-9-30
 */
@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "thread_id")
    val threadId: Int,
    @ColumnInfo(name = "snippet")
    val snippet: String = "",
    @ColumnInfo(name = "count")
    val count: Int = 0
) {
    // ignore type should not in constructor
    @Ignore val ignoreStr: String = ""
}