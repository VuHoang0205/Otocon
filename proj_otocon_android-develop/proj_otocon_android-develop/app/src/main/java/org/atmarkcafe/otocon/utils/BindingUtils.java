package org.atmarkcafe.otocon.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.PartyDetailData;
import org.atmarkcafe.otocon.view.loopview.LoopView;
import org.atmarkcafe.otocon.view.loopview.OnItemSelectedListener;

import java.util.List;

@BindingMethods(
        {
                @BindingMethod(type = Toolbar.class,
                        attribute = "android:onNavigationClick",
                        method = "setNavigationOnClickListener"),
        }
)

public interface BindingUtils {

    @BindingAdapter({"android:src"})
    public static void setImage(ImageView imageView, int res) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(res);
    }

    @BindingAdapter({"android:src"})
    public static void setImage(ImageView imageView, String url) {
        //imageView.setImageResource (res);
        // TODO
    }

    @BindingAdapter({"android:setAdapter"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        //recyclerView.addItemDecoration(new IntroActivity.CirclePagerIndicatorDecoration());

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"android:setTextError"})
    public static void setTextError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
    }

    @BindingAdapter("app:setListener")
    public static void setOnItemSelected(LoopView loopView, OnItemSelectedListener listener) {
        loopView.setListener(listener);
    }

    @BindingAdapter("app:setCurrentPosition")
    public static void setCurrentPosition(LoopView loopView, int pos) {
        loopView.setCurrentPosition(pos);
    }

    @BindingAdapter("app:setItems")
    public static void setItemsLoopView(LoopView loopView, List<String> items) {
        if (items.size() <= 0) return;
        loopView.setItems(items);
    }

    @BindingAdapter("app:notLoop")
    public static void setNotLoop(LoopView loopView, boolean notLoop) {
        if (notLoop) loopView.setNotLoop();
    }

    @BindingAdapter("app:textSize")
    public static void setTextSize(LoopView loopView, float size) {
        loopView.setTextSize(size);
    }

    @BindingAdapter("app:setEndTextColor")
    public static void setEndTextColor(TextView text, int color) {
        String txt = text.getText().toString().trim();
        Spannable spannable = new SpannableString(txt);
        if (txt.length() > 0) {
            spannable.setSpan(new ForegroundColorSpan(color), txt.length() - 1, txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    @BindingAdapter("app:underline")
    public static void setUnderline(TextView text, boolean underline) {
        String txt = text.getText().toString().trim();
        Spannable spannable = new SpannableString(txt);
        if (underline && txt.length() > 0) {
            spannable.setSpan(new UnderlineSpan(), 0, txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    @BindingAdapter("app:setErrors")
    public static void setErrors(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
    }

    @BindingAdapter("android:setTextEndColor")
    public static void setTextEndColor(TextView textView, String text) {
        final String color = "#F7648F";
        String simple = text.trim();
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(builder);
    }

    @BindingAdapter("app:loadImageUrl")
    public static void loadUrl(ImageView imageView, String url) {
        GlideUtils.show(url,imageView,R.drawable.xml_bakground_default_load_image,R.drawable.ic_photo_unavailable);
    }


    @BindingAdapter({"app:loadAvatar", "app:gender"})
    public static void loadUrl(ImageView imageView, String url, int gender) {
        int avatarDefault = R.drawable.xml_background_white_item_menu_list_new;
        GlideUtils.show(url,imageView,avatarDefault,avatarDefault);

    }

    @BindingAdapter("app:loadRoundImageUrl")
    public static void loadCircleImageUrl(ImageView imageView, String url) {
        GlideUtils.show(url,imageView,R.drawable.xml_background_circle_load_image_default,R.drawable.xml_background_circle_load_image_default);
    }

    @BindingAdapter("app:loadCorrnerImageUrl")
    public static void loadCorrnerImageUrl(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.load_image_default_corrner_5dp);
        requestOptions.error(R.drawable.load_image_default_corrner_5dp);
        requestOptions.transforms(new CenterCrop(), new RoundedCorners((int) (5 * imageView.getContext().getResources().getDisplayMetrics().density)));

        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    @BindingAdapter("app:loadCorrnerImage")
    public static void loadCorrnerImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.xml_background_image_default);
        requestOptions.error(R.drawable.bg_image_default);
        requestOptions.transforms(new CenterCrop(), new RoundedCorners((int) (2.5 * imageView.getContext().getResources().getDisplayMetrics().density)));

        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    // Register Party Extension
    @BindingAdapter("app:onPartyValue")
    public static void onPartyValue(TextView textView, PartyDetailData party) {
        String tag = textView.getTag().toString();
        Context context = textView.getContext();
        String data = party.formatStrikePriceMale(context, party.getFeeCustomerM()).toString();

        if (Gender.factory(tag) == Gender.female) {
            data = party.formatStrikePriceFeeMale(context, party.getFeeCustomerF()).toString();
        }

        int end = data.length();

        SpannableString format = new SpannableString(data + context.getString(R.string.tax_included));
        format.setSpan(new RelativeSizeSpan(10f / 16f), end, format.length(), 0); // set size

        if (DBManager.isLogin(context)) {
            if (DBManager.getMyAccount(context).isPremium()) {
                format.setSpan(new StrikethroughSpan(), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            if (DateUtils.sameMonth(DBManager.getMyAccount(context).getBirthday(), party.getEventDate())) {
                format.setSpan(new StrikethroughSpan(), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        textView.setText(format);
    }

    @BindingAdapter("app:onPartyValueWithPremium")
    public static void onPartyValueWithPremium(TextView textView, PartyDetailData party) {
        String tag = textView.getTag().toString();

        int amount = party.getFeeCustomerM() - 500;
        if (Gender.factory(tag) == Gender.female) {
            amount = party.getFeeCustomerF() - 500;
        }

        if (amount < 0) {
            amount = 0;
        }

        Context context = textView.getContext();
        String data = party.formatStrikePriceMale(context, amount).toString();
        data = "プレミアム割 " + data;

        textView.setVisibility(View.GONE);
        int end = data.length();

        SpannableString format = new SpannableString(data + context.getString(R.string.tax_included));
        format.setSpan(new RelativeSizeSpan(10f / 16f), end, format.length(), 0); // set size

        if (DBManager.isLogin(context)) {
            if (DBManager.getMyAccount(context).isPremium()) {
                textView.setVisibility(View.VISIBLE);
            }

            if (DateUtils.sameMonth(DBManager.getMyAccount(context).getBirthday(), party.getEventDate())) {
                format.setSpan(new StrikethroughSpan(), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        textView.setText(format);

    }

    @BindingAdapter("app:onPartyValueWithBirthDay")
    public static void onPartyValueWithBirthDay(TextView textView, PartyDetailData party) {

        Context context = textView.getContext();
        String tag = textView.getTag().toString();

        int amount = party.getFeeCustomerM() - 500;
        if (Gender.factory(tag) == Gender.female) {
            amount = party.getFeeCustomerF() - 500;
        }

        if (DBManager.isLogin(context) && DBManager.getMyAccount(context).isPremium()) {
            amount = amount - 500;
        }

        if (amount < 0) {
            amount = 0;
        }

        String data = party.formatStrikePriceMale(context, amount).toString();
        data = "誕生日割 " + data;

        textView.setVisibility(View.GONE);
        int end = data.length();

        SpannableString format = new SpannableString(data + context.getString(R.string.tax_included));
        format.setSpan(new RelativeSizeSpan(10f / 16f), end, format.length(), 0); // set size

        if (DBManager.isLogin(context)) {
            if (DateUtils.sameMonth(DBManager.getMyAccount(context).getBirthday(), party.getEventDate())) {
                textView.setVisibility(View.VISIBLE);
            }
        }

        textView.setText(format);
    }

    @BindingAdapter("app:registerPartyTextStep")
    public static void registerPartyTextStep(TextView text, int step) {
        String title = text.getContext().getResources().getStringArray(R.array.register_party_step)[step - 1].replace("\n", "<br>");
        String titleShow = String.format("<b><u>STEP%s</u><br>%s</b>", step, title);

        text.setText(Html.fromHtml(titleShow));
    }

    @BindingAdapter("app:setInternalScrollEnable")
    public static void setInternalScrollEnable(EditText edit, boolean enable) {
        edit.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(enable);
                    break;
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
    }
}
