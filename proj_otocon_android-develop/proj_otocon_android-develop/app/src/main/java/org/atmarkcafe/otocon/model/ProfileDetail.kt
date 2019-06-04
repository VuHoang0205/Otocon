package org.atmarkcafe.otocon.model

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.google.gson.annotations.SerializedName
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.atmarkcafe.otocon.BR
import org.atmarkcafe.otocon.R
import org.atmarkcafe.otocon.function.mypage.item.UserProfileParams
import org.atmarkcafe.otocon.function.rematch.item.ProfileItem
import java.text.SimpleDateFormat
import java.util.*

class ProfileDetail : BaseObservable() {
    var email: String = ""

    @SerializedName("name_sei")
    var nameSei: String = ""

    @SerializedName("name_mei")
    var nameMei: String = ""

    @SerializedName("name_kanasei")
    var nameKanaSei: String = ""

    @SerializedName("name_kanamei")
    var nameKanaMei: String = ""

    var gender: Int = 1

    var age: Int = 1

    var tel: String = ""
    var birthday: String = ""
    var prefecture: String = ""

    private var selectedAvatar: Int = 0

    var profile: Profile? = null

    @SerializedName("photo")
    var avatar: ArrayList<Avatar> = ArrayList<Avatar>()

    fun getName(): String {
        return "$nameSei $nameMei"
    }

    fun getAgePrefecture(): String {
        return "${age}歳 - $prefecture"
    }

    fun getAvatar(index: Int): String {
        if (avatar == null) return ""
        if (avatar.size <= index) return ""
        return avatar[index].picture
    }

    @Bindable
    fun getSelectedAvatar(): Int {
        return selectedAvatar
    }

    fun setSeletedAvatar(index: Int) {
        this.selectedAvatar = index
        notifyPropertyChanged(BR.selectedAvatar)
    }

    fun getCommonCompleted(): String {
        if (profile == null || profile?.common_completed == null) return ""
        return "${profile!!.common_completed}"

    }

    fun getCommonDescription(): String {
        if (profile == null || profile?.common_description == null) return ""
        return "${profile!!.common_description}"

    }

    fun refactorAvatar() {
        // sort array avatar
        if (avatar.size <= 1) return
        Collections.sort(avatar, Comparator<Avatar> { o1, o2 ->
            if (o1!!.order < o2!!.order) {
                return@Comparator -1
            }
            1
        })
        if (avatar[0].type == RematchDetail.TYPE_IMAGE_AVATAR) {
            avatar.removeAt(0)
        }
        setSeletedAvatar(0)
    }

    fun hasHobby(): Boolean {
        return (profile?.lifestyle_sort_hobby1 != null || profile?.lifestyle_sort_hobby2 != null || profile?.lifestyle_sort_hobby3 != null)
    }

