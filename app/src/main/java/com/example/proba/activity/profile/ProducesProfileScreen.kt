package com.example.proba.activity.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proba.navigation.MainRoutes

@Composable
fun EditProductView(
    navController: NavController
) {
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
                                text = "Products",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.darkGreenTxt)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        EditProductCard(
                            productName = "Tomatoes",
                            price = "200 din",
                            producer = "Proizvodjac",
                            rating = "4.5",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                        EditProductCard(
                            productName = "Plum",
                            price = "220 din",
                            producer = "Proizvodjac",
                            rating = "4.5",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        EditProductCard(
                            productName = "Potatoes",
                            price = "250 din",
                            producer = "Proizvodjac",
                            rating = "4.5",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                        EditProductCard(
                            productName = "Strawberry",
                            price = "300 din",
                            producer = "Proizvodjac",
                            rating = "4.5",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        EditProductCard(
                            productName = "Cabbage",
                            price = "180 din",
                            producer = "Proizvodjac",
                            rating = "4.6",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                        EditProductCard(
                            productName = "Pepper",
                            price = "260 din",
                            producer = "Proizvodjac",
                            rating = "4.4",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        EditProductCard(
                            productName = "Onion",
                            price = "120 din",
                            producer = "Proizvodjac",
                            rating = "4.7",
                            imageProduct = R.drawable.basket,
                            imageProducer = R.drawable.user,
                            onEdit = { navController.navigate(MainRoutes.ProductEdit) },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
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
    @DrawableRes imageProduct: Int,
    @DrawableRes imageProducer: Int,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(
            6.dp,
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
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(imageProduct),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(imageProducer),
                        contentDescription = "Producer",
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                    )
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

@Preview(showBackground = true)
@Composable
fun EditProductViewPreview() {
    EditProductView(rememberNavController())
}
