package com.huang.homan.encryptdecrypttool.EncryptionTool

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Ulez on 2017/9/30.
 * Email：1104128773@qq.com
 *
 * Kotlin by Homan Huang 2019/4/29
 */


class Encrypt4J : Encrypt {

    /**
     * Encrypts the given plain text
     *
     * @param plainText The plain text to aesEncrypt
     */
    @Throws(Exception::class)
    override fun aesEncrypt(plainText: ByteArray, key: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key, ALGORITHM)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(plainText)
    }

    /**
     * Decrypts the given byte array
     *
     * @param cipherText The data to aesDecrypt
     */
    @Throws(Exception::class)
    override fun aesDecrypt(cipherText: ByteArray, key: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key, ALGORITHM)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(cipherText)
    }

    companion object {

        private val ALGORITHM = "AES"
    }
}
