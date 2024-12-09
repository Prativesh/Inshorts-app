package com.example.videoapp.ui.screens.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.videoapp.models.viewModels.SharedViewModel
import com.example.videoapp.ui.layouts.MovieListVertical
import com.example.videoapp.utils.checkNetworkConnectivity
import kotlinx.coroutines.delay

@Composable
fun SearchTab(sharedViewModel: SharedViewModel, navController: NavController) {

    val context = LocalContext.current

    val movies = sharedViewModel.searchedMovies.observeAsState().value
    val savedQuery = sharedViewModel.savedQuery.value
    val queryState = remember { mutableStateOf(TextFieldValue(savedQuery ?: "")) }

    // Track the query input with a debounced effect
    var debounceQuery by remember { mutableStateOf("") }

    fun onSearch(query: String, isNewSearch: Boolean) {
        sharedViewModel.fetchSearchedMovies(query, isNewSearch)
    }

    // Run whenever queryState changes and delay the search until user has stopped typing for 1 second
    LaunchedEffect(queryState.value.text) {
        debounceQuery = queryState.value.text
        delay(100)
        if (debounceQuery == queryState.value.text) {
            onSearch(queryState.value.text, true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Row for searchbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = queryState.value,
                onValueChange = { queryState.value = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(queryState.value.text, true)
                    }
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 0.dp)
            )

            IconButton(onClick = {
                onSearch(queryState.value.text, true)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }

        // Show "No Internet Connection" message if internet is unavailable
        if (!checkNetworkConnectivity(context)) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No Internet Connection",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Movies List
        movies?.let {
            MovieListVertical(
                movies = movies,
                fetchMoreMovies = { onSearch(queryState.value.text, false) },
                navController = navController
            )
        }
    }
}
