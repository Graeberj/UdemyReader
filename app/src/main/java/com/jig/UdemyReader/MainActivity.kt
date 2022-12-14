package com.jig.UdemyReader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jig.UdemyReader.navigation.ReaderNavigation
import com.jig.UdemyReader.ui.theme.UdemyReaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UdemyReaderTheme {
                ReaderApp()
            }
        }
    }
}

@Composable
fun ReaderApp(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            ReaderNavigation()
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UdemyReaderTheme {
    }
}