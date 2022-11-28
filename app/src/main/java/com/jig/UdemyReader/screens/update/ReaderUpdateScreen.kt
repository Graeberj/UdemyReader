package com.jig.UdemyReader.screens.update

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jig.UdemyReader.components.ReaderAppBar
import com.jig.UdemyReader.data.DataOrException
import com.jig.UdemyReader.model.MBook
import com.jig.UdemyReader.screens.home.HomeViewModel

@Composable
fun UpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeViewModel = hiltViewModel()){
    Scaffold(topBar = { ReaderAppBar(
            title = "Update Book",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController){
        navController.popBackStack()
    } }
    ) { it
        
        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean,
                Exception>>(initialValue = DataOrException(
            data = emptyList(), true, Exception(""))){
                    value = viewModel.data.value
        }.value

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
        ) {
            Column(modifier = Modifier.padding(top = 3.dp),
            verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Log.d("INFO", "UpdateScreen: ${viewModel.data.value}")
                if (bookInfo.loading == true){
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Surface(modifier = Modifier.padding(2.dp)
                        .fillMaxWidth(),
                    shape = CircleShape,
                    elevation = 4.dp){
                        ShowUpdate(bookInfo = viewModel.data.value, bookItemId)

                    }
                }

            }
        }
    }
}

@Composable
fun ShowUpdate(bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
               bookItemId: String
) {
    Row(){
        Spacer(Modifier.width(43.dp))
    }
}
