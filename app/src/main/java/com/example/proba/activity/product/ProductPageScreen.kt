package com.example.proba.activity.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import com.example.proba.data.repository.ChatRepository
import com.example.proba.model.ProductUi
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.Resource
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.viewmodel.ProductDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun ProductPageScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    productDetailViewModel: ProductDetailViewModel
) {
    val productState by productDetailViewModel.productState.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val chatRepository = remember { ChatRepository() }

    var isCreatingChat by remember { mutableStateOf(false) }
    var chatCreationError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            bottomBarView(navController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = productState) {
                is Resource.Success -> {
                    val data = state.data
                    val product = ProductUi(
                        id = data.id,
                        name = data.name,
                        price = data.price,
                        producer = data.producer.fullName,
                        producerReview = data.producer.overallReview.overallReview,
                        city = data.producer.city,
                        imageUrl = data.imageUrl,
                        producerId = data.producer.id
                    )
                    ProductPageView(
                        productName = data.name,
                        price = data.price,
                        producerName = data.producer.fullName,
                        review = data.producer.overallReview.overallReview ?: 0.0,
                        city = data.producer.city,
                        description = data.description,
                        imageUrl = data.imageUrl,
                        producerImageUrl = data.producer.imageUrl,
                        isFavorite = favoriteIds.contains(product.id),
                        onBackClick = { navController.popBackStack() },
                        onProducerClick = {
                            navController.navigate(MainRoutes.profileProducerRoute(data.producer.id))
                        },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product) },
                        isContactLoading = isCreatingChat,
                        onContactProducerClick = {
                            coroutineScope.launch {
                                isCreatingChat = true
                                chatCreationError = null
                                try {
                                    val producerId = data.producer.id.toIntOrNull()
                                    if (producerId == null) {
                                        chatCreationError = "Invalid producer ID"
                                        isCreatingChat = false
                                        return@launch
                                    }
                                    val productId = data.id.toIntOrNull()
                                    if (productId == null) {
                                        chatCreationError = "Invalid product ID"
                                        isCreatingChat = false
                                        return@launch
                                    }

                                    when (val result = chatRepository.createChat(producerId, productId)) {
                                        is Resource.Success -> {
                                            navController.navigate(
                                                MainRoutes.messageChatRoute(
                                                    chatId = result.data.id,
                                                    initialMessage = "Hello, is this product still available?"
                                                )
                                            )
                                        }
                                        is Resource.Error -> {
                                            chatCreationError = result.message
                                        }
                                        is Resource.Loading -> {}
                                    }
                                } finally {
                                    isCreatingChat = false
                                }
                            }
                        }
                    )
                }
                is Resource.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }
                }
            }
        }
    }
}
