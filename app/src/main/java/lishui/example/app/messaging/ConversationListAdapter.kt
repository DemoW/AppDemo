package lishui.example.app.messaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import lishui.example.app.DependencyBackup
import lishui.example.app.R
import lishui.example.app.db.entity.ConversationEntity
import lishui.example.app.messaging.ConversationListAdapter.ConversationListViewHolder
import lishui.example.app.messaging.ConversationListItemView.HostInterface

class ConversationListAdapter(private val mHostInterface: HostInterface?) :
    RecyclerView.Adapter<ConversationListViewHolder>() {
    private val mConversationList: MutableList<ConversationEntity> = mutableListOf()

    fun updateDataIfNeed(conversationEntities: List<ConversationEntity>?) {
        if (conversationEntities == null || conversationEntities.isEmpty())
            mConversationList.clear()
        else {
            mConversationList.clear()
            mConversationList.addAll(conversationEntities)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationListViewHolder {
        val layoutInflater = LayoutInflater.from(DependencyBackup.get().appContext)
        val itemView = layoutInflater.inflate(
            R.layout.conversation_list_item_view, null) as ConversationListItemView
        return ConversationListViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ConversationListViewHolder,
        position: Int
    ) {
        val conversationListItemView = holder.mView
        conversationListItemView.bind(mConversationList[position], mHostInterface)
    }

    override fun getItemCount(): Int {
        return mConversationList.size
    }

    /**
     * ViewHolder that holds a ConversationListItemView.
     */
    class ConversationListViewHolder(val mView: ConversationListItemView) : ViewHolder(mView)
}