package com.morhot.galvanizing.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Login : Screen("login")
    
    object JobCardList : Screen("job_card_list")
    
    object JobCardCreate : Screen("job_card_create")
    
    object JobCardDetail : Screen(
        route = "job_card_detail/{jobCardId}",
        arguments = listOf(
            navArgument("jobCardId") {
                type = NavType.StringType
            }
        )
    ) {
        fun createRoute(jobCardId: String) = "job_card_detail/$jobCardId"
    }
}