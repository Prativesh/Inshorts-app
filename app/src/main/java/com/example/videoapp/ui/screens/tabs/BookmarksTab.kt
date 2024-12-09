package com.example.videoapp.ui.screens.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.videoapp.models.viewModels.SharedViewModel
import com.example.videoapp.ui.layouts.MovieListVertical
import com.example.videoapp.utils.checkNetworkConnectivity

@Composable
fun BookmarksTab(sharedViewModel: SharedViewModel, navController: NavController) {

    val context = LocalContext.current
    val movies = sharedViewModel.favouriteMovies.observeAsState().value

    //Fetch movies from API or db
    LaunchedEffect(Unit) {
        if(movies == null) {
            sharedViewModel.fetchFavouriteMovies()
        }
    }

    if (movies.isNullOrEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if(!checkNetworkConnectivity(context)) {
                    //If no Favourite movies and no internet show internet error as Favourites are managed through APIs
                    "No Internet Connection"
                    } else {
                        "No bookmarks yet"
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    } else {
        MovieListVertical(
            movies = movies,
            fetchMoreMovies = { sharedViewModel.fetchFavouriteMovies() },
            navController = navController
        )
    }
}