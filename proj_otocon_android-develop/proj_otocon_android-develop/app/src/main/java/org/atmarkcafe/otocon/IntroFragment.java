package org.atmarkcafe.otocon;

import android.content.Intent;
import android.content.res.Resources;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;

import android.databinding.ViewDataBinding;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import org.atmarkcafe.otocon.function.config.DefaultSearchCityFragment;
import org.atmarkcafe.otocon.ktextension.OnItemClickListener;
import org.atmarkcafe.otocon.pref.SearchDefault;
import org.atmarkcafe.otocon.utils.BindingUtils;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.databinding.ActivityIntroBinding;
import org.atmarkcafe.otocon.databinding.ItemIntroBinding;

public class IntroFragment extends OtoconBindingFragment<ActivityIntroBinding> implements BindingUtils, View.OnScrollChangeListener {

    @Override
    public int layout() {
        return R.layout.activity_intro;
    }

    @Override
    public void onCreateView(ActivityIntroBinding binding) {
        binding.recyclerView.setAdapter(new IntroAdapter(new OnItemClickListener<String, String>() {
            @Override
            public void onItemClick(int position, String s, String data) {
                FragmentUtils.replace(getActivity(), new MainFragment(), false);
            }
        }));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setOnScrollChangeListener(this);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerView);

        //binding.recyclerView.set
        // load save default for screen
        if (SearchDefault.getInstance().init(getActivity()).isSaveDefault()) {
            binding.frame.setVisibility(View.VISIBLE);
            setStoreChildFrgementManager(getChildFragmentManager());
            DefaultSearchCityFragment f =  new DefaultSearchCityFragment();
            FragmentUtils.replaceChild(getChildFragmentManager(),R.id.frame,  f, false);

        } else {
            binding.frame.setVisibility(View.GONE);
        }
    }
    public  void onSusscess(){
        getStoreChildFrgementManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        setStoreChildFrgementManager(null);
        viewDataBinding.frame.setVisibility(View.GONE);
    }
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int[] res = new int[]{
                R.drawable.intro_v01,
                R.drawable.intro_v02,
                R.drawable.intro_v03,
                R.drawable.intro_v04,
                R.drawable.intro_v05,

        };
        LinearLayoutManager layoutManager = ((LinearLayoutManager)viewDataBinding.recyclerView.getLayoutManager());
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        viewDataBinding.indivicaotor.setImageResource(res[firstVisiblePosition]);
    }


    public class IntroAdapter extends RecyclerView.Adapter<IntroViewHolder> implements View.OnClickListener {

        private OnItemClickListener<String, String> onItemClickListener;

        public IntroAdapter(OnItemClickListener<String, String> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(0, "", "");
        }

        @NonNull
        @Override
        public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemIntroBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_intro, viewGroup, false);
            return new IntroViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull IntroViewHolder introViewHolder, int i) {
            int[] res = new int[]{
                    R.drawable.intro_01,
                    R.drawable.intro_02,
                    R.drawable.intro_03,
                    R.drawable.intro_04,
                    R.drawable.intro_05,

            };
            introViewHolder.setBind(res[i]);


            if(i == 0){
                introViewHolder.binding.imgIntro2.setVisibility(View.VISIBLE);
                introViewHolder.binding.imgIntro2.setImageResource( R.drawable.intro_01);
            }else{
                introViewHolder.binding.imgIntro2.setVisibility(View.GONE);
            }

            introViewHolder.binding.btnNext.setVisibility(View.GONE);
            if(i == 4){
                introViewHolder.binding.btnNext.setVisibility(View.VISIBLE);
                introViewHolder.binding.btnNext.setOnClickListener(this);
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    public class IntroViewHolder extends RecyclerView.ViewHolder {
        ItemIntroBinding binding;

        public IntroViewHolder(ItemIntroBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setBind(int image) {
            binding.imgIntro.setImageResource(image);

        }
    }

    public static class CirclePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

        private int colorActive = 0x727272;
        private int colorInactive = 0xF44336;

        private static final float DP = Resources.getSystem().getDisplayMetrics().density;

        /**
         * Height of the space the indicator takes up at the bottom of the view.
         */
        private final int mIndicatorHeight = (int) (DP * 16);

        /**
         * Indicator stroke width.
         */
        private final float mIndicatorStrokeWidth = DP * 2;

        /**
         * Indicator width.
         */
        private final float mIndicatorItemLength = DP * 16;
        /**
         * Padding between indicators.
         */
        private final float mIndicatorItemPadding = DP * 4;

        /**
         * Some more natural animation interpolation
         */
        private final android.view.animation.Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

        private final Paint mPaint = new Paint();

        public CirclePagerIndicatorDecoration() {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mIndicatorStrokeWidth);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int itemCount = parent.getAdapter().getItemCount();

            // center horizontally, calculate width and subtract half from center
            float totalLength = mIndicatorItemLength * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

            // center vertically in the allotted space
            float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


            // find active page (which should be highlighted)
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }

            // find offset of active page (if the user is scrolling)
            final View activeChild = layoutManager.findViewByPosition(activePosition);
            int left = activeChild.getLeft();
            int width = activeChild.getWidth();

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
        }

        private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
            mPaint.setColor(Color.GRAY);

            // width of item indicator including padding
            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            float start = indicatorStartX;
            for (int i = 0; i < itemCount; i++) {
                // draw the line for every item
                c.drawCircle(start + mIndicatorItemLength, indicatorPosY, itemWidth / 6, mPaint);
                //  c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
                start += itemWidth;
            }
        }

        private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                    int highlightPosition, float progress, int itemCount) {
            mPaint.setColor(Color.RED);

            // width of item indicator including padding
            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            if (progress == 0F) {
                // no swipe, draw a normal indicator
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
         /*   c.drawLine(highlightStart, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
        */
                c.drawCircle(highlightStart, indicatorPosY, itemWidth / 6, mPaint);

            } else {
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                // calculate partial highlight
                float partialLength = mIndicatorItemLength * progress;
                c.drawCircle(highlightStart + mIndicatorItemLength, indicatorPosY, itemWidth / 6, mPaint);

                // draw the cut off highlight
           /* c.drawLine(highlightStart + partialLength, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
*/
                // draw the highlight overlapping to the next item as well
           /* if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth;
                *//*c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + partialLength, indicatorPosY, mPaint);*//*
                c.drawCircle(highlightStart ,indicatorPosY,itemWidth/4,mPaint);

            }*/
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mIndicatorHeight;
        }
    }


}
