package org.atmarkcafe.otocon.function.rematch

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.tab_indivicator.*
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.api.MVPExtension
import org.atmarkcafe.otocon.api.MVPPresenter
import org.atmarkcafe.otocon.api.interactor.InteractorManager
import org.atmarkcafe.otocon.databinding.FragmentProfileDetailBinding
import org.atmarkcafe.otocon.databinding.ItemImageBinding
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog
import org.atmarkcafe.otocon.function.rematch.item.ProfileItem
import org.atmarkcafe.otocon.model.Avatar
import org.atmarkcafe.otocon.model.ProfileDetail
import org.atmarkcafe.otocon.model.response.OnResponse
import org.atmarkcafe.otocon.model.response.ResponseExtension
import java.util.*

class ProfileDetailFragment : OtoconBindingFragment<FragmentProfileDetailBinding>(), MVPExtension.View<ResponseExtension<ProfileDetail>>, OtoconFragment.OtoconFragmentListener, View.OnClickListener {
    val presenter = ProfileDetailPresenter(this)
    var item: ProfileDetail? = null

    override fun onHandlerReult(status: Int, extras: Bundle?) {
        // do nothing
    }

    override fun layout(): Int {
        return R.layout.fragment_profile_detail
    }

    override fun onCreateView(viewDataBinding: FragmentProfileDetailBinding?) {
        val userId = arguments!!.getString("user_id")
        val eventId = arguments!!.getString("event_id")

        viewDataBinding?.let {
            it.toolbar.setNavigationOnClickListener { finish() }

            it.avatar00.setOnClickListener(this)
            it.avatar01.setOnClickListener(this)
            it.avatar02.setOnClickListener(this)
            it.avatar03.setOnClickListener(this)

            it.avatarViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {

                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {
                    item?.setSeletedAvatar(p0)
                }

            })
        }

        val params = Arrays.asList(userId, eventId)
        presenter.onExecute(context, 0, params)

    }

    // View.OnClickListener: click avatar
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.avatar_00 -> {
                viewDataBinding.avatarViewPager.currentItem = 0
                item?.setSeletedAvatar(0)
            }
            R.id.avatar_01 -> {
                viewDataBinding.avatarViewPager.currentItem = 1
                item?.setSeletedAvatar(1)
            }
            R.id.avatar_02 -> {
                viewDataBinding.avatarViewPager.currentItem = 2
                item?.setSeletedAvatar(2)
            }
            R.id.avatar_03 -> {
                viewDataBinding.avatarViewPager.currentItem = 3
                item?.setSeletedAvatar(3)
            }
        }
    }

    // MVPExtension.View
    override fun showPopup(title: String?, message: String?) {
        PopupMessageErrorDialog.show(context, title, message) { finish() }
    }

    // MVPExtension.View
    override fun success(response: ResponseExtension<ProfileDetail>?) {
        item = response!!.dataList[0]
        item?.refactorAvatar()
        viewDataBinding.item = item

        updateAvatar()
        viewDataBinding.profileRecyclerView.adapter = item?.getProfileAdapter(context!!)

        updateWithTextName()
    }

    private fun updateWithTextName(){
        val rect = Rect()
        var txt = item!!.getAgePrefecture()
        val textPaint = viewDataBinding.agePrefecture.paint
        textPaint.getTextBounds(txt, 0, txt.length, rect)

        val rect2 = Rect()
        txt = String.format(getString(R.string.rematch_profile_completed), item!!.getCommonCompleted())
        textPaint.getTextBounds(txt, 0, txt.length, rect2)

        val width = context?.resources?.displayMetrics!!.density * 66 + rect.width() + rect2.width()
        viewDataBinding.name.maxWidth = context?.resources?.displayMetrics!!.widthPixels - width.toInt()
    }

    private fun updateAvatar() {
        val adapter = AvatarPagerAdapter(item!!.avatar, item!!.gender)
        viewDataBinding.avatarViewPager.adapter = adapter
        viewDataBinding.avatarViewPager.currentItem = 0
        adapter.notifyDataSetChanged()
    }

    // MVPExtension.View
    override fun showProgress(show: Boolean) {
        viewDataBinding.loadingLayout.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    // MVPExtension.View
    override fun showMessage(response: ResponseExtension<ProfileDetail>?) {

    }

}

class ProfileDetailPresenter(view: MVPExtension.View<ResponseExtension<ProfileDetail>>) : MVPPresenter<List<String>, ResponseExtension<ProfileDetail>>(view) {
    override fun onExecute(context: Context?, action: Int, params: List<String>?) {
        execute(InteractorManager.getApiInterface(context).getCardUserParty(params!![0], params!![1]), object : MVPPresenter.ExecuteListener<ResponseExtension<ProfileDetail>> {
            override fun onNext(respone: ResponseExtension<ProfileDetail>?) {
                if (respone != null && respone.isSuccess) {
                    view.success(respone)
                } else {
                    val message = OnResponse.getMessage(context, null, respone)
                    view.showPopup(message[0], message[1])
                }
            }

            override fun onError(e: Throwable) {
                val message = OnResponse.getMessage(context, e, null)
                view.showPopup(message[0], message[1])
            }
        })
    }
}

class AvatarPagerAdapter(val list: List<Avatar>, val gender: Int) : PagerAdapter() {
    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageBinding = DataBindingUtil.inflate<ItemImageBinding>(LayoutInflater.from(container.context), R.layout.item_image, container, true)

        imageBinding.urlImage = list[position].picture
        imageBinding.gender = gender

        return imageBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return list.size
    }

}