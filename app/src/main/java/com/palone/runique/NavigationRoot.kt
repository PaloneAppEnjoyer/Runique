package com.palone.runique

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.palone.auth.presentation.intro.IntroScreenRoot
import com.palone.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "auth") {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(startDestination = "intro", route = "auth") {
        composable("intro") {
            IntroScreenRoot(
                onSignupClick = { navController.navigate("register") },
                onSignInClick = { navController.navigate("login") })
        }
        composable("register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = { navController.navigate("login") })
        }
        composable("login") {
            Text("Login Screen")
        }

    }


}