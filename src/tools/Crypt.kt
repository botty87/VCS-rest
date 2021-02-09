package com.vcs.tools

import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Crypt {
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    private const val KEY = "~6({2JS(,YK~p7EgE6`99>xm;]zY]n[R"

    fun encrypt(str:String):String{
        val encrypted = cipher(Cipher.ENCRYPT_MODE).doFinal(str.toByteArray(Charsets.UTF_8))
        return String(encoder.encode(encrypted))
    }

    fun decrypt(str:String):String{
        val byteStr = decoder.decode(str.toByteArray(Charsets.UTF_8))
        return String(cipher(Cipher.DECRYPT_MODE).doFinal(byteStr))
    }

    private fun cipher(opmode:Int):Cipher{
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(KEY.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(KEY.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opmode, sk, iv)
        return c
    }

    fun createRandomToken() : String {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(32) { alphabet.random() }.joinToString("")
    }

    private fun hash(value: String): String {
        val bytes = value.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun checkHash(hash: String, stringToCheck: String): Boolean {
        fun decodeAndCompare(nowDateTime: LocalDateTime): Boolean {
            val passwordToHash = stringToCheck + nowDateTime.minute.toString().padStart(2, '0')
            val hashToCheck = hash(passwordToHash)
            return hashToCheck == hash
        }

        val nowDateTime = LocalDateTime.now(ZoneOffset.UTC)
        return if(decodeAndCompare(nowDateTime)) {
            true
        } else {
            decodeAndCompare(nowDateTime.minusMinutes(1))
        }
    }
}