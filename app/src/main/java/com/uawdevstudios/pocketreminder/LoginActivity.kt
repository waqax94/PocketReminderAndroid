package com.uawdevstudios.pocketreminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import kotlinx.android.synthetic.main.content_login.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUsername.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginUsername.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        loginPassword.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginPassword.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

        })


        loginBtn.setOnClickListener {
            if(validateRequired()){
                Toast.makeText(applicationContext,"Login Successful",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }

        loginSigninbtn.setOnClickListener {
            val intent = Intent(baseContext,SignUpActivity::class.java)
            startActivity(intent)
        }

        loginParentLayout.setOnClickListener {
            hideKeybord()
        }
        loginMainLayout.setOnClickListener {
            hideKeybord()
        }
    }

    fun validateRequired(): Boolean{

        if(loginUsername.text == null || loginUsername.text.toString() == ""){
            loginUsername.background = resources.getDrawable(R.drawable.textfield_style2)
            return false
        }
        else if(loginPassword.text == null || loginPassword.text.toString() == ""){
            loginPassword.background = resources.getDrawable(R.drawable.textfield_style2)
            return false
        }
        return true
    }


    fun hideKeybord(){
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            imm.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }

}
