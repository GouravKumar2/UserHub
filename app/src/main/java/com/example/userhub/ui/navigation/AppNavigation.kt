package com.example.userhub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.userhub.ui.users.UsersScreen
import com.example.userhub.ui.detail.UserDetailScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "users"
    ) {

        composable("users") {
            UsersScreen(
                onUserClick = { userId ->
                    navController.navigate("user/$userId")
                }
            )
        }

        composable(
            route = "user/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                }
            )
        ) {
            UserDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}