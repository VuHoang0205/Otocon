package org.atmarkcafe.otocon.function.mypage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.BuildConfig;
import org.atmarkcafe.otocon.ExtensionActivity;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.AcitivityMypageBinding;
import org.atmarkcafe.otocon.databinding.LayoutAvatarActionBinding;
import org.atmarkcafe.otocon.dialog.DialogRematchMessage;
import org.atmarkcafe.otocon.dialog.MessageDialog;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.dialog.RematchFlowStatusDialog;
import org.atmarkcafe.otocon.function.menu.contactus.RulesContactDialog;
import org.atmarkcafe.otocon.function.mypage.coupon.CouponListFragment;
import org.atmarkcafe.otocon.function.mypage.item.MyPageActionItem;
import org.atmarkcafe.otocon.function.mypage.profile.ProfileSettingFragment;
import org.atmarkcafe.otocon.function.mypage.rematch.RequestRematchFragment;
import org.atmarkcafe.otocon.function.notification.PushType;
import org.atmarkcafe.otocon.function.party.LikePartyListFragment;
import org.atmarkcafe.otocon.function.party.RegisteredPartyListFragment;
import org.atmarkcafe.otocon.function.party.list.PartyListResponse;
import org.atmarkcafe.otocon.function.register.RulesDialog;
import org.atmarkcafe.otocon.function.rematch.RequestRematchDetailFragment;
import org.atmarkcafe.otocon.model.PhotoModel;
import org.atmarkcafe.otocon.model.PrintIntroductionSelf;
import org.atmarkcafe.otocon.model.UserProfileCardModel;
import org.atmarkcafe.otocon.model.parameter.UploadImageParams;
import org.atmarkcafe.otocon.model.response.ImageResponse;
import org.atmarkcafe.otocon.model.response.MyPageMenuResponse;
import org.atmarkcafe.otocon.model.response.NotificationRespone;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.UserCardRespone;
import org.atmarkcafe.otocon.model.response.UserRespose;
import org.atmarkcafe.otocon.utils.AuthenticationUtils;
import org.atmarkcafe.otocon.utils.BitmapUtils;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.GlideUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;

import static org.atmarkcafe.otocon.function.mypage.MyPageMenuPresenter.GET_TOTAL_NOTIFICATIONS;
import static org.atmarkcafe.otocon.function.mypage.MyPageMenuPresenter.GET_USER_PROFILE;
import static org.atmarkcafe.otocon.function.mypage.MyPageMenuPresenter.UPLOAD_IMAGE_PROFILE;

public class MyPageMenuFragment extends OtoconBindingFragment<AcitivityMypageBinding> implements MVPExtension.View<OnResponse>, OtoconFragment.OtoconFragmentListener {

    private MyPageActionItem rematchItem;
    //private int totalRematching = 0;

    private GroupAdapter groupAdapter = new GroupAdapter();

    private MyPageMenuPresenter presenter = new MyPageMenuPresenter(this);

    UserProfileCardModel model;

    private int currentPos;

    @Override
    public int layout() {
        return R.layout.acitivity_mypage;
    }

