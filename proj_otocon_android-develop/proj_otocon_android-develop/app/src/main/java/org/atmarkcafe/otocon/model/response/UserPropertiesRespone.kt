package org.atmarkcafe.otocon.model.response

import com.google.gson.annotations.SerializedName
import org.atmarkcafe.otocon.dialog.multiple.MultipleModel
import java.util.ArrayList

class UserPropertiesRespone : OnResponse() {
    @SerializedName("data")
    lateinit var data: List<String>

    @SerializedName("select_prefecture")
    lateinit var selectPrefectures: List<MultipleModel>

    @SerializedName("prefectures")
    lateinit var prefectures: List<MultipleModel>

    @SerializedName("common_status")
    lateinit var commonStatus: List<MultipleModel>

    @SerializedName("active")
    lateinit var actives: List<MultipleModel>

    @SerializedName("gender")
    lateinit var genders: List<MultipleModel>

    @SerializedName("yes_no")
    lateinit var yesNos: List<MultipleModel>

    @SerializedName("payment_method")
    lateinit var paymentMethods: List<MultipleModel>

    @SerializedName("basic_height")
    lateinit var basicHeight: List<MultipleModel>

    @SerializedName("user_type")
    lateinit var userTypes: List<MultipleModel>

    @SerializedName("language")
    lateinit var languages: List<MultipleModel>

    @SerializedName("residence")
    lateinit var residences: List<MultipleModel>

    @SerializedName("basic_housing")
    lateinit var basicHousings: List<MultipleModel>

    @SerializedName("body_type")
    lateinit var bodyTypes: List<MultipleModel>

    @SerializedName("blood_type")
    lateinit var bloodTypes: List<MultipleModel>

    @SerializedName("brother_sister")
    lateinit var brotherSisters: List<MultipleModel>

    @SerializedName("child_status")
    lateinit var childStatus: List<MultipleModel>

    @SerializedName("job_name")
    lateinit var jobNames: List<MultipleModel>

    @SerializedName("job_salary")
    lateinit var jobSalarys: List<MultipleModel>

    @SerializedName("final_education")
    lateinit var finalEducations: List<MultipleModel>

    @SerializedName("marital_status")
    lateinit var maritalStatus: List<MultipleModel>

    @SerializedName("last_educational_qualification")
    lateinit var lastEducationalQualifications: List<MultipleModel>

    @SerializedName("lifestyle_sex")
    lateinit var lifestyleSexs: List<MultipleModel>

    @SerializedName("lifestyle_sociability")
    lateinit var lifestyleSociabilitys: List<MultipleModel>

    @SerializedName("lifestyle_character")
    lateinit var lifestyleCharacters: List<MultipleModel>

    @SerializedName("lifestyle_charm_point")
    lateinit var lifestyleCharmPoints: List<MultipleModel>

    @SerializedName("lifestyle_sake")
    lateinit var lifestyleSakes: List<MultipleModel>

    @SerializedName("lifestyle_smoking")
    lateinit var lifestyleSmokings: List<MultipleModel>

    @SerializedName("lifestyle_cohabitant")
    lateinit var lifestyleCohabitants: List<MultipleModel>

    @SerializedName("after_marriage_house_work")
    lateinit var aftermarriagehouseWorks: List<MultipleModel>

    @SerializedName("after_marriage_job")
    lateinit var afterMarriageJobs: List<MultipleModel>

    @SerializedName("after_marriage_parent")
    lateinit var afterMarriageParents: List<MultipleModel>

    @SerializedName("marriage_time")
    lateinit var marriageTimes: List<MultipleModel>

    @SerializedName("marriage_hope")
    lateinit var marriageHopes: List<MultipleModel>

    @SerializedName("marriage_friend")
    lateinit var marriageFriends: List<MultipleModel>

    @SerializedName("marriage_first_date_cost")
    lateinit var marriagefirstdateCosts: List<MultipleModel>

    @SerializedName("marriage_partner_selection")
    lateinit var marriagepartnerSelections: List<MultipleModel>

    @SerializedName("marriage_hobby")
    lateinit var marriagehobbys: List<MultipleModel>

    @SerializedName("marriage_holiday")
    lateinit var marriageHolidays: List<MultipleModel>

    @SerializedName("lifestyle_holiday")
    lateinit var lifestyleHolidays: List<MultipleModel>

//    fun getNameModelByKey(id: String, key: ListData): String {
//        when (key) {
//            ListData.bodyTypes -> return getNameById(bodyTypes,id.toInt())
//            ListData.marriageHopes -> return getNameById(marriageHopes,id.toInt())
//            ListData.marriageFriends -> return getNameById(marriageFriends,id.toInt())
//            ListData.marriagefirstdateCosts -> return getNameById(marriagefirstdateCosts,id.toInt())
//            ListData.marriagehobbys -> return getNameById(marriagehobbys,id.toInt())
//            ListData.aftermarriagehouseWorks -> return getNameById(aftermarriagehouseWorks,id.toInt())
//            ListData.afterMarriageParents -> return getNameById(afterMarriageParents,id.toInt())
//        }
//        return ""
//    }

