package lishui.example.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lishui.example.app.net.NetworkManager
import lishui.example.app.wanandroid.Article

/**
 * Created by lishui.lin on 20-11-12
 */
class SearchViewModel : ViewModel() {

    private val articleLiveData = MutableLiveData<List<Article>>()
    var currentPage = 1

    fun searchKeyword(key: String) {
        viewModelScope.launch {
            val result = NetworkManager.get().queryBySearchKey(1, key)
            if (0 == result.errorCode) {
                articleLiveData.postValue(result.data.datas)
            }
        }
    }

    fun getArticleLiveData() = articleLiveData

}