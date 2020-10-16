package lishui.example.app

import android.content.Context
import androidx.room.Room
import lishui.example.app.db.AppDatabase

/**
 * Created by lishui.lin on 20-9-30
 */
class AppRepository(private val appContext: Context) {

    companion object {
        fun get(): AppRepository {
            return Dependency.get().appRepository
        }
    }

//    val MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE conversations ADD COLUMN pub_year INTEGER")
//        }
//    }

    val appDb: AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "app_demo.db"
    ).build()
}