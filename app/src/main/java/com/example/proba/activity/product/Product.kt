package com.example.proba.activity.product

import com.example.proba.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.AsyncImage

@Composable
fun ProductView(
    productName: String,
    price: Double,
    producer: String,
    producerReview: Double?,
    city: String,
    imageUrl: String,
    producerImageUrl: String? = null,
    isFavorite: Boolean,
    onProductClick: () -> Unit,
    onProducerClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(if (isFavorite) 1.2f else 1f)

    Card(
        onClick = onProductClick,
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
                    model = imageUrl,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = productName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                        Text(
                            text = String.format("%.2f RSD", price),
                            fontSize = 13.sp,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }

                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                if (isFavorite) R.drawable.heart else R.drawable.favorite
                            ),
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .size(if (isFavorite) 26.dp else 30.dp)
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onProducerClick,
                        modifier = Modifier.size(30.dp)
                    ) {
                        if (producerImageUrl != null) {
                            AsyncImage(
                                model = producerImageUrl,
                                contentDescription = "Producer",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.user),
                                contentDescription = "Producer"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = producer,
                            fontSize = 13.sp,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                        Text(
                            text = city,
                            fontSize = 12.sp,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }

                    if (producerReview != null) {
                        Image(
                            painter = painterResource(R.drawable.star),
                            contentDescription = "Star",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "$producerReview",
                            fontSize = 12.sp,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductViewPreview() {
    ProductView(
        productName = "Jabuke",
        price = 120.0,
        producer = "Marko",
        producerReview = 4.5,
        city = "Novi Sad",
        imageUrl = "",
        isFavorite = false,
        onFavoriteClick = {},
        onProductClick = {},
        onProducerClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
