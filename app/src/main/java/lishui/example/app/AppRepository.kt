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
            return Factory.get().appRepository
        }
    }

    val database: AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "app_demo_db"
    ).build()

}