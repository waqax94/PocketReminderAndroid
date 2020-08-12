package com.uawdevstudios.pocketreminder.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.uawdevstudios.pocketreminder.Activities.SignUpActivity
import com.uawdevstudios.pocketreminder.Models.ServerResponse
import com.uawdevstudios.pocketreminder.R
import com.uawdevstudios.pocketreminder.Services.APIService
import com.uawdevstudios.pocketreminder.Services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_sign_up_email.*
import kotlinx.android.synthetic.main.fragment_sign_up_email.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpEmailFragment : Fragment() {

    lateinit var progressDialog: AlertDialog
    var code = ""
    lateinit var fadeInAnimation : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(R.layout.fragment_sign_up_email, container, false)

        (activity as SignUpActivity?)!!.setEmailFragmentIcon()

        fadeInAnimation = AnimationUtils.loadAnimation(activity,R.anim.fade_in)

        val progressDialogBuilder = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.progressview,null)
        progressDialogBuilder.setView(dialogView)
        progressDialogBuilder.setCancelable(false)
        progressDialog = progressDialogBuilder.create()

        val permissionDialog = AlertDialog.Builder(activity)
        permissionDialog.setTitle("Change email?")
        permissionDialog.setMessage("Do you want to change email?")

        permissionDialog.setPositiveButton("Yes") { dialog, which ->
            rootView.signupEmailChangeBtn.visibility = View.GONE
            rootView.signupEmailInput.visibility = View.VISIBLE
            rootView.signupEmailVerifyBtn.visibility = View.VISIBLE
            rootView.signupEmailCodeInfo.visibility = View.GONE
            rootView.signupEmailCodeInput.visibility = View.GONE
            rootView.signupEmailNextBtn.visibility = View.GONE
        }

        permissionDialog.setNegativeButton("No") { dialog, which ->
        }



        rootView.signupEmailInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                signupEmailInputInfo.visibility = View.INVISIBLE
                signupEmailInput.background = resources.getDrawable(R.drawable.textfield_style1)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        rootView.signupEmailChangeBtn.setOnClickListener {
            permissionDialog.show()
        }

        rootView.signupEmailVerifyBtn.setOnClickListener {
            (activity as SignUpActivity?)!!.hideKeybord()
            validateEmail(signupEmailInput.text.toString(),rootView)
        }

        rootView.signupEmailCodeInput.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                rootView.signupEmailCodeInput.background = resources.getDrawable(R.drawable.textfield_style1)
            }
        })

        rootView.signupEmailNextBtn.setOnClickListener {
            if(code == signupEmailCodeInput.text.toString()){
                val bundle = Bundle()
                bundle.putString("email", rootView.signupEmailInput.text.toString())
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                val signUpUsernameFragment = SignUpUsernameFragment()
                signUpUsernameFragment.arguments = bundle
                if(transaction != null){
                    transaction.replace(R.id.signupFormFrame,signUpUsernameFragment)
                    transaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
            else{
                signupEmailCodeInput.background = resources.getDrawable(R.drawable.textfield_style2)
            }
        }

        rootView.signupEmailMainLayout.setOnClickListener {
            (activity as SignUpActivity?)!!.hideKeybord()
        }

        return rootView
    }

    fun validateEmail(email: String, view: View) {
        if(email != ""){
            if (matchEmailPattern(email)){

                progressDialog.show()
                val apiService: APIService = ServiceBuilder.buildService(APIService::class.java)
                val requestCall: Call<ServerResponse> = apiService.checkEmail(view.signupEmailInput.text.toString())

                requestCall.enqueue(object : Callback<ServerResponse>{
                    override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                        progressDialog.dismiss()
                        if (response.isSuccessful){
                            if(response.body()?.connectionStatus!!){
                                if(response.body()?.queryStatus!!){
                                    view.signupEmailChangeBtn.text = view.signupEmailInput.text.toString()
                                    view.signupEmailInput.visibility = View.INVISIBLE
                                    view.signupEmailChangeBtn.visibility = View.VISIBLE
                                    view.signupEmailInputInfo.visibility = View.INVISIBLE
                                    view.signupEmailVerifyBtn.visibility = View.INVISIBLE
                                    code = response.body()?.message!!
                                    view.signupEmailCodeInfo.startAnimation(fadeInAnimation)
                                    view.signupEmailCodeInfo.visibility = View.VISIBLE
                                    view.signupEmailCodeInput.startAnimation(fadeInAnimation)
                                    view.signupEmailCodeInput.visibility = View.VISIBLE
                                    view.signupEmailNextBtn.startAnimation(fadeInAnimation)
                                    view.signupEmailNextBtn.visibility = View.VISIBLE
                                }
                                else{
                                    view.signupEmailInputInfo.text = response.body()?.message
                                    view.signupEmailInputInfo.setTextColor(resources.getColor(R.color.colorRed))
                                    view.signupEmailInputInfo.visibility = View.VISIBLE
                                }
                            }
                            else{
                                Toast.makeText(activity,"Connection status error!",Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(activity,"Response error!",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                        progressDialog.dismiss()
                        Toast.makeText(activity,"Connection error!",Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else{
                view.signupEmailInputInfo.setTextColor(resources.getColor(R.color.colorRed))
                view.signupEmailInputInfo.text = "Incorrect email pattern ✕"
                view.signupEmailInputInfo.visibility = View.VISIBLE
            }
        }
        else{
            view.signupEmailInput.background = resources.getDrawable(R.drawable.textfield_style2)
        }
    }

    fun matchEmailPattern(email : String) : Boolean{
        val emailPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$")

        if(emailPattern.matcher(email).matches()){
            return true
        }

        return false
    }
}