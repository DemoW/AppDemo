package lishui.example.app.messaging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lishui.example.app.R
import lishui.example.app.base.BaseFragment
import lishui.example.app.ui.RvItemDecoration
import lishui.example.app.viewmodel.MessagingViewModel
import java.io.FileDescriptor
import java.io.PrintWriter

class MessagingFragment : BaseFragment() {

    private lateinit var mViewModel: MessagingViewModel
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
        return inflater.inflate(R.layout.fragment_messaging_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.rv_messaging_container)
        mRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.addItemDecoration(RvItemDecoration())
        mRecyclerView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MessagingViewModel::class.java)
        mViewModel.loadSmsConversation(false)
        mViewModel.mSmsConversationLiveData.observe(viewLifecycleOwner, Observer {
            mAdapter.updateDataIfNeed(it)
        })
    }


    override fun dump(
        prefix: String,
        fd: FileDescriptor?,
        writer: PrintWriter,
        args: Array<out String>?
    ) {
        super.dump(prefix, fd, writer, args)
        writer.println("==============================")
        writer.println("${javaClass.simpleName} dumpInfo： ")
        writer.println("\tConversationListAdapter itemCount=${mAdapter.itemCount}")
        mViewModel.dumpInfo(writer)
        writer.println("==============================")
    }
}