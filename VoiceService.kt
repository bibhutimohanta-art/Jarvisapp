
package com.jarvis.app

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.speech.*
import java.util.*

class VoiceService : Service() {

    private lateinit var recognizer: SpeechRecognizer

    override fun onCreate() {
        super.onCreate()

        recognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        recognizer.setRecognitionListener(object : RecognitionListener {

            override fun onResults(results: Bundle) {
                val text = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.get(0) ?: ""

                TTSManager.speak(this@VoiceService, "You said $text")
                restart(intent)
            }

            override fun onError(error: Int) {
                restart(intent)
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        recognizer.startListening(intent)
    }

    private fun restart(intent: Intent) {
        recognizer.stopListening()
        recognizer.startListening(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
