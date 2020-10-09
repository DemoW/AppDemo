package lishui.example.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lishui.example.app.R
import lishui.example.app.messaging.ConversationListAdapter
import lishui.example.app.viewmodel.MainViewModel
import java.io.PrintWriter

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private lateinit var mViewModel: MainViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ConversationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = ConversationListAdapter(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.rv_message_container)
        mRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mViewModel.loadSmsConversation(false)
        //mViewModel.testCoroutine()

        mViewModel.mSmsConversationLiveData.observe(viewLifecycleOwner, Observer {
            mAdapter.updateDataIfNeed(it)
        })
    }

    fun dumpInfo(writer: PrintWriter) {
        mViewModel.dumpInfo(writer)
        writer.println("${javaClass.simpleName}: ")
        writer.println("\tConversationListAdapter itemCount=${mAdapter.itemCount}")
    }
}