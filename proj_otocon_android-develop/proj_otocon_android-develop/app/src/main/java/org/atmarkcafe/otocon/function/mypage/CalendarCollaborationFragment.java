package org.atmarkcafe.otocon.function.mypage;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.FragmentCalendarCollaborationBinding;
import org.atmarkcafe.otocon.dialog.MessageDialog;
import org.atmarkcafe.otocon.function.party.list.PartyListResponse;
import org.atmarkcafe.otocon.model.DBManager;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.response.OnResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalendarCollaborationFragment extends OtoconBindingFragment<FragmentCalendarCollaborationBinding> implements MVPExtension.View<PartyListResponse> {
    private CalendarCollaborationPresenter presenter = new CalendarCollaborationPresenter(this);
    private CalendarManager calendarManager = new CalendarManager();
    private List<Party> partyList = null;

    @Override
    public int layout() {
        return R.layout.fragment_calendar_collaboration;
    }

    @Override
    public void onCreateView(FragmentCalendarCollaborationBinding viewDataBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        viewDataBinding.googleCalendarButton.setOnClickListener(v -> {
            if (calendarManager.checkPermission(this)) {
                // TODO save event to calendar
                calendarManager.saveEvent(this, partyList);

                try {
                    PackageManager packageManager = getActivity().getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.google.android.calendar");
                    startActivity(intent);
                } catch (Exception e) {
                    Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                    builder.appendPath("time");
                    ContentUris.appendId(builder, System.currentTimeMillis());
                    Intent intent = new Intent(Intent.ACTION_VIEW)
                            .setData(builder.build());
                    startActivity(intent);
                }
            } else {
                calendarManager.showPermissionDialog(this);
            }
        });

        showProgress(true);
        presenter.onExecute(getContext(), 0, null);

    }

    @Override
    public void showPopup(String title, String message) {
        showProgress(false);
    }

    @Override
    public void success(PartyListResponse partyRespone) {
        showProgress(false);
        partyList = partyRespone.getDataList();
    }

    @Override
    public void showProgress(boolean show) {
        viewDataBinding.loadingLayout.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(PartyListResponse partyConditionRespone) {
        showProgress(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (calendarManager.checkPermission(this)) {
            viewDataBinding.googleCalendarButton.callOnClick();
        }
    }
}

class CalendarManager {
    public static final int REQUEST_PERMISSION_CALENDAR = 1212;
    public static final String permission = Manifest.permission.WRITE_CALENDAR;

    public void showPermissionDialog(Fragment frag) {
        boolean showed = frag.getActivity().getSharedPreferences(permission, 0).getBoolean(permission, false);

        if (showed) {
            MessageDialog dialog = new MessageDialog(frag.getContext(), ok -> {
                if (ok) {
                    ExtensionActivity.openAppSettings(frag.getActivity());
                }
            });
            dialog.set(
                    frag.getString(R.string.calendar_collaboration_permission_title),
                    frag.getString(R.string.calendar_collaboration_permission_description)
            );
            dialog.setButtonOk(frag.getString(R.string.permission_accept_button));
            dialog.setButtonCancel(frag.getString(R.string.permission_deny_button));

            dialog.show();
        } else {
            frag.getActivity().getSharedPreferences(permission, 0).edit().putBoolean(permission, true).commit();
            frag.requestPermissions(new String[]{permission}, REQUEST_PERMISSION_CALENDAR);
        }

    }

    public boolean checkPermission(Fragment fragment) {
        return fragment.getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    private String getCalendarId(Context context) {
        String calId = DBManager.getCalendarId(context);
        ContentResolver cr = context.getContentResolver();

        if (!calId.isEmpty()) {
            // delete calendar if existed
            try {
                Uri evuri = CalendarContract.Calendars.CONTENT_URI;
                Uri deleteUri = ContentUris.withAppendedId(evuri, Long.parseLong(calId));
                cr.delete(deleteUri, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // create new Calendar
        String calendarName = context.getString(R.string.app_name);
        ContentValues calendar = new ContentValues();
        calendar.put(CalendarContract.Calendars.ACCOUNT_NAME, calendarName);
        calendar.put(CalendarContract.Calendars.NAME, calendarName);
        calendar.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        calendar.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, calendarName);
        calendar.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.GREEN);
        calendar.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        calendar.put(CalendarContract.Calendars.IS_PRIMARY, "false");
        calendar.put(CalendarContract.Calendars.OWNER_ACCOUNT, true);

        Uri calUri = CalendarContract.Calendars.CONTENT_URI;
        calUri = calUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, calendarName)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .build();

        Uri uriCalendar = cr.insert(calUri, calendar);
        calId = uriCalendar.getLastPathSegment();

        DBManager.setCalendarId(context, calId);

        return calId;
    }

    public void saveEvent(Fragment frag, List<Party> list) {
        ContentResolver cr = frag.getActivity().getContentResolver();

        String calId = getCalendarId(frag.getContext());

        if (list != null) {
            for (Party party : list) {
                ContentValues values = new ContentValues();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date startDate, endDate;
                try {
                    String start = party.eventDate.substring(0, 11) + party.startTime;
                    String end = party.eventDate.substring(0, 11) + party.endTime;
                    startDate = dateFormat.parse(start);
                    endDate = dateFormat.parse(end);
                } catch (ParseException e) {
                    continue;
                }

                values.put(CalendarContract.Events.DTSTART, startDate.getTime());
                values.put(CalendarContract.Events.DTEND, endDate.getTime());
                values.put(CalendarContract.Events.TITLE, party.name);
                values.put(CalendarContract.Events.CALENDAR_ID, calId);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Tokyo");
                values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PUBLIC);
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            }
        }
    }

}

class CalendarCollaborationPresenter extends MVPPresenter<String, PartyListResponse> {

    public CalendarCollaborationPresenter(MVPExtension.View<PartyListResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, String s) {
        execute(InteractorManager.getApiInterface(context).getPartyForCalendarList(1, 3000, Party.JOIN_STATUS_REGISTERED), new ExecuteListener<PartyListResponse>() {
            @Override
            public void onNext(PartyListResponse response) {
                if (response != null && response.isSuccess()) {
                    view.success(response);
                } else {
                    view.showMessage(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                String[] messages = OnResponse.getMessage(context, e, null);
                view.showPopup(messages[0], messages[1]);
            }

        });
    }
}