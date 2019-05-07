package com.huang.homan.encryptdecrypttool.EncryptionTool

interface Encrypt {
    @Throws(Exception::class)
    fun aesEncrypt(plainText: ByteArray, key: ByteArray): ByteArray

    @Throws(Exception::class)
    fun aesDecrypt(cipherText: ByteArray, key: ByteArray): ByteArray
}
