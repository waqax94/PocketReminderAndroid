package com.uawdevstudios.pocketreminder.Activities

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.uawdevstudios.pocketreminder.Models.User
import com.uawdevstudios.pocketreminder.R

class CodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)

        val user: User = intent.getParcelableExtra("user")
        val code = intent.getStringExtra("code")

        Toast.makeText(applicationContext,""+user.email+"\n"+code,Toast.LENGTH_SHORT).show()
    }
}