package org.atmarkcafe.otocon.function.rematch

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.atmarkcafe.otocon.*
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentRequestRematchDetailBinding
import org.atmarkcafe.otocon.dialog.DialogRematchMessage
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog
import org.atmarkcafe.otocon.function.mypage.rematch.RequestRematchFragment
import org.atmarkcafe.otocon.function.rematch.process.RematchRootProgessFragment
import org.atmarkcafe.otocon.model.Avatar
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.RematchDetail
import org.atmarkcafe.otocon.model.parameter.ExpiredRematchParams
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.GlideUtils
import org.atmarkcafe.otocon.utils.LogUtils

class RequestRematchDetailFragment : OtoconBindingFragment<FragmentRequestRematchDetailBinding>(), android.view.View.OnClickListener, MVPExtension.View<OnResponse>, OtoconFragment.OtoconFragmentListener {
    var presenter: ReatchDetailPresenter? = null

    var userId: String = ""
    var eventId: String = ""
    var avatarDefault = -1
    var isSendRequest = true

    override fun showPopup(title: String?, message: String?) {
        ExtensionActivity.setEnableBack(activity, true)
        if (title != null) {
            PopupMessageErrorDialog.show(context, title, message, null)
        } else {
            DialogRematchMessage(activity!!, message, DialogRematchMessage.DialogRematchListener { isOke, isChecked -> })
                    .setTextButtonGreen(getString(R.string.ok)).show()
        }
    }

    override fun success(response: OnResponse?) {
        if (response is ResponseExtension<*>) {
            // rematch detail
            val data = response as ResponseExtension<RematchDetail>
            val item = data.getData()[0]
            viewDataBinding.item = item
            item.gender = DBManager.getMyAccount(context).gender
            isSendRequest = item.isSendRequestList()

            viewDataBinding.rematchMessage.setBackgroundColor(item.getRematchColor())

            if (item.isWithDrawalUser()) {
                // TODO update drawal user
                viewDataBinding?.rematchSend?.visibility = View.GONE
                viewDataBinding?.rematchApprove?.visibility = View.GONE
                viewDataBinding?.rematchReject?.visibility = View.GONE
                viewDataBinding?.rematchRequest?.visibility = View.GONE
                updateAvatar(item)
            } else {
                // TODO update normal user
                updateAvatar(item)

                when (item.rematchUserInfoStatus) {
                    RematchDetail.STATUS_SEND_REQUEST -> {
                        viewDataBinding?.rematchSend?.visibility = View.VISIBLE
                        viewDataBinding?.rematchApprove?.visibility = View.GONE
                        viewDataBinding?.rematchReject?.visibility = View.GONE
                        viewDataBinding?.rematchRequest?.visibility = View.VISIBLE

                    }
                    RematchDetail.STATUS_APPROVE_REQUEST -> {
                        viewDataBinding?.rematchSend?.visibility = View.GONE
                        viewDataBinding?.rematchApprove?.visibility = View.VISIBLE
                        viewDataBinding?.rematchReject?.visibility = View.VISIBLE
                        viewDataBinding?.rematchRequest?.visibility = View.VISIBLE
                    }

                    RematchDetail.STATUS_SENT_REQUEST -> {
                        viewDataBinding?.rematchSend?.visibility = View.GONE
                        viewDataBinding?.rematchApprove?.visibility = View.GONE
                        viewDataBinding?.rematchReject?.visibility = View.GONE
                        viewDataBinding?.rematchRequest?.visibility = View.VISIBLE
                    }

                }
            }
        } else {
            // approve rematch, reject rematch
            ExtensionActivity.setEnableBack(activity, true)
            presenter?.onExecute(context, ReatchDetailPresenter.ACTION_REMATCH_DETAIL, null)
        }
    }

    private fun updateAvatar(item: RematchDetail) {
        item.refactorAvatar()
        val adapter = AvatarPagerAdapter(item.avatar, item.gender)
        viewDataBinding.avatarViewPager.adapter = adapter
        viewDataBinding.avatarViewPager.currentItem = 0
        adapter.notifyDataSetChanged()

    }

    override fun showProgress(show: Boolean) {
        viewDataBinding.loadingLayout.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(response: OnResponse?) {

    }

    override fun layout(): Int {
        return R.layout.fragment_request_rematch_detail
    }

    override fun onCreateView(viewDataBinding: FragmentRequestRematchDetailBinding?) {
        userId = arguments!!.getString("user_id")
        eventId = arguments!!.getString("event_id")
        presenter = ReatchDetailPresenter(userId, eventId, this)
        viewDataBinding?.let {
            it.rematchSend.setOnClickListener(this)
            it.rematchApprove.setOnClickListener(this)
            it.rematchReject.setOnClickListener(this)
            it.rematchFlow.setOnClickListener(this)
            it.rematchRequest.setOnClickListener(this)
            it.rematchMyCard.setOnClickListener(this)

            it.backgroundRematchAvatar00.setOnClickListener(this)
            it.backgroundRematchAvatar01.setOnClickListener(this)
            it.backgroundRematchAvatar02.setOnClickListener(this)
            it.backgroundRematchAvatar03.setOnClickListener(this)

            it.avatarViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {}

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

                override fun onPageSelected(p0: Int) {
                    it.item?.setSeletedAvatar(p0)
                }
            })

        }

