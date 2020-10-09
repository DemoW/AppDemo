package lishui.example.app.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lishui.example.app.db.entity.ConversationEntity

/**
 * Created by lishui.lin on 20-9-30
 */
@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversations")
    fun listAllConversations(): List<ConversationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversations(conversationList: List<ConversationEntity>)
}