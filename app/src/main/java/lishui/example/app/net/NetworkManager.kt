package lishui.example.app.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lishui.example.app.DependencyBackup
import lishui.example.app.WAN_ANDROID_BASE_URI
import lishui.example.app.wanandroid.Article
import lishui.example.app.wanandroid.PageBody
import lishui.example.app.wanandroid.QnAEntity
import lishui.example.app.wanandroid.WanResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    companion object {
        fun get(): NetworkManager {
            return DependencyBackup.get().networkManager
        }
    }

    private val wanAndroidService: WanAndroidService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(WAN_ANDROID_BASE_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        wanAndroidService = retrofit.create(WanAndroidService::class.java)
    }

    // 获取问答数据
    suspend fun listQnAList(page: Int): WanResult<PageBody<QnAEntity>> =
        withContext(Dispatchers.Default) {
            wanAndroidService.listQnADataList(page)
        }

    // 搜索文章
    suspend fun queryBySearchKey(page: Int, keyWord: String): WanResult<PageBody<Article>> =
        withContext(Dispatchers.Default) {
            wanAndroidService.queryBySearchKey(page, keyWord)
        }

}