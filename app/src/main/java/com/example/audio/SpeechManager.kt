package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.data.AlphabetItem
import java.util.Locale

/**
 * Manages native Android TextToSpeech and audio cues for toddlers in ABCoo.
 * Uses a warm, friendly pitch and speech rate suited for early childhood education.
 */
class SpeechManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var toneGenerator: ToneGenerator? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
        } catch (e: Exception) {
            Log.e("SpeechManager", "Error initializing TTS or audio", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            setupVoiceParameters()
        } else {
            Log.w("SpeechManager", "TextToSpeech init failed with status: $status")
        }
    }

    private fun setupVoiceParameters() {
        tts?.let { engine ->
            engine.language = Locale.US
            // Slightly higher pitch and relaxed speech rate makes the voice warm & kid-friendly
            engine.setPitch(1.25f)
            engine.setSpeechRate(0.85f)
        }
    }

    /**
     * Pronounces the alphabet letter cleanly with high clarity.
     */
    fun speakLetter(uppercase: String, lowercase: String) {
        playPopSound()
        speakText("Letter $uppercase. Lowercase $lowercase.")
    }

    /**
     * Speaks the phonetic sound sequence: Letter name, sound, and word.
     */
    fun speakPhonicsSequence(item: AlphabetItem, includeBangla: Boolean = false) {
        playChimeSound()
        val speechText = if (includeBangla) {
            "${item.uppercase}! ${item.word}! In Bangla, ${item.banglaWord}."
        } else {
            "${item.uppercase} says ${item.phonics.substringBefore(" as in")}. ${item.word}!"
        }
        speakText(speechText)
    }

    /**
     * Speaks the object word with enthusiastic toddler-friendly praise.
     */
    fun speakWord(item: AlphabetItem, includeBangla: Boolean = false) {
        playPopSound()
        val speechText = if (includeBangla) {
            "${item.word}. ${item.funFact} Bangla: ${item.banglaWord}"
        } else {
            "${item.word}! ${item.funFact}"
        }
        speakText(speechText)
    }

    /**
     * Plays encouraging praise for learning and games.
     */
    fun speakEncouragement() {
        playSuccessChime()
        val praises = listOf(
            "Great job!",
            "Super star!",
            "Hooray, you found it!",
            "Wonderful learning!",
            "You did it!",
            "Awesome!"
        )
        speakText(praises.random())
    }

    /**
     * Speaks a quiz prompt for finding a letter.
     */
    fun speakQuizPrompt(item: AlphabetItem) {
        playPopSound()
        speakText("Can you tap the letter ${item.uppercase} for ${item.word}?")
    }

    private fun speakText(text: String) {
        if (!isInitialized || tts == null) return
        try {
            val params = android.os.Bundle()
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "abcoo_utterance_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.e("SpeechManager", "Failed to speak text: $text", e)
        }
    }

    fun playPopSound() {
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
        } catch (e: Exception) {
            // Non-critical audio feedback
        }
    }

    fun playChimeSound() {
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 100)
        } catch (e: Exception) {
            // Non-critical audio feedback
        }
    }

    fun playSuccessChime() {
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 180)
        } catch (e: Exception) {
            // Non-critical audio feedback
        }
    }

    fun stop() {
        try {
            tts?.stop()
        } catch (e: Exception) {
            // Ignored
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            toneGenerator?.release()
        } catch (e: Exception) {
            // Ignored
        }
    }
}
