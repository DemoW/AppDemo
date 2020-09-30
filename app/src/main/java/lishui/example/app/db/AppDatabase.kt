package lishui.example.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lishui.example.app.db.dao.ConversationDao
import lishui.example.app.db.entity.ConversationEntity

/**
 * Created by lishui.lin on 20-9-30
 */
@Database(
    entities = [ConversationEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun conversationDao(): ConversationDao
}
