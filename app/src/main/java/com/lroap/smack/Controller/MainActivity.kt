package com.lroap.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.lroap.smack.R
import com.lroap.smack.Services.AuthService
import com.lroap.smack.Services.UserDataService
import com.lroap.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // revisar esta parte la linea de arriba no va en el codigo original que es toolbar

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver,
            IntentFilter(BROADCAST_USER_DATA_CHANGE))

    }

    private val userDataChangeReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
             if (AuthService.isLoggedIn){
                userNameNavHeader.text = UserDataService.name
                 userEmailNavHeader.text = UserDataService.email
                 val resourceId = resources.getIdentifier(UserDataService.avatarName, "drawable",
                     packageName)
                 userImageNavHeader.setImageResource(resourceId)
                 userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                 loginBtnNavHeader.text =  "Logout"

            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {

            super.onBackPressed()
        }

    }

    fun loginBtnNavClicked (view:View){

        if(AuthService.isLoggedIn){
            //logout
            UserDataService.logout()
            userNameNavHeader.text = ""
            userEmailNavHeader.text = ""
            userImageNavHeader.setImageResource((R.drawable.profiledefault))
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            loginBtnNavHeader.text = "Login"

        } else{
            val loginIntent = Intent(this, LoginActivity::class.java) // aqui creo el intent
            startActivity(loginIntent) // con esto logro que desde el loginBtnNavClicked al oprimirlo yo vaya a la siguiente actividad  LoginActivity que contiene activity_login
        }

    }

    fun addChannelClicked (view: View){

    }

    fun sendMsgBtnClicked (view: View){

    }
}


