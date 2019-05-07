package com.huang.homan.encryptdecrypttool

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Created by Homan Huang at 5/7/2019

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val TAG = "MYLOG MainActivityTest"
    private fun ltag(message: String) { Log.i(TAG, message) }

    private lateinit var mainActivity: MainActivity

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mainActivity = activityTestRule.activity
    }


    @Test
    fun testResult() {
        ltag("Test")
        assertNotNull(mainActivity)

        //check api key content
        val mApiKeyEt = mainActivity.apiKeyET

        //onView(withId(mApiKeyEt.id)).perform(replaceText(""))//red flag

        ltag("API key length: ${mApiKeyEt.text.length}")
        assertThat(mApiKeyEt.text.length, greaterThan(1))

        //check pass key content
        val mPassKeyEt = mainActivity.passKeyET

        //onView(withId(mPassKeyEt.id)).perform(replaceText(""))//red flag

        ltag("Pass key length: ${mPassKeyEt.text.length}")
        assertThat(mPassKeyEt.text.length, greaterThan(1))

        //click encryption button
        val mEncryptBt = mainActivity.encryptBT
        //now press the encrypt button
        //    mEncryptBt.performClick() is not working
        //Espresso way:
        onView(withId(mEncryptBt.id)).perform(click())
        //check encryption result
        val mEncryptTv = mainActivity.encryptTV

        //mEncryptTv.text = "" //red flag

        ltag("Encrypted text length: ${mEncryptTv.text.length}")
        assertThat(mEncryptTv.text.length, greaterThan(1))

        //click decryption button
        val mDecryptBt = mainActivity.decryptBT
        onView(withId(mDecryptBt.id)).perform(click())

        //check decryption result
        val mDecryptTv = mainActivity.decryptTV

        //mDecryptTv.text = "" //red flag

        ltag("Encrypted text length: ${mDecryptTv.text.length}")
        assertThat(mDecryptTv.text.length, greaterThan(1))

        //check apikey with decrypted text
        ltag("Api Key: ${mApiKeyEt.text}")
        ltag("Decrypted text: ${mDecryptTv.text}")

        mDecryptTv.text = "" //red flag

        assertThat(mApiKeyEt.text.toString(), equalTo(mDecryptTv.text))

    }

}