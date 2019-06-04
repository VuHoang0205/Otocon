package org.atmarkcafe.otocon.function.mypage

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.RectF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentCommonNotificationBinding
import org.atmarkcafe.otocon.databinding.ItemCommonNotificationBinding
import org.atmarkcafe.otocon.dialog.DialogRematchMessage
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.card.SwipeController
import org.atmarkcafe.otocon.function.mypage.card.SwipeControllerActions
import org.atmarkcafe.otocon.function.party.PartyDetailFragment
import org.atmarkcafe.otocon.function.rematch.RequestRematchDetailFragment
import org.atmarkcafe.otocon.ktextension.OnClickDeleteButtonListener
import org.atmarkcafe.otocon.ktextension.OnLoadMoreListener
import org.atmarkcafe.otocon.ktextension.addOnLoadMoreListener
import org.atmarkcafe.otocon.ktextension.addOnSwipeToDelete
import org.atmarkcafe.otocon.model.CommonNotification
import org.atmarkcafe.otocon.model.response.CommonNotificationResponse
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.view.ItemDecoration

class CommonNotificationFragment : OtoconBindingFragment<FragmentCommonNotificationBinding>(), MVPExtension.View<OnResponse>, CommonNotificatinAdapter.OnCommonNotificationListener {
    val presenter = CommonNotificationPresenter(this)
    val adapter = CommonNotificatinAdapter(this)
    var total = 0
    var page = 1


    override fun layout(): Int {
        return R.layout.fragment_common_notification
    }

    override fun onCreateView(viewDataBinding: FragmentCommonNotificationBinding?) {
        viewDataBinding?.toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
        viewDataBinding?.commonNotificationRecyclerView?.adapter = adapter

        viewDataBinding?.commonNotificationRefresh?.setOnRefreshListener {
            viewDataBinding.commonNotificationRefresh.isRefreshing = false
            presenter.onExecute(context, CommonNotificationPresenter.ACTION_GET_LIST, "1")
        }

        viewDataBinding?.commonNotificationRecyclerView?.addOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                if (total > adapter.itemCount) {
                    presenter.onExecute(context, CommonNotificationPresenter.ACTION_GET_LIST, "${page + 1}")
                }
            }

        })

        // swipe delete controller
        viewDataBinding?.commonNotificationRecyclerView?.addOnSwipeToDelete(object : OnClickDeleteButtonListener {
            override fun delete(position: Int) {
                presenter.onExecute(context, CommonNotificationPresenter.ACTION_DELETE_NOTI, adapter.list[position].id)
            }

        }, RectF(0f, 0f, 0f, 0f), context?.getString(R.string.common_notification_delete_confirm_message))

        presenter.onExecute(context, CommonNotificationPresenter.ACTION_GET_LIST, "1")
    }

    // MVPExtension.View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(context, title, message, null)
    }

    // MVPExtension.View
    override fun success(response: OnResponse?) {
        if (response is CommonNotificationResponse) {
            // update list
            total = response.total
            page = Integer.parseInt(presenter.currentPage)

            viewDataBinding?.noDataMessage?.visibility = if (response.total == 0) View.VISIBLE else View.GONE

            if (presenter.currentPage == "1") {
                adapter.clear()
            }

            adapter.update(response.dataList)
        } else {
            // delete
            total -= 1
            adapter.remove(presenter.notificationId!!)
            viewDataBinding?.noDataMessage?.visibility = if (total == 0) View.VISIBLE else View.GONE
        }
    }

    // MVPExtension.View
    override fun showProgress(show: Boolean) {
        viewDataBinding?.loadingLayout?.root?.visibility = if (show) View.VISIBLE else View.GONE
    }

    // MVPExtension.View
    override fun showMessage(response: OnResponse?) {
    }


    // CommonNotificatinAdapter.OnCommonNotificationListener
    override fun onClickItem(item: CommonNotification) {
        if (item.readAt == null) {
            presenter.onExecute(context, CommonNotificationPresenter.ACTION_READ_NOTI, item.id)
            item.readAt = ""
            adapter.notifyItemChanged(adapter.getIndexOf(item.id))
        }

        val fragment = PartyDetailFragment()
        val args = Bundle()
        args.putString(PartyDetailFragment.PARTY_ID, item.eventId)
        fragment.arguments = args

        FragmentUtils.replace(activity!!, fragment, true)
    }
}


