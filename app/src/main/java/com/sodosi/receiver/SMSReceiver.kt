package com.sodosi.receiver

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.telephony.SmsMessage
import com.sodosi.util.LogUtil

/**
 *  SMSReceiver.kt
 *
 *  Created by Minji Jeong on 2022/03/01
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LogUtil.d("onReceive", "SMSReceiver")
        if (intent.action.equals(INTENT_SMS_RECEIVED)) {
            val bundle = intent.extras
            bundle?.let {
                val messages = smsMessageParse(it)

                if (messages.isNotEmpty()) {
                    val content = messages[0]?.messageBody.toString()
                    val smsCode = content.replace("[^0-9]".toRegex(), "")

                    val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("smsCode", smsCode)
                    clipboard.setPrimaryClip(clipData)
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
