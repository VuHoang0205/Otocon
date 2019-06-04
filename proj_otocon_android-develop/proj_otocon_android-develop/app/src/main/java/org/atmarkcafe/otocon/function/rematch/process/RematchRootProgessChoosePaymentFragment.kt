package org.atmarkcafe.otocon.function.rematch.process

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.*
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.mypage.updatepremium.RematchRootProgessPaymentUpdatePremiumChooseCardFragment
import org.atmarkcafe.otocon.function.mypage.updatepremium.UpgradeUserPremiumPravicyFragment
import org.atmarkcafe.otocon.function.rematch.RematchStatus
import org.atmarkcafe.otocon.model.DBManager
import org.atmarkcafe.otocon.model.parameter.ShareContactUseCoupon
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.RematchCouponResponse
import org.atmarkcafe.otocon.utils.FragmentUtils
import org.atmarkcafe.otocon.utils.GlideUtils
import org.atmarkcafe.otocon.utils.KeyExtensionUtils


class RematchRootProgessChoosePaymentFragment : OtoconBindingFragment<FragmentRematchUserProgressChoosePaymentBinding>(), MVPExtension.View<OnResponse>, OtoconFragment.OtoconFragmentListener {
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        onBackPressed()
        otoconFragmentListener?.onHandlerReult(0, extras)
    }

    var presenter: RematchRootProgessChoosePaymentPresenter = RematchRootProgessChoosePaymentPresenter(this)
    var params: ShareContactUseCoupon = ShareContactUseCoupon()
    var isPayOff = true
    var transaction_id: String = ""

    override fun layout(): Int {
        return R.layout.fragment_rematch_user_progress_choose_payment
    }

    override fun onCreateView(viewDataBinding: FragmentRematchUserProgressChoosePaymentBinding) {
        viewDataBinding.fragment = this
        if (arguments != null) {
            transaction_id = arguments!!.getString("transaction_id")
            params.transaction_id = transaction_id
            presenter.userId= arguments!!.getString(KeyExtensionUtils.KEY_USER_ID)
            presenter.eventId= arguments!!.getString(KeyExtensionUtils.KEY_EVENT_ID)

        }
        presenter.onExecute(activity, 0, null)

        viewDataBinding.ivClose.setOnClickListener() {
            //            activity?.supportFragmentManager?.popBackStack()
            activity!!.onBackPressed()
        }
        viewDataBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_use) {
                viewDataBinding.freeMeber.setImageResource(R.drawable.ic_free_pay)
            } else {
                viewDataBinding.freeMeber.setImageResource(R.drawable.ic_payoff_member)
            }
        }

        viewDataBinding.freeMeber.setOnClickListener() {
            if (isPayOff) {
                // TODO pay off

                val fragment = RematchRootProgessPaymentUpdatePremiumChooseCardFragment()
                val bundle = Bundle()
                bundle.putString("transaction_id", transaction_id)
                fragment.arguments = bundle
                fragment.otoconFragmentListener = this
                fragment.transaction_id = transaction_id
                fragment.isPremium = false
                FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
            } else {
                // todo call api pay coupon
                presenter.onExecute(activity, 1, params)
            }
        }

        viewDataBinding.premiumMember.setOnClickListener() {
            //TODO Premium

            val fragment = UpgradeUserPremiumPravicyFragment()
            fragment.otoconFragmentListener = this
            fragment.transaction_id = transaction_id
            fragment.isPremium = true
            FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, fragment, true)
        }
    }

    //View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(activity, title, message, null)
    }

    override fun success(response: OnResponse?) {
        if (response is RematchCouponResponse) {
            if (response.model == null) {
                viewDataBinding.layoutCoupon.visibility = View.GONE
            } else if (response.model != null) {
                params.coupon_id = response.model.id.toString()
                viewDataBinding.edCoupon.text = response.model.code
                viewDataBinding.layoutCoupon.visibility = View.VISIBLE
                loadUrl(viewDataBinding.myAvatar,response.model.avatar_request)
                loadUrl(viewDataBinding.theirAvatar,response.model.user_receive_avatar)
                if (response.model.code != null) {
                    viewDataBinding.layoutCoupon.visibility=View.VISIBLE
                    viewDataBinding.radioUse.isChecked = true
                    viewDataBinding.freeMeber.setImageResource(R.drawable.ic_free_pay)
                }else{
                    viewDataBinding.layoutCoupon.visibility=View.GONE
                }
            }
        } else {
            var extras = Bundle()
            extras.putSerializable("status", RematchStatus.coupon)
//            finish(0, extras)
            finishParent(0, extras)
        }
    }

    override fun showProgress(show: Boolean) {
        setEnableBack(!show)
        if (show) viewDataBinding.loading.root.visibility = View.VISIBLE else viewDataBinding.loading.root.visibility = View.GONE
    }

    override fun showMessage(response: OnResponse?) {
        PopupMessageErrorDialog.show(context, getString(R.string.app_name), response?.message, null)
    }

    private fun loadUrl(imageView: ImageView, url: String?) {
        GlideUtils.show(url,imageView, R.drawable.xml_bakground_default_load_image, R.drawable.ic_photo_unavailable)
    }
}

class RematchRootProgessChoosePaymentPresenter(view: MVPExtension.View<OnResponse>) : MVPPresenter<ShareContactUseCoupon, OnResponse>(view) {

    var userId : String? =null
    var eventId : String? =null
    override fun onExecute(context: Context?, action: Int, params: ShareContactUseCoupon?) {
        if (action == 0) {
            execute(InteractorManager.getApiInterface(context).getRematchCoupon(userId, eventId), object : ExecuteListener<RematchCouponResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: RematchCouponResponse?) {
                    if (response!!.isSuccess) {
                        view.success(response)
                    } else {
                        view.showMessage(response)
                    }
                }
            })
        } else if (action == 1) {
            execute(InteractorManager.getApiInterface(context).shareContactUseCoupon(params), object : ExecuteListener<OnResponse> {
                override fun onError(e: Throwable?) {
                    val messages = OnResponse.getMessage(context, e, null)
                    view.showPopup(messages[0], messages[1])
                }

                override fun onNext(response: OnResponse?) {
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