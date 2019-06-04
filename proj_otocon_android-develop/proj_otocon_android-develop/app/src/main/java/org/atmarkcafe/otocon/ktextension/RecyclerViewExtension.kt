package org.atmarkcafe.otocon.ktextension

import android.graphics.Canvas
import android.graphics.RectF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.dialog.DialogRematchMessage
import org.atmarkcafe.otocon.function.mypage.card.SwipeController
import org.atmarkcafe.otocon.function.mypage.card.SwipeControllerActions
import org.atmarkcafe.otocon.view.ItemDecoration

interface OnLoadMoreListener {
    fun onLoadMore()
}

interface OnClickDeleteButtonListener {
    fun delete(position: Int)
}

fun RecyclerView.addOnLoadMoreListener(listener: OnLoadMoreListener) {
    addOnScrollListener( object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (lastVisibleItem == layoutManager!!.itemCount - 1) {
                // load more
                listener.onLoadMore()
            }
        }
    })
}

fun RecyclerView.addOnSwipeToDelete(listener: OnClickDeleteButtonListener, rect: RectF, message: String?) {
    val context = this.context
    val controller = SwipeController(object : SwipeControllerActions() {
        override fun onRightClicked(position: Int) {
            if (message == null) {
                listener.delete(position)
            } else {
                DialogRematchMessage(context, message,
                        DialogRematchMessage.DialogRematchListener { isOke, _ ->
                            if (isOke) {
                                listener.delete(position)
                            }
                        })
                        .setTextButtonGreen(context.getString(R.string.yes))
                        .setTexButtonBlack(context.getString(R.string.no))
                        .show()
            }
        }
    })
    val decoration: ItemDecoration = object : ItemDecoration(rect.top, rect.left, rect.bottom, rect.right) {
        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            controller.onDraw(c)
        }
    }
    addItemDecoration(decoration)
    val itemTouchHelper = ItemTouchHelper(controller)
    itemTouchHelper.attachToRecyclerView(this)
}