        viewDataBinding!!.toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }

        presenter?.onExecute(context, ReatchDetailPresenter.ACTION_REMATCH_DETAIL, null)
    }

    fun showPopupConfirm(type: Int) {
        var keyData = ""
        var message = ""
        when (type) {
            ReatchDetailPresenter.ACTION_APPROVE_REQUEST -> {
                keyData = "RequestRematchAccept"
                message = getString(R.string.rematch_approve_message)
            }
            ReatchDetailPresenter.ACTION_REJECT_REQUEST -> {
                keyData = "RequestRematchDenied"
                message = getString(R.string.rematch_reject_message)
            }
            ReatchDetailPresenter.ACTION_SEND_REQUEST -> {
                keyData = "RematchUserDetailFragment"
                message = getString(R.string.message_confirm_request)
            }

        }
        if (!DBManager.get(activity!!, keyData, false)) {
            var listener = DialogRematchMessage.DialogRematchListener { isOke, isChecked ->
                if (isOke) {
                    if (isChecked) {
                        // TODO save
                        LogUtils.d("isChecked : " + isChecked, null)
                        DBManager.save(activity!!, keyData, true)
                    }
                    // call api approve rematch
                    ExtensionActivity.setEnableBack(activity, false)
                    when (type) {
                        ReatchDetailPresenter.ACTION_APPROVE_REQUEST -> presenter?.onExecute(context, ReatchDetailPresenter.ACTION_APPROVE_REQUEST, "$type")
                        ReatchDetailPresenter.ACTION_REJECT_REQUEST -> presenter?.onExecute(context, ReatchDetailPresenter.ACTION_REJECT_REQUEST, "$type")
                        ReatchDetailPresenter.ACTION_SEND_REQUEST -> gotoRematchProgress()
                    }
                }
            }
            val dialog = DialogRematchMessage(activity!!, message, listener)
            dialog.setTextButtonGreen(getString(R.string.next))
            dialog.setTexButtonBlack(getString(R.string.cancel))
            dialog.setTextCheckBox(getString(R.string.do_not_display_next_time))
            dialog.show()
        } else {
            // call api approve rematch
            ExtensionActivity.setEnableBack(activity, false)
            when (type) {
                ReatchDetailPresenter.ACTION_APPROVE_REQUEST -> presenter?.onExecute(context, ReatchDetailPresenter.ACTION_APPROVE_REQUEST, "$type")
                ReatchDetailPresenter.ACTION_REJECT_REQUEST -> presenter?.onExecute(context, ReatchDetailPresenter.ACTION_REJECT_REQUEST, "$type")
                ReatchDetailPresenter.ACTION_SEND_REQUEST -> gotoRematchProgress()
            }

        }
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.rematch_flow -> RematchFlowStatusDialog(context, getString(R.string.title_rematch_flow), BuildConfig.LINK_REMATCH_FLOW).show()

            R.id.rematch_approve -> {
                // TODO accepted request
                presenter?.onExecute(context, ReatchDetailPresenter.ACTION_CHECK_EXPIRED, "${ReatchDetailPresenter.ACTION_APPROVE_REQUEST}")
            }

            R.id.rematch_reject -> {
                // TODO denied request
                presenter?.onExecute(context, ReatchDetailPresenter.ACTION_CHECK_EXPIRED, "${ReatchDetailPresenter.ACTION_REJECT_REQUEST}")
            }

            R.id.rematch_send -> {
                // TODO send request
                presenter?.onExecute(context, ReatchDetailPresenter.ACTION_CHECK_EXPIRED, "${ReatchDetailPresenter.ACTION_SEND_REQUEST}")
            }

            R.id.rematch_request -> {
                // TODO Rematch Request
                val fragment = RequestRematchFragment()
                val args = Bundle()
                args.putBoolean("tab_their_request", isSendRequest)
                fragment.arguments = args

                fragment.setOtoconFragmentListener(object : OtoconFragmentListener {
                    override fun onHandlerReult(status: Int, extras: Bundle) {
                        // TODO nothing
                        activity!!.onBackPressed()
                        otoconFragmentListener.onHandlerReult(status, extras)
                    }
                })

                FragmentUtils.add(activity, fragment, true)
            }

            R.id.rematch_my_card -> {
                val fragment = ProfileDetailFragment()

                val args = Bundle()
                args.putString("user_id", userId)
                args.putString("event_id", eventId)
                fragment.arguments = args

                FragmentUtils.replace(activity!!, fragment, true)
            }

            R.id.background_rematch_avatar_00 -> {
                viewDataBinding.avatarViewPager.currentItem = 0
                viewDataBinding.item?.setSeletedAvatar(0)
            }
            R.id.background_rematch_avatar_01 -> {
                viewDataBinding.avatarViewPager.currentItem = 1
                viewDataBinding.item?.setSeletedAvatar(1)
            }
            R.id.background_rematch_avatar_02 -> {
                viewDataBinding.avatarViewPager.currentItem = 2
                viewDataBinding.item?.setSeletedAvatar(2)
            }
            R.id.background_rematch_avatar_03 -> {
                viewDataBinding.avatarViewPager.currentItem = 3
                viewDataBinding.item?.setSeletedAvatar(3)
            }
        }
    }

    fun gotoRematchProgress() {
        ExtensionActivity.setEnableBack(activity, true)
        val fragment = RematchRootProgessFragment()
        val bundle = Bundle()
        bundle.putString("rematch_user_receive_id", userId)
        bundle.putString("event_id", eventId)
        fragment.arguments = bundle
        fragment.otoconFragmentListener = this@RequestRematchDetailFragment
        FragmentUtils.add(activity!!, fragment, true)
    }

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        // send request is successful
        val status = extras?.getSerializable("status") as RematchStatus
        if (status == RematchStatus.isPremium || status == RematchStatus.updatePremium) {
            presenter?.onExecute(context, ReatchDetailPresenter.ACTION_REMATCH_DETAIL, null)
        } else {
            val listener = DialogRematchMessage.DialogRematchListener { _, _ -> presenter?.onExecute(context, ReatchDetailPresenter.ACTION_REMATCH_DETAIL, null) }
            val msg = if (status == RematchStatus.coupon) getString(R.string.message_send_request_success_by_coupon) else getString(R.string.message_send_request_success)
            val dialog = DialogRematchMessage(activity!!, msg, listener)
            dialog.setTextButtonGreen(getString(R.string.next))
            dialog.show()
        }
    }
}

