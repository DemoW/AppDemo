package lishui.example.app.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import lishui.example.app.Factory
import lishui.example.app.R


/**
 * Created by lishui.lin on 20-10-12
 */
class RvItemDecoration : RecyclerView.ItemDecoration() {

    private val mDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDividerSize: Float
    private val mDividerLeftPadding: Float
    private val mDividerRightPadding: Float

    init {
        val context = Factory.get().appContext
        mDividerSize = context.resources.getDimension(R.dimen.item_decoration_divider_size)
        val paddingExtSize = context.resources.getDimension(R.dimen.demo_padding_size_16dp)
        mDividerLeftPadding = context.resources.getDimension(R.dimen.default_item_icon_size)+paddingExtSize
        mDividerRightPadding = context.resources.getDimension(R.dimen.demo_padding_size_10dp) + paddingExtSize
        mDividerPaint.color = context.resources.getColor(R.color.demo_divider, null)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mDividerSize.toInt() // item bottom divider height
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            //val viewHolder = parent.getChildViewHolder(childView)
            val dividerTop: Float = childView.bottom - mDividerSize
            val dividerBottom = childView.bottom.toFloat()
            val dividerStart = parent.paddingStart.toFloat() + mDividerLeftPadding
            val dividerEnd = parent.width - parent.paddingEnd.toFloat() - mDividerRightPadding
            canvas.drawRect(
                dividerStart,
                dividerTop,
                dividerEnd,
                dividerBottom,
                mDividerPaint
            )
        }
    }
}