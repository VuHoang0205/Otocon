package org.atmarkcafe.otocon.function.rematch

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.xwray.groupie.*
import org.atmarkcafe.otocon.BuildConfig
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.ListUserBinding
import org.atmarkcafe.otocon.dialog.DialogRematchMessage
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog
import org.atmarkcafe.otocon.function.rematch.item.UserItem
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.UserRematch
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.UserRematchResponse
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.view.InfiniteScrollListener

class RematchListUserFragment : OtoconBindingFragment<ListUserBinding>(), MVPExtension.View<OnResponse>, OnItemClickListener {


    var adapter = Adapter()
    var presenter: RematchListUserPresenter = RematchListUserPresenter(this)

    override fun onCreateView(viewDataBinding: ListUserBinding?) {

        // Receive data
        presenter.eventId = arguments!!.getString("event_id")

        // Config Item RecycleView
        viewDataBinding!!.recyclerView.setAddItemDecoration()

        //Set loadmore first
        infiniteScrollListener.setLoading(false)
        infiniteScrollListener.setGridLayoutManager(viewDataBinding!!.recyclerView.layoutManager as GridLayoutManager?)
        viewDataBinding.recyclerView.addOnScrollListener(infiniteScrollListener)
        viewDataBinding.recyclerView.adapter = adapter

        if (DBManager.setIsFirst(activity, "RematchListUserFragment")) { onClickToStatusRematch() }

        viewDataBinding.back.setOnClickListener { onBackPressed() }
        viewDataBinding.explainStatus.setOnClickListener { onClickToStatusRematch() }

        //Click to chage rematch user detail
        adapter.setOnItemClickListener(this)

        //On refesh data
        viewDataBinding.refresh.setOnRefreshListener {
            viewDataBinding.refresh.isRefreshing = false
            adapter.clearData()
            presenter.page = 1
            presenter.onExecute(context, 0, null)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        otoconFragmentListener?.onHandlerReult(0,null)
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.refresh.isRefreshing = false
        adapter.clearData()
        presenter.page = 1
        presenter.onExecute(context, 0, null)
    }

    override fun onItemClick(item: Item<*>, view: View) {
        // send id event
        val model = item as UserItem
        val data = Bundle()
        val fragment = RequestRematchDetailFragment()
        data.putString("event_id", presenter.eventId)
        data.putString("user_id", model.response.user_id)
        fragment.arguments = data
        FragmentUtils.replace(activity!!, fragment, true)
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity!!, title, message, null)

    }

    override fun success(response: OnResponse?) {
        if (response is UserRematchResponse) {
            if (presenter.page == 1){
                adapter.clearData();
            }
            adapter.addItem(response.userRematches)
            if (response.total != adapter.itemCount) {
                infiniteScrollListener.setLoading(true)

            }
        }
    }

    override fun showProgress(show: Boolean) {
        viewDataBinding.loading.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(response: OnResponse?) {
        DialogRematchMessage(context!!, response?.message, null)
                .setTextButtonGreen(getString(R.string.ok))
                .show()
    }

    fun onClickToStatusRematch() {
        RematchFlowStatusDialog(context, getString(R.string.title_rematch_status), BuildConfig.LINK_REMATCH_STATUS).show()
    }

    override fun layout(): Int {
        return R.layout.fragment_rematch_list_user
    }


    private val infiniteScrollListener = object : InfiniteScrollListener() {
        override fun onLoadMore(current_page: Int) {
            presenter.page += 1
            presenter.onExecute(activity, 0, null)
        }
    }

    fun RecyclerView.setAddItemDecoration() {
        addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val params = view.layoutParams as RecyclerView.LayoutParams
                val position = params.viewAdapterPosition
                val density = viewDataBinding.recyclerView.context.resources.displayMetrics.density
                if (position % 2 == 0) {
                    outRect.top = (density * 7.0).toInt()
                    outRect.left = (density * 10.0).toInt()
                    outRect.bottom = (density * 0.0).toInt()
                    outRect.right = (density * 7.0).toInt()
                } else {
                    outRect.top = (density * 7.0).toInt()
                    outRect.left = (density * 7.0).toInt()
                    outRect.bottom = (density * 0.0).toInt()
                    outRect.right = (density * 10.0).toInt()

                }
            }
        })

    }

    class RematchListUserPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {
        var page = 1
        var limit: Int = 20
        var eventId = ""
        override fun onExecute(context: Context?, action: Int, params: String?) {
            if (action == 0) {
                execute(InteractorManager.getApiInterface(context).getUserRematch(eventId, page, limit), object : ExecuteListener<UserRematchResponse> {
                    override fun onError(e: Throwable?) {
                        val messages = OnResponse.getMessage(context, e, null)
                        view.showPopup(messages[0], messages[1])

                    }

                    override fun onNext(response: UserRematchResponse?) {
                        if (response!!.isSuccess) {
                            view.success(response)
                        } else {
                            view.showMessage(response)
                        }
                    }
                })

            }
        }
    }

    class Adapter : GroupAdapter<ViewHolder>() {
        fun clearData() {
            this.clear()
            section = Section()
            this.add(section)
        }

        var section: Section = Section()

        init {
            add(section)
        }

        fun addItem(items: List<UserRematch>) {
            for (model in items) {
                section.add(UserItem(model))
            }
            notifyDataSetChanged()
        }
    }
}