class ReatchDetailPresenter(val userId: String, val eventId: String, view: MVPExtension.View<OnResponse>) : MVPPresenter<String, OnResponse>(view) {
    companion object {
        const val ACTION_REMATCH_DETAIL = 0
        const val ACTION_SEND_REQUEST = 1
        const val ACTION_APPROVE_REQUEST = 2
        const val ACTION_REJECT_REQUEST = 3
        const val ACTION_CHECK_EXPIRED = 4
        const val SHARE_CONTACT_TYPE_APPROVE = "1"
        const val SHARE_CONTACT_TYPE_REJECT = "2"
    }

    var shareContactId: String? = null

    private fun getRematchRequest(context: Context?) {
        execute(InteractorManager.getApiInterface(context).getRematchDetail(userId, eventId), object : MVPPresenter.ExecuteListener<ResponseExtension<RematchDetail>> {
            override fun onNext(response: ResponseExtension<RematchDetail>?) {
                if (response != null && response.isSuccess) {
                    if (response.dataList.size > 0) {
                        shareContactId = response.dataList[0].shareContactId
                        view.success(response)
                    }
                } else {
                    val messages = OnResponse.getMessage(context, null, response)
                    view.showPopup(messages[0], messages[1])
                }
            }

            override fun onError(e: Throwable) {
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])
            }
        })
    }

    private fun actRequest(context: Context?, type: String) {
        // TODO send request approve/ reject
        execute(InteractorManager.getApiInterface(context).actRematch(shareContactId, type), object : MVPPresenter.ExecuteListener<OnResponse> {
            override fun onNext(response: OnResponse?) {
                if (response != null && response.isSuccess) {
                    view.success(response)
                } else {
                    val messages = OnResponse.getMessage(context, null, response)
                    view.showPopup(messages[0], messages[1])
                }
            }

            override fun onError(e: Throwable) {
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])
            }
        })
    }

    fun checkExpired(context: Context?, type: Int) {
        val param = ExpiredRematchParams()
        param.eventId = eventId

        execute(InteractorManager.getApiInterface(context).checkRematchExpried(param), object : MVPPresenter.ExecuteListener<OnResponse> {
            override fun onNext(response: OnResponse?) {
                if (response != null && response.isSuccess) {
                    // TODO show popup confirm first
                    (view as RequestRematchDetailFragment).showPopupConfirm(type)
                } else if (response != null) {
                    view.showPopup(null, response.message)
                } else {
                    val messages = OnResponse.getMessage(context, null, response)
                    view.showPopup(messages[0], messages[1])
                }
            }

            override fun onError(e: Throwable) {
                val messages = OnResponse.getMessage(context, e, null)
                view.showPopup(messages[0], messages[1])
            }
        })
    }

    override fun onExecute(context: Context?, action: Int, params: String?) {
        when (action) {
            ACTION_REMATCH_DETAIL -> getRematchRequest(context)
            ACTION_CHECK_EXPIRED -> checkExpired(context, Integer.parseInt(params))
            ACTION_APPROVE_REQUEST -> actRequest(context, SHARE_CONTACT_TYPE_APPROVE)
            ACTION_REJECT_REQUEST -> actRequest(context, SHARE_CONTACT_TYPE_REJECT)
        }
    }


}