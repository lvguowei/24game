package com.guowei.twentyfourgame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random


class MainActivity : ComponentActivity() {

    private lateinit var bgMusicServiceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bgMusicServiceIntent = Intent(this, BackgroundSoundService::class.java)

        setContent {
            val deck = remember {
                Type.entries
                    .flatMap { type -> (1..13).map { value -> CardModel(type, value) } }
                    .toMutableStateList()
            }

            var started by remember {
                mutableStateOf(false)
            }

            Box {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.bg),
                    contentDescription = ""
                )
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!started) {
                        Image(
                            modifier = Modifier
                                .size(300.dp, 300.dp),
                            contentScale = ContentScale.FillHeight,
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.presents),
                            contentDescription = ""
                        )

                    }
                    Cards(Modifier.weight(1f), deck, started)
                    Buttons(started,
                        onShuffleClicked = {
                            deck.shuffle()
                            if (!started) {
                                this@MainActivity.stopService(bgMusicServiceIntent)
                            }
                            started = true
                            MediaPlayer.create(this@MainActivity, R.raw.coin).also {
                                it.start()
                            }
                        },
                        onAnswerClicked = {
                            val ans = twentyFour(deck.subList(0, 4).map { it.value }).firstOrNull()
                            Toast.makeText(
                                this@MainActivity,
                                ans?.toString() ?: "No solution!", Toast.LENGTH_LONG
                            ).show()
                            MediaPlayer.create(this@MainActivity, R.raw.answer).also {
                                it.start()
                            }
                        })
                }
            }
        }

        startService(bgMusicServiceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(bgMusicServiceIntent)
    }

    @Composable
    private fun Buttons(
        started: Boolean,
        onShuffleClicked: () -> Unit,
        onAnswerClicked: () -> Unit
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, bottom = 40.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = onShuffleClicked
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 40.dp),
                    fontSize = 20.sp,
                    text = if (started) "Shuffle" else "Start",
                )
            }
            if (started) {
                Button(onClick = onAnswerClicked) {
                    Text(modifier = Modifier.padding(8.dp), text = "Answer")
                }
            }
        }
    }
}

@Composable
fun Card(card: CardModel, rotation: Float, started: Boolean) {
    if (started) {
        Image(
            modifier = Modifier
                .size(160.dp, 200.dp)
                .padding(20.dp)
                .rotate(rotation),
            painter = painterResource(card.getImage()),
            contentDescription = "",
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
fun Cards(modifier: Modifier = Modifier, cards: List<CardModel>, started: Boolean) {
    Column(
        modifier = modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Row {
            Card(cards[0], (Random.nextFloat() - 0.5f) * 50, started)
            Card(cards[1], (Random.nextFloat() - 0.5f) * 50, started)
        }
        Row {
            Card(cards[2], (Random.nextFloat() - 0.5f) * 50, started)
            Card(cards[3], (Random.nextFloat() - 0.5f) * 50, started)
        }
    }
}
