package com.jig.UdemyReader.screens.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jig.UdemyReader.components.InputField
import com.jig.UdemyReader.components.ReaderAppBar
import com.jig.UdemyReader.model.Item
import com.jig.UdemyReader.model.MBook
import com.jig.UdemyReader.navigation.ReaderScreens


@Composable
fun Search(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) { it
        Surface {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    viewModel
                ){ query ->
                    viewModel.searchBooks(query)
                }
                Spacer(modifier = Modifier.height(13.dp))
                SearchedBookList(navController, viewModel)
            }
        }
    }

}

@Composable
fun SearchedBookList(navController: NavController,
                     viewModel: SearchViewModel = hiltViewModel()) {


    val listOfBooks = viewModel.listOfBooks

    LazyColumn(modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp)){
        items(listOfBooks){ book ->
            BookRow(book, navController)
        }
    }
}



@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {
        Row(modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.Top){
            val imageUrl: String = book.volumeInfo.imageLinks.thumbnail.ifEmpty {
                "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            }
            Image(painter = rememberImagePainter(data = imageUrl ),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Column() {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
                Text(text = "Authors: ${ book.volumeInfo.authors }", overflow = TextOverflow.Clip, style = MaterialTheme.typography.caption)
                //todo: more fields later
            }
        }
        
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
){
    Column {
        val searchQueryState = rememberSaveable{ mutableStateOf("")}
        val keyboardController = LocalSoftwareKeyboardController.current
        val isValid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        
        InputField(valueState = searchQueryState,
            labelId = "search",
            enabled = true,
            onAction = KeyboardActions {
                if(!isValid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}