    @Override
    public void onCreateView(AcitivityMypageBinding mBinding) {

        mBinding.setVariable(BR.activity, this);


        mBinding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        mBinding.commonNotificationButton.setOnClickListener(v -> {
            FragmentUtils.replace(getActivity(), new CommonNotificationFragment(), true);
        });

        mBinding.myPageRecyclerView.setFocusable(false);

        Section section = new Section();
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_EDIT_PROFILE, R.drawable.ic_edit_profile, R.string.my_page_edit_profile));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_PRINT_INTRODUCTION, R.drawable.ic_printer, R.string.my_page_print_introduction));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_PARTY_LIST, R.drawable.ic_party_history, R.string.my_page_party_list));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_FAVORITE, R.drawable.ic_heart, R.string.my_page_favorite));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_CALENDAR, R.drawable.ic_calendar_collaboration, R.string.my_page_calendar));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_COUPON, R.drawable.ic_menu_benefits, R.string.my_page_coupon));
        section.add(rematchItem = new MyPageActionItem(MyPageActionItem.ACTION_TYPE_REMATH_REQUEST, R.drawable.ic_contact, R.string.my_page_rematch_request).setNotify(0, 0));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_NOTIFICATION_SETTINGS, R.drawable.ic_setting, R.string.my_page_notification_setting));
        section.add(new MyPageActionItem(MyPageActionItem.ACTION_TYPE_WIDTH_DRAWAL, R.drawable.ic_circle_close, R.string.my_page_with_drawal));
        groupAdapter.add(section);

        mBinding.myPageRecyclerView.setAdapter(groupAdapter);

        groupAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                MyPageActionItem actionItem = (MyPageActionItem) item;
                switch (actionItem.getType()) {
                    case MyPageActionItem.ACTION_TYPE_COUPON:
                        FragmentUtils.replace(getActivity(), MyPageRootFragment.create(MyPageRootFragment.Rediect.coupon), true);
                        break;

                    case MyPageActionItem.ACTION_TYPE_PARTY_LIST:
                        FragmentUtils.replace(getActivity(), new RegisteredPartyListFragment(), true);
                        break;
                    case MyPageActionItem.ACTION_TYPE_FAVORITE:
                        FragmentUtils.replace(getActivity(), new LikePartyListFragment(), true);
                        break;
                    case MyPageActionItem.ACTION_TYPE_EDIT_PROFILE:
                        FragmentUtils.replace(getActivity(), new ProfileSettingFragment(), true);
                        break;
                    case MyPageActionItem.ACTION_TYPE_CALENDAR:
                        FragmentUtils.replace(getActivity(), new CalendarCollaborationFragment(), true);
                        break;
                    case MyPageActionItem.ACTION_TYPE_NOTIFICATION_SETTINGS:
                        FragmentUtils.replace(getActivity(), new NotificationSettingFragment(), true);
                        break;
                    case MyPageActionItem.ACTION_TYPE_REMATH_REQUEST:

                        RequestRematchFragment fragment = new RequestRematchFragment();

                        fragment.setOtoconFragmentListener(new OtoconFragmentListener() {
                            @Override
                            public void onHandlerReult(int status, Bundle extras) {
                                setEnableBack(true);
                                getActivity().onBackPressed();
                                MyPageMenuFragment.this.otoconFragmentListener.onHandlerReult(status, extras);
                            }
                        });

                        FragmentUtils.replace(getActivity(), fragment, true);
                        break;
                    case MyPageActionItem.ACTION_TYPE_WIDTH_DRAWAL:
                        WithDrawalFragment withDrawalFragment = new WithDrawalFragment();
                        withDrawalFragment.setOtoconFragmentListener((status, extras) -> {
                            // TODO reset data login
                        });
                        FragmentUtils.replace(getActivity(), withDrawalFragment, true);
                        break;

                    case MyPageActionItem.ACTION_TYPE_PRINT_INTRODUCTION:
                        // TODO download file
                        presenter.onExecute(getContext(), 2, null);
                        break;
                    default:
                        new PopupMessageErrorDialog(getActivity(), getString(R.string.in_development)).show();
                }
            }
        });

        mBinding.mypageMessageGuide.requestFocus();

        redirect();


        //Call api user proflie image
        presenter.onExecute(getContext(), GET_USER_PROFILE, null);

        //Call api get total notifications
        presenter.onExecute(getContext(), GET_TOTAL_NOTIFICATIONS, null);
    }

    AvatarBottomSheetFragment fragAvatarAction;

    public void changeGallery(int type) {
        if (type > model.numberPhoto() + 1) return;
        if (model.hasAvatar(type)) {
            fragAvatarAction = new AvatarBottomSheetFragment()
                    .setOnClickDeleteListener(v -> {
                        fragAvatarAction.dismiss();
                        deleteAvatar(type);
                    })
                    .setOnClickEditionListener(v -> {
                        fragAvatarAction.dismiss();
                        editAvatar(type);
                    });
            fragAvatarAction.show(getActivity().getSupportFragmentManager(), "avatar_action");
        } else {
            editAvatar(type);
        }
    }

    public void editAvatar(int pos) {
        currentPos = pos;
        ChoosePictureFragment fragChoosePicture = new ChoosePictureFragment();
        fragChoosePicture.setOtoconFragmentListener(this);
        FragmentUtils.replace(getActivity(), fragChoosePicture, true);
    }

    public void deleteAvatar(int pos) {
        new DialogRematchMessage(getActivity(), getString(R.string.message_delete_photo), (isOke, isChecked) -> {
            if (isOke) {
                for (PhotoModel photo : model.photo) {
                    if (Integer.parseInt(photo.order) == pos) {
                        presenter.onExecute(getContext(), presenter.DELETE_IMAGE_PROFILE, photo.id);
                        break;
                    }
                }
            }
        })
                .setTexButtonBlack(getString(R.string.cancel))
                .setTextButtonGreen(getString(R.string.ok))
                .show();
    }

    private void redirect() {
        Bundle data = getArguments();
        if (data != null && data.containsKey("redirect") && data.getBoolean("redirect")) {
            String type = data.getString(KeyExtensionUtils.KEY_TYPE);
            switch (PushType.factory(type)) {
                case couponList:
                    FragmentUtils.replace(getActivity(), MyPageRootFragment.create(MyPageRootFragment.Rediect.coupon), true);
                    break;
                case rematchDetail:
                    String userId = data.getString(KeyExtensionUtils.KEY_USER_ID);
                    String eventId = data.getString(KeyExtensionUtils.KEY_EVENT_ID);

                    RequestRematchDetailFragment fragment = new RequestRematchDetailFragment();

                    Bundle args = new Bundle();
                    args.putString("user_id", userId);
                    args.putString("event_id", eventId);
                    fragment.setArguments(args);

                    FragmentUtils.replace(getActivity(), fragment, true);
                    break;
                case rematchListRequest:
                    RequestRematchFragment fragmentRematchRequest = new RequestRematchFragment();
                    fragmentRematchRequest.setOtoconFragmentListener(new OtoconFragmentListener() {
                        @Override
                        public void onHandlerReult(int status, Bundle extras) {
                            // TODO
                        }
                    });

                    FragmentUtils.add(getActivity(), fragmentRematchRequest, true);
                    break;
                case rematchTopPage:
                    break;
            }
        }
    }

    // Action of fragment
    public void onClickReMatch() {

        //TODO
        new RulesDialog(getActivity(), getString(R.string.title_rematch_flow), BuildConfig.LINK_REMATCH_FLOW).show();
    }

    public void onClickNotice() {
        // TODO POPUP
    }

    @Override
    public void onResume() {
        super.onResume();

        // reload information when goto screen
        presenter.onExecute(getActivity(), 0, null);

        // reload infomation when back screen
        presenter.onExecute(getContext(), GET_USER_PROFILE, null);

        // reload infomation when back screen
        presenter.onExecute(getContext(), GET_TOTAL_NOTIFICATIONS, null);
    }

    //MVPExtension.View
    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(OnResponse response) {
        if (response instanceof MyPageMenuResponse) {
            rematchItem.setNotify(((MyPageMenuResponse) response).getTotal(), ((MyPageMenuResponse) response).getTotal_unread());
            viewDataBinding.myPageRecyclerView.setAdapter(groupAdapter);
        }
        if (response instanceof UserCardRespone) {

            // add item ImageView
            List<ImageView> imageViews = new ArrayList<>();
            imageViews.add(viewDataBinding.mypageAvatar);
            imageViews.add(viewDataBinding.avatar01.avatar);
            imageViews.add(viewDataBinding.avatar02.avatar);
            imageViews.add(viewDataBinding.avatar03.avatar);
            imageViews.add(viewDataBinding.avatar04.avatar);

            model = ((UserCardRespone) response).getData();
            getImage(((UserCardRespone) response).getData(), imageViews);
            viewDataBinding.setModel(model);
        }
        if (response instanceof ImageResponse) {
            presenter.onExecute(getContext(), GET_USER_PROFILE, null);
        }
        if (response instanceof NotificationRespone) {
            if (((NotificationRespone) response).getTotal_unread() == 0) {
                viewDataBinding.toolbarNotify.setVisibility(View.GONE);
            } else {
                viewDataBinding.toolbarNotify.setVisibility(View.VISIBLE);
                viewDataBinding.toolbarNotify.setText(String.valueOf(((NotificationRespone) response).getTotal_unread()));
            }
        }
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showMessage(OnResponse onResponse) {

    }


    public void printIntroductionSelf(ResponseBody responseBody) {
        if (responseBody == null) {
            // TODO save file error
            return;
        }

        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);

        // Set job name, which will be displayed in the print queue
        String jobName = getString(R.string.app_name) + " Print";

        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, new MyPrintDocumentAdapter(responseBody),
                null); //
    }

    public void getImage(UserProfileCardModel data, List<ImageView> imageViews) {
        for (ImageView img : imageViews) {
            img.setImageResource(R.drawable.ic_take_photo);
        }
        if (data.photo != null) {
            for (int i = 0; i < data.photo.size(); i++) {
                PhotoModel photo = data.photo.get(i);
                if (photo.type.equals("2")) {
                    GlideUtils.show(data.photo.get(0).picture, imageViews.get(0), R.drawable.ic_take_photo, R.drawable.ic_take_photo);
                } else {
                    GlideUtils.show(photo.picture, imageViews.get(Integer.parseInt(photo.order) - 1), R.drawable.ic_take_photo, R.drawable.ic_take_photo);
                }
            }
        }
    }

    // Receiver data callback
    @Override
    public void onHandlerReult(int status, Bundle extras) {
        presenter.params = new UploadImageParams();
        Bitmap bitmap = BitmapUtils.toBitmap(extras.getString("Bitmap_key"));
        presenter.params.setParams(currentPos, BitmapUtils.resizeImage(bitmap));
        presenter.onExecute(getContext(), UPLOAD_IMAGE_PROFILE, null);
    }
}

