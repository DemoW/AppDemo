package lishui.example.app.messaging

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
    private lateinit var mIconView: ImageView
    private lateinit var mSnippetText: TextView
    private lateinit var mNameText: TextView
    private lateinit var mReceivedTimeText: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        mIconView = findViewById(R.id.iv_icon)
        mSnippetText = findViewById(R.id.tv_snippet)
        mNameText = findViewById(R.id.tv_name)
        mReceivedTimeText = findViewById(R.id.tv_received_time)

        setOnClickListener {
            //WebViewUtils.launchInnerBrowser(context, "https://www.baidu.com")
            findNavController().navigate(R.id.action_global_snack_dialog,
                bundleOf(TAG to mSnippetText.text.toString()))
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
        if (entity.previewUri.isEmpty())
            Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions.centerInsideTransform())
            .load(R.drawable.ic_baseline_android_24)
            .into(mIconView)
        else
            Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions.centerInsideTransform())
                .load(entity.previewUri)
                .into(mIconView)
    }

    interface HostInterface {}
}