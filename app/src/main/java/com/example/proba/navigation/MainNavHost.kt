package com.example.proba.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.activity.HomePage
import com.example.proba.activity.favorite.FavoriteScreenView
import com.example.proba.activity.message.MessageChatPage
import com.example.proba.activity.message.MessagePage
import com.example.proba.activity.product.ProductPageScreen
import com.example.proba.activity.product.ProductAddView
import com.example.proba.activity.product.ProductEditView
import com.example.proba.activity.product.ProducesScreenView
import com.example.proba.activity.profile.EditProductView
import com.example.proba.activity.profile.ProfileCreateView
import com.example.proba.activity.profile.ProfilePage
import com.example.proba.activity.profile.ProfileProducerView
import com.example.proba.activity.profile.ReviewPageView
import com.example.proba.util.TokenManager
import com.example.proba.viewmodel.ChatInfoViewModel
import com.example.proba.viewmodel.ChatMessagesViewModel
import com.example.proba.viewmodel.ChatsViewModel
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.viewmodel.ProfileViewModel

@Composable
fun MainNavHost(startDestination: String = MainRoutes.Home, onLogout: () -> Unit = {}) {
    val navController = rememberNavController()
    val favoritesViewModel: FavoritesViewModel = viewModel()
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.Factory(TokenManager(context.applicationContext))
    )
    val chatsViewModel: ChatsViewModel = viewModel()

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
                onBackClick = { navController.popBackStack() },
                chatsViewModel = chatsViewModel
            )
        }
        composable(MainRoutes.Profile) {
            ProfilePage(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                profileViewModel = profileViewModel,
                onLogout = onLogout
            )
        }
        composable(MainRoutes.ProfileCreate) {
            ProfileCreateView(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                profileViewModel = profileViewModel
            )
        }
        composable(MainRoutes.Product) {
            ProductPageScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(MainRoutes.ProductAdd) {
            ProductAddView(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(MainRoutes.ProductEdit) {
            ProductEditView(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            MainRoutes.MessageChat,
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val tokenManager = TokenManager(context.applicationContext)
            val token = tokenManager.getTokenSync() ?: ""
            val chatInfoViewModel: ChatInfoViewModel = viewModel(
                factory = ChatInfoViewModel.Factory(chatId)
            )
            val chatMessagesViewModel: ChatMessagesViewModel = viewModel(
                factory = ChatMessagesViewModel.Factory(chatId, token)
            )
            MessageChatPage(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                chatInfoViewModel = chatInfoViewModel,
                chatMessagesViewModel = chatMessagesViewModel
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
        composable(MainRoutes.ReviewPage) {
            ReviewPageView(navController = navController)
        }
    }
}
