package org.atmarkcafe.otocon.function.rematch


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xwray.groupie.*
import org.atmarkcafe.otocon.BuildConfig

import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentRematchBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog
import org.atmarkcafe.otocon.function.mypage.rematch.RequestRematchFragment
import org.atmarkcafe.otocon.function.rematch.RematchPairingFragment.RematchPairingPresenter.Companion.ACTION_GET_LIST_PARTY
import org.atmarkcafe.otocon.function.rematch.RematchPairingFragment.RematchPairingPresenter.Companion.ACTION_READ_PARTY
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.PartyConditionRespone

import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.KeyExtensionUtils
import org.atmarkcafe.otocon.view.InfiniteScrollListener

class RematchPairingFragment : OtoconBindingFragment<FragmentRematchBinding>(), HeaderMatchingItem.Listener, MVPExtension.View<OnResponse>, SwipeRefreshLayout.OnRefreshListener {

    var adapter = Adapter()
    var presenter: RematchPairingPresenter = RematchPairingPresenter(this)

    override fun layout(): Int {
        return R.layout.fragment_rematch
    }

    override fun onCreateView(viewDataBinding: FragmentRematchBinding) {

        adapter.init(this)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(item: Item<*>, view: View) {

                // send id event
                if (item is MatchingItem) {
                    var model = item as MatchingItem
                    var data: Bundle = Bundle()
                    var fragment: RematchListUserFragment = RematchListUserFragment()
                    data.putString("event_id", model.respone.getId().toString())
                    fragment.arguments = data
                    fragment.otoconFragmentListener = object : OtoconFragmentListener {
                        override fun onHandlerReult(status: Int, extras: Bundle?) {
                            onRefresh()
                        }
                    }
                    FragmentUtils.replace(activity!!, fragment, true)

                    //read party
                    if (item.respone.read_at == null){
                        presenter.onExecute(context, ACTION_READ_PARTY, model.respone.getId().toString())
                    }
                }
            }
        })

        infiniteScrollListener.setLoading(false)
        infiniteScrollListener.setLinearLayoutManager(viewDataBinding.recyclerView.layoutManager as LinearLayoutManager)
        viewDataBinding.recyclerView.addOnScrollListener(infiniteScrollListener)
        viewDataBinding.recyclerView.adapter = adapter

        //To do change Screen to Rematch Request
        viewDataBinding.btnConfirm.setOnClickListener {

            val fragment = RequestRematchFragment()

            fragment.setOtoconFragmentListener(object : OtoconFragmentListener {
                override fun onHandlerReult(status: Int, extras: Bundle) {
                    // TODO nothing
                }
            })
            FragmentUtils.replace(activity, fragment, true)
        }

        if (DBManager.setIsFirst(activity, "RematchPairingFragment")) {
            //TODO show dialog when first
            onClickGuide()

        }
        viewDataBinding.btnConfirm.visibility = if (DBManager.isLogin(context)) View.VISIBLE else View.GONE
        (adapter.getItem(0) as HeaderMatchingItem).isShow = if (DBManager.isLogin(context)) false else true


        //pull to reload
        viewDataBinding.refresh.setOnRefreshListener(this)

        presenter.onExecute(context, ACTION_GET_LIST_PARTY, null)
    }

    //pull to reload
    override fun onRefresh() {
        viewDataBinding.refresh.isRefreshing = false
        adapter.clearData()
        adapter.init(this)
        presenter.page = 1
        infiniteScrollListener.setLoading(false)
        presenter.onExecute(context, ACTION_GET_LIST_PARTY, null)
        (adapter.getItem(0) as HeaderMatchingItem).isShow = if (DBManager.isLogin(context)) false else true
    }

    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity!!, title, message, null)
    }

    override fun success(response: OnResponse?) {
        if (response is PartyConditionRespone) {

            addData(response)
            //Check page end
            infiniteScrollListener.setLoading(adapter.itemCount - 1 != response.total)

            //Check total party
            if (response.total == 0) {
                viewDataBinding.contrainLayout.visibility = View.GONE
                (adapter.getItem(0) as HeaderMatchingItem).isShow = true
            } else {
                viewDataBinding.contrainLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun showProgress(show: Boolean) {
        viewDataBinding.loading.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(response: OnResponse?) {
    }

    override fun onClickGuide() {
        RematchFlowStatusDialog(context, getString(R.string.title_rematch_flow), BuildConfig.LINK_REMATCH_FLOW).show()

    }

    private val infiniteScrollListener = object : InfiniteScrollListener() {
        override fun onLoadMore(current_page: Int) {
            this.setLoading(false)
            presenter.page += 1
            presenter.onExecute(activity, ACTION_GET_LIST_PARTY, null)
        }
    }

    fun addData(response: PartyConditionRespone) {
        adapter.addListItem(response)
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
        viewDataBinding.btnConfirm.visibility = if (DBManager.isLogin(context)) View.VISIBLE else View.GONE
    }

    override fun onReload() {
        super.onReload()
        try {
            viewDataBinding.btnConfirm.visibility = if (DBManager.isLogin(context)) View.VISIBLE else View.GONE
        }catch (e: Exception){

        }
    }

    class RematchPairingPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {
        var page = 1
        var joinStatus: Int = 3
        var limit: Int = 20

        companion object {
            var ACTION_GET_LIST_PARTY = 0
            var ACTION_READ_PARTY = 1
        }

        override fun onExecute(context: Context?, action: Int, params: String?) {
            when (action) {
                ACTION_GET_LIST_PARTY -> {
                    if (!DBManager.isLogin(context)){

                        return
                    }
                    execute(InteractorManager.getApiInterface(context).getPartyCondition(page, limit, joinStatus,1), object : ExecuteListener<PartyConditionRespone> {
                        override fun onError(e: Throwable?) {
                            val messages = OnResponse.getMessage(context, e, null)
                            view.showPopup(messages[0], messages[1])

                        }

                        override fun onNext(response: PartyConditionRespone?) {
                            if (response != null && response!!.isSuccess) {
                                view.success(response)
                            } else {
                                view.showMessage(response)
                            }
                        }
                    })
                }

                ACTION_READ_PARTY -> {
                    execute(InteractorManager.getApiInterface(context).readParty(params), object : ExecuteListener<OnResponse>{
                        override fun onNext(response: OnResponse?) {
                            context?.sendBroadcast(Intent(KeyExtensionUtils.ACTION_NOTI_MY_REMATCH))
                        }

                        override fun onError(e: Throwable?) {

                        }
                    })
                }
            }
        }
    }

    class Adapter : GroupAdapter<ViewHolder>() {

        fun getList(): ArrayList<MatchingItem>{
            val list = ArrayList<MatchingItem>()
//            if (section.itemCount > 0){
                for (i in 1 until section.itemCount){
                    list.add(section.getItem(i) as MatchingItem)
                }
//            }

            return list
        }

        fun clearData() {
            this.clear()
            section = Section()
            this.add(section)
        }

        var section = Section()

        fun init(listener: HeaderMatchingItem.Listener) {
            section.add(HeaderMatchingItem(listener as RematchPairingFragment))

        }

        init {
            add(section)
        }


        fun addListItem(response: PartyConditionRespone) {
            for (model in response.datas) {
                if (!getList().contains(MatchingItem(model))){
                    section.add(MatchingItem(model))
                }
            }
            notifyDataSetChanged()
        }
    }
}
