package lishui.example.app.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import lishui.example.app.base.BaseFragment
import lishui.example.app.databinding.FragmentSearchLayoutBinding
import lishui.example.app.viewmodel.SearchViewModel
import lishui.example.common.util.Utilities

/**
 * Created by lishui.lin on 20-11-12
 */
class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchLayoutBinding
    private lateinit var viewModel: SearchViewModel

    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpThumbnailRv()
        binding.searchInput.addTextChangedListener {
            Utilities.trim(it.toString())?.let { keyword ->
                viewModel.searchKeyword(keyword)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        subscribeToViewModel()
    }

    private fun setUpThumbnailRv() {
        binding.searchList.layoutManager = LinearLayoutManager(context)
        binding.searchList.adapter = searchAdapter
    }

    private fun subscribeToViewModel() {
        viewModel.getArticleLiveData().observe(viewLifecycleOwner, Observer {
            searchAdapter.updateDataIfNeed(it)
        })
    }
}