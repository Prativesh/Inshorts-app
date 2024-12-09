package com.example.videoapp.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.videoapp.models.dataModels.components.Movie
import com.example.videoapp.ui.components.MovieCardVertical
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MovieListHorizontal(movies: List<Movie?>?, fetchMoreMovies: () -> Unit, navController: NavController) {

    val lastIndex = movies?.lastIndex ?: 0

    //Lazy row for lazy loading of movies
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        movies?.let {
            val gson = Gson()
            itemsIndexed(movies) { index, movie ->
                if(lastIndex-index == 5){
                    fetchMoreMovies()
                }
                movie?.let { _ ->
                    MovieCardVertical(
                        posterPath = movie.posterPath,
                        title = movie.title,
                        releaseDate = movie.releaseDate,
                        language = movie.originalLanguage,
                        onClick = {
                            val movieJson = gson.toJson(movie)
                            val encodedJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())

                            /*Pass whole movie as encoded json so that MovieDetails screen does
                            not need to make any API call*/
                            navController.navigate("movie_detail/$encodedJson")
                        }
                    )
                }
            }
        }
    }
}