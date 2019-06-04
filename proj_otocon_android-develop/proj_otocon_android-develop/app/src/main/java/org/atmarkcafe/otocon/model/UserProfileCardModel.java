package org.atmarkcafe.otocon.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.utils.GlideUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileCardModel {
    public String id;
    public String email;
    public String role_value;
    public String name;
    public String created_at;
    public String updated_at;
    public String is_active;
    public String username;
    public String name_sei;
    public String name_mei;
    public String name_kanasei;
    public String name_kanamei;
    public String ponta_id;
    public String gender;
    public String age;
    public String age_certification_status;
    public String tel;
    public String birthday;
    public String prefecture;
    public String payment_method;
    public String line_id;
    public String premium_expired_date;
    public String type;
    public String cancel_premium;
    public String status;
    public String online_start;
    public String online_end;
    public String is_install_app;
    public String join_count;
    public String last_join_date;
    public String active_date;
    public String deleted_at;
    public ProfileCardModel profile;
    public List<PhotoModel> photo;

    public String fomatAge(Context context, UserProfileCardModel model) {
        return String.format(context.getString(R.string.fomat_data), model.age, model.prefecture, model.profile.common_completed);
    }

    public int numberPhoto() {
        if (photo == null || photo.size()==0) return 0;
        if (photo.size() == 1 && photo.get(0).order == null) return 0;
        return photo.size();
    }

    public boolean hasAvatar(int position) {
        if (photo != null) {
            String url = null;
            for (int i = 0; i < photo.size(); i++) {
                if (photo.get(i).order != null && Integer.parseInt(photo.get(i).order) == position) {
                    url = photo.get(i).picture;
                    break;
                }
            }
            return (url != null && url.length() > 0);
        }
        return false;
    }

}
