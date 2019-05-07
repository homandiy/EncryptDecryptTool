package com.huang.homan.encryptdecrypttool.EncryptionTool

/**
 * Created by Ulez on 2017/9/30.
 * Email：1104128773@qq.com
 *
 * Kotlin by Homan Huang 2019/4/29
 */


class Encrypt4C : Encrypt {

    @Throws(Exception::class)
    override fun aesEncrypt(plainText: ByteArray, key: ByteArray): ByteArray {
        return AES_ECB_encrypt_byte(plainText, key)
    }

    @Throws(Exception::class)
    override fun aesDecrypt(cipherText: ByteArray, key: ByteArray): ByteArray {
        return AES_ECB_decrypt_byte(cipherText, key)
    }

    // check JNI functions in native-lib

    @Throws(Exception::class)
    external fun AES_ECB_encrypt_byte(origin: ByteArray, key: ByteArray): ByteArray

    @Throws(Exception::class)
    external fun AES_ECB_decrypt_byte(origin: ByteArray, key: ByteArray): ByteArray

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