    fun getProfileAdapter(context: Context): ProfileAdapter {
        val adapter = ProfileAdapter()
        profile?.let {

            adapter.add(context.getString(R.string.profile_nickname), it.nickname)

            adapter.add(context.getString(R.string.profile_phonetic), "$nameKanaSei $nameKanaMei")

            adapter.add(context.getString(R.string.profile_current_address), it.address)

            adapter.add(context.getString(R.string.profile_gender), if (gender == 1) context.getString(R.string.male) else context.getString(R.string.female))

            val format = SimpleDateFormat("yyyy-MM-dd")
            val toFormat = SimpleDateFormat("yyyy年M月d日")
            adapter.add(context.getString(R.string.profile_birthday), toFormat.format(format.parse(birthday)))

            adapter.add(context.getString(R.string.profile_prefecture), prefecture)
            adapter.add(context.getString(R.string.profile_hobby), it.common_hobby)

            adapter.add(context.getString(R.string.profile_residence), it.basic_residence)
            adapter.add(context.getString(R.string.profile_height), it.basic_height)
            adapter.add(context.getString(R.string.profile_blood_type), it.basic_blood_type)
            adapter.add(context.getString(R.string.profile_family_structure), it.basic_brother_sister)
            adapter.add(context.getString(R.string.profile_marital_status), it.basic_marital_status)
            adapter.add(context.getString(R.string.profile_children), it.basic_child_status)
            adapter.add(context.getString(R.string.profile_profession), it.job_name)
            adapter.add(context.getString(R.string.profile_annual_income), it.job_salary)
            adapter.add(context.getString(R.string.profile_final_education), it.job_final_education)
            adapter.add(context.getString(R.string.profile_final_academic_report), it.job_last_educational_qualification)

            adapter.add(context.getString(R.string.profile_sake), it.lifestyle_sake)
            adapter.add(context.getString(R.string.profile_smocking), it.lifestyle_smoking)

            adapter.add(context.getString(R.string.profile_car), it.lifestyle_have_car)
//            if (hasHobby()) {
                adapter.add(context.getString(R.string.profile_favorite_ranking))
//            }
            adapter.add(context.getString(R.string.profile_favorite_ranking_0), it.lifestyle_sort_hobby1, false)
            adapter.add(context.getString(R.string.profile_favorite_ranking_1), it.lifestyle_sort_hobby2, false)
            adapter.add(context.getString(R.string.profile_favorite_ranking_2), it.lifestyle_sort_hobby3, false)

            adapter.add(context.getString(R.string.profile_holliday), it.lifestyle_holiday)

            adapter.add(context.getString(R.string.profile_married_parents), it.marriage_time)

            adapter.add(context.getString(R.string.profile_partner_selection), it.marriage_partner_selection)

            adapter.add(context.getString(R.string.profile_to_spend_the_holliday), it.marriage_holiday)

            adapter.add(context.getString(R.string.profile_ideal_couple_statue), it.marriage_ideal_wife)

            adapter.add(context.getString(R.string.profile_working_together), it.after_marriage_job)
            // TODO EXAMPLE
            adapter.add(context.getString(R.string.profile_promis_when_married), it.after_marriage_job)
            adapter.add(context.getString(R.string.profile_type_of_prefecture), it.type_of_preference)
            adapter.add(context.getString(R.string.profile_self_introduction), it.common_description)

            adapter.add(context.getString(R.string.profile_talkable_language), it.basic_language)

            adapter.add(context.getString(R.string.profile_body_type), it.basic_body_type)

            adapter.add(context.getString(R.string.profile_occupation_detail), it.job_description)
            adapter.add(context.getString(R.string.profile_school_name), it.job_university)
            adapter.add(context.getString(R.string.profile_personality), it.lifestyle_character)
            adapter.add(context.getString(R.string.profile_sociability), it.lifestyle_sociability)
            adapter.add(context.getString(R.string.profile_charm_point), it.lifestyle_charm_point)
            adapter.add(context.getString(R.string.profile_housemate), it.lifestyle_cohabitant)
            adapter.add(context.getString(R.string.profile_hope_util_meeting), it.marriage_hope)
            adapter.add(context.getString(R.string.profile_like_to_see_my_friends), it.marriage_friend)


//            adapter.add(context.getString(R.string.profile_image_after_marriage))
            adapter.add(context.getString(R.string.profile_first_date_cost), it.marriage_first_date_cost)
            adapter.add(context.getString(R.string.profile_sharing_hobbies), it.marriage_hobby)
            adapter.add(context.getString(R.string.profile_role_assignment_of_housework), it.after_marriage_house_work)
            adapter.add(context.getString(R.string.profile_parenting), it.after_marriage_parent)

        }

        return adapter
    }
}

class Profile {
    val user_id: String = ""
    val nickname: String = ""
    val common_hobby: String = ""
    val common_diagnosis: String = ""
    val common_like_count: String = ""
    val common_completed: String = ""
    val common_description: String = ""
    val basic_language: String = ""
    val basic_body_type: String = ""
    val basic_blood_type: String = ""
    val basic_brother_sister: String = ""
    val basic_marital_status: String = ""
    val basic_child_status: String = ""
    val basic_weight: String = ""
    val basic_height: String = ""
    val address: String = ""
    val type_of_preference: String = ""
    val basic_housing: String = ""
    val basic_hometown: String = ""
    val basic_hometown_prefecture: String = ""
    val basic_residence: String = ""
    val job_name: String = ""
    val job_salary: String = ""
    val job_final_education: String = ""
    val job_last_educational_qualification: String = ""
    val job_description: String = ""
    val job_location: String = ""
    val job_location_prefecture: String = ""
    val job_salary_certification: String = ""
    val job_education_certification: String = ""
    val job_university: String = ""
    val lifestyle_character: String = ""
    val lifestyle_sociability: String = ""
    val lifestyle_charm_point: String = ""
    val lifestyle_sake: String = ""
    val lifestyle_smoking: String = ""
    val lifestyle_cohabitant: String = ""
    val lifestyle_holiday: String = ""
    val lifestyle_have_car: String = ""
    val lifestyle_sort_hobby1: String = ""
    val lifestyle_sort_hobby2: String = ""
    val lifestyle_sort_hobby3: String = ""
    val lifestyle_sort_hobby: String = ""
    val lifestyle_sex: String = ""
    val marriage_time: String = ""
    val marriage_hope: String = ""
    val marriage_friend: String = ""
    val marriage_first_date_cost: String = ""
    val marriage_partner_selection: String = ""
    val marriage_hobby: String = ""
    val marriage_holiday: String = ""
    val marriage_ideal_wife: String = ""
    val after_marriage_job: String = ""
    val after_marriage_house_work: String = ""
    val after_marriage_parent: String = ""
    val after_marriage_committed: String = ""
    val deleted_at: String = ""
    val created_at: String = ""
    val updated_at: String = ""
}

class ProfileAdapter : GroupAdapter<ViewHolder>() {
    fun add(key: String, value: String?, bold: Boolean) {
        if (value != null) {
            add(ProfileItem(key, value, bold))
        } else {
            add(ProfileItem(key, "", bold))
        }
    }

    fun add(key: String, value: String?) {
        add(key, value, true)
    }

    fun add(title: String) {
        add(ProfileItem(title, "", true))
    }
}