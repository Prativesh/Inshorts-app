package com.example.videoapp.ui.screens.tabs

import android.util.Pair
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.videoapp.ui.layouts.MovieListHorizontal
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videoapp.models.viewModels.HomeTabViewModel
import com.example.videoapp.ui.properties.AppNameHeader
import com.example.videoapp.ui.properties.StyledHeading
import com.example.videoapp.utils.checkNetworkConnectivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTab(homeTabViewModel: HomeTabViewModel = viewModel(), navController: NavController) {

    val context = LocalContext.current

    val nowPlayingMovies = homeTabViewModel.nowPlayingMovies.observeAsState().value
    val topRatedMovies = homeTabViewModel.topRatedMovies.observeAsState().value

    val errorState = homeTabViewModel.errorState.observeAsState().value

    //Check refresh state for pull down to refresh
    val isRefreshing = homeTabViewModel.isLoading.observeAsState().value

    val pullRefreshState = rememberPullToRefreshState()

    //Fetch movies from API or db
    LaunchedEffect(Unit) {
        if (topRatedMovies == null) {
            homeTabViewModel.fetchTopRatedMovies()
        }
        if (nowPlayingMovies == null) {
            homeTabViewModel.fetchNowPlayingMovies()
        }
    }

    //If both fetch failed, navigate to error screen
    if(errorState == Pair(true, true)){
        navController.navigate("error_screen")
    }

    //Used for pull down to refresh functionality
    PullToRefreshBox(
        isRefreshing = isRefreshing ?: false,
        state = pullRefreshState,
        onRefresh = { homeTabViewModel.refreshHomePage()}
    )
    {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            AppNameHeader("Inshorts")
            Spacer(modifier = Modifier.height(16.dp))

            topRatedMovies?.let {
                StyledHeading("Top-Rated Movies")
                Spacer(modifier = Modifier.height(4.dp))
                MovieListHorizontal(
                    movies = topRatedMovies,
                    fetchMoreMovies = {
                        if(checkNetworkConnectivity(context)) {
                            homeTabViewModel.fetchTopRatedMovies()
                        }
                    },
                    navController = navController
                )

                Spacer(modifier = Modifier.height(36.dp))
            }

            nowPlayingMovies?.let {
                StyledHeading("Now Playing Movies")
                Spacer(modifier = Modifier.height(4.dp))
                MovieListHorizontal(
                    movies = nowPlayingMovies,
                    fetchMoreMovies = {
                        if(checkNetworkConnectivity(context)) {
                            homeTabViewModel.fetchNowPlayingMovies()
                        }
                    },
                    navController = navController
                )
            }
        }
    }
}
