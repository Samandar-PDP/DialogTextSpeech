package uz.digital.dialogs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import java.util.*
import kotlin.collections.ArrayList

class SecondActivity : AppCompatActivity() {
    private lateinit var appCompatImageButton: AppCompatImageButton
    private lateinit var textSpeech: AppCompatTextView
    private lateinit var btnSpeak: AppCompatButton
    private lateinit var editText: AppCompatEditText
    private var textToSpeech: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        appCompatImageButton = findViewById(R.id.btnVoice)
        textSpeech = findViewById(R.id.textVoice)

        editText = findViewById(R.id.editText)
        btnSpeak = findViewById(R.id.btnSpeak)

        appCompatImageButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
            try {
                startActivityForResult(intent, 100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnSpeak.setOnClickListener {
            val text = editText.text.toString().trim()
            speakOut(text)
        }
    }

    private fun speakOut(text: String) {
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.UK
                textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null) {
            val result: ArrayList<String> =
                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ?: arrayListOf()
            textSpeech.text = result[0]
        }
    }
}