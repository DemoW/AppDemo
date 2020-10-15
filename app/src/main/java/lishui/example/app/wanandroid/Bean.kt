package lishui.example.app.wanandroid

// 页面结果列表
data class PageBody<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int = 0,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

// 问答实体类
data class QnAEntity(
    var author: String = "",
    var chapterId: Int = 0,
    var courseId: Int = 0,
    var desc: String = "",
    var link: String = "",
    var niceDate: String = "",
    var title: String = ""
)

// 文章实体类
data class Article(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val shareDate: String,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    var top: String
)