    private fun getNameById(list: List<MultipleModel>, id: Int): String{
        for (item in list){
            if (item.id == id)
                return item.title;
        }
        return "";
    }

    fun getListDataByKey(respone: UserPropertiesRespone, key: ListData): List<MultipleModel> {

        var listData: List<MultipleModel> = ArrayList()
        when (key) {
            ListData.selectPrefectures -> {
                listData = respone.prefectures
            }
            ListData. genders -> {
                listData = respone.genders
            }
            ListData.yesNos -> {
                listData = respone.yesNos
            }
            ListData.paymentMethods -> {
                listData = respone.paymentMethods
            }
            ListData.languages -> {
                listData = respone.languages
            }
            ListData.userTypes -> {
                listData = respone.userTypes
            }
            ListData.residences -> {
                listData = respone.residences
            }
            ListData.basicHousings -> {
                listData = respone.basicHousings
            }
            ListData.bodyTypes -> {
                listData = respone.bodyTypes
            }
            ListData.bloodTypes -> {
                listData = respone.bloodTypes
            }
            ListData.brotherSisters -> {
                listData = respone.brotherSisters
            }
            ListData. childStatus -> {
                listData = respone.childStatus
            }
            ListData.jobNames -> {
                listData = respone.jobNames
            }
            ListData.jobSalarys -> {
                listData = respone.jobSalarys
            }
            ListData.finalEducations -> {
                listData = respone.finalEducations
            }
            ListData.maritalStatus -> {
                listData = respone.maritalStatus
            }
            ListData.lastEducationalQualifications -> {
                listData = respone.lastEducationalQualifications
            }
            ListData.lifestyleSexs -> {
                listData = respone.lifestyleSexs
            }
            ListData.lifestyleSociabilitys -> {
                listData = respone.lifestyleSociabilitys
            }
            ListData.lifestyleCharacters -> {
                listData = respone.lifestyleCharacters
            }
            ListData.lifestyleCharmPoints -> {
                listData = respone.lifestyleCharmPoints
            }
            ListData.marriageFriends -> {
                listData = respone.marriageFriends
            }
            ListData.marriagefirstdateCosts -> {
                listData = respone.marriagefirstdateCosts
            }
            ListData.marriagepartnerSelections -> {
                listData = respone.marriagepartnerSelections
            }
            ListData.marriagehobbys -> {
                listData = respone.marriagehobbys
            }
            ListData.marriageHolidays -> {
                listData = respone.marriageHolidays
            }
            ListData.lifestyleHolidays -> {
                listData = respone.lifestyleHolidays
            }
            ListData.lifestyleSakes -> {
                listData = respone.lifestyleSakes
            }
            ListData.marriageTimes -> {
                listData = respone.marriageTimes
            }
            ListData.yesNos -> {
                listData = respone.yesNos
            }
            ListData.lifestyleSmokings -> {
                listData = respone.lifestyleSmokings
            }
            ListData.lifestyleSmokings -> {
                listData = respone.lifestyleSmokings
            }
            ListData.afterMarriageJobs -> {
                listData = respone.afterMarriageJobs
            }
            ListData.afterMarriageParents -> {
                listData = respone.afterMarriageParents
            }
            ListData.aftermarriagehouseWorks -> {
                listData = respone.aftermarriagehouseWorks
            }
            ListData.lifestyleCohabitants -> {
                listData = respone.lifestyleCohabitants
            }
            ListData.marriageHopes -> {
                listData = respone.marriageHopes
            }
            ListData.height->{
                listData=respone.basicHeight
            }
            ListData.basic_marital_status->{
                listData=respone.maritalStatus
            }
            ListData.prefecture->{
                listData=respone.prefectures
            }
        }
        return listData
    }
}


enum class ListData {
    selectPrefectures, genders, yesNos, paymentMethods, languages, userTypes, residences,
    basicHousings, bodyTypes, bloodTypes, brotherSisters, childStatus, jobNames, jobSalarys,
    finalEducations, maritalStatus, lastEducationalQualifications, lifestyleSexs, lifestyleSociabilitys, lifestyleCharacters, lifestyleCharmPoints,
    lifestyleSmokings, lifestyleCohabitants, aftermarriagehouseWorks, afterMarriageJobs, afterMarriageParents, marriageTimes, marriageHopes,
    marriageFriends, marriagefirstdateCosts, marriagepartnerSelections, marriagehobbys, marriageHolidays,lifestyleHolidays,lifestyleSakes,comm,basic_marital_status,height,prefecture
}