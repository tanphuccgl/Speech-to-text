package com.smartremote.speechtotext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.smartremote.speechtotext.ui.theme.SpeechToTextTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var txt by mutableStateOf("text default")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeechToTextTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirstPage(txt)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            txt = result?.get(0).toString()
        }
    }
}

fun startAudio(activity: Activity) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
    )

    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")

    activity.startActivityForResult(intent, 101)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FirstPage(name: String) {
    val activity = LocalContext.current as Activity

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {

            Column {
                Text(
                    text = name,
                    )
                Text(
                    text = "open Speed to text!",
                    modifier = Modifier
                        .clickable { startAudio(activity) }
                )
            }

        }
    }
}
