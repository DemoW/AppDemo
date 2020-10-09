package lishui.example.app.messaging;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lishui.example.app.Factory;
import lishui.example.app.R;
import lishui.example.app.db.entity.ConversationEntity;

public class ConversationListAdapter
        extends RecyclerView.Adapter<ConversationListAdapter.ConversationListViewHolder> {

    private ConversationListItemView.HostInterface mHostInterface;
    private final List<ConversationEntity> mConversationList = new ArrayList<>();

    public ConversationListAdapter(@Nullable ConversationListItemView.HostInterface hostInterface) {
        mHostInterface = hostInterface;
    }

    public void updateDataIfNeed(List<ConversationEntity> conversationEntities) {
        if (conversationEntities == null || conversationEntities.size() == 0) {
            mConversationList.clear();
        } else {
            mConversationList.clear();
            mConversationList.addAll(conversationEntities);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(Factory.get().getAppContext());
        final ConversationListItemView itemView =
                (ConversationListItemView) layoutInflater.inflate(
                        R.layout.conversation_list_item_view, null);
        return new ConversationListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationListViewHolder holder, int position) {
        final ConversationListItemView conversationListItemView = holder.mView;
        conversationListItemView.bind(mConversationList.get(position), mHostInterface);
    }

    @Override
    public int getItemCount() {
        return mConversationList.size();
    }

    /**
     * ViewHolder that holds a ConversationListItemView.
     */
    public static class ConversationListViewHolder extends RecyclerView.ViewHolder {
        final ConversationListItemView mView;

        public ConversationListViewHolder(final ConversationListItemView itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}
