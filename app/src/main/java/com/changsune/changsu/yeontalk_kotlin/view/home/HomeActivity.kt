package com.changsune.changsu.yeontalk_kotlin.view.home

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.view.home.chat_room_list.ChatRoomListFragment
import com.changsune.changsu.yeontalk_kotlin.view.home.more.MoreFragment
import com.changsune.changsu.yeontalk_kotlin.view.home.user_list.UserListFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    val fragment1 = UserListFragment()
    val fragment2 = ChatRoomListFragment()
    val fragment3 = MoreFragment()

    var active: Fragment = fragment1

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.ic_person -> {
                toolbar_title.text = "친구"
                supportFragmentManager.beginTransaction().hide(active).show(fragment1).commit()
                active = fragment1
                fragment1.viewModel.refresh()
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_chat -> {
                toolbar_title.text = "채팅"
                supportFragmentManager.beginTransaction().hide(active).show(fragment2).commit()
                active = fragment2
                fragment2.viewModel.refresh()
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_more -> {
                toolbar_title.text = "기타"
                supportFragmentManager.beginTransaction().hide(active).show(fragment3).commit()
                active = fragment3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().add(R.id.container, fragment3, fragment3.javaClass.getSimpleName()).hide(fragment3).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment2, fragment2.javaClass.getSimpleName()).hide(fragment2).commit()
        toolbar_title.text = "친구"
        supportFragmentManager.beginTransaction().add(R.id.container, fragment1, fragment1.javaClass.getSimpleName()).commit()
    }

    override fun onRestart() {
        super.onRestart()
        fragment1.viewModel.refresh()
    }


}
