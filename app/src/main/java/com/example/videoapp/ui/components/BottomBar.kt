package com.example.videoapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.videoapp.utils.bottomNavItems

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = bottomNavItems

    //Utilized NavBar in home screen, more tabs can be added through bottomNavItems in constants file of utils
    NavigationBar {
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == item.route, //Selected tab logic
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.name)
                },
                label = { Text(text = item.name) }
            )
        }
    }
}
