package lishui.example.app.messaging

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import lishui.demo.browser.WebViewBrowser
import lishui.demo.browser.WebViewContainer
import lishui.demo.browser.util.WebViewUtils
import lishui.example.app.R
import lishui.example.app.db.entity.ConversationEntity

/**
 * Created by lishui.lin on 20-10-9
 */
class ConversationListItemView(
    context: Context,
    attributes: AttributeSet
) : RelativeLayout(context, attributes) {

    companion object {
        const val TAG: String = "ConversationListItemView"
    }

    private var mHostInterface: HostInterface? = null
    private lateinit var mSnippetText: TextView
    private lateinit var mNameText: TextView
    private lateinit var mReceivedTimeText: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        mSnippetText = findViewById(R.id.tv_snippet)
        mNameText = findViewById(R.id.tv_name)
        mReceivedTimeText = findViewById(R.id.tv_received_time)

        setOnClickListener {
            WebViewUtils.startBrowsingIntent(context, "https://www.baidu.com")
        }
    }

    fun bind(entity: ConversationEntity, hostInterface: HostInterface?) {
        mHostInterface = hostInterface

        mSnippetText.text = entity.snippet
        mNameText.text = entity.address
        mReceivedTimeText.text = DateUtils.formatDateTime(
            context,
            entity.receivedTime,
            DateUtils.FORMAT_SHOW_DATE
                    or DateUtils.FORMAT_ABBREV_ALL
        )
    }

    private fun setShortAndLongClickable(clickable: Boolean) {
        isClickable = clickable
        isLongClickable = clickable
    }

    interface HostInterface {}
}