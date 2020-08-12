package com.uawdevstudios.pocketreminder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uawdevstudios.pocketreminder.Activities.SignUpActivity
import com.uawdevstudios.pocketreminder.R
import kotlinx.android.synthetic.main.fragment_sign_up_username.*
import kotlinx.android.synthetic.main.fragment_sign_up_username.view.*

class SignUpUsernameFragment : Fragment() {

    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(R.layout.fragment_sign_up_username, container, false)

        (activity as SignUpActivity?)!!.setUsernameFragmentIcon()

        email = arguments?.getString("email") ?: ""
        Toast.makeText(activity,email,Toast.LENGTH_SHORT).show()


        rootView.signupUsernameMainLayout.setOnClickListener {
            try {
                (activity as SignUpActivity?)!!.hideKeybord()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }

        return rootView
    }

}