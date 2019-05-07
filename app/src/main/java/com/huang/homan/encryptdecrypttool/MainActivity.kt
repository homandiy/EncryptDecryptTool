package com.huang.homan.encryptdecrypttool

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import com.huang.homan.encryptdecrypttool.EncryptionTool.Encrypt4C
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    // Encryption Variable
    private var encrypt4C: Encrypt4C? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        encrypt4C = Encrypt4C()

        encryptBT.setOnClickListener {
            val text = passKeyET.text.toString()
            if (text.length < 13) {
                toast("Pass key must have more or equal than 13 characters.")
            } else {
                encrypt()
            }
        }
        decryptBT.setOnClickListener { decrypt() }
    }

    // decrypt cipher string with passcode
    private fun decrypt() {
        try {
            val passKey = passKeyET!!.text.toString()
            val encryptionKey = puzzleYourKey(passKey).toByteArray(StandardCharsets.UTF_8)

            val apiText = encryptTV!!.text.toString()
            lge("apiText: $apiText")
            val apiEncrypted = apiText.toByteArray()

            // decode Base64 first
            val result64 = Base64.decode(apiEncrypted, Base64.DEFAULT)
            // decrypt AES
            val aes = encrypt4C!!.AES_ECB_decrypt_byte(result64, encryptionKey)

            //show off
            val result = String(aes)
            lge("NDK decrypted result = $result")
            decryptTV!!.text = result
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    // encrypt api key with passcode
    private fun encrypt() {
        try {
            // get text values
            val apiKey = apiKeyET!!.text.toString()
            val passKey = passKeyET!!.text.toString()

            lge("Before encryption, API key = $apiKey")
            lge("Before encryption，Pass key = $passKey")

            // convert to byte[]
            val origin = apiKey.toByteArray(StandardCharsets.UTF_8)
            val encryptionKey = puzzleYourKey(passKey).toByteArray(StandardCharsets.UTF_8)

            // AES-ECB + Base64
            val bytes_result = encrypt4C!!.AES_ECB_encrypt_byte(origin, encryptionKey)
            val bytes_result_base64 = Base64.encode(bytes_result, Base64.DEFAULT)

            // Show off
            val apiEncrypted = String(bytes_result_base64)
            lge("After encryption, apiText = $apiEncrypted")
            encryptTV!!.text = apiEncrypted

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /**
     * This function will take even characters from passKey +1 and
     * keep odd characters same to form a new key.
     * The key must > 12 characters.
     */
    private fun puzzleYourKey(passKey: String): String {
        lge("Input string: $passKey")

        val charArray = passKey.toCharArray()
        val builder = StringBuilder("")
        for (i in charArray.indices) {
            if (i %2 == 0) {
                builder.append(charArray[i]+1)
            } else {
                builder.append(charArray[i])
            }
        }
        lge("Puzzle string: ${builder.toString()}")
        return builder.toString()
    }


    companion object {

        /* Log tag and shortcut */
        private val TAG = "MYLOG " + MainActivity::class.java.simpleName

        fun lge(s: String) { Log.e(TAG, s) }

    }
}
