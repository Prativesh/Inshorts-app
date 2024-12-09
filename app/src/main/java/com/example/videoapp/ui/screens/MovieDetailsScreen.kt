package com.example.videoapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.videoapp.models.dataModels.components.Movie
import com.example.videoapp.models.viewModels.SharedViewModel
import com.example.videoapp.utils.getGenresFromIds
import com.example.videoapp.utils.shareMovieLink
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(sharedViewModel: SharedViewModel, movie: Movie) {

    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val favouriteMovies = sharedViewModel.favouriteMovies.observeAsState().value
    val showToast = sharedViewModel.showToastMessage.observeAsState().value

    val genres = getGenresFromIds(movie.genreIds)
    val favouriteStatus = favouriteMovies?.any { it.id == movie.id } == true

    val favouriteIcon = if (favouriteStatus) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    // Determine rating color based on the vote_average
    val ratingColor = when {
        movie.voteAverage >= 7 -> Color.Green
        movie.voteAverage >= 5 -> Color.Yellow
        else -> Color.Red
    }

    // Fetch Favourite Movies from API
    LaunchedEffect(favouriteMovies) {
        if (favouriteMovies == null) {
            sharedViewModel.fetchFavouriteMovies()
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 6.dp)
            .heightIn(min = screenHeight),
    ) {
        IconButton(
            onClick = {
                sharedViewModel.manageFavouriteMovies(!favouriteStatus, movie)
            },
            modifier = Modifier
                .align(Alignment.End)
                .size(48.dp)
        ) {
            Icon(
                imageVector = favouriteIcon,
                contentDescription = "Bookmark",
                tint = Color.Red // Ensure white icon on black background
            )
        }

        // Movie Backdrop Image
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Movie Title
        Text(
            text = movie.title ?: "",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        // Movie Release Date
        Text(
            text = "Release Date: ${movie.releaseDate}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp)
        )

        // Movie Language
        Text(
            text = "Language: ${movie.originalLanguage ?: ""}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Movie Overview
        Text(
            text = movie.overview ?: "",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // FlowRow for genre chips
        FlowRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            genres.forEach { genre ->
                AssistChip(
                    onClick = {},
                    modifier = Modifier.padding(end = 8.dp),
                    label = { Text(text = genre, color = Color.White) }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Vote Average
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Rating: ",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ratingColor)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${
                        String.format(Locale.getDefault(), "%.1f", movie.voteAverage).toDouble()
                    }",
                    modifier = Modifier.padding(4.dp),
                    color = Color.White,
                )
            }
        }

        // Popularity
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Popularity: ",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = String.format(Locale.getDefault(), "%.1f", movie.popularity),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Deeplink Share Button
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(onClick = {
                val deepLink = sharedViewModel.createMovieDetailDeepLink(movie)
                shareMovieLink(context, deepLink)
            }) {
                Text(
                    text = "Share Movie"
                )
            }
        }
    }
}
