package lishui.example.app.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import lishui.example.app.base.BaseActivity
import lishui.example.app.databinding.ActivitySearchBinding
import lishui.example.app.viewmodel.SearchViewModel
import lishui.example.app.wanandroid.Article
import lishui.example.common.UiIntents
import lishui.example.common.util.Utilities

class SearchActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private val searchAdapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpThumbnailRv()
        binding.searchInput.addTextChangedListener {
            Utilities.trim(it.toString())?.let { keyword ->
                viewModel.searchKeyword(keyword)
            }
        }

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        subscribeToViewModel()
    }

    private fun setUpThumbnailRv() {
        binding.searchList.layoutManager = LinearLayoutManager(this)
        binding.searchList.adapter = searchAdapter
        searchAdapter.setClickListener(this)
    }

    private fun subscribeToViewModel() {
        viewModel.getArticleLiveData().observe(this, Observer {
            searchAdapter.updateDataIfNeed(it)
        })
    }

    override fun onClick(v: View?) {
        val tag = v?.tag
        if (tag is Article) {
            UiIntents.get().launchBrowser(this, tag.link)
        }
    }
}