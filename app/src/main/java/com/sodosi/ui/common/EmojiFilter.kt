package com.sodosi.ui.common

import android.text.InputFilter
import android.text.Spanned

/**
 *  EmojiFilter.kt
 *
 *  Created by Minji Jeong on 2022/04/04
 *  Copyright © 2022 GwanakMT All rights reserved.
 */

class EmojiFilter : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        for (i in start until end) {
             val type = Character.getType(source[i]).toByte()
            if (type != Character.SURROGATE && type != Character.OTHER_SYMBOL) {
                return ""
            }
        }

        return null
    }
}
