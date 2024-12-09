package com.example.videoapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.videoapp.models.dataModels.components.Movie
import com.example.videoapp.models.viewModels.SharedViewModel
import com.example.videoapp.ui.components.BottomNavigationBar
import com.example.videoapp.ui.screens.ErrorScreen
import com.example.videoapp.ui.screens.MovieDetailsScreen
import com.example.videoapp.ui.screens.tabs.BookmarksTab
import com.example.videoapp.ui.screens.tabs.HomeTab
import com.example.videoapp.ui.screens.tabs.SearchTab
import com.example.videoapp.utils.bottomNavItems
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun MyApp(navController: NavHostController = rememberNavController()) {

    val sharedViewModel: SharedViewModel = viewModel()

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryFlow
                .collectAsState(initial = null).value?.destination?.route

            if (bottomNavItems.any { it.route == currentRoute }) {
                BottomNavigationBar(navController)
            }
        },

        content = { paddingValues ->
            NavHost(navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues) //Adjustment for Device's NavBar

            ) {
                composable(
                    route = "home",
                    enterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popEnterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    exitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popExitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    ) {
                    HomeTab(navController = navController)
                }

                composable(
                    route = "search",
                    enterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popEnterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    exitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popExitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},) {
                    SearchTab(sharedViewModel, navController = navController)
                }

                composable(
                    route ="bookmarks",
                    enterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popEnterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    exitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popExitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )}) {
                    BookmarksTab(sharedViewModel, navController = navController)
                }

                composable(
                    route ="movie_detail/{movieJson}",
                    enterTransition = { slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    popExitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    exitTransition = { slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                    )},
                    arguments = listOf(navArgument("movieJson") { type = NavType.StringType }),
                    deepLinks = listOf(
                        navDeepLink {
                            uriPattern = "https://www.dummy-deep-link.com/movie_detail/{movieJson}"
                        }
                    )) { backStackEntry ->
                    val movieJson = backStackEntry.arguments?.getString("movieJson")
                    val decodedJson = URLDecoder.decode(movieJson, StandardCharsets.UTF_8.toString())
                    val movie = Gson().fromJson(decodedJson, Movie::class.java)
                    MovieDetailsScreen(sharedViewModel, movie = movie)
                }

                composable("error_screen") {
                    ErrorScreen(navController = navController)
                }
            }
        }
    )
}