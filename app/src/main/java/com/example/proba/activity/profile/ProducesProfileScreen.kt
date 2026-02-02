package com.example.proba.activity.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proba.R
import com.example.proba.model.ProductUi
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.Resource
import com.example.proba.viewmodel.MyProductsViewModel

@Composable
fun EditProductView(
    navController: NavController,
    myProductsViewModel: MyProductsViewModel
) {
    val productsState by myProductsViewModel.products.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgorund),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.25f },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        colorResource(R.color.greenBackground),
                        RoundedCornerShape(topStart = 140.dp, topEnd = 140.dp)
                    )
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.size(30.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "My Products",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.darkGreenTxt)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    when (val state = productsState) {
                        is Resource.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = colorResource(R.color.darkGreenTxt)
                                )
                            }
                        }
                        is Resource.Error -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = state.message,
                                    color = Color.Red,
                                    fontSize = 14.sp
                                )
                                Button(
                                    onClick = { myProductsViewModel.loadMyProducts() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.darkGreenTxt)
                                    )
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                        is Resource.Success -> {
                            if (state.data.isEmpty()) {
                                Text(
                                    text = "No products yet. Add your first product!",
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.darkGreenTxt),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 32.dp),
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                state.data.forEach { item ->
                                    val product = ProductUi.fromApi(item)
                                    EditProductCard(
                                        productName = product.name,
                                        price = String.format("%.2f RSD", product.price),
                                        producer = product.producer,
                                        rating = product.producerReview?.toString() ?: "-",
                                        imageProductUrl = product.imageUrl,
                                        imageProducerUrl = product.producerImageUrl,
                                        onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = { navController.navigate(MainRoutes.ProductAdd) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .height(48.dp)
                    .shadow(18.dp, RoundedCornerShape(14.dp), clip = false),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.darkGreenTxt),
                    contentColor = colorResource(R.color.white)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(text = "Add product", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 26.dp, top = 74.dp)
                .size(30.dp)
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f)
            )
        }
    }
}

@Composable
private fun EditProductCard(
    productName: String,
    price: String,
    producer: String,
    rating: String,
    imageProductUrl: String,
    imageProducerUrl: String?,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            4.dp,
            Brush.verticalGradient(
                listOf(
                    colorResource(R.color.greenStrokeLight),
                    colorResource(R.color.greenStrokeDark)
                )
            )
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = imageProductUrl,
                    contentDescription = productName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = productName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.darkGreenTxt)
                )
                Text(
                    text = price,
                    fontSize = 15.sp,
                    color = colorResource(R.color.darkGreenTxt)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (imageProducerUrl != null) {
                            AsyncImage(
                                model = imageProducerUrl,
                                contentDescription = "Producer",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.user),
                                contentDescription = "Producer",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = producer,
                                fontSize = 12.sp,
                                color = colorResource(R.color.darkGreenTxt)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = "Star",
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = rating,
                                    fontSize = 12.sp,
                                    color = colorResource(R.color.darkGreenTxt)
                                )
                            }
                        }
                    }

                    EditIconButton(onClick = onEdit)
                }
            }
        }
    }
}

@Composable
private fun EditIconButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(38.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.edit),
            contentDescription = "Edit",
            modifier = Modifier.size(30.dp)
        )
    }
}

