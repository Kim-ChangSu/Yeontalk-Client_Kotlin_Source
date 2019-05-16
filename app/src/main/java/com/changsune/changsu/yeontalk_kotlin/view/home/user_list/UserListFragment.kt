package com.changsune.changsu.yeontalk_kotlin.view.home.user_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost

import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.viewmodel.home.user_list.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    lateinit var viewModel: UserListViewModel

    lateinit var mUserListRecyclerViewAdapter: UserListRecyclerViewAdapter
    lateinit var mMeRecyclerViewAdapter: UserListRecyclerViewAdapter
    lateinit var mFavoriteRecyclerViewAdapter: UserListRecyclerViewAdapter
    lateinit var mChatbotRecyclerViewAdapter: UserListRecyclerViewAdapter

    lateinit var userImageListRecyclerViewAdapter: UserImageListRecyclerViewAdapter

    var mBoolean_lockList_users: Boolean = false
    var mBoolean_isPossibleLoadMore_users: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)

        mUserListRecyclerViewAdapter = UserListRecyclerViewAdapter(context!!, arrayListOf(), false)
        mMeRecyclerViewAdapter = UserListRecyclerViewAdapter(context!!, arrayListOf(), false)
        mFavoriteRecyclerViewAdapter = UserListRecyclerViewAdapter(context!!, arrayListOf(), true)
        mChatbotRecyclerViewAdapter = UserListRecyclerViewAdapter(context!!, arrayListOf(), false)
        userImageListRecyclerViewAdapter = UserImageListRecyclerViewAdapter(context!!, arrayListOf())

        recyclerview_user_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mUserListRecyclerViewAdapter
        }

        recyclerview_me.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mMeRecyclerViewAdapter
        }
        recyclerview_chatbot.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mChatbotRecyclerViewAdapter
        }
        recyclerview_favorite_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mFavoriteRecyclerViewAdapter
        }

        recyclerview_users_image_list.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = userImageListRecyclerViewAdapter

        }

        // NestedScrollView(a~z)
        nestedscrollview1.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(p0: NestedScrollView?, p1: Int, p2: Int, p3: Int, p4: Int) {
                if (p2 == nestedscrollview1.getChildAt(0).measuredHeight - nestedscrollview1.measuredHeight && mBoolean_lockList_users == false) {

                    if (mBoolean_isPossibleLoadMore_users) {
                        mBoolean_lockList_users = true

                        bottom_progressbar1.setVisibility(View.VISIBLE)
                        viewModel.loadMoreUserList()

                    } else {
                        mBoolean_lockList_users = false
                    }
                }
            }

        })

        viewModel.refresh()

        setUpTabHost(tabHost)

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.userList.observe(this, Observer { userList ->
            userList?.let {
                bottom_progressbar1.setVisibility(View.GONE)
                mUserListRecyclerViewAdapter.updateUsers(it)

            }
        })

        viewModel.meList.observe(this, Observer { userList ->
            userList?.let {
                mMeRecyclerViewAdapter.updateUsers(it)
            }
        })

        viewModel.chatBotList.observe(this, Observer { userList ->
            userList?.let {
                mChatbotRecyclerViewAdapter.updateUsers(it)
            }
        })

        viewModel.favoriteList.observe(this, Observer { userList ->
            userList?.let {
                if(it.size > 0) {
                    linearlayout_favorites_text.visibility = View.VISIBLE

                } else {
                    linearlayout_favorites_text.visibility = View.GONE
                }
                mFavoriteRecyclerViewAdapter.updateUsers(it)

            }
        })

        viewModel.userImageList.observe(this, Observer { userList ->
            userList?.let {
                userImageListRecyclerViewAdapter.updateUserImage(it)
            }
        })
    }

    private fun setUpTabHost(tabHost_casted: TabHost) {
        tabHost_casted.setup()
        var spec: TabHost.TabSpec = tabHost_casted.newTabSpec("Tab One")
        spec.setContent(R.id.tab1)
        spec.setIndicator("목록")
        tabHost_casted.addTab(spec)
        spec = tabHost_casted.newTabSpec("Tab Two")
        spec.setContent(R.id.tab2)
        spec.setIndicator("사진")
        tabHost_casted.addTab(spec)
        tabHost_casted.tabWidget.getChildAt(0).setOnClickListener { onTab1Clicked() }
        tabHost_casted.tabWidget.getChildAt(1).setOnClickListener { onTab2Clicked() }
    }

    // Tab
    private fun onTab1Clicked() {
        tabHost.setCurrentTab(0)
//        if (!mSwipeRefreshLayout_firstTab.isRefreshing()) {
//            mSwipeRefreshLayout_firstTab.setRefreshing(true)
//            // Receiving Data from DataBase ------------------------------------------------------------
//            mFetchUserListUseCase.fetchUserListUseCaseAndNotify(Constants.DEVICE_ID,
//                    Constants.LOAD_LIMIT,
//                    "",
//                    mGender,
//                    (Constants.CURRENT_YEAR_PLUS_ONE - Integer.valueOf(mMinAge)).toString(),
//                    (Constants.CURRENT_YEAR_PLUS_ONE - Integer.valueOf(mMaxAge)).toString(),
//                    mNation,
//                    mRegion)
//            // -----------------------------------------------------------------------------------------
//        }

    }

    private fun onTab2Clicked() {
        tabHost.setCurrentTab(1)
//        if (!mSwipeRefreshLayout_secondTab.isRefreshing()) {
//            mSwipeRefreshLayout_secondTab.setRefreshing(true)
//            mFetchUsersImageListUseCase.fetchUsersImageListAndNotify(mList_me.get(0).getMeId(),
//                    Constants.LOAD_LIMIT,
//                    "",
//                    mGender,
//                    (Constants.CURRENT_YEAR_PLUS_ONE - Integer.valueOf(mMinAge)).toString(),
//                    (Constants.CURRENT_YEAR_PLUS_ONE - Integer.valueOf(mMaxAge)).toString(),
//                    mNation,
//                    mRegion)
//        }
    }


}
