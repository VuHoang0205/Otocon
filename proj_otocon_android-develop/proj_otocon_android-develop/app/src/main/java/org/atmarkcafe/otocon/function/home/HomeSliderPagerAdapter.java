package org.atmarkcafe.otocon.function.home;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemSliderBinding;
import org.atmarkcafe.otocon.model.RecommendSlider;

import java.util.List;

public class HomeSliderPagerAdapter extends PagerAdapter {
    private ViewPager mViewPager;
    private List<RecommendSlider> mSliderList;
    private Handler mHandler;
    private OnClickSliderListener mListener;

    public interface OnClickSliderListener{
        void onClick(RecommendSlider recommendSlider);
    }

    public HomeSliderPagerAdapter(ViewPager viewPager, List<RecommendSlider> list, OnClickSliderListener listener) {
        this.mViewPager = viewPager;
        this.mSliderList = list;
        this.mHandler = new Handler();
        this.mListener = listener;
        init();
    }



    private void init() {
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int jumpPosition = -1;

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    jumpPosition = getRealCount();
                } else if (position == getRealCount() + 1) {
                    jumpPosition = 1;
                }

                if (mSliderList.size()>1) {
                    mHandler.removeCallbacks(mAutoSwipeSlider);
                    mHandler.postDelayed(mAutoSwipeSlider, 10000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && jumpPosition >= 0) {
                    //Jump without animation so the user is not aware what happened.
                    mViewPager.setCurrentItem(jumpPosition, false);
                    //Reset jump position.
                    jumpPosition = -1;
                }

            }
        });
    }

    private Runnable mAutoSwipeSlider = new Runnable() {

        @Override
        public void run() {
            int pos = mViewPager.getCurrentItem();
            if (pos == getRealCount() + 1){
                pos = 1;
            }
            mViewPager.setCurrentItem(pos + 1);
        }
    };

    public int getRealCount() {
        if (mSliderList == null) return 0;
        return mSliderList.size();
    }

    @Override
    public int getCount() {
        if (mSliderList == null) return 0;
        return (mSliderList.size() > 1) ? mSliderList.size() + 2 : mSliderList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemSliderBinding sliderBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.item_slider, container, true);
        int realPosition = position % mSliderList.size();
        sliderBinding.setItem(mSliderList.get(realPosition));


        RecommendSlider slider = mSliderList.get(realPosition);
        sliderBinding.image.setOnClickListener(v -> {
            if (mListener == null) return;
            mListener.onClick(slider);
        });

        return sliderBinding.getRoot();

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
