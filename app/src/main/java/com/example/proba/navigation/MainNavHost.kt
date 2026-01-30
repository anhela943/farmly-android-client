package com.example.proba.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.activity.HomePage
import com.example.proba.activity.favorite.FavoriteScreenView
import com.example.proba.activity.message.MessageChatPage
import com.example.proba.activity.message.MessagePage
import com.example.proba.activity.product.ProductPageScreen
import com.example.proba.activity.product.ProducesScreenView
import com.example.proba.activity.profile.EditProductView
import com.example.proba.activity.profile.ProfilePage
import com.example.proba.activity.profile.ProfileProducerView
import com.example.proba.viewmodel.FavoritesViewModel

@Composable
fun MainNavHost(startDestination: String = MainRoutes.Home) {
    val navController = rememberNavController()
    val favoritesViewModel: FavoritesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainRoutes.Home) {
            HomePage(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainRoutes.Explore) {
            ProducesScreenView(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainRoutes.Favorite) {
            FavoriteScreenView(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainRoutes.Message) {
            MessagePage(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(MainRoutes.Profile) {
            ProfilePage(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(MainRoutes.Product) {
            ProductPageScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainRoutes.MessageChat) {
            MessageChatPage(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(MainRoutes.ProfileProducer) {
            ProfileProducerView(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainRoutes.EditProduct) {
            EditProductView(navController)
        }
    }
}
