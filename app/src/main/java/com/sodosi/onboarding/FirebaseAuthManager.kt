package com.sodosi.onboarding

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
        override fun onVerificationFailed(e: FirebaseException) {}
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Companion.verificationId = verificationId
        }
    }

    fun verifyPhoneNumber(phoneNumber: String = Companion.phoneNumber) {
        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // +8210________
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")

        Companion.phoneNumber = phoneNumber
    }

    fun signInWithPhoneAuthCredential(smsCode: String, listener: VerificationPhoneListener) {
        val credential = PhoneAuthProvider.getCredential(verificationId, smsCode)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    listener.onAuthSuccess()
                } else {
                    listener.onAuthFail()
                }
            }
    }

    interface VerificationPhoneListener {
        fun onAuthSuccess()
        fun onAuthFail()
    }

    companion object {
        private var phoneNumber: String = ""
        private var verificationId: String = ""
    }
}
