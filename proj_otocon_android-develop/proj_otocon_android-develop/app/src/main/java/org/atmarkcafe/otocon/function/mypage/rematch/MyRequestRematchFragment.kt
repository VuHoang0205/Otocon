package org.atmarkcafe.otocon.function.mypage.rematch

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.ViewHolder
import org.atmarkcafe.otocon.MainFragment
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentRequestRematchListBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.card.SwipeController
import org.atmarkcafe.otocon.function.mypage.card.SwipeControllerActions
import org.atmarkcafe.otocon.function.rematch.RequestRematchDetailFragment
import org.atmarkcafe.otocon.function.rematch.item.RequestRematchItem
import org.atmarkcafe.otocon.model.RequestRematch
import org.atmarkcafe.otocon.model.response.ListRequestRematchResponse
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyExtensionUtils
import org.atmarkcafe.otocon.view.InfiniteScrollListener
import org.atmarkcafe.otocon.view.ItemDecoration

open class MyRequestRematchFragment : OtoconBindingFragment<FragmentRequestRematchListBinding>(), MVPExtension.View<OnResponse>, OnItemClickListener {

    var presenter: MyRequestRematchPresenter = MyRequestRematchPresenter("1", this)
    val groupAdapter = GroupAdapter<ViewHolder>()

    private var enableCallApi = true
    var first: Boolean = true
    private var positionCurrent: Int = 0
    private var itemCurrent: RequestRematch = RequestRematch()

    var controller: SwipeController? = null


    override fun layout(): Int {
        return R.layout.fragment_request_rematch_list
    }

    fun setEnableCallApi(enable: Boolean): MyRequestRematchFragment {
        this.enableCallApi = enable
        return this
    }

    private fun onDeleteCard(item: RequestRematchItem) {
        presenter.onExecute(activity, presenter.ACTION_DELETE_ITEM, item.item.share_contact_id.toString())
    }

