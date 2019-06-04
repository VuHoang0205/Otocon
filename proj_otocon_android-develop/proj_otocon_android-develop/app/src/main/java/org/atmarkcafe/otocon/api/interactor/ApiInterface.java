package org.atmarkcafe.otocon.api.interactor;

import org.atmarkcafe.otocon.function.mypage.card.AddNewCardResponse;
import org.atmarkcafe.otocon.function.mypage.card.UpdateCreditCardResponse;
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchParams;
import org.atmarkcafe.otocon.function.mypage.profile.userinforematch.data.UserInfoRematchResponse;
import org.atmarkcafe.otocon.function.mypage.item.UserProfileParams;
import org.atmarkcafe.otocon.function.party.list.PartyListResponse;
import org.atmarkcafe.otocon.model.Coupon;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.model.LoginInfoModel;
import org.atmarkcafe.otocon.model.NotificationSetting;
import org.atmarkcafe.otocon.model.PrintIntroductionSelf;
import org.atmarkcafe.otocon.model.ProfileDetail;
import org.atmarkcafe.otocon.model.RegisterPartyModel;
import org.atmarkcafe.otocon.model.RematchDetail;
import org.atmarkcafe.otocon.model.UserInfo;
import org.atmarkcafe.otocon.model.login.LoginParams;
import org.atmarkcafe.otocon.model.login.LoginRespone;
import org.atmarkcafe.otocon.model.menunew.MenuNewRespone;
import org.atmarkcafe.otocon.model.parameter.AddDeviceParams;
import org.atmarkcafe.otocon.model.parameter.ConditionSearchParams;
import org.atmarkcafe.otocon.model.parameter.ConfirmCreditCardParams;
import org.atmarkcafe.otocon.model.parameter.ContactUsParams;
import org.atmarkcafe.otocon.model.parameter.ExpiredRematchParams;
import org.atmarkcafe.otocon.model.parameter.ForgotPasswordParams;
import org.atmarkcafe.otocon.model.parameter.PartyParams;
import org.atmarkcafe.otocon.model.parameter.PaymentParams;
import org.atmarkcafe.otocon.model.parameter.RegisterParams;
import org.atmarkcafe.otocon.model.parameter.RequestRematchParams;
import org.atmarkcafe.otocon.model.parameter.ShareContactUseCoupon;
import org.atmarkcafe.otocon.model.parameter.UploadImageParams;
import org.atmarkcafe.otocon.model.response.CityByAreaResponse;
import org.atmarkcafe.otocon.model.response.CommonNotificationResponse;
import org.atmarkcafe.otocon.model.response.ConditionSearchResponse;
import org.atmarkcafe.otocon.model.response.ConfirmCreditCardResponse;
import org.atmarkcafe.otocon.model.response.ContactUsResponse;
import org.atmarkcafe.otocon.model.response.CreditCardResponse;
import org.atmarkcafe.otocon.model.response.ForgotPasswordResponse;
import org.atmarkcafe.otocon.model.response.ImageResponse;
import org.atmarkcafe.otocon.model.response.ListRequestRematchResponse;
import org.atmarkcafe.otocon.model.response.MyPageMenuResponse;
import org.atmarkcafe.otocon.model.response.NotificationRespone;
import org.atmarkcafe.otocon.model.response.PartyConditionRespone;
import org.atmarkcafe.otocon.model.response.PartyRegisterInfomationRespone;
import org.atmarkcafe.otocon.model.response.CustomJoinEventResponse;
import org.atmarkcafe.otocon.model.response.PartyRespone;
import org.atmarkcafe.otocon.model.response.MenuNotifyCountResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.model.response.PartyDetailRespone;
import org.atmarkcafe.otocon.model.response.PaymentResponse;
import org.atmarkcafe.otocon.model.response.PrefectureResponse;
import org.atmarkcafe.otocon.model.response.RegisterPartyResponse;
import org.atmarkcafe.otocon.model.response.RegisterResponse;
import org.atmarkcafe.otocon.model.response.RematchCouponResponse;
import org.atmarkcafe.otocon.model.response.ResponseExtension;
import org.atmarkcafe.otocon.model.response.SettingsResponse;
import org.atmarkcafe.otocon.model.response.ShareContactInfoResponse;
import org.atmarkcafe.otocon.model.response.TotalNotiRematch;
import org.atmarkcafe.otocon.model.response.UnreadNotificationResponse;
import org.atmarkcafe.otocon.model.response.UserCardRespone;
import org.atmarkcafe.otocon.model.response.UserInfoResponse;
import org.atmarkcafe.otocon.model.response.UserProfileResponse;
import org.atmarkcafe.otocon.model.response.UserPropertiesRespone;
import org.atmarkcafe.otocon.model.response.UserRematchResponse;
import org.atmarkcafe.otocon.model.response.UserRespose;
import org.atmarkcafe.otocon.model.response.UserJoinEventResponse;
import org.atmarkcafe.otocon.model.response.ValidatePasswordRespone;
import org.atmarkcafe.otocon.model.response.ValidateUserInfoResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("/api/login-normal")
    Observable<LoginRespone> login(@Body LoginParams params);

    @POST("/api/register-normal")
    Observable<RegisterResponse> registerAccount(@Body RegisterParams params);

    @POST("/api/reset-password")
    Observable<ForgotPasswordResponse> forgotPassworldSendEmail(@Body ForgotPasswordParams params);

    @POST("/api/register-validate")
    Observable<RegisterResponse> registerValidate(@Body RegisterParams params);

    @GET("/api/location/city")
    Observable<CityByAreaResponse> getCitys();

    @GET("/api/location/prefecture")
    Observable<PrefectureResponse> listPefectures();

    @POST("/api/event/advance-search")
    Observable<ConditionSearchResponse> advancedSearch(@Body ConditionSearchParams params);

    @POST("/api/contact-us/store")
    Observable<ContactUsResponse> contactUs(@Body ContactUsParams params);

    @POST("/api/validate-contact-us")
    Observable<ContactUsResponse> contactValidate(@Body ContactUsParams params);

    @GET("/api/news")
    Observable<MenuNewRespone> menuListNews(@Query("limit") int limit, @Query("page") int page, @Query("device_id") String deviceId);

    @GET("/api/get-notify")
    Observable<MenuNotifyCountResponse> getNotifyCount(@Query("device_id") String deviceId);

    @GET("/api/newparty/get-unread-notification")
    Observable<UnreadNotificationResponse> getUnreadNotification(@Query("device_id") String deviceId);

    @PUT("/api/newparty/mark-as-read")
    Observable<OnResponse> markAsReadAllParty(@Query("device_id") String deviceId);

    //News - Mark as read
    @PUT("/api/news/mark-as-read")
    Observable<OnResponse> newMarkAsRead(@Query("device_id") String deviceId, @Query("notify_id") String notify_id);

    //Add Device no login
    @POST("/api/add-device")
    Observable<OnResponse> addDeviceNoLogin(@Body AddDeviceParams params);

    @POST("/api/user/add-device")
    Observable<OnResponse> addDeviceLogged(@Body AddDeviceParams params);

    Observable<MenuNewRespone> menuListNews(@Query("limit") int limit, @Query("page") int page);

    @POST("/api/event/advance-search")
    Observable<PartyRespone> getPartys(@Body PartyParams params);

    @POST("/api/user/like-event")
    Observable<OnResponse> likeEvent(@Query("event_id") String eventId);

    @POST("/api/user/unlike-event")
    Observable<OnResponse> unlikeEvent(@Query("event_id") String eventId);

    @GET("/api/settings")
    Observable<SettingsResponse> getSettings();


    @GET("/api/event/{id}")
    Observable<PartyDetailRespone> partyDetail(@Path("id") String id);

    @GET("/api/coupon")
    Observable<ResponseExtension<Coupon>> getCoupons();

    @POST("/api/updateCouponUser")
    Observable<OnResponse> useCoupon(@Query("coupon_id") int couponId);


    @GET("/api/user/get-info")
    Observable<UserRespose> getUserInfo();

    @GET("/api/event-details/{event_id}")
    Observable<PartyRegisterInfomationRespone> getEventDetail(@Path("event_id") String id);

    @POST("/api/user/register-join-event-validation")
    Observable<RegisterPartyResponse> validateParty(@Body RegisterPartyModel model);

    @POST("/api/user/join-event-validation")
    Observable<RegisterPartyResponse> validatePartyLogin(@Body RegisterPartyModel model);

    @GET("/api/credit-card")
    Observable<CreditCardResponse> getCreditCardList();

    @DELETE("/api/credit-card/{id}")
    Observable<OnResponse> deleteCreditCard(@Path("id") String id);

    @PUT("/api/credit-card/{id}")
    Observable<UpdateCreditCardResponse> updateCreditCard(@Path("id") String id, @Body CreditCard creditCard);

    @PUT("/api/credit-card-update-default/{id}")
    Observable<OnResponse> updateDefaultCreditCard(@Path("id") String id);

    @POST("/api/credit-card")
    Observable<AddNewCardResponse> addCreditCard(@Body CreditCard card);

    @POST("/api/confirm-credit-card")
    Observable<ConfirmCreditCardResponse> confirmCreditCard(@Body ConfirmCreditCardParams params);

    @POST("/api/payment")
    Observable<PaymentResponse> payment(@Body PaymentParams params);

    @POST("/api/user/join-event")
    Observable<UserJoinEventResponse> userJsoinEvent(@Body RegisterPartyModel params);

    @POST("/api/user/register-join-event")
    Observable<CustomJoinEventResponse> customJoinEvent(@Body RegisterPartyModel params);

    @GET("/api/event/newest")
    Observable<PartyListResponse> lastestEvent(@Query("page") int page);

    @GET("/api/event/special-discount")
    Observable<PartyListResponse> specialDiscountEvent(
            @Query("page") int page,
            @Query("city") String city,
            @Query("event_date") String eventDate,
            @Query("day_of_week") String dayOfWeek,
            @Query("age") String age,
            @Query("check_slot") String checkSlots
    );

    @GET("/api/event/early-benefit")
    Observable<PartyListResponse> earlyBenefitEvent(
            @Query("page") int page,
            @Query("city") String city,
            @Query("event_date") String eventDate,
            @Query("day_of_week") String dayOfWeek,
            @Query("age") String age,
            @Query("check_slot") String checkSlots
    );

    @GET("/api/user/events")
    Observable<PartyListResponse> likedListEvent(@Query("page") int page, @Query("like_status") int like_status);

    //(1: đăng ký, 2: hủy đăng ký, 3: đã tham gia, 4:không tham gia, 5:đang cân nhắc)
    @GET("/api/user/events")
    Observable<PartyListResponse> joinedListEvent(@Query("page") int page, @Query("join_status") int join_status);

    @GET("/api/user/events")
    Observable<PartyListResponse> getPartyForCalendarList(@Query("page") int page, @Query("limit") int limit, @Query("join_status") int join_status);

    @GET("/api/event/next-week")
    Observable<PartyListResponse> nextWeekEvent(
            @Query("page") int page,
            @Query("city") String city,
            @Query("event_date") String eventDate,
            @Query("day_of_week") String dayOfWeek,
            @Query("age") String age,
            @Query("check_slot") String checkSlots
    );

    @PUT("/api/change-password")
    Observable<OnResponse> changePassword(@Body LoginInfoModel params);

    // Mypage
    @POST("/api/confirm-credit-card-premium")
    Observable<ConfirmCreditCardResponse> userConfirmCardUpdatePremium(@Body ConfirmCreditCardParams params);

    @PUT("/api/user/upgrade-premium")
    Observable<PaymentResponse> userUpToUpdatePremium(@Body PaymentParams params);

    @POST("/api/share-contact-by-upgrade-to-premium")
    Observable<PaymentResponse> rematchUpToUpdatePremium(@Body PaymentParams params);

    @GET("/api/get-properties")
    Observable<UserPropertiesRespone> getProperties();

    @GET("/api/user-profile")
    Observable<UserProfileResponse> getUserProfile();

    @GET("/api/user-profile")
    Observable<UserInfoRematchResponse> getUserInfoRematch();

    @PUT("/api/user-profile/update")
    Observable<OnResponse> updateUserProfile(@Body UserProfileParams params);

    @PUT("/api/user-profile/update")
    Observable<ResponseExtension<String>> userUpdateInfoRematch(@Body UserInfoRematchParams params);

    @GET("/api/user/events")
    Observable<PartyConditionRespone> getPartyCondition(@Query("page") int page, @Query("limit") int limit, @Query("join_status") int join_status,@Query("type") int type);

    @GET("/api/rematch/userlist-by-event")
    Observable<UserRematchResponse> getUserRematch(@Query("event_id") String event_id, @Query("page") int page, @Query("limit") int limit);

    @POST("/api/save-share-contact-info")
    Observable<ShareContactInfoResponse> saveShareContactInfo(@Body RequestRematchParams params);

    @POST("/api/rematch-coupon")
    Observable<RematchCouponResponse> getRematchCoupon(@Query("user_id") String user_id, @Query("event_id") String event_id);

    @GET("/api/share-contact-total-message-unread")
    Observable<MyPageMenuResponse> myPageMatchingCount();

    @POST("/api/share-contact-use-coupon")
    Observable<OnResponse> shareContactUseCoupon(@Body ShareContactUseCoupon params);

    @POST("/api/share-contact-normal")
    Observable<PaymentResponse> rematchNormalPayment(@Body PaymentParams params);

    @POST("/api/user/out-group")
    Observable<ResponseExtension<String>> outGroup(@Query("password") String password);

    @PUT("/api/user/downgrade-normal")
    Observable<ResponseExtension<String>> leavePremium(@Query("password") String password);

    @POST("/api/rematch/check-expried")
    Observable<OnResponse> checkRematchExpried(@Body ExpiredRematchParams params);

    @GET("/api/rematch/common-user-info")
    Observable<ResponseExtension<RematchDetail>> getRematchDetail(@Query("user_id") String userId, @Query("event_id") String eventId);

    @PUT("/api/share-contact/{shareContactId}")
    Observable<OnResponse> actRematch(@Path("shareContactId") String shareContactId, @Query("type") String type);

    @GET("/api/user/get-info")
    Observable<UserInfoResponse> getUserInfoBasic();

    @PUT("/api/info-validation")
    Observable<ValidateUserInfoResponse> validateUserInfo(@Body UserInfo params);

    @PUT("/api/update-info")
    Observable<OnResponse> updateUserInfo(@Body UserInfo params);

    @GET("/api/rematch/userlist-all")
    Observable<ListRequestRematchResponse> getRequestRematch(@Query("is_request") String event_id, @Query("page") int page, @Query("limit") int limit);

    @PUT("/api/share-contact-mark-read/{shareContactId}")
    Observable<OnResponse> readRequest(@Path("shareContactId") String id);

    @PUT("/api/rematch/userlist-hide")
    Observable<OnResponse> hidenRequest(@Query("share_contact_id") String id);

    @GET("/api/user/print-card")
    Observable<PrintIntroductionSelf> getFileIntroductionSelf();

    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @PUT("/api/user/update-time-read-party")
    Observable<OnResponse> readParty(@Query("event_id") String event_id);

    @GET("/api/user/get-common-notifications")
    Observable<CommonNotificationResponse> getCommonNotifications(@Query("page") String page);

    @PUT("/api/user/mark-read-common-notifications/{notificationId}")
    Observable<OnResponse> readNotification(@Path("notificationId") String notificationId);

    @DELETE("/api/user/delete-common-notifications/{notificationId}")
    Observable<OnResponse> deleteNotification(@Path("notificationId") String notificationId);

    @GET("/api/user/get-card")
    Observable<UserCardRespone> getCard();

    @POST("/api/upload-image")
    Observable<ImageResponse> uploadImage(@Body UploadImageParams params);

    @DELETE("/api/delete-image")
    Observable<ImageResponse> deleteImage(@Query("photo_id") String photoId);


    @GET("/api/setting-notification/list-setting-notification")
    Observable<ResponseExtension<NotificationSetting>> getNotificationSettings();

    @POST("/api/setting-notification/create-update")
    Observable<OnResponse> updateNotificationSettings(@Body NotificationSetting setting);

    @POST("/api/get-click-banner")
    Observable<OnResponse> updateClickBanner(@Query("setting_id") String setting_id, @Query("device_id") String device_id);

    @POST("/api/validate-change-password")
    Observable<ValidatePasswordRespone> validateChangePassword(@Body LoginInfoModel loginInfoModel);

    @GET("/api/user/get-card-user-party/{user_id}/{event_id}")
    Observable<ResponseExtension<ProfileDetail>> getCardUserParty(@Path("user_id") String userId, @Path("event_id") String eventId);

    @GET("/api/user/get-total-notifications")
    Observable<NotificationRespone> getTotalNotifications();

    @PUT("/api/user/update-time-end-online")
    Observable<OnResponse> updateTimeEndOnline();

    @PUT("/api/user/update-time-start-online")
    Observable<OnResponse> updateTimeStartOnline();

    @GET("/api/rematch/total-notification")
    Observable<TotalNotiRematch> getTotalNotificationRematch();

    @PUT("/api/user/update-time-read-party")
    Observable<TotalNotiRematch> updateReadPartyTime(@Query("event_id") String event_id, @Query("rematch_type") String rematch_type);
}