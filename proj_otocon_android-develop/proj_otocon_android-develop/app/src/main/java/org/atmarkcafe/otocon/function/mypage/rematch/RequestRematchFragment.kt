package org.atmarkcafe.otocon.function.mypage.rematch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import org.atmarkcafe.otocon.OtoconBindingFragment
import org.atmarkcafe.otocon.OtoconFragment
import org.atmarkcafe.otocon.databinding.FragmentRequestRematchBinding
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.function.rematch.RequestRematchDetailFragment
import org.atmarkcafe.otocon.utils.FragmentUtils

class RequestRematchFragment : OtoconBindingFragment<FragmentRequestRematchBinding>(), android.view.View.OnClickListener, OtoconFragment.OtoconFragmentListener, TabLayout.OnTabSelectedListener {
    var pagerAdapter: RequestRematchPagerAdapter? = null

    override fun layout(): Int {
        return R.layout.fragment_request_rematch
    }

    override fun onCreateView(it: FragmentRequestRematchBinding) {
        val tabTheirRequest = if (arguments != null) arguments!!.getBoolean("tab_their_request", true) else true
        val index = if (tabTheirRequest) 0 else 1

        it.tabLayout.addOnTabSelectedListener(this@RequestRematchFragment)
        it.toolbar.setNavigationOnClickListener({ v -> activity!!.onBackPressed() })

        pagerAdapter = RequestRematchPagerAdapter(childFragmentManager, this, tabTheirRequest)

        it.requestRematchViewPager.setAdapter(pagerAdapter)

        it.tabLayout.getTabAt(index)?.select()
        it.requestRematchViewPager.currentItem = index
    }

    override fun onClick(v: View?) {
        FragmentUtils.replaceChild(storeChildFrgementManager, R.id.frame, RequestRematchDetailFragment(), true)
    }

    override fun onReload() {
        super.onReload()
        val index = viewDataBinding.requestRematchViewPager.currentItem

        pagerAdapter?.get(index)?.refresh()
    }

    // Handler when success
    override fun onHandlerReult(status: Int, extras: Bundle?) {
        activity!!.onBackPressed()
        otoconFragmentListener.onHandlerReult(status, extras)
    }

    // Tab listener
    override fun onTabSelected(tab: TabLayout.Tab) {
        val index = tab.position

        viewDataBinding.requestRematchViewPager.currentItem = index
        pagerAdapter?.get(index)?.loadFirstApi()
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

}

class RequestRematchPagerAdapter(fm: FragmentManager, val onGotoRematchListinner: OtoconFragment.OtoconFragmentListener, val tabTheirRequest: Boolean) : FragmentPagerAdapter(fm) {

    var fragments = ArrayList<MyRequestRematchFragment>()

    init {
        val theirRequestRematchFragment = TheirRequestRematchFragment().setEnableCallApi(tabTheirRequest)
        theirRequestRematchFragment.otoconFragmentListener = onGotoRematchListinner

        val myRequestRematchFragment = MyRequestRematchFragment().setEnableCallApi(!tabTheirRequest)
        myRequestRematchFragment.otoconFragmentListener = onGotoRematchListinner
        fragments.add(theirRequestRematchFragment)

        fragments.add(myRequestRematchFragment)
    }

    operator fun get(i: Int): MyRequestRematchFragment {
        return fragments[i]
    }

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}