    override fun onCreateView(viewDataBinding: FragmentRequestRematchListBinding?) {

        controller = SwipeController(object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                groupAdapter.notifyItemRangeChanged(position, groupAdapter.getItemCount())
                positionCurrent = position
                // call to delete
                onDeleteCard(groupAdapter.getItem(position) as RequestRematchItem)
            }
        })
        val decoration: ItemDecoration? = object : ItemDecoration(0f, 0f, 0f, 0f) {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                controller!!.onDraw(c)
            }
        }

        viewDataBinding?.requestRematchRecyclerView?.addItemDecoration(decoration!!)
        val itemTouchHelper = ItemTouchHelper(controller!!)
        itemTouchHelper.attachToRecyclerView(viewDataBinding?.requestRematchRecyclerView)


        viewDataBinding?.requestRematchRecyclerView?.adapter = groupAdapter
        viewDataBinding?.btnSubmit?.setOnClickListener {
            // TODO click submit
            FragmentUtils.backToTop(activity)
            val currentFragment = FragmentUtils.getFragment(activity)
            (currentFragment as MainFragment)?.gotoTab(3)
        }
        // setting loadmore
        infiniteScrollListener.setLoading(false)
        infiniteScrollListener.setLinearLayoutManager(viewDataBinding!!.requestRematchRecyclerView.layoutManager as LinearLayoutManager?)
        viewDataBinding.requestRematchRecyclerView.addOnScrollListener(infiniteScrollListener)

        groupAdapter.setOnItemClickListener(this)

        if (enableCallApi) {
            enableCallApi = false
            first = false
            presenter.onExecute(activity, presenter.ACTION_LOAD_ITEM, null)
        }

        viewDataBinding.refresh.setOnRefreshListener {
            viewDataBinding.refresh.isRefreshing = false
            refresh()
        }
    }

    fun loadFirstApi() {
        if (first && activity != null) {
            presenter.onExecute(activity, presenter.ACTION_LOAD_ITEM, null)
            first = false
        }

    }

    fun refresh(){
        presenter.page = 1
        presenter.onExecute(activity, presenter.ACTION_LOAD_ITEM, null)
    }

    // OnItemClickListener click item adapter
    override fun onItemClick(item: Item<*>, view: View) {
        item as RequestRematchItem
        if (item.item.read_at.isNullOrBlank()) {
            itemCurrent = item.item
            presenter.onExecute(activity, presenter.ACTION_READ_ITEM, itemCurrent.share_contact_id.toString())
        }

        val data = Bundle()
        val fragment = RequestRematchDetailFragment()
        data.putString("event_id", item.item.event_id.toString())
        data.putString("user_id", item.item.user_id.toString())
        fragment.arguments = data
        FragmentUtils.replace(activity!!, fragment, true)

    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    override fun success(response: OnResponse?) {
        if (response is ListRequestRematchResponse) {
            // load item
            val listRequest = response.dataList as ArrayList<RequestRematch>
            if (presenter.page == 1) groupAdapter.clear()
            if (response.total > 0) {
                viewDataBinding.layoutNodata.visibility = View.GONE
                viewDataBinding.layoutNotiNodata.visibility = View.GONE
                for (itemRequest in listRequest) {
                    if (itemRequest.withdrawal_status == 0) {
                        groupAdapter.add(RequestRematchItem(itemRequest))
                    } else if (itemRequest.withdrawal_status == 1) {
                        // add item defaul
                        itemRequest.nickname = getString(R.string.name_item_request_rematch_defaul)
                        itemRequest.rematch_request = getString(R.string.request_item_request_rematch_defaul)
                        groupAdapter.add(RequestRematchItem(itemRequest))
                    }
                }
                if (response.total != groupAdapter.itemCount) {
                    infiniteScrollListener.setLoading(true)

                }
            } else {
                viewDataBinding.layoutNodata.visibility = View.VISIBLE
                viewDataBinding.layoutNotiNodata.visibility = View.VISIBLE
            }
        } else {
            // delete item
            groupAdapter.removeGroup(positionCurrent)
            groupAdapter.notifyDataSetChanged()

            if (groupAdapter.itemCount == 0){
                viewDataBinding.layoutNodata.visibility = View.VISIBLE
                viewDataBinding.layoutNotiNodata.visibility = View.VISIBLE
            }
        }


    }

    override fun showProgress(show: Boolean) {
        viewDataBinding?.loadingLayout?.root?.visibility = if (show) View.VISIBLE else View.GONE

    }

    override fun showMessage(response: OnResponse?) {

    }

    private val infiniteScrollListener = object : InfiniteScrollListener() {
        override fun onLoadMore(current_page: Int) {
            presenter.page += 1
            presenter.onExecute(activity, 0, null)
        }
    }
}

class MyRequestRematchPresenter(val isRequest: String, view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {
    val ACTION_LOAD_ITEM: Int = 0;
    val ACTION_READ_ITEM: Int = 1;
    val ACTION_DELETE_ITEM: Int = 2;
    var page: Int = 0
    var limit: Int = 20

    override fun onExecute(context: Context?, action: Int, params: String?) {
        if (action == ACTION_LOAD_ITEM) {
            execute(InteractorManager.getApiInterface(context).getRequestRematch(isRequest, page, limit), object : ExecuteListener<ListRequestRematchResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])

                }

                override fun onNext(response: ListRequestRematchResponse) {
                    if (response.isSuccess) {
                        view.success(response)
                    } else {
                        view.showMessage(response)
                    }
                }
            })
        } else if (action == ACTION_READ_ITEM) {
            execute(InteractorManager.getApiInterface(context).readRequest(params), object : ExecuteListener<OnResponse> {
                override fun onError(e: Throwable?) {
//                    val messages = OnResponse.getMessage(context, e, null)
//                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: OnResponse) {
//                    if (response.isSuccess) {
//                        view.success(response)
//                    } else {
//                        view.showMessage(response)
//                    }
                }
            })
        } else if (action == ACTION_DELETE_ITEM) {
            execute(InteractorManager.getApiInterface(context).hidenRequest(params), object : ExecuteListener<OnResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])

                }

                override fun onNext(response: OnResponse) {
                    if (response.isSuccess) {
                        view.success(response)
                    } else {
                        view.showMessage(response)
                    }
                }
            })
        }

    }


}

