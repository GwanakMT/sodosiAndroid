package com.sodosi.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage

/**
 *  SMSReceiver.kt
 *
 *  Created by Minji Jeong on 2022/03/01
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(INTENT_SMS_RECEIVED)) {
            val bundle = intent.extras
            bundle?.let {
                val messages = smsMessageParse(it)

                if (messages.isNotEmpty()) {
                    val content = messages[0]?.messageBody.toString()
                    val certNumber = content.replace("[^0-9]".toRegex(), "")

                    // TODO: 인증번호(certNumber) 전역에 저장 or 클립보드에 복사
                }
            }
        }
    }

    private fun smsMessageParse(bundle: Bundle): Array<SmsMessage?> {
        val objs = bundle["pdus"] as Array<*>
        val messages: Array<SmsMessage?> = arrayOfNulls(objs.size)
        for (i in objs.indices) {
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
        }

        return messages
    }

    companion object {
        private const val INTENT_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}
