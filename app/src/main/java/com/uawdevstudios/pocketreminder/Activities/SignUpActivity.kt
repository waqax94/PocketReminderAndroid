package com.uawdevstudios.pocketreminder.Activities

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.uawdevstudios.pocketreminder.Fragments.SignUpEmailFragment
import com.uawdevstudios.pocketreminder.R
import kotlinx.android.synthetic.main.content_sign_up.*
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    val fragmentManager = supportFragmentManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        hideKeybord()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setMessage("Form data will be lost")

        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }

        builder.setNegativeButton("Dismiss") { dialog, which ->
        }

        val emailFragment = SignUpEmailFragment()

        displayFragment(emailFragment)

        signupBackBtn.setOnClickListener {
            builder.show()
        }

        signupMainLayout.setOnClickListener {
            hideKeybord()
        }
        signupFragmentContainer.setOnClickListener {
            hideKeybord()
        }

    }

    fun displayFragment(fragment: Fragment){
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out)
        transaction.replace(R.id.signupFormFrame,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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

    override fun onBackPressed() {

    }

    fun setEmailFragmentIcon(){
        signupFragmentIcon.setImageDrawable(resources.getDrawable(R.drawable.email))
    }

    fun setUsernameFragmentIcon(){
        signupFragmentIcon.setImageDrawable(resources.getDrawable(R.drawable.user))
    }
}