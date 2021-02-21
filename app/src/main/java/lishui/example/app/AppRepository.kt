package lishui.example.app

import android.content.Context
import androidx.room.Room
import lishui.example.app.db.AppDatabase
import lishui.example.app.db.entity.ConversationEntity
import lishui.example.app.messaging.MessagingLoader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by lishui.lin on 20-9-30
 */
@Singleton
class AppRepository @Inject constructor(appContext: Context) {

//    val MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE conversations ADD COLUMN pub_year INTEGER")
//        }
//    }

    val appDb: AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "app_demo.db"
    ).build()

    private val messagingLoader: MessagingLoader = MessagingLoader(context = appContext)

    fun loadSmsConversations(conversationList: ArrayList<ConversationEntity>) =
        messagingLoader.loadSmsConversations(conversationList)

    fun loadSmsDataWithThreadId(id: Int, entity: ConversationEntity) =
        messagingLoader.loadSmsDataWithThreadId(id, entity)
}