package com.lroap.smack.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.lroap.smack.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {

            super.onBackPressed()
        }

    }

    fun loginBtnNavClicked (view:View){

        val loginIntent = Intent(this, LoginActivity::class.java) // aqui creo el intent
        startActivity(loginIntent) // con esto logro que desde el loginBtnNavClicked al oprimirlo yo vaya a la siguiente actividad  LoginActivity que contiene activity_login
    }

    fun addChannelClicked (view: View){

    }

    fun sendMsgBtnClicked (view: View){

    }
}


