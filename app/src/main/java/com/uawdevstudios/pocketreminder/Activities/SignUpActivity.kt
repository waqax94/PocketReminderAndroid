package com.uawdevstudios.pocketreminder.Activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import com.uawdevstudios.pocketreminder.Models.ServerResponse
import com.uawdevstudios.pocketreminder.R
import com.uawdevstudios.pocketreminder.Services.APIService
import com.uawdevstudios.pocketreminder.Services.ServiceBuilder

import kotlinx.android.synthetic.main.content_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.progressview,null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()

        signupCCP.registerCarrierNumberEditText(signupPhone)
        signupCCP.setNumberAutoFormattingEnabled(true)


        signupFirstName.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signupFirstName.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        signupUsernameCheckBtn.setOnClickListener {
            userNameValadity()

        }

        signupUsername.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signupUsernameInfo.visibility = View.INVISIBLE
                signupUsername.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        signupEmailCheckBtn.setOnClickListener {
            emailValidity()
        }

        signupEmail.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signupEmail.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        signupCCP.setPhoneNumberValidityChangeListener {
            signupPhone.background = resources.getDrawable(R.drawable.textfield_style1)
            if (signupCCP.isValidFullNumber){
                signupPhoneCheck.visibility = View.VISIBLE
            }
            else{
                signupPhoneCheck.visibility = View.INVISIBLE
            }
        }

        signupPassword.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signupPassword.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        signupConfirmPw.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signupConfirmPw.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })



        signupBtn.setOnClickListener {

            if (checkFormValidity()){
                Toast.makeText(applicationContext,"Sign In done",Toast.LENGTH_SHORT).show()
            }
        }


        signupMainLayout.setOnClickListener {
            hideKeybord()
        }
        signupParentLayout.setOnClickListener {
            hideKeybord()
        }


        signupLoginBtn.setOnClickListener {
            this.finish()
        }

    }

    override fun onBackPressed() {

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

    fun userNameValadity(): Boolean{

        var check = false;

        if(signupUsername.text.toString() != ""){

            dialog.show()
            val apiService: APIService = ServiceBuilder.buildService(APIService::class.java)
            val requestCall: Call<ServerResponse> = apiService.checkUsername(signupUsername.text.toString())

            requestCall.enqueue(object : Callback<ServerResponse> {

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    dialog.dismiss()
                    if(response.isSuccessful){
                        if(response.body()?.connectionStatus!!){
                            signupUsernameInfo.visibility = View.VISIBLE
                            if(response.body()?.queryStatus!!){
                                signupUsernameInfo.setTextColor(resources.getColor(R.color.colorGreen))
                                signupUsernameInfo.text = response.body()!!.message
                                check = true
                            }
                            else{
                                signupUsernameInfo.setTextColor(resources.getColor(R.color.colorRed))
                                signupUsernameInfo.text = response.body()!!.message
                                check = false
                            }
                        }
                        else{
                            check = false
                        }
                    }
                    else{
                        Toast.makeText(applicationContext,"Response error",Toast.LENGTH_SHORT).show()
                        check = false
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    dialog.dismiss()
                    Toast.makeText(applicationContext,"Connection failure",Toast.LENGTH_SHORT).show()
                    check = false
                }

            })
            return check
        }
        signupUsername.background = resources.getDrawable(R.drawable.textfield_style2)
        return false
    }

    fun emailValidity(): Boolean{
        var check = true
        if(signupEmail.text.toString() != ""){

            dialog.show()

            val apiService: APIService = ServiceBuilder.buildService(APIService::class.java)
            val requestCall: Call<ServerResponse> = apiService.checkEmail(signupEmail.text.toString())

            requestCall.enqueue(object: Callback<ServerResponse>{

                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    dialog.dismiss()
                    if(response.isSuccessful){
                        if(response.body()?.connectionStatus!!){
                            signupEmailInfo.visibility = View.VISIBLE
                            if(response.body()?.queryStatus!!){
                                check = true
                                signupEmailInfo.setTextColor(resources.getColor(R.color.colorGreen))
                                signupEmailInfo.text = response.body()?.message
                            }
                            else{
                                check = false
                                signupEmailInfo.setTextColor(resources.getColor(R.color.colorRed))
                                signupEmailInfo.text = response.body()?.message
                            }
                        }
                        else{
                            check = false
                        }
                    }
                    else{
                        Toast.makeText(applicationContext,"Response error",Toast.LENGTH_SHORT).show()
                        check = false
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    dialog.dismiss()
                    Toast.makeText(applicationContext,"Connection failure",Toast.LENGTH_SHORT).show()
                    check = false
                }

            })

            return check
        }
        signupEmail.background = resources.getDrawable(R.drawable.textfield_style2)
        return false
    }

    fun checkFormValidity() : Boolean{
        var check = true
        if(signupFirstName.text.toString() == ""){
            signupFirstName.background = resources.getDrawable(R.drawable.textfield_style2)
            check = false
        }
        if(!userNameValadity()){
            check = false
        }
        if(!emailValidity()){
            check = false
        }
        if(!signupCCP.isValidFullNumber){
            signupPhone.background = resources.getDrawable(R.drawable.textfield_style2)
            check = false
        }
        if(signupPassword.text.toString().length < 8 || signupPassword.text.toString() == ""){
            signupPassword.background = resources.getDrawable(R.drawable.textfield_style2)
            check = false
        }
        if(signupPassword.text.toString() != signupConfirmPw.text.toString()){
            signupConfirmPw.background = resources.getDrawable(R.drawable.textfield_style2)
            check = false
        }
        return check
    }

}
