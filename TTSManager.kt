
package com.jarvis.app

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

object TTSManager {

    private var tts: TextToSpeech? = null

    fun speak(context: Context, text: String) {
        if (tts == null) {
            tts = TextToSpeech(context) {
                tts?.language = Locale.US
                tts?.setPitch(0.9f)
                tts?.setSpeechRate(1.0f)
            }
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