class MyPageMenuPresenter extends MVPPresenter<String, OnResponse> {
    public static int DOWN_LOAD_FILE = 3;
    public static int GET_USER_PROFILE = 4;
    public static int UPLOAD_IMAGE_PROFILE = 5;
    public static int GET_TOTAL_NOTIFICATIONS = 6;
    public static int DELETE_IMAGE_PROFILE = 7;

    public MyPageMenuPresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    UploadImageParams params = new UploadImageParams();

    @Override
    public void onExecute(Context context, int action, String s) {
        if (action == 0) {
            loadInfor(context, new ExecuteListener<UserRespose>() {
                @Override
                public void onNext(UserRespose userRespose) {
                    if (userRespose != null && userRespose.isSuccess()) {
                        onExecute(context, 1, null);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, userRespose);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == 1) {
            ///api/share-contact-total-message-unread
            // get read rematch
            execute(InteractorManager.getApiInterface(context).myPageMatchingCount(), new ExecuteListener<MyPageMenuResponse>() {
                @Override
                public void onNext(MyPageMenuResponse response) {
                    if (response != null && response.isSuccess()) {
                        view.success(response);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, response);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == 2) {
            execute(InteractorManager.getApiInterface(context).getFileIntroductionSelf(), new ExecuteListener<PrintIntroductionSelf>() {
                @Override
                public void onNext(PrintIntroductionSelf response) {
                    if (response != null && response.isSuccess()) {
                        onExecute(context, 3, response.getFile());
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, response);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == DOWN_LOAD_FILE) {
            InteractorManager.getApiInterface(context).downloadFile(s)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .safeSubscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody response) {
                            ((MyPageMenuFragment) view).printIntroductionSelf(response);
                        }

                        @Override
                        public void onError(Throwable e) {
                            String[] messages = OnResponse.getMessage(context, e, null);
                            view.showPopup(messages[0], messages[1]);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (action == GET_USER_PROFILE) {
            execute(InteractorManager.getApiInterface(context).getCard(), new ExecuteListener<UserCardRespone>() {
                @Override
                public void onNext(UserCardRespone respone) {
                    if (respone != null && respone.isSuccess()) {
                        view.success(respone);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, respone);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == UPLOAD_IMAGE_PROFILE) {
            execute(InteractorManager.getApiInterface(context).uploadImage(params), new ExecuteListener<ImageResponse>() {
                @Override
                public void onNext(ImageResponse respone) {
                    if (respone != null && respone.isSuccess()) {
                        view.success(respone);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, respone);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == DELETE_IMAGE_PROFILE) {
            execute(InteractorManager.getApiInterface(context).deleteImage(s), new ExecuteListener<ImageResponse>() {
                @Override
                public void onNext(ImageResponse respone) {
                    if (respone != null && respone.isSuccess()) {
                        view.success(respone);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, respone);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == GET_TOTAL_NOTIFICATIONS) {
            execute(InteractorManager.getApiInterface(context).getTotalNotifications(), new ExecuteListener<NotificationRespone>() {
                @Override
                public void onNext(NotificationRespone respone) {
                    if (respone != null && respone.isSuccess()) {
                        view.success(respone);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, respone);
                        view.showPopup(messages[0], messages[1]);
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
}

class MyPrintDocumentAdapter extends PrintDocumentAdapter {
    ResponseBody responseBody;

    public MyPrintDocumentAdapter(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                .Builder("introduction-self")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN);

        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, false);
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = responseBody.byteStream();
            out = new FileOutputStream(destination.getFileDescriptor());

            byte[] buf = new byte[16384];
            int size;

            while ((size = in.read(buf)) >= 0
                    && !cancellationSignal.isCanceled()) {
                out.write(buf, 0, size);
            }

            if (cancellationSignal.isCanceled()) {
                callback.onWriteCancelled();
            } else {
                callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
            }
        } catch (Exception e) {
            callback.onWriteFailed(e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ignored) {
            }
        }
    }
}