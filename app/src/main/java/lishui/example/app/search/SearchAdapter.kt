package lishui.example.app.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lishui.example.app.R
import lishui.example.app.databinding.ItemSearchLayoutBinding
import lishui.example.app.wanandroid.Article

/**
 * Created by lishui.lin on 20-11-12
 */
class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val searchResult = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_search_layout, parent, false)
        return SearchViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchViewHolder) {
            val article = searchResult[position]
            val itemView = ItemSearchLayoutBinding.bind(holder.itemView)
            itemView.searchTitle.text = article.title
            itemView.searchDesc.text = article.desc
            itemView.searchAuthor.text = article.author
            itemView.searchDate.text = article.niceDate
            holder.itemView.tag = article
        }

    }

    override fun getItemCount(): Int = searchResult.size

    fun updateDataIfNeed(result: List<Article>) {
        searchResult.clear()
        searchResult.addAll(result)
        notifyDataSetChanged()
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}