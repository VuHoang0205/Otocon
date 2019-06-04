package org.atmarkcafe.otocon.function.mypage

import android.content.Context
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.atmarkcafe.otocon.ExtensionActivity
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentNotificationSettingBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.item.NotificationSettingItem
import org.atmarkcafe.otocon.model.NotificationSetting
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension

class NotificationSettingFragment : OtoconBindingFragment<FragmentNotificationSettingBinding>(), MVPExtension.View<OnResponse>, NotificationSettingItem.OnChangedCheckboxListener {
    val presenter = NotificationSettingPresenter(this)
    val adapter = GroupAdapter<ViewHolder>()
    var setting : NotificationSetting ?= null
    var changeSettingType = -1

    override fun layout(): Int {
        return R.layout.fragment_notification_setting
    }

    override fun onCreateView(viewDataBinding: FragmentNotificationSettingBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener { _ -> activity!!.onBackPressed() }

        viewDataBinding.notificationSettingRecyclerView.adapter = adapter

        presenter.onExecute(context, NotificationSettingPresenter.ACTION_GET_SETTING, null)
    }

    // MVPExtension.View<OnResponse>
    override fun showPopup(title: String?, message: String?) {
        if (presenter.actionType == NotificationSettingPresenter.ACTION_GET_SETTING) {
            // get notification settings api failed -> finish
            PopupMessageErrorDialog.show(activity!!, title, message) {
                ExtensionActivity.setEnableBack(activity, true)
                finish()
            }
        } else {
            setting?.change(changeSettingType)
            adapter.notifyDataSetChanged()
            PopupMessageErrorDialog.show(activity!!, title, message, null)
        }
    }

    // MVPExtension.View<OnResponse>
    override fun success(response: OnResponse?) {
        if (response is ResponseExtension<*>) {
            setting = (response as ResponseExtension<NotificationSetting>).dataList[0]
            adapter.add(NotificationSettingItem(NotificationSetting.NEW_PARTY, setting!!, this))
            adapter.add(NotificationSettingItem(NotificationSetting.COUPON, setting!!, this))
            adapter.add(NotificationSettingItem(NotificationSetting.BEFORE_PARTY_LIKED, setting!!, this))
            adapter.add(NotificationSettingItem(NotificationSetting.PARTY_SLOT, setting!!, this))
            adapter.add(NotificationSettingItem(NotificationSetting.BEFORE_PARTY_JOINED, setting!!, this))
            adapter.add(NotificationSettingItem(NotificationSetting.REMATCH, setting!!, this))
            adapter.notifyDataSetChanged()
        } else {
            // update success
            adapter.notifyDataSetChanged()
        }
    }

    // MVPExtension.View<OnResponse>
    override fun showProgress(show: Boolean) {
        viewDataBinding.loadingLayout.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    // MVPExtension.View<OnResponse>
    override fun showMessage(response: OnResponse?) {

    }

    // NotificationSettingItem.OnChangedCheckboxListener
    override fun onChanged(type: Int) {
        changeSettingType = type
        setting?.change(type)
        presenter.onExecute(context, NotificationSettingPresenter.ACTION_UPDATE_SETTING, setting)

    }
}

class NotificationSettingPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<NotificationSetting, OnResponse>(view) {
    var actionType = -1
    override fun onExecute(context: Context?, action: Int, params: NotificationSetting?) {
        actionType = action
        when (action) {
            ACTION_GET_SETTING -> {
                execute(InteractorManager.getApiInterface(context).notificationSettings, object : MVPPresenter.ExecuteListener<ResponseExtension<NotificationSetting>> {
                    override fun onNext(response: ResponseExtension<NotificationSetting>?) {
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
                    }
                })
            }
            ACTION_UPDATE_SETTING -> {
                execute(InteractorManager.getApiInterface(context).updateNotificationSettings(params), object : MVPPresenter.ExecuteListener<OnResponse> {
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
                    }
                })
            }
        }
    }

    companion object {
        const val ACTION_GET_SETTING = 0
        const val ACTION_UPDATE_SETTING = 1
    }
}
