package lishui.example.app.net

import lishui.example.app.wanandroid.Article
import lishui.example.app.wanandroid.PageBody
import lishui.example.app.wanandroid.QnAEntity
import retrofit2.http.*

interface WanAndroidService {

    // Question and Answer
    @GET("wenda/list/{page}/json")
    suspend fun listQnADataList(@Path("page") page: Int): WanResult<PageBody<QnAEntity>>

    // Search
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(@Path("page") page: Int,
                         @Field("k") key: String): WanResult<PageBody<Article>>
}