package com.uawdevstudios.pocketreminder

import com.uawdevstudios.pocketreminder.Activities.SignUpActivity
import com.uawdevstudios.pocketreminder.Fragments.SignUpEmailFragment
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun usernameFormat_isCorrect(){
        val activity = SignUpActivity()
        val userName = "waqas_94"
        //assertEquals(true,activity.verifyUsernameFormat(userName))
    }

    @Test
    fun emailFormat_isCorrect(){
        val fragment = SignUpEmailFragment()
        val email = "w@gmail.com"
        assertEquals(true,fragment.matchEmailPattern(email))
    }
}
