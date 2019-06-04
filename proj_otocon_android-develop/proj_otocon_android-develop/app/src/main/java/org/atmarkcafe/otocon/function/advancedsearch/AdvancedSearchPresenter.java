package org.atmarkcafe.otocon.function.advancedsearch;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.utils.AgeUtils;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.utils.CityByAreaUtils;
import org.atmarkcafe.otocon.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdvancedSearchPresenter implements AdvancedSearchContract.Presenter {
    public final ObservableField<String> area = new ObservableField<>("");
    public final ObservableField<String> eventDate = new ObservableField<>("");
    public final ObservableField<String> dayOfWeek = new ObservableField<>("");
    public final ObservableField<String> gender = new ObservableField<>("");
    public final ObservableField<String> age = new ObservableField<>("");
    public final ObservableField<String> ageOfOpponent = new ObservableField<>("");
    public final ObservableBoolean isOnlyParty = new ObservableBoolean(false);

    private AdvancedSearchContract.View mView;

    public AdvancedSearchPresenter(AdvancedSearchContract.View view) {
        mView = view;
    }

    @Override
    public void updateArea(Context ctx, String area) {
        String result = CityByAreaUtils.getNameCity(ctx, area);
        this.area.set(result);
    }

    @Override
    public void updateEventDate(Context ctx, String eventDate) {
        DateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);
        DateFormat dfOutput = new SimpleDateFormat("M/d(E)", Locale.JAPANESE);
        if (eventDate == null || eventDate.length() == 0) {
            this.eventDate.set("");
            return;
        }

        String dot = ctx.getString(R.string.format_advanced_dot);
        String[] dates = eventDate.split(",");

        String showText = "";
        for (String date : dates) {

            try {
                if (!showText.isEmpty()) {
                    showText = showText + dot;
                }
                showText += dfOutput.format(dfInput.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        this.eventDate.set(showText);
    }

    @Override
    public void updateDayOfWeek(Context ctx, String dayOfWeek) {
        this.dayOfWeek.set(DateUtils.getDayList(ctx, dayOfWeek));
    }

    @Override
    public void updateGender(Context ctx, int gender) {
        switch (gender) {
            case 1:
                this.gender.set(ctx.getString(R.string.male));
                break;
            case 2:
                this.gender.set(ctx.getString(R.string.female));
                break;
            default:
                this.gender.set("");

        }
    }

    @Override
    public void updateAge(Context ctx, int age) {
        if (age < 20) {
            this.age.set("");
            return;
        }
        this.age.set(String.format(ctx.getString(R.string.advanced_search_age_format), age));
    }

    @Override
    public void updateAgeOfOpponent(Context ctx, String ageOfOpponent) {
        this.ageOfOpponent.set(AgeUtils.getText(ctx, ageOfOpponent));
    }

}
