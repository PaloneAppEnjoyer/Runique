package com.palone.runique

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.palone.auth.presentation.intro.IntroScreenRoot
import com.palone.auth.presentation.login.LoginScreenRoot
import com.palone.auth.presentation.register.RegisterScreenRoot
import com.palone.run.presentation.active_run.ActiveRunScreenRoot
import com.palone.run.presentation.run_overview.RunOverviewScreenRot

@Composable
fun NavigationRoot(navController: NavHostController, isLoggedIn: Boolean) {
    NavHost(navController = navController, startDestination = if (isLoggedIn) "run" else "auth") {
        authGraph(navController)
        runGraph(navController)
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
            LoginScreenRoot(onLoginSuccess = {
                navController.navigate("run") {
                    popUpTo("auth") {
                        inclusive = true
                    }
                }
            }, onSignUpClick = {
                navController.navigate("register") {
                    popUpTo("login") {
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                }
            })
        }

    }
}

private fun NavGraphBuilder.runGraph(navController: NavHostController) {
    navigation(startDestination = "run_overview", route = "run") {
        composable("run_overview") {
            RunOverviewScreenRot(onStartRunClick = { navController.navigate("active_run") })
        }
        composable("active_run") {
            ActiveRunScreenRoot()
        }
    }
}