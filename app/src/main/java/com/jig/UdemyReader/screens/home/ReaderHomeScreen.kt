package com.jig.UdemyReader.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jig.UdemyReader.components.BookCard
import com.jig.UdemyReader.components.FABContent
import com.jig.UdemyReader.components.ReaderAppBar
import com.jig.UdemyReader.components.TitleSection
import com.jig.UdemyReader.model.MBook
import com.jig.UdemyReader.navigation.ReaderScreens


@Composable
fun Home(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Yosh Reader", navController = navController)
    },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) {
        it
        //content
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController)
        }

    }
}


@Composable
fun HomeContent(navController: NavController) {
    val listOfBooks = listOf(
        MBook(id = "23", title = "title", authors = "authors", notes = "notes"),
        MBook(id = "23", title = "title", authors = "authors", notes = "notes"),
        MBook(id = "23", title = "title", authors = "authors", notes = "notes"),
        MBook(id = "23", title = "title", authors = "authors", notes = "notes"),
        MBook(id = "23", title = "title", authors = "authors", notes = "notes"),
        MBook(id = "23", title = "title", authors = "authors", notes = "notes"),
    )

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) {
        email.split("@")[0]

    } else {
        "Username"
    }
    Column(
        Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            Modifier.align(alignment = Alignment.Start)
        ) {
            TitleSection(label = "What you're reading now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        CurrentlyReadingArea(books = listOf(), navController = navController)
        TitleSection(label = "reading list")
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {
        //Todo: oncardclicked navigate
    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            BookCard(book) {
                onCardPressed(it)
            }
        }

    }
}


@Composable
fun CurrentlyReadingArea(
    books: List<MBook>,
    navController: NavController
) {
    BookCard()
}