class CommonNotificatinAdapter(val listener: OnCommonNotificationListener) : RecyclerView.Adapter<CommonNotificatinAdapter.CommonNotificationViewHolder>() {
    val list = ArrayList<CommonNotification>()

    interface OnCommonNotificationListener {
        fun onClickItem(item: CommonNotification)
    }

    fun getIndexOf(notificationId: String): Int {
        val noti = CommonNotification()
        noti.id = notificationId
        return list.indexOf(noti)
    }

    fun clear() {
        list.clear()
    }

    fun remove(notificationId: String) {
        val index = getIndexOf(notificationId)
        list.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, list.size - index - 1)
    }

    fun update(data: Collection<CommonNotification>) {
        var count = 0
        for (item in data) {
            if (!list.contains(item)) {
                list.add(item)
                count ++
            }
        }
        if (list.size == data.size) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(list.size - count, count)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CommonNotificationViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = DataBindingUtil.inflate<ItemCommonNotificationBinding>(inflater, R.layout.item_common_notification, viewGroup, false)
        return CommonNotificationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CommonNotificationViewHolder, pos: Int) {
        holder.bind(list[pos])

    }

    inner class CommonNotificationViewHolder(val binding: ItemCommonNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: CommonNotification) {
            binding.notification = notification
            binding.root.setOnClickListener {
                listener.onClickItem(notification)
            }
        }
    }
}


class CommonNotificationPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {
    var notificationId: String? = ""
    var currentPage = ""
    var loadingList = false

    override fun onExecute(context: Context?, action: Int, params: String?) {
        when (action) {
            ACTION_GET_LIST -> {
                if (loadingList) return
                loadingList = true
                currentPage = params!!
                execute(InteractorManager.getApiInterface(context).getCommonNotifications(currentPage), object : MVPPresenter.ExecuteListener<CommonNotificationResponse> {
                    override fun onError(e: Throwable?) {
                        val messages = OnResponse.getMessage(context, e, null)
                        view.showPopup(messages[0], messages[1])
                        loadingList = false
                    }

                    override fun onNext(response: CommonNotificationResponse?) {
                        if (response != null && response!!.isSuccess) {
                            view.success(response)
                        } else {
                            val messages = OnResponse.getMessage(context, null, response)
                            view.showPopup(messages[0], messages[1])
                        }
                        loadingList = false
                    }
                })
            }
            ACTION_READ_NOTI ->
                execute(InteractorManager.getApiInterface(context).readNotification(params), object : MVPPresenter.ExecuteListener<OnResponse> {
                    override fun onNext(response: OnResponse?) {
                        // TODO nothing
                    }

                    override fun onError(e: Throwable?) {
                        // TODO nothing
                    }
                })

            ACTION_DELETE_NOTI -> {
                notificationId = params
                execute(InteractorManager.getApiInterface(context).deleteNotification(notificationId), object : MVPPresenter.ExecuteListener<OnResponse> {
                    override fun onNext(response: OnResponse?) {
                        if (response != null && response.isSuccess) {
                            view.success(response)
                        } else {
                            val messages = OnResponse.getMessage(context, null, response)
                            view.showPopup(messages[0], messages[1])
                        }
                    }

                    override fun onError(e: Throwable?) {
                        val messages = OnResponse.getMessage(context, e, null)
                        view.showPopup(messages[0], messages[1])
                        loadingList = false
                    }
                })
            }
        }
    }

    companion object {
        const val ACTION_GET_LIST = 0
        const val ACTION_READ_NOTI = 1
        const val ACTION_DELETE_NOTI = 2
    }
}