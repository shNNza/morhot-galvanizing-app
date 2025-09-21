package com.morhot.galvanizing.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.morhot.galvanizing.ui.screens.jobcard.JobCardListScreen
import com.morhot.galvanizing.ui.screens.jobcard.JobCardCreateScreen
import com.morhot.galvanizing.ui.screens.jobcard.JobCardDetailScreen
import com.morhot.galvanizing.ui.screens.login.LoginScreen

@Composable
fun MorhotNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.JobCardList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.JobCardList.route) {
            JobCardListScreen(
                onNavigateToCreate = {
                    navController.navigate(Screen.JobCardCreate.route)
                },
                onNavigateToDetail = { jobCardId ->
                    navController.navigate(Screen.JobCardDetail.createRoute(jobCardId))
                }
            )
        }
        
        composable(Screen.JobCardCreate.route) {
            JobCardCreateScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onJobCardCreated = { jobCardId ->
                    navController.navigate(Screen.JobCardDetail.createRoute(jobCardId)) {
                        popUpTo(Screen.JobCardList.route)
                    }
                }
            )
        }
        
        composable(
            route = Screen.JobCardDetail.route,
            arguments = Screen.JobCardDetail.arguments
        ) { backStackEntry ->
            val jobCardId = backStackEntry.arguments?.getString("jobCardId") ?: ""
            JobCardDetailScreen(
                jobCardId = jobCardId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}