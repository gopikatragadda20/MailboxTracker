package com.example.mailboxtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        loginToSystem.setOnClickListener {
                if(editTextTextEmailAddress.text.toString() == "admin" && editTextTextPassword.text.toString() == "admin"){
                    startActivity(Intent(this, ShowBluetoothActivity::class.java ))
                    this.textViewInvalid.isInvisible = true
                }else{
                    this.textViewInvalid.isInvisible = false
                }
        }
    }
}