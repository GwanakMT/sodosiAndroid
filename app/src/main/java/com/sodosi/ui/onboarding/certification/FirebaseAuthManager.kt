package com.sodosi.ui.onboarding.certification

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sodosi.util.LogUtil
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 *  FirebaseAuthManager.kt
 *
 *  Created by Minji Jeong on 2022/02/21
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class FirebaseAuthManager(private val activity: Activity) {
    private val auth = Firebase.auth

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            LogUtil.d("minjiji: ${credential.smsCode}, $credential")

            val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("smsCode", credential.smsCode)
            clipboard.setPrimaryClip(clipData)
        }
        override fun onVerificationFailed(e: FirebaseException) {}
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Companion.verificationId = verificationId
        }
    }

    fun verifyPhoneNumber(phoneNumber: String = Companion.phoneNumber) {
        if (phoneNumber.isBlank()) Toast.makeText(activity, "phoneNumber is Blank", Toast.LENGTH_SHORT).show()

        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // +8210________
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")
    }

    fun signInWithPhoneAuthCredential(smsCode: String, listener: VerificationPhoneListener) {
        try {
            val credential = PhoneAuthProvider.getCredential(verificationId, smsCode)

            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onAuthSuccess()
                    } else {
                        listener.onAuthFail()
                    }
                }
        } catch (e: Exception) {
            listener.onAuthFail()
            LogUtil.e(e.message.toString())
        }
    }

    interface VerificationPhoneListener {
        fun onAuthSuccess()
        fun onAuthFail()
    }

    companion object {
        var phoneNumber: String = ""
        private var verificationId: String = ""
    }
}
