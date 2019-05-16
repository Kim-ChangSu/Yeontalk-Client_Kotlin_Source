package com.changsune.changsu.yeontalk_kotlin.viewmodel.home.user_list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.content.Context
import android.util.Log
import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.di.DaggerApiComponent
import com.changsune.changsu.yeontalk_kotlin.model.user.User
import com.changsune.changsu.yeontalk_kotlin.networking.MultipleUserListResponse
import com.changsune.changsu.yeontalk_kotlin.networking.UserListResponse
import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var yeonTalkService: YeonTalkService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    var sharedPreferences_setting = getApplication<Application>().getSharedPreferences(Constants.SHAREDPREF_KEY_SETTING, Context.MODE_PRIVATE)
    var sharedPreferences_profile = getApplication<Application>().getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, Context.MODE_PRIVATE)

    val meList = MutableLiveData<List<User>>()
    val chatBotList = MutableLiveData<List<User>>()
    val favoriteList = MutableLiveData<List<User>>()
    val userList = MutableLiveData<ArrayList<User>>()
    val userImageList = MutableLiveData<List<User>>()

    var fetchUserListError = MutableLiveData<Boolean>()

    fun refresh() {

        var selection_gender = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_GENDER, "")
        var selection_maxAge = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_MAX_AGE, "100")
        var selection_minAge = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_MIN_AGE, "0")
        var selection_nation = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_NATION, "")
        var selection_region = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_REGION, "")

        fetchUserList(
            Constants.DEVICE_ID,
            Constants.LOAD_LIMIT,
            "",
            selection_gender.toString(),
            (Constants.CURRENT_YEAR_PLUS_ONE?.minus(Integer.valueOf(selection_minAge))).toString(),
            (Constants.CURRENT_YEAR_PLUS_ONE?.minus(Integer.valueOf(selection_maxAge))).toString(),
            selection_nation.toString(),
            selection_region.toString())

        fetchUserImageList(
            Constants.DEVICE_ID,
            Constants.LOAD_LIMIT,
            "",
            selection_gender.toString(),
            (Constants.CURRENT_YEAR_PLUS_ONE?.minus(Integer.valueOf(selection_minAge))).toString(),
            (Constants.CURRENT_YEAR_PLUS_ONE?.minus(Integer.valueOf(selection_maxAge))).toString(),
            selection_nation.toString(),
            selection_region.toString())

    }

    fun loadMoreUserList() {

        var selection_gender = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_GENDER, "")
        var selection_maxAge = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_MAX_AGE, "100")
        var selection_minAge = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_MIN_AGE, "0")
        var selection_nation = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_NATION, "")
        var selection_region = sharedPreferences_setting?.getString(Constants.SHAREDPREF_KEY_SETTING_REGION, "")

        fetchUserList(
            Constants.DEVICE_ID,
            Constants.LOAD_LIMIT,
            userList.value!!.get(userList.value!!.size!! -1).userLogInTime!!,
            selection_gender.toString(),
            (Constants.CURRENT_YEAR_PLUS_ONE?.minus(Integer.valueOf(selection_minAge))).toString(),
            (Constants.CURRENT_YEAR_PLUS_ONE?.minus(Integer.valueOf(selection_maxAge))).toString(),
            selection_nation.toString(),
            selection_region.toString())
    }

    private fun fetchUserList(user_device_id: String, load_limit: String, user_login_time_loaded_last: String, selection_gender: String, selection_max_birthyear: String, selection_min_birthyear: String, selection_nation: String, selection_region: String) {
        disposable.add(
            yeonTalkService.fetchUserList(user_device_id, load_limit, user_login_time_loaded_last, selection_gender, selection_max_birthyear, selection_min_birthyear, selection_nation, selection_region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MultipleUserListResponse>(){
                    override fun onSuccess(value: MultipleUserListResponse?) {
                        if (user_login_time_loaded_last.equals("")) {

                            val me = value?.multipleUserList?.get(0)?.userList_me?.get(0)
                            meList.value = arrayListOf(User(me?.meBirthYear, me?.meDeviceId, me?.meGender, me?.meId, me?.meIntroduction, me?.meLogInTime, me?.meNation, me?.meNickname, me?.meProfileImage, me?.meRegion))
                            chatBotList.value = value?.multipleUserList?.get(0)?.userList_chatbot
                            favoriteList.value = value?.multipleUserList?.get(0)?.userList_favorite
                            userList.value = value?.multipleUserList?.get(0)?.userList_users

                            val editor = sharedPreferences_profile.edit()
                            editor.clear()
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, me?.meId)
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_NICKNAME, me?.meBirthYear)
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_GENDER, me?.meGender)
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_BIRTHYEAR, me?.meBirthYear)
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_NATION, me?.meNation)

                            if (me?.meRegion != "") {
                                editor.putString(Constants.SHAREDPREF_KEY_PROFILE_REGION, me?.meRegion)
                            }
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_IMAGE, me?.meProfileImage)
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_INSTRODUCTION, me?.meIntroduction)
                            editor.putString(Constants.SHAREDPREF_KEY_PROFILE_POINT, me?.mePoint)
                            editor.commit()
                        } else {

                            userList.value?.addAll(value?.multipleUserList?.get(0)?.userList_users!!)
                            userList.value = userList.value

                        }

                    }

                    override fun onError(e: Throwable?) {
                        fetchUserListError.value = true
                    }

                })

        )

    }

    private fun fetchUserImageList(user_device_id: String, load_limit: String, user_login_time_loaded_last: String, selection_gender: String, selection_max_birthyear: String, selection_min_birthyear: String, selection_nation: String, selection_region: String) {

        Log.e("device_id", user_device_id)

        disposable.add(
            yeonTalkService.fetchUserImageList(user_device_id, load_limit, user_login_time_loaded_last, selection_gender, selection_max_birthyear, selection_min_birthyear, selection_nation, selection_region)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserListResponse>(){
                    override fun onSuccess(value: UserListResponse?) {
                        userImageList.value = value?.userList_users
                        Log.e("name", value?.userList_users?.get(0)?.userNickname)
                    }

                    override fun onError(e: Throwable?) {
                        fetchUserListError.value = true
                    }

                })

        )

